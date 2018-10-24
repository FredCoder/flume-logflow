package com.logflow.plugin;

/**
 * 日志信息
 * @author Fren
 *
 */
public class Logs {
	
	// 日志日期
	private String logDate;
	// 日志时间
	private String logTime;
	// 日志内容
	private String content;
	// 服务名称
	private String serviceName;
	// 日志类型
	private String logType;
	// 日志调用包
	private String pack;
	
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
}
