package com.moredian.fishnet.auth.manager;

import java.util.List;

import com.moredian.fishnet.auth.domain.Role;
import com.moredian.fishnet.auth.request.RoleAddRequest;
import com.moredian.fishnet.auth.request.RoleQueryRequest;
import com.moredian.fishnet.auth.request.RoleUpdateRequest;

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
