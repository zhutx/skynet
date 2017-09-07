package com.moredian.fishnet.auth.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.auth.model.RoleInfo;
import com.moredian.fishnet.auth.model.SimplePermInfo;
import com.moredian.fishnet.auth.model.SimpleRoleInfo;
import com.moredian.fishnet.auth.request.RoleAddRequest;
import com.moredian.fishnet.auth.request.RoleQueryRequest;
import com.moredian.fishnet.auth.request.RoleUpdateRequest;
import com.moredian.fishnet.auth.request.SimplePermQueryRequest;

/**
 * 角色服务
 * @author zhutx
 *
 */
public interface RoleService {
	
	ServiceResponse<Long> addRole(RoleAddRequest request);
	
	ServiceResponse<Boolean> updateRole(RoleUpdateRequest request);
	
	ServiceResponse<Boolean> deleteRole(Long roleId, Long orgId);
	
	List<SimplePermInfo> querySimplePerm(SimplePermQueryRequest request);
	
	List<RoleInfo> findAllRole(Long orgId);
	
	List<RoleInfo> findRole(RoleQueryRequest request);
	
	List<SimpleRoleInfo> findFullRoleByOper(Long orgId, Long operId);
	
	RoleInfo getRoleById(Long roleId, Long orgId);
	
	List<Long> findRoleIdByOper(Long operId);
	
	RoleInfo getAdminRole(Long orgId);

}
