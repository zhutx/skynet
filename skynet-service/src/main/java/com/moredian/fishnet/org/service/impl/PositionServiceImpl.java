package com.moredian.fishnet.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.domain.Position;
import com.moredian.fishnet.org.manager.PositionManager;
import com.moredian.fishnet.org.request.FlatPositionAddRequest;
import com.moredian.fishnet.org.request.TreePositionAddRequest;
import com.moredian.fishnet.org.response.PositionInfo;
import com.moredian.fishnet.org.response.PositionNode;
import com.moredian.fishnet.org.service.PositionService;

@SI
public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionManager positionManager;
	
	private PositionInfo positionToPositionInfo(Position position){
		PositionInfo positionInfo = new PositionInfo();
		positionInfo.setPositionType(position.getPositionType());
		positionInfo.setFullName(position.getFullName());
		positionInfo.setGmtCreate(position.getGmtCreate());
		positionInfo.setGmtModify(position.getGmtModify());
		positionInfo.setLevel(position.getLevel());
		positionInfo.setOrgId(position.getOrgId());
		positionInfo.setParentId(position.getParentId());
		positionInfo.setPositionCode(position.getPositionCode());
		positionInfo.setPositionId(position.getPositionId());
		positionInfo.setPositionModel(position.getPositionModel());
		positionInfo.setPositionName(position.getPositionName());
		return positionInfo;
	}

	@Override
	public ServiceResponse<PositionInfo> addTreePosition(TreePositionAddRequest request) {
		Position position = positionManager.addTreePosition(request);
        return new ServiceResponse<PositionInfo>(true, null, this.positionToPositionInfo(position));
	}
	
	@Override
	public ServiceResponse<PositionInfo> addFlatPosition(FlatPositionAddRequest request) {
		Position position = positionManager.addFlatPosition(request);
        return new ServiceResponse<PositionInfo>(true, null, this.positionToPositionInfo(position));
	}
	
	@Override
	public PositionInfo getRootPosition(Long orgId) {
		Position position = positionManager.getRootPosition(orgId);
		return this.positionToPositionInfo(position);
	}
	
	@Override
	public PositionInfo getPositionById(Long orgId, Long positionId) {
		Position position = positionManager.getPositionById(orgId, positionId);
		return this.positionToPositionInfo(position);
	}

	@Override
	public ServiceResponse<Boolean> updatePosition(Long orgId, Long positionId, String newPositionName) {
		boolean result = positionManager.updatePosition(orgId, positionId, newPositionName);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deletePosition(Long orgId, Long positionId) {
		boolean result = positionManager.deletePosition(orgId, positionId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	private List<PositionInfo> positionListToPositionInfoList(List<Position> positionList) {
		if(positionList == null) return null;
		List<PositionInfo> positionInfoList = new ArrayList<>();
		for(Position position : positionList) {
			positionInfoList.add(this.positionToPositionInfo(position));
		}
		return positionInfoList;
	}

	@Override
	public PositionInfo getParentPosition(Long orgId, Long positionId) {
		Position position = positionManager.getParentPosition(orgId, positionId);
		return this.positionToPositionInfo(position);
	}

	@Override
	public Long getParentPositionId(Long orgId, Long positionId) {
		return positionManager.getParentPositionId(orgId, positionId);
	}

	@Override
	public List<PositionInfo> findPlatPositions(Long orgId, Integer positionType) {
		List<Position> positionList = positionManager.findPlatPositions(orgId, positionType);
		return positionListToPositionInfoList(positionList);
	}

	private PositionNode positionToPositionNode(Position position) {
		if(position == null) return null;
		return BeanUtils.copyProperties(PositionNode.class, position);
	}
	
	private List<PositionNode> positionListToPositionNodeList(List<Position> positionList) {
		List<PositionNode> positionNodeList = new ArrayList<>();
		for(Position position : positionList) {
			positionNodeList.add(this.positionToPositionNode(position));
		}
		return positionNodeList;
	}
	
	private PositionNode buildPositionTree(Long orgId, PositionNode positionNode) {
		
		List<Position> positionList =  positionManager.findDirectChildrens(orgId, positionNode.getPositionId());
		
		List<PositionNode> children = positionListToPositionNodeList(positionList);
		
		for(PositionNode node : children) {
			this.buildPositionTree(orgId, node);
		}
		
		positionNode.setChildren(children);
		
		return positionNode;
	}
	
	@Override
	public PositionNode getPositionTree(Long orgId, Integer positionType) {
		Position position =  positionManager.getRootPosition(orgId);
		PositionNode positionNode = positionToPositionNode(position);
		return this.buildPositionTree(orgId, positionNode);
	}

	@Override
	public List<PositionInfo> findDirectChildrens(Long orgId, Long parentId) {
		List<Position> positionList = positionManager.findDirectChildrens(orgId, parentId);
		return positionListToPositionInfoList(positionList);
	}

	@Override
	public List<Long> findDirectChildrenIds(Long orgId, Long parentId) {
		return positionManager.findDirectChildrenIds(orgId, parentId);
	}

	@Override
	public List<Long> findAllChildrenIds(Long orgId, Long parentId) {
		return positionManager.findAllChildrenIds(orgId, parentId);
	}




}
