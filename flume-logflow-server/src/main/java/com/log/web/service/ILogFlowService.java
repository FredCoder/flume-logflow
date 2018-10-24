package com.log.web.service;

import java.util.List;

import com.log.web.entity.LogFlow;
import com.log.web.utils.PageResult;

public interface ILogFlowService {
	/**
	 * 分页查询日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public PageResult<LogFlow> queryPage(int pageNum, int pageSize, LogFlow param);
	
	public LogFlow queryLogContent(long id);
	
	public List<String> getServiceNames();
	
	public List<String> getLogTypes();
}
