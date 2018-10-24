package com.log.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.log.web.entity.Logs;
import com.log.web.mapper.LogsMapper;


@Service 
public class LogsServiceImpl implements LogsService{

	@Autowired
	LogsMapper logs;
	
	@Override
	public Long deleteAll() {
		return logs.deleteAll();
	}

	@Override
	public Long deleteByPro(String project) {
		return logs.deleteByPro(project);
	}

	@Override
	public List<Logs> selectAll() {
		return logs.selectAll();
	}

	@Override
	public List<Logs> selectByPro(String project) {
		return logs.selectByProject(project);
	}

	@Override
	public List<Logs> selectByType(String type) {
		return logs.selectByType(type);
	}

	@Override
	public List<Logs> selectByProAndType(String project, String type) {
		return logs.selectByProAndType(project, type);
	}
}
