package com.moredian.skynet.device.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper {

	int insert(@Param("model") Map<String, Object> model);
	
	Map<String,Object> getByCondition(@Param("condition") Map<String, Object> condition);
	
	int updateByCondition(@Param("model") Map<String, Object> model, @Param("condition") Map<String, Object> condition);
	
	int deleteByCondition(@Param("condition") Map<String, Object> condition);
	
	public List<Map<String,Object>> findByCondition(@Param("condition") Map<String, Object> condition);
	
	int getCountByCondition(@Param("condition") Map<String, Object> condition);
}
