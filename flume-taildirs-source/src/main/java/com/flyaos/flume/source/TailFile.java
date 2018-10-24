
package com.flyaos.flume.source;

import com.google.common.collect.Lists;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 待 tailf 文件定义
public class TailFile {
    private static final Logger logger = LoggerFactory.getLogger(TailFile.class);

    // NewLine
    private static final byte BYTE_NL = (byte) 10;
    // 回车
    private static final byte BYTE_CR = (byte) 13;

    public static final String sep = "\n";

    // 次读取的 buffer 大小
    private static final int BUFFER_SIZE = 8192;
    // 需要继续读数据到 buffer
    private static final int NEED_READING = -1;

    // 随机读
    private RandomAccessFile raf;
    private final String path;
    private final long inode;
    // 当前的写文件偏移量
    private long pos;
    // 最近更新时间
    private long lastUpdated;
    // 是否需要tail 当文件未发生变化设为 false 默认true 例如新文件
    private boolean needTail;

    private long lineNumber;
    private final Map<String, String> headers;
    private byte[] buffer;
    private byte[] oldBuffer;
    private int bufferPos;
    private long lineReadPos;
    private String linePrefix;

    public TailFile(File file, Map<String, String> headers, long inode, long pos, String linePrefix)
            throws IOException {
        this.raf = new RandomAccessFile(file, "r");
        if (pos > 0) {
            raf.seek(pos);
            lineReadPos = pos;
        }
        this.path = file.getAbsolutePath();
        this.inode = inode;
        this.pos = pos;
        this.lastUpdated = 0L;
        this.needTail = true;
        this.headers = headers;
        this.oldBuffer = new byte[0];
        this.bufferPos = NEED_READING;
        this.lineNumber = 0L;
        this.linePrefix = linePrefix;
    }

    public TailFile(File file, Map<String, String> headers, long inode, long pos, long lineNumber, String linePrefix)
            throws IOException {
        this.raf = new RandomAccessFile(file, "r");
        if (pos > 0) {
            raf.seek(pos);
            lineReadPos = pos;
        }

        this.lineNumber = 0L;
        if (lineNumber > 0) {
            setLineNumber(lineNumber);
        }

        this.path = file.getAbsolutePath();
        this.inode = inode;
        this.pos = pos;
        this.lastUpdated = 0L;
        this.needTail = true;
        this.headers = headers;
        this.oldBuffer = new byte[0];
        this.bufferPos = NEED_READING;
        this.linePrefix = linePrefix;
    }

    public RandomAccessFile getRaf() {
        return raf;
    }

    public String getPath() {
        return path;
    }

    public long getInode() {
        return inode;
    }

