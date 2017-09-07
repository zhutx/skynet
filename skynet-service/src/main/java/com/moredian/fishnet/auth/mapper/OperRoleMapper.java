package com.moredian.fishnet.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.auth.domain.OperRole;

@Mapper
public interface OperRoleMapper {
	
	int getCountByRoleId(Long roleId);
	
	List<Long> findRoleIdByOperId(Long operId);
	
	int insert(OperRole operRole);
	
	int delete(@Param("operId")Long operId, @Param("roleId")Long roleId);
	
	int deleteByOper(Long operId);

}
