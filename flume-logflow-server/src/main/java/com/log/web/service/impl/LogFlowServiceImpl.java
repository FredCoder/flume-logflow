package com.log.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.log.web.entity.LogFlow;
import com.log.web.mapper.LogFlowMapper;
import com.log.web.service.ILogFlowService;
import com.log.web.utils.PageResult;

@Service
@Transactional(readOnly = true)
public class LogFlowServiceImpl implements ILogFlowService {
	
	@Autowired
	private LogFlowMapper LogFlowMapper;

	@Override
	public PageResult<LogFlow> queryPage(int pageNum, int pageSize, LogFlow param) {
		PageHelper.startPage(pageNum, pageSize);
		Page<LogFlow> page = (Page<LogFlow>) LogFlowMapper.queryAllContract(param);
		return new PageResult<LogFlow>(page.getTotal(), page.getResult());
	}

	@Override
	public LogFlow queryLogContent(long id) {
		return LogFlowMapper.queryLogContent(id);
	}

	@Override
	public List<String> getServiceNames() {
		return LogFlowMapper.getServiceNames();
	}

	@Override
	public List<String> getLogTypes() {
		return LogFlowMapper.getLogTypes();
	}
}
