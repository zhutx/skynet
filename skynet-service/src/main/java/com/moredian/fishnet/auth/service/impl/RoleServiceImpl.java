package com.moredian.fishnet.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.domain.Perm;
import com.moredian.fishnet.auth.domain.Role;
import com.moredian.fishnet.auth.manager.OperRoleManager;
import com.moredian.fishnet.auth.manager.PermManager;
import com.moredian.fishnet.auth.manager.RoleManager;
import com.moredian.fishnet.auth.manager.RolePermManager;
import com.moredian.fishnet.auth.model.RoleInfo;
import com.moredian.fishnet.auth.model.SimplePermInfo;
import com.moredian.fishnet.auth.model.SimpleRoleInfo;
import com.moredian.fishnet.auth.request.RoleAddRequest;
import com.moredian.fishnet.auth.request.RoleQueryRequest;
import com.moredian.fishnet.auth.request.RoleUpdateRequest;
import com.moredian.fishnet.auth.request.SimplePermQueryRequest;
import com.moredian.fishnet.auth.service.RoleService;

@SI
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private PermManager permManager;
	@Autowired
	private RolePermManager rolePermManager;
	@Autowired
	private OperRoleManager operRoleManager;

	@Override
	public ServiceResponse<Long> addRole(RoleAddRequest request) {
		Long roleId = roleManager.addRole(request);
		return new ServiceResponse<Long>(true, null, roleId);
	}

	@Override
	public ServiceResponse<Boolean> updateRole(RoleUpdateRequest request) {
		boolean result = roleManager.updateRole(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteRole(Long roleId, Long orgId) {
		boolean result = roleManager.deleteRole(roleId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	private RoleInfo roleToRoleInfo(Role role) {
		if(role == null) return null;
		return BeanUtils.copyProperties(RoleInfo.class, role);
	}
	
	private List<RoleInfo> roleListToRoleInfoList(List<Role> roleList) {
		List<RoleInfo> roleInfoList = new ArrayList<>();
		if(roleList == null) return roleInfoList;
		
		for(Role role : roleList) {
			RoleInfo roleInfo = this.roleToRoleInfo(role);
			
			List<Long> permIds = rolePermManager.findPermIdByRoleId(role.getRoleId());
			
			roleInfo.setPermIds(permIds);
			roleInfoList.add(roleInfo);
		}
		
		return roleInfoList;
	}
	
	private SimplePermInfo permToSimplePermInfo(Perm perm, List<Long> myPermIdList) {
		SimplePermInfo simplePermInfo = new SimplePermInfo();
		if(myPermIdList.contains(perm.getPermId())){
			simplePermInfo.setChecked(true);
		} else {
			simplePermInfo.setChecked(false);
		}
		if(perm.getChildrenSize() > 0){
			simplePermInfo.setHasChild(true);
		} else {
			simplePermInfo.setHasChild(false);
		}
		simplePermInfo.setPermId(perm.getPermId());
		simplePermInfo.setPermName(perm.getPermName());
		return simplePermInfo;
	}

	@Override
	public List<RoleInfo> findAllRole(Long orgId) {
		List<Role> roleList = roleManager.findAllRole(orgId);
		return roleListToRoleInfoList(roleList);
	}
	
	private List<SimplePermInfo> buildSimplePermInfoList(List<Perm> allPermList, List<Long> myPermIdList) {
		List<SimplePermInfo> simplePermInfoList = new ArrayList<>();
		if(allPermList == null) return simplePermInfoList;
		
		for(Perm perm : allPermList) {
			simplePermInfoList.add(permToSimplePermInfo(perm, myPermIdList));
		}
		return simplePermInfoList;
	}
	
	@Override
	public List<SimplePermInfo> querySimplePerm(SimplePermQueryRequest request) {
		List<Perm> allPermList = permManager.findPerm(request.getModuleType(), request.getParentPermId());
		List<Long> myPermIdList = rolePermManager.findPermIdByRoleId(request.getRoleId());
		return buildSimplePermInfoList(allPermList, myPermIdList);
	}

	@Override
	public RoleInfo getRoleById(Long roleId, Long orgId) {
		Role role = roleManager.getRoleById(roleId, orgId);
		return this.roleToRoleInfo(role);
	}
	
	private List<SimpleRoleInfo> roleListToSimpleRoleInfo(List<Role> roleList, List<Long> myRoleIds) {
		List<SimpleRoleInfo> simpleRoleInfoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(roleList)) return simpleRoleInfoList;
		
		for(Role role : roleList) {
			SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
			simpleRoleInfo.setRoleId(role.getRoleId());
			simpleRoleInfo.setRoleName(role.getRoleName());
			if(myRoleIds.contains(role.getRoleId())) {
				simpleRoleInfo.setChecked(true);
			} else {
				simpleRoleInfo.setChecked(false);
			}
			simpleRoleInfoList.add(simpleRoleInfo);
		}
		return simpleRoleInfoList;
	}

	@Override
	public List<SimpleRoleInfo> findFullRoleByOper(Long orgId, Long operId) {
		List<Role> roleList = roleManager.findAllRole(orgId);
		List<Long> myRoleIds = operRoleManager.findRoleIdByOperId(operId);
		return this.roleListToSimpleRoleInfo(roleList, myRoleIds);
	}

	@Override
	public List<Long> findRoleIdByOper(Long operId) {
		return operRoleManager.findRoleIdByOperId(operId);
	}

	@Override
	public List<RoleInfo> findRole(RoleQueryRequest request) {
		List<Role> roleList = roleManager.findRole(request);
		return roleListToRoleInfoList(roleList);
	}

	@Override
	public RoleInfo getAdminRole(Long orgId) {
		Role role = roleManager.getAdminRole(orgId);
		return this.roleToRoleInfo(role);
	}

}
