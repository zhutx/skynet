package com.moredian.skynet.auth.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.auth.domain.Perm;
import com.moredian.skynet.auth.enums.AuthErrorCode;
import com.moredian.skynet.auth.enums.PermStatus;
import com.moredian.skynet.auth.manager.PermManager;
import com.moredian.skynet.auth.mapper.OperRoleMapper;
import com.moredian.skynet.auth.mapper.PermMapper;
import com.moredian.skynet.auth.mapper.RolePermMapper;
import com.moredian.skynet.auth.request.PermAddRequest;
import com.moredian.skynet.auth.request.PermQueryRequest;
import com.moredian.skynet.auth.request.PermUpdateRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class PermManagerImpl implements PermManager {
	
	@Autowired
	private PermMapper permMapper;
	@Autowired
	private RolePermMapper rolePermMapper;
	@Autowired
	private OperRoleMapper operRoleMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	
	private Perm permAddRequestToPerm(PermAddRequest request){
		Perm perm = new Perm();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.auth.Perm").getData();
		perm.setPermId(id);
		perm.setPermType(request.getPermType());
		perm.setPermName(request.getPermName());
		perm.setPermAction(request.getPermAction());
		perm.setPermUrl(request.getPermUrl());
		perm.setPermDesc(request.getPermDesc());
		perm.setIconName(request.getIconName());
		if(request.getParentId() == null) {
			perm.setParentId(0L);
			perm.setPermLevel(1);
		} else {
			perm.setParentId(request.getParentId());
			Perm parent = permMapper.load(request.getParentId());
			perm.setPermLevel(parent.getPermLevel() + 1);
		}
		perm.setModuleType(request.getModuleType());
		perm.setStatus(PermStatus.USABLE.getValue());
		return perm;
	}

	@Override
	@Transactional
	public boolean addPerm(PermAddRequest request) {
		
		BizAssert.notNull(request.getPermType(), "permType must not be null");
		BizAssert.notNull(request.getPermName(), "permName must not be null");
		//BizAssert.notNull(request.getPermAction(), "permAction must not be null");
		BizAssert.notNull(request.getModuleType(), "moduleType must not be null");
		
		Perm existPerm = permMapper.loadByPermName(request.getPermName(), request.getModuleType());
		if(existPerm != null) ExceptionUtils.throwException(AuthErrorCode.PERM_EXIST, AuthErrorCode.PERM_EXIST.getMessage());
		
		Perm perm = this.permAddRequestToPerm(request);
		permMapper.insert(perm);
		
		permMapper.incChildrenSize(perm.getParentId());
		
		return true;
		
	}
	
	private Perm permUpdateRequestToPerm(PermUpdateRequest request) {
		
		Perm perm = permMapper.load(request.getPermId());
		
		if(!perm.getPermName().equals(request.getPermName())) {
			Perm existPerm = permMapper.loadByPermName(request.getPermName(), perm.getModuleType());
			if(existPerm != null) ExceptionUtils.throwException(AuthErrorCode.PERM_EXIST, String.format(AuthErrorCode.PERM_EXIST.getMessage(), request.getPermName()));
		}
		
		perm.setPermName(request.getPermName());
		perm.setPermUrl(request.getPermUrl());
		perm.setPermDesc(request.getPermDesc());
		
		return perm;
	}

	@Override
	public boolean updatePerm(PermUpdateRequest request) {
		
		BizAssert.notNull(request.getPermId(), "permId must not be null");
		
		int result = permMapper.update(this.permUpdateRequestToPerm(request));
		
		return result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean deletePerm(Long permId) {
		BizAssert.notNull(permId, "permId must not be null");
		
		int childrenCount = permMapper.getChildrenCount(permId);
		if(childrenCount > 0) ExceptionUtils.throwException(AuthErrorCode.HAD_CHILD_PERM, AuthErrorCode.HAD_CHILD_PERM.getMessage());
		
		int refCount = rolePermMapper.getCountByPermId(permId);
		if(refCount > 0) ExceptionUtils.throwException(AuthErrorCode.PERM_IN_USE, AuthErrorCode.PERM_IN_USE.getMessage());
		
		Perm perm = permMapper.load(permId);
		
		permMapper.deleteById(permId);
		
		permMapper.decChildrenSize(perm.getParentId());
		
		return true;
	}

	@Override
	public boolean disablePerm(Long permId) {
		int result = permMapper.updateStatus(permId, PermStatus.DISABLE.getValue());
		return result > 0 ? true : false;
	}

	@Override
	public boolean activePerm(Long permId) {
		int result = permMapper.updateStatus(permId, PermStatus.USABLE.getValue());
		return result > 0 ? true : false;
	}

	@Override
	public List<Perm> findPerm(PermQueryRequest request) {
		if(request.getParentId() == null) {
			request.setParentId(0L);
		}
		return permMapper.findByCondition(request.getModuleType(), request.getParentId(), request.getPermName());
	}

	@Override
	public List<Perm> findPerm(Integer moduleType, Long parentId) {
		
		BizAssert.notNull(moduleType, "moduleType must not be nul");
		if(parentId == null) parentId = 0L;
		
		return permMapper.findByCondition(moduleType, parentId, null);
	}

	@Override
	public List<String> findPermUrlByOper(Long operId) {
		
		List<String> permUrlList = new ArrayList<>();
		
		BizAssert.notNull(operId, "operId must not be nul");
		
		List<Long> roleIdList = operRoleMapper.findRoleIdByOperId(operId);
		if(CollectionUtils.isEmpty(roleIdList)) return permUrlList;
		
		List<Long> permIdList = rolePermMapper.findPermIdByRoleIds(roleIdList);
		if(CollectionUtils.isEmpty(permIdList)) return permUrlList;
		
		permUrlList = permMapper.findPermUrl(permIdList);
		
		return permUrlList;
	}

	@Override
	public List<Perm> findPermByOper(Long operId, Integer moduleType) {
		return permMapper.findPermByOper(operId, moduleType);
	}

	@Override
	public List<Perm> findPermByModuleType(Integer moduleType) {
		return permMapper.findPermByModule(moduleType);
	}

	@Override
	public Perm getPerm(Long permId) {
		BizAssert.notNull(permId);
		return permMapper.load(permId);
	}

	@Override
	public List<Long> findPermIdByModuleType(Integer moduleType) {
		return permMapper.findPermIdByModule(moduleType);
	}

}
