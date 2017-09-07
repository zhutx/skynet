package com.moredian.fishnet.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.auth.domain.RolePerm;

@Mapper
public interface RolePermMapper {
	
	int getCountByPermId(Long permId);
	
	int insert(RolePerm rolePerm);
	
	List<Long> findPermIdByRoleId(Long roleId);
	
	int delete(@Param("roleId")Long roleId, @Param("permId")Long permId);
	
	int deleteByRoleId(Long roleId);
	
	List<Long> findPermIdByRoleIds(@Param("roleIdList")List<Long> roleIdList);

}