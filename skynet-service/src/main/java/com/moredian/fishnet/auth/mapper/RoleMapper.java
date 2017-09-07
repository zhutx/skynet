package com.moredian.fishnet.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.auth.domain.Role;
import com.moredian.fishnet.auth.domain.RoleQueryCondition;

@Mapper
public interface RoleMapper {
	
	int insert(Role role);
	
	Role loadByRoleName(@Param("roleName")String roleName, @Param("orgId")Long orgId);
	
	Role load(@Param("roleId")Long roleId, @Param("orgId")Long orgId);
	
	int update(Role role);
	
	int deleteById(Long roleId);
	
	List<Role> findAll(Long orgId);
	
	List<Role> findByCondition(RoleQueryCondition condition);
	
}
