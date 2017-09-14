package com.moredian.skynet.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.org.domain.Area;

@Mapper
public interface AreaMapper {
	
	List<Area> findByParent(Integer parentCode);
	
	List<Integer> findChildrenCode(Integer parentCode);
	
	Area loadByCode(Integer areaCode);
	
	List<Area> findByPrefix(@Param("prefixCode")String prefixCode, @Param("areaLevel")Integer areaLevel);
	
	int updateExtendsInfo(@Param("areaId")Long areaId, @Param("extendsInfo")String extendsInfo);

}
