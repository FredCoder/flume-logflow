package com.logflow.plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class MysqlSink extends AbstractSink implements Configurable {

	private Logger LOG = LoggerFactory.getLogger(MysqlSink.class);
	private String hostname;
	private String port;
	private String databaseName;
	private String tableName;
	private String user;
	private String password;
	private boolean useSSL; 
	private String characterEncoding;
	
	private PreparedStatement preparedStatement;
	private Connection conn;
	private int batchSize; // 每次提交的批次大小

	public MysqlSink() {
		LOG.info("MySqlSink start...");
	}

	/** 实现Configurable接口中的方法：可获取配置文件中的属性 */
	public void configure(Context context) {
		hostname = context.getString("hostname");
		Preconditions.checkNotNull(hostname, "hostname must be set!!");
		port = context.getString("port");
		Preconditions.checkNotNull(port, "port must be set!!");
		databaseName = context.getString("databaseName");
		Preconditions.checkNotNull(databaseName, "databaseName must be set!!");
		tableName = context.getString("tableName");
		Preconditions.checkNotNull(tableName, "tableName must be set!!");
		user = context.getString("user");
		Preconditions.checkNotNull(user, "user must be set!!");
		password = context.getString("password");
		Preconditions.checkNotNull(password, "password must be set!!");
		batchSize = context.getInteger("batchSize", 100); // 设置了batchSize的默认值
		Preconditions.checkNotNull(batchSize > 0, "batchSize must be a positive number!!");
		useSSL = context.getBoolean("useSSL", false); // 设置了batchSize的默认值
		Preconditions.checkNotNull(useSSL, "useSSL must be a set!!");
		characterEncoding = context.getString("characterEncoding", "utf8");
		Preconditions.checkNotNull(characterEncoding.length() > 0, "characterEncoding must be set!!");
		
	}

	public Status process() throws EventDeliveryException {
		Status result = Status.READY;
		Channel channel = getChannel();
		Transaction transaction = channel.getTransaction();
		Event event;
		List<Logs> logsArray = Lists.newArrayList();
		String content;
		transaction.begin();
		try {
			/* event处理 */
			LogsReader lr = LogsReader.getInstance();
			for (int i = 0; i < batchSize; i++) {
				event = channel.take();
				if (event != null) {
					// 对事件进行处理
					String service = null;
					Logs logs = new Logs();
					// 处理 headers 获得 项目名称
					if (event.getHeaders() != null) {
						service = lr.readHead(event.getHeaders().get("file"));
					}
					//  处理 body 获得 日志信息
					if (event.getBody() != null) {
						content = new String(event.getBody());
						logs = lr.readBody(content);
					}
					logs.setServiceName(service);
					logsArray.add(logs);
				} else {
					result = Status.BACKOFF;
					break;
				}
			}
			/* jdbc提交 */
			if (logsArray.size() > 0) {
				preparedStatement.clearBatch();
				for (Logs temp : logsArray) {
					preparedStatement.setString(1, temp.getLogDate() + " " + temp.getLogTime());
					preparedStatement.setString(2, temp.getContent());
					preparedStatement.setString(3, temp.getServiceName());
					preparedStatement.setString(4, temp.getLogType());
					preparedStatement.setString(5, temp.getPack());
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				conn.commit();
			}
			transaction.commit();
		} catch (Exception e) {
			try {
				transaction.rollback();
			} catch (Exception e2) {
				LOG.error("Exception in rollback. Rollback might not have been.successful.", e2);
			}
			LOG.error("Failed to commit transaction.Transaction rolled back.", e);
			Throwables.propagate(e);
		} finally {
			transaction.close();
		}
		return result;
	}

	@Override
	public void start() {
		super.start();
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName + "?characterEncoding="
				+ characterEncoding + "&useSSL=" + String.valueOf(useSSL);
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // 创建一个Statement对象
			String sql = "INSERT INTO " + tableName
					+ " (log_date, content, service_name, log_type, pack, collect_time) VALUES (?,?,?,?,?,now())";
			preparedStatement = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void stop() {
		super.stop();
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}