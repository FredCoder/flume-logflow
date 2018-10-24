package com.log.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.log.web.entity.Logs;

@Mapper
public interface LogsMapper {
	
	@Delete("delete from logs_table")
    Long deleteAll();
	
	@Delete("delete from logs_table where project=#{project}")
    Long deleteByPro(@Param("project") String project);
	
	@Select("select id, log_date, log_time, content, project, log_type, pack from logs_table")
    List<Logs> selectAll();
	
	@Select("select id, log_date, log_time, content, project, log_type, pack from logs_table where project=#{project} limit 1000")
    List<Logs> selectByProject(@Param("project") String project);
	
	@Select("select id, log_date, log_time, content, project, log_type, pack from logs_table where log_type=#{type} limit 1000")
    List<Logs> selectByType(@Param("type") String type);
	
	@Select("select id, log_date, log_time, content, project, log_type, pack from logs_table where  project=#{project} and log_type=#{type} limit 1000")
    List<Logs> selectByProAndType(@Param("project") String project, @Param("type") String type);
}
