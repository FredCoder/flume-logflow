package com.log.web.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.log.web.entity.Logs;

public interface LogsService {
	
	public Long deleteAll();
	
	public Long deleteByPro(String project);
	
	public List<Logs> selectAll();
	
	public List<Logs> selectByPro(String project);
	
	List<Logs> selectByType(String type);
	
	List<Logs> selectByProAndType(String project, String type);
}
