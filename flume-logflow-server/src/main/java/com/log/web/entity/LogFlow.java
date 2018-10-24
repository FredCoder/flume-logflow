package com.log.web.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogFlow  implements Serializable {
	private static final long serialVersionUID = -5532602661677075598L;

	private long id;

	private Date logDate;

	private String content;

	private String serviceName;

	private String logType;

	private String pack;
	
	private Date collectTime;

	private Date startTime;
	
	private Date endTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLogDate() {
		return logDate;
	}
	
	public String getLogDateStr(){
		if (logDate != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(logDate);
		}
		return "";
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
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

	public Date getCollectTime() {
		return collectTime;
	}
	
	public String getCollectTimeStr() {
		if (collectTime != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(collectTime);
		}
		return "";
	}


	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
