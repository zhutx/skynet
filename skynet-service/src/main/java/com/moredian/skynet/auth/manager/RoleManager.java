package com.moredian.skynet.auth.manager;

import java.util.List;

import com.moredian.skynet.auth.domain.Role;
import com.moredian.skynet.auth.request.RoleAddRequest;
import com.moredian.skynet.auth.request.RoleQueryRequest;
import com.moredian.skynet.auth.request.RoleUpdateRequest;

public interface RoleManager {
	
	Long addRole(RoleAddRequest request);
	
	boolean updateRole(RoleUpdateRequest request);
	
	boolean deleteRole(Long roleId, Long orgId);
	
	List<Role> findAllRole(Long orgId);
	
	Role getRoleById(Long roleId, Long orgId);
	
	List<Role> findRole(RoleQueryRequest request);
	
	Role getRoleByName(Long orgId, String roleName);
	
	Role getAdminRole(Long orgId);
	
}
