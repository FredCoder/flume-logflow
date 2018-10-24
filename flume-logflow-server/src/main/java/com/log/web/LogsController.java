package com.log.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.log.web.entity.Logs;
import com.log.web.service.LogsService;

@RestController
public class LogsController {
	
	@Autowired
	public LogsService logsService;
	
	@RequestMapping("/selectAll")
	public List<Logs> selectAll() {
        return logsService.selectAll();
    }
	
	@RequestMapping(value = "/selectByPro")
    public List<Logs> selectByProject(String project) {
        return logsService.selectByPro(project);
    }
	
	@RequestMapping(value = "/selectByType")
    public List<Logs> selectByType(String type) {
        return logsService.selectByType(type);
    }
	
	@RequestMapping(value = "/selectByProAndType")
    public List<Logs> selectByProAndType(Logs logs) {
        return logsService.selectByProAndType(logs.getService_name(), logs.getLog_type());
    }
	
	@RequestMapping(value = "/deleteByPro")
    public Long deleteByPro(String project) {
        return logsService.deleteByPro(project);
    }
	
	@RequestMapping(value = "/deleteAll")
    public Long deleteAll() {
        return logsService.deleteAll();
    }
}
