package com.log.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.log.web.entity.LogFlow;

/**
 * 操作日志映射类
 * 
 * @author plzhang
 * @version 1.0
 * @created 05-6月-2018 10:22:16
 */
@Mapper
public interface LogFlowMapper {

	@Select({ "<script>", "SELECT t.id, t.log_date, t.service_name, t.log_type, pack, collect_time FROM log_flow t",
			"WHERE 1=1",
			// 日志类型（级别）
			"<when test='logType!=null'>AND t.log_type = #{logType}</when>",
			// 操作状态
			"<when test='serviceName!=null'>AND t.service_name = #{serviceName}</when>",
			// 开始时间
			" <when test = 'startTime != null'>", " AND t.log_date &gt;= #{startTime,jdbcType=DATE} ", " </when>",
			// 结束时间
			" <when test = 'endTime != null'>", " AND t.log_date &lt;= #{endTime,jdbcType=DATE} ", " </when>",
			"ORDER BY t.log_date DESC", "</script>" })
	@Results({ @Result(id = true, column = "id", property = "id"), 
		@Result(column = "log_date", property = "logDate"),
			@Result(column = "service_name", property = "serviceName"),
			@Result(column = "log_type", property = "logType"),
			@Result(column = "collect_time", property = "collectTime") })
	public List<LogFlow> queryAllContract(LogFlow log);

	@Select("select t.id, t.content from log_flow t where t.id = #{id}")
	public LogFlow queryLogContent(long id);
	
	
	@Select("select t.service_name from log_flow t group by t.service_name")
	public List<String> getServiceNames();

	@Select("select t.log_type from log_flow t group by t.log_type")
	public List<String> getLogTypes();
}
