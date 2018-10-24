package com.log.web.entity;

import java.io.Serializable;

public class Logs implements Serializable{
	private static final long serialVersionUID = 1056414459475154474L;
	private long id;
	// 日志日期
	private String log_date;
	// 日志时间
	private String log_time;
	// 日志内容
	private String content;
	// 项目名称
	private String service_name;
	// 日志类型
	private String log_type;
	// 日志调用包
	private String pack;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
}
