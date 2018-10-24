package com.log.web;

import java.util.Vector;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.log.web.entity.LogFlow;
import com.log.web.service.ILogFlowService;
import com.log.web.utils.PageResult;


@RestController
@RequestMapping("/logflow/")
public class LogFlowController {
	private static Logger logger = LoggerFactory.getLogger(LogFlowController.class);
	
	private static Vector<String> serviceNames = new Vector<String>();
	
	private static Vector<String> logTypes = new Vector<String>();

	@Resource(name = "logFlowServiceImpl")
	private ILogFlowService logFlowService;

	@RequestMapping(value = "/queryPage", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public PageResult<LogFlow> queryPage(int page, int rows, LogFlow vo) {
		return logFlowService.queryPage(page, rows, vo);
	}

	@RequestMapping(value = "/queryLogContent", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public LogFlow queryLogContent(long id) {
		return logFlowService.queryLogContent(id);
	}
	
	@RequestMapping(value = "/getServiceNames", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Vector<String> getServiceNames(){
		if (serviceNames.size() == 0){
			serviceNames.addAll(logFlowService.getServiceNames());
		}
		return serviceNames;
	}
	
	@RequestMapping(value = "/getLogTypes", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Vector<String> getLogTypes(){
		if (logTypes.size() == 0){
			logTypes.addAll(logFlowService.getLogTypes());
		}
		return logTypes;
	}
}
/**
 * 遗留问题：
 * 1.目前不能动态添加服务。
 * 2.目前不能动态添加日志级别。
 */