    public long getPos() {
        return pos;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public boolean needTail() {
        return needTail;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public long getLineReadPos() {
        return lineReadPos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setNeedTail(boolean needTail) {
        this.needTail = needTail;
    }

    public void setLineReadPos(long lineReadPos) {
        this.lineReadPos = lineReadPos;
    }

    public boolean updatePos(String path, long inode, long pos) throws IOException {
        if (this.inode == inode && this.path.equals(path)) {
            setPos(pos);
            updateFilePos(pos);
            logger.info("Updated position, file: " + path + ", inode: " + inode + ", pos: " + pos);
            return true;
        }
        return false;
    }

    public boolean updatePos(String path, long inode, long pos, long lineNumber) throws IOException {
        if (this.inode == inode && this.path.equals(path)) {
            setPos(pos);
            updateFilePos(pos);
            setLineNumber(lineNumber);
            logger.info("Updated position, file: " + path + ", inode: " + inode + ", pos: " + pos + ", lineNumber: " + lineNumber);
            return true;
        }
        return false;
    }

    public void updateFilePos(long pos) throws IOException {
        raf.seek(pos);
        lineReadPos = pos;
        bufferPos = NEED_READING;
        oldBuffer = new byte[0];
    }


    public List<Event> readEvents(int numEvents, boolean backoffWithoutNL,
                                  boolean addByteOffset) throws IOException {
        List<Event> events = Lists.newLinkedList();
        for (int i = 0; i < numEvents; i++) {
            Event event = readEvent(backoffWithoutNL, addByteOffset);
            if (event == null) {
                break;
            }

            this.lineNumber++;
            event.getHeaders().put("lineNumber", String.valueOf(this.lineNumber));
            events.add(event);
        }
        return events;
    }

    private Event readEvent(boolean backoffWithoutNL, boolean addByteOffset) throws IOException {
        Long posTmp = getLineReadPos();
        LineResult line = readLine();
        if (line == null) {
            return null;
        }

        // 如果 line result 不包含行分隔符或者结束符, back off 不返回 event
        if (backoffWithoutNL && !line.lineSepInclude) {
            logger.info("Backing off in file without newline: "
                    + path + ", inode: " + inode + ", pos: " + raf.getFilePointer());
            // 读取之前的 position
            updateFilePos(posTmp);
            return null;
        }

        // 处理 java error stack 这类格式
        // 备份 之前的 line pos
        if (this.linePrefix != null && !this.linePrefix.equals("null")) {
            long tm = getLineReadPos();

            // 读取下一行
            LineResult nextLine1 = readLine();
            if (nextLine1 == null || (backoffWithoutNL && !nextLine1.lineSepInclude) || nextLine1.isNewLine()) {
                updateFilePos(tm);
            } else {
                // next line != null && is stack
                // 向下搜索
                List<LineResult> list = new ArrayList<>();
                list.add(nextLine1);
                while (true) {
                    long currPosTmp = getLineReadPos();
                    LineResult nextLine2 = readLine();

                    // 遇到了 stack 但是没能读取到新的一行 返回 null, 保证每次读取的是完整的 stack
                    if (nextLine2 == null || (backoffWithoutNL && !nextLine2.lineSepInclude)) {
                        updateFilePos(posTmp);
                        return null;
                    }

                    // 直到找到新的一行
                    if (nextLine2.isNewLine()) {
                        updateFilePos(currPosTmp);
                        for (LineResult lr : list) {
                            line.append(lr);
                        }
                        break;
                    }

                    list.add(nextLine2);
                }
            }
        }

        Event event = EventBuilder.withBody(line.line);
        if (addByteOffset) {
            event.getHeaders().put(TaildirSourceConfigurationConstants.BYTE_OFFSET_HEADER_KEY, posTmp.toString());
        }
        return event;
    }


    private void readFile() throws IOException {
        if ((raf.length() - raf.getFilePointer()) < BUFFER_SIZE) {
            buffer = new byte[(int) (raf.length() - raf.getFilePointer())];
        } else {
            buffer = new byte[BUFFER_SIZE];
        }
        raf.read(buffer, 0, buffer.length);
        bufferPos = 0;
    }

    private byte[] concatByteArrays(byte[] a, int startIdxA, int lenA,
                                    byte[] b, int startIdxB, int lenB) {
        byte[] c = new byte[lenA + lenB];
        System.arraycopy(a, startIdxA, c, 0, lenA);
        System.arraycopy(b, startIdxB, c, lenA, lenB);
        return c;
    }

    // core
    public LineResult readLine() throws IOException {
        LineResult lineResult = null;
        while (true) {
            // 当没有遇到行换行符时 需要继续 read file
            if (bufferPos == NEED_READING) {
                if (raf.getFilePointer() < raf.length()) {
                    // raf 读取一个 buffer
                    readFile();
                } else {
                    // 文件读取完毕
                    if (oldBuffer.length > 0) {
                        // 标记不包含行分隔符
                        lineResult = new LineResult(false, oldBuffer);
                        oldBuffer = new byte[0];
                        // 当前读取位置
                        setLineReadPos(lineReadPos + lineResult.line.length);
                    }
                    break;
                }
            }

            // 遍历读取的 buffer
            for (int i = bufferPos; i < buffer.length; i++) {
                // 遇到行分隔符, 构造 line result 返回, old buffer 清空
                if (buffer[i] == BYTE_NL) {

                    int oldLen = oldBuffer.length;
                    // Don't copy last byte(NEW_LINE)
                    int lineLen = i - bufferPos;
                    // For windows, check for CR
                    // Dos based : 	[CR][LF]
                    if (i > 0 && buffer[i - 1] == BYTE_CR) {
                        lineLen -= 1;
                    } else if (oldBuffer.length > 0 && oldBuffer[oldBuffer.length - 1] == BYTE_CR) {
                        oldLen -= 1;
                    }


                    lineResult = new LineResult(true,
                            concatByteArrays(oldBuffer, 0, oldLen, buffer, bufferPos, lineLen));
                    setLineReadPos(lineReadPos + (oldBuffer.length + (i - bufferPos + 1)));
                    oldBuffer = new byte[0];
                    if (i + 1 < buffer.length) {
                        bufferPos = i + 1;
                    } else {
                        //  buffer 读完了
                        bufferPos = NEED_READING;
                    }
                    break;
                }
            }

            if (lineResult != null) {
                break;
            }

            // buffer 读完还没有遇到行分隔符, 继续读取 buffer
            // NEW_LINE not showed up at the end of the buffer
            oldBuffer = concatByteArrays(oldBuffer, 0, oldBuffer.length,
                    buffer, bufferPos, buffer.length - bufferPos);
            bufferPos = NEED_READING;
        }
        return lineResult;
    }

    public void close() {
        try {
            raf.close();
            raf = null;
            long now = System.currentTimeMillis();
            setLastUpdated(now);
        } catch (IOException e) {
            logger.error("Failed closing file: " + path + ", inode: " + inode, e);
        }
    }


    private class LineResult {
        // 是否包含行分隔符
        final boolean lineSepInclude;
        // data of line
        byte[] line;

        public LineResult(boolean lineSepInclude, byte[] line) {
            super();
            this.lineSepInclude = lineSepInclude;
            this.line = line;
        }

        public void setLine(byte[] li) {
            this.line = li;
        }


        // todo match
        boolean isStack() {
            return !new String(line).matches(linePrefix);
        }

        // is new line
        boolean isNewLine() {
            return !isStack();
        }

        void append(LineResult lr) {
            byte[] com = new byte[this.line.length + lr.line.length + sep.getBytes().length];
            System.arraycopy(line, 0, com, 0, line.length);
            System.arraycopy(sep.getBytes(), 0, com, this.line.length, sep.getBytes().length);
            System.arraycopy(lr.line, 0, com, this.line.length + sep.getBytes().length, lr.line.length);
            setLine(com);
        }
    }
}
