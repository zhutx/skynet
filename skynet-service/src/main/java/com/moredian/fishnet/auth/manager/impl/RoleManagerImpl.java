package com.moredian.fishnet.auth.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.domain.Role;
import com.moredian.fishnet.auth.domain.RolePerm;
import com.moredian.fishnet.auth.domain.RoleQueryCondition;
import com.moredian.fishnet.auth.enums.AuthErrorCode;
import com.moredian.fishnet.auth.manager.RoleManager;
import com.moredian.fishnet.auth.mapper.OperRoleMapper;
import com.moredian.fishnet.auth.mapper.PermMapper;
import com.moredian.fishnet.auth.mapper.RoleMapper;
import com.moredian.fishnet.auth.mapper.RolePermMapper;
import com.moredian.fishnet.auth.request.RoleAddRequest;
import com.moredian.fishnet.auth.request.RoleQueryRequest;
import com.moredian.fishnet.auth.request.RoleUpdateRequest;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.xier.sesame.common.utils.BeanUtils;

@Service
public class RoleManagerImpl implements RoleManager {
	
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermMapper rolePermMapper;
	@Autowired
	private OperRoleMapper operRoleMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private PermMapper permMapper;
	
	private Role roleAddRequestToRole(RoleAddRequest request) {
		
		Role role = new Role();
		Long id = idgeneratorService.getNextIdByTypeName(Role.class.getName()).getData();
		role.setRoleId(id);
		role.setOrgId(request.getOrgId());
		role.setRoleName(request.getRoleName());
		role.setRoleDesc(request.getRoleDesc());
		return role;
	}
	
	private List<Long> findDeepChildrenId(Long parentId) {
		List<Long> permIdList = new ArrayList<>();
		List<Long> childrenIdList = permMapper.findChildrenId(parentId);
		if(CollectionUtils.isNotEmpty(childrenIdList)) {
			for(Long permId : childrenIdList) {
				permIdList.add(permId);
				permIdList.addAll(this.findDeepChildrenId(permId));
			}
		}
		return permIdList;
	}
	
	/**
	 * UI只选父权限的情况下，后端把子权限数据一并追加进来
	 * @param permIdList
	 * @return
	 */
	private Set<Long> appendChildren(List<Long> permIdList) {
		Set<Long> resultSet = new HashSet<>();
		if(CollectionUtils.isEmpty(permIdList)) return resultSet;
		
		for(Long permId : permIdList) {
			resultSet.add(permId);
			resultSet.addAll(findDeepChildrenId(permId));
		}
		
		return resultSet;
	}
	
	private void updateRolePerm(Long roleId, List<Long> newPermIds){
		
		List<Long> orgiPermIds = rolePermMapper.findPermIdByRoleId(roleId);
		if(orgiPermIds == null) orgiPermIds = new ArrayList<>();
		if(newPermIds == null) {
			newPermIds = new ArrayList<>();
		} else {
			Set<Long> permSet = appendChildren(newPermIds);
			newPermIds.clear();
			newPermIds.addAll(permSet);
		}
		
		// 复制一份，备用
		List<Long> orgiPermIds_clone = new ArrayList<>();
		orgiPermIds_clone.addAll(orgiPermIds);
		
		
		orgiPermIds.removeAll(newPermIds);// 定位删除的权限
		for(Long permId : orgiPermIds) {
			rolePermMapper.delete(roleId, permId);
		}

		newPermIds.removeAll(orgiPermIds_clone); //定位新增的权限
		for(Long permId : newPermIds) {
			RolePerm rolePerm = new RolePerm();
			Long id = idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.auth.RolePerm").getData();
			rolePerm.setRolePermId(id);
			rolePerm.setRoleId(roleId);
			rolePerm.setPermId(permId);
			rolePermMapper.insert(rolePerm);
		}
	}

	@Override
	@Transactional
	public Long addRole(RoleAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notBlank(request.getRoleName(), "roleName must not be blank");
		
		Role existRole = roleMapper.loadByRoleName(request.getRoleName(), request.getOrgId());
		if(existRole != null) ExceptionUtils.throwException(AuthErrorCode.ROLE_EXIST, String.format(AuthErrorCode.ROLE_EXIST.getMessage(), request.getRoleName()));
		
		Role role = this.roleAddRequestToRole(request);
		roleMapper.insert(role);
		
		this.updateRolePerm(role.getRoleId(), request.getPermIds());
		
		return role.getRoleId();
	}
	
	private Role roleUpdateRequestToRole(RoleUpdateRequest request) {
		
		Role role = roleMapper.load(request.getRoleId(), request.getOrgId());
		
		if(request.getRoleName() != null && !role.getRoleName().equals(request.getRoleName())) {
			Role existRole = roleMapper.loadByRoleName(request.getRoleName(), request.getOrgId());
			if(existRole != null) ExceptionUtils.throwException(AuthErrorCode.ROLE_EXIST, String.format(AuthErrorCode.ROLE_EXIST.getMessage(), request.getRoleName()));
		}
		
		role.setRoleName(request.getRoleName());
		role.setRoleDesc(request.getRoleDesc());
		
		return role;
	}

	@Override
	@Transactional
	public boolean updateRole(RoleUpdateRequest request) {
		
		BizAssert.notNull(request.getRoleId(), "roleId must not be null");
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		Role role = this.roleUpdateRequestToRole(request);
		roleMapper.update(role);
		
		this.updateRolePerm(role.getRoleId(), request.getPermIds());
		
		return true;
		
	}

	@Override
	@Transactional
	public boolean deleteRole(Long roleId, Long orgId) {
		BizAssert.notNull(roleId, "roleId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		
		Role existRole = roleMapper.load(roleId, orgId);
		BizAssert.notNull(existRole);
		
		int count = operRoleMapper.getCountByRoleId(roleId);
		if(count > 0) ExceptionUtils.throwException(AuthErrorCode.ROLE_IN_USE, AuthErrorCode.ROLE_IN_USE.getMessage());
		
		roleMapper.deleteById(roleId);
		rolePermMapper.deleteByRoleId(roleId);
		
		return true;
		
	}

	@Override
	public List<Role> findAllRole(Long orgId) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		
		return roleMapper.findAll(orgId);
	}

	@Override
	public Role getRoleById(Long roleId, Long orgId) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(roleId, "roleId must not be null");
		
		return roleMapper.load(roleId, orgId);
	}
	
	public static RoleQueryCondition roleQueryRequestToRoleQueryCondition(RoleQueryRequest request) {
		return BeanUtils.copyProperties(RoleQueryCondition.class, request);
	}

	@Override
	public List<Role> findRole(RoleQueryRequest request) {
		BizAssert.notNull(request.getOrgId());
		
		RoleQueryCondition condition = roleQueryRequestToRoleQueryCondition(request);
		
		return roleMapper.findByCondition(condition);
	}

	@Override
	public Role getRoleByName(Long orgId, String roleName) {
		return roleMapper.loadByRoleName(roleName, orgId);
	}

	@Override
	public Role getAdminRole(Long orgId) {
		return roleMapper.loadByRoleName("系统管理员", orgId);
	}

}
