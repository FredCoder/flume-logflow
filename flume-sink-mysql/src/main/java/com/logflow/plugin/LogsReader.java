package com.logflow.plugin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LogsReader {

	private static LogsReader uniqueInstance = null;

	private LogsReader() {
	}

	public static LogsReader getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new LogsReader();
		}
		return uniqueInstance;
	}

	/**
	 * 
	 * @param log
	 * @return
	 */
	public Logs readBody(String log) {
		Logs logs = new Logs();
		try {
			String after = log.substring(0, log.indexOf("["));
			List<String> array = new LinkedList<String>(Arrays.asList(after.split(" ")));
			ListIterator<String> iterator = array.listIterator();
			while (iterator.hasNext()) {
				String str = iterator.next();
				if (str.trim().length() == 0) {
					iterator.remove();
				} else {
					iterator.set(str.trim());
				}
			}
			logs.setLogDate(array.get(0));
			logs.setLogTime(array.get(1));
			logs.setLogType(array.get(2));
			String thread = log.substring(log.indexOf("[") + 1, log.indexOf("]")).trim();
			log = log.substring(log.indexOf("]") + 1);
			logs.setPack(log.substring(0, log.indexOf(":")).trim());
			logs.setContent(log.substring(log.indexOf(":") + 1, log.length()).trim());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return logs;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public String readHead(String file) {
		String projectName = null;
		try {
			projectName = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
			return projectName;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectName;
	}
}
