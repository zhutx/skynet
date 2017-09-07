package com.moredian.fishnet.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.domain.Perm;
import com.moredian.fishnet.auth.manager.PermManager;
import com.moredian.fishnet.auth.manager.RolePermManager;
import com.moredian.fishnet.auth.model.PermInfo;
import com.moredian.fishnet.auth.model.PermNode;
import com.moredian.fishnet.auth.model.PermZNode;
import com.moredian.fishnet.auth.request.PermAddRequest;
import com.moredian.fishnet.auth.request.PermQueryRequest;
import com.moredian.fishnet.auth.request.PermUpdateRequest;
import com.moredian.fishnet.auth.service.PermService;

@SI
public class PermServiceImpl implements PermService {
	
	@Autowired
	private PermManager permManager;
	@Autowired
	private RolePermManager rolePermManager;

	@Override
	public ServiceResponse<Boolean> addPerm(PermAddRequest request) {
		boolean result = permManager.addPerm(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updatePerm(PermUpdateRequest request) {
		boolean result = permManager.updatePerm(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deletePerm(Long permId) {
		boolean result = permManager.deletePerm(permId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> disablePerm(Long permId) {
		boolean result = permManager.disablePerm(permId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> activePerm(Long permId) {
		boolean result = permManager.activePerm(permId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	private PermInfo permToPermInfo(Perm perm) {
		if(perm == null) return null;
		PermInfo permInfo = new PermInfo();
		BeanUtils.copyProperties(permInfo, perm);
		return permInfo;
	}
	
	private List<PermInfo> permListToPermInfoList(List<Perm> permList){
		List<PermInfo> permInfoList = new ArrayList<>();
		if(permList == null) return permInfoList;
		for(Perm perm : permList) {
			permInfoList.add(permToPermInfo(perm));
		}
		return permInfoList;
	}

	@Override
	public List<PermInfo> findPerm(PermQueryRequest request) {
		List<Perm> permList = permManager.findPerm(request);
		return permListToPermInfoList(permList);
	}

	@Override
	public List<String> findPermUrlByOper(Long operId) {
		return permManager.findPermUrlByOper(operId);
	}
	
	@Override
	public PermInfo getPerm(Long permId) {
		Perm perm = permManager.getPerm(permId);
		return this.permToPermInfo(perm);
	}

	private PermNode permToPermNode(Perm perm) {
		PermNode permNode = new PermNode();
		permNode.setPermId(perm.getPermId());
		permNode.setPermType(perm.getPermType());
		permNode.setPermName(perm.getPermName());
		permNode.setPermUrl(perm.getPermUrl());
		permNode.setIconName(perm.getIconName());
		permNode.setParentId(perm.getParentId());
		permNode.setStatus(perm.getStatus());
		return permNode;
	}

	@Override
	public List<PermNode> findPermNodeByOper(Long operId, Integer moduleType) {
		
		List<PermNode> levelPermList = new ArrayList<>();
		
		List<Perm> permList = permManager.findPermByOper(operId, moduleType);
		
		// 组装一级菜单
		for (Perm perm : permList) {
			if (perm.getParentId() == 0) {
				levelPermList.add(permToPermNode(perm));
			}
		}
		
		// 组装二级菜单
		for (PermNode firstLevel : levelPermList) {

			for (Perm perm : permList) {
				if (perm.getParentId().equals(firstLevel.getPermId())) {
					firstLevel.getChildren().add(permToPermNode(perm));
				}
			}

		}
		
		return levelPermList;
	}

	@Override
	public List<PermNode> findPermNode(Integer moduleType) {
		List<PermNode> levelPermList = new ArrayList<>();
		
		List<Perm> permList = permManager.findPermByModuleType(moduleType);
		
		// 组装一级菜单
		for (Perm perm : permList) {
			if (perm.getParentId() == 0) {
				levelPermList.add(permToPermNode(perm));
			}
		}
		
		// 组装二级菜单
		for (PermNode firstLevel : levelPermList) {

			for (Perm perm : permList) {
				if (perm.getParentId().equals(firstLevel.getPermId())) {
					firstLevel.getChildren().add(permToPermNode(perm));
				}
			}

		}
		
		return levelPermList;
	}
	
	private PermZNode permToPermZNode(Perm perm, Long roleId) {
		
		List<Long> myPermIds = new ArrayList<>();
		if(roleId != null) {
			myPermIds = rolePermManager.findPermIdByRoleId(roleId);
		}
		
		PermZNode zNode = new PermZNode();
		zNode.setId(String.valueOf(perm.getPermId()));
		zNode.setName(perm.getPermName());
		zNode.setRemark(perm.getPermDesc());
		zNode.setpId(String.valueOf(perm.getParentId()));
		zNode.setOpen(true);
		if(myPermIds.contains(perm.getPermId())) {
			zNode.setChecked(true);
		} else {
			zNode.setChecked(false);
		}
		
		return zNode;
	}
	
	@Override
	public List<PermZNode> findPermZNode(Integer moduleType, Long roleId) {
		List<PermZNode> resultList = new ArrayList<>();
		List<Perm> permList = permManager.findPermByModuleType(moduleType);
		for (Perm perm : permList) {
			resultList.add(permToPermZNode(perm, roleId));
		}
		return resultList;
	}

	@Override
	public List<Long> findPermByRole(Long roleId) {
		return rolePermManager.findPermIdByRoleId(roleId);
	}

}
