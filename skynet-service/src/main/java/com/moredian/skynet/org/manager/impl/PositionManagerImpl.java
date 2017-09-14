package com.moredian.skynet.org.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Position;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.enums.PositionModel;
import com.moredian.skynet.org.enums.PositionType;
import com.moredian.skynet.org.manager.PositionCodeManager;
import com.moredian.skynet.org.manager.PositionManager;
import com.moredian.skynet.org.mapper.PositionMapper;
import com.moredian.skynet.org.request.FlatPositionAddRequest;
import com.moredian.skynet.org.request.TreePositionAddRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class PositionManagerImpl implements PositionManager {
	
	@Autowired
	private PositionMapper positionMapper;
	@Autowired
	private PositionCodeManager positionCodeManager;
	@SI
	private IdgeneratorService idgeneratorService;
	
	private Long genPrimaryKey(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}

	@Override
	public Position getRootPosition(Long orgId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		
		Long parentId = 0L;
		Integer positionModel = PositionModel.TREE.getValue();
		return positionMapper.getRoot(orgId, parentId, positionModel);
	}

	@Override
	public Position getPositionById(Long orgId, Long positionId) {
		BizAssert.notNull(positionId, "positionId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		
		return positionMapper.load(orgId, positionId);
	}
	
	private Position flatPositionAddRequestToPosition(FlatPositionAddRequest request) {
		Position position = new Position();
		position.setOrgId(request.getOrgId());
		position.setPositionType(request.getPositionType());
		position.setPositionName(request.getPositionName());
		position.setPositionModel(PositionModel.COMMON.getValue());
		position.setLevel(1);
		position.setParentId(0L);
		position.setFullName(request.getPositionName());
		return position;
	}

	@Override
	public Position addFlatPosition(FlatPositionAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getPositionType(), "positionType must not be null");
		BizAssert.notNull(request.getPositionName(), "positionName must not be null");
		
		Position position = this.flatPositionAddRequestToPosition(request);
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.org.Position").getData();
		position.setPositionId(id);
		position.setPositionCode(String.valueOf(id));
		positionMapper.insert(position);
		return position;
	}
	
	private Position treePositionAddRequestToPosition(TreePositionAddRequest request) {
		Position position = new Position();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.org.Position").getData();
		position.setPositionId(id);
		position.setOrgId(request.getOrgId());
		position.setPositionType(request.getPositionType());
		position.setPositionName(request.getPositionName());
		position.setPositionModel(PositionModel.TREE.getValue());
		
		Position parentPosition = null;
		if(request.getParentId() == null) {
			parentPosition = this.getRootPosition(request.getOrgId());
			if(parentPosition == null) ExceptionUtils.throwException(OrgErrorCode.ROOT_POSITION_NOT_EXIST, OrgErrorCode.ROOT_POSITION_NOT_EXIST.getMessage());
		} else {
			parentPosition = this.getPositionById(request.getOrgId(), request.getParentId());
			if(parentPosition == null) ExceptionUtils.throwException(OrgErrorCode.PARENT_POSITION_NOT_EXIST, OrgErrorCode.PARENT_POSITION_NOT_EXIST.getMessage());
		}
		
		position.setFullName(parentPosition.getFullName()+request.getPositionName());
		position.setLevel(parentPosition.getLevel() + 1);
		position.setParentId(parentPosition.getPositionId());
		
		position.setPositionCode(positionCodeManager.genPositionCode(position));
		return position;
	}

	@Override
	@Transactional
	public Position addTreePosition(TreePositionAddRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getPositionType(), "positionType must not be null");
		BizAssert.notNull(request.getPositionName(), "positionName must not be null");
		
		Position position = this.treePositionAddRequestToPosition(request);
		positionMapper.insert(position);
		return position;
	}

	@Override
	@Transactional
	public boolean updatePosition(Long orgId, Long positionId, String newPositionName) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(positionId, "positionId must not be null");
		BizAssert.notBlank(newPositionName, "newPositionName must not be null");
		
		Position position = positionMapper.load(orgId, positionId);
		if(position == null) ExceptionUtils.throwException(OrgErrorCode.POSITION_NOT_EXIST, OrgErrorCode.POSITION_NOT_EXIST.getMessage());
		
		String oldFullName = position.getFullName(); // 修改前的位置全名
		
		String prefixName = "";
		String newFullName = "";
		
		if(position.getPositionModel() == PositionModel.TREE.getValue()){ // 树型
			
			if(position.getLevel() > 0) {
				Position parentPosition = positionMapper.load(orgId, position.getParentId());
				prefixName = parentPosition.getFullName();
			} 
		} 
		
		newFullName = prefixName + newPositionName;
		
		positionMapper.updatePositionName(positionId, orgId, newPositionName, newFullName);
		
		// 树型位置结构，需要联动更新下级节点的位置全限定名
		/*if(position.getPositionModel() == PositionModel.TREE.getValue()){ // 树型
			String positionCodePrefix = removeTailZero(position.getPositionCode());
			List<Position> children = positionMapper.findChildren(orgId, PositionModel.TREE.getValue(), positionCodePrefix, position.getLevel());
			if(CollectionUtils.isNotEmpty(children)) {
				for(Position child : children) {
				
					String newChildFullName = newFullName + child.getFullName().substring(oldFullName.length());
					
					positionMapper.updateFullName(child.getOrgId(), child.getPositionId(), newChildFullName);
				}
			}
		}*/
		
		return true;
		
	}
	
	private static int getTailZeroSize(String positionCode) {
		int size = 0;
		for(int i = positionCode.length()-1; i >= 0; i--) {
			if(positionCode.charAt(i) == '0') {
				size++;
				continue;
			}
			break;
		}
		return size;
	}
	
	private static String removeTailZero(String positionCode){
		int endIndex =  positionCode.length() - getTailZeroSize(positionCode);
		return positionCode.substring(0, endIndex);
	}
	
	/*public static void main(String[] args) {
		System.out.println(removeTailZero("330110001000010030020000000000000000"));
	}*/

	@Override
	@Transactional
	public boolean deletePosition(Long orgId, Long positionId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(positionId, "positionId must not be null");
		
		Position position = positionMapper.load(orgId, positionId);
		if(position == null) ExceptionUtils.throwException(OrgErrorCode.POSITION_NOT_EXIST, OrgErrorCode.POSITION_NOT_EXIST.getMessage());
		if(position.getPositionModel() == PositionModel.TREE.getValue()) ExceptionUtils.throwException(OrgErrorCode.TREE_POSITION_REFUSE_DELETE, OrgErrorCode.TREE_POSITION_REFUSE_DELETE.getMessage());
		
		int result = positionMapper.delete(orgId, positionId);
		return result > 0 ? true : false;
	}

	@Override
	public Position getParentPosition(Long orgId, Long positionId) {
		Position position = positionMapper.load(orgId, positionId);
		if(position.getParentId() == 0L) return null;
		return positionMapper.load(orgId, position.getParentId());
	}

	@Override
	public Long getParentPositionId(Long orgId, Long positionId) {
		Position position = positionMapper.load(orgId, positionId);
		return position.getParentId();
	}

	@Override
	public List<Position> findPlatPositions(Long orgId, Integer positionType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		return positionMapper.findPlatPositions(orgId, positionType, PositionModel.COMMON.getValue());
	}

	@Override
	public List<Position> findDirectChildrens(Long orgId, Long parentId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		
		Position parentPosition = positionMapper.load(orgId, parentId);
		if(parentPosition.getPositionModel() != PositionModel.TREE.getValue()) {
			return new ArrayList<>();
		}
		return positionMapper.findDirectChildrens(orgId, parentId, PositionModel.TREE.getValue());
	}

	@Override
	public List<Long> findDirectChildrenIds(Long orgId, Long parentId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		
		Position parentPosition = positionMapper.load(orgId, parentId);
		if(parentPosition.getPositionModel() != PositionModel.TREE.getValue()) {
			return new ArrayList<>();
		}
		return positionMapper.findDirectChildrenIds(orgId, parentId, PositionModel.TREE.getValue());
	}

	@Override
	public List<Long> findAllChildrenIds(Long orgId, Long parentId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		
		String positionCodePrefix = "";
		int level = 0;
		Position parentPosition = null;
		if(parentId == null || parentId == 0L) {
			return new ArrayList<>();
		} else {
			parentPosition = positionMapper.load(orgId, parentId);
			if(parentPosition.getPositionModel() != PositionModel.TREE.getValue()) {
				return new ArrayList<>();
			}
		}
		positionCodePrefix = removeTailZero(parentPosition.getPositionCode());
		level = parentPosition.getLevel();
		
		return positionMapper.findAllChildrenIds(orgId, PositionModel.TREE.getValue(), positionCodePrefix, level);
	}

	@Override
	public Position addRootPosition(Long orgId, String positionName) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(positionName, "positionName must not be null");
		
		Position position = new Position();
		position.setOrgId(orgId);
		position.setPositionType(PositionType.ROOT.getValue());
		position.setPositionName(positionName);
		position.setPositionModel(PositionModel.TREE.getValue());
		
		position.setFullName(positionName);
		position.setLevel(0);
		position.setParentId(0L);
		
		//position.setPositionCode(positionCodeManager.genPositionCode(position));
		
		position.setPositionId(this.genPrimaryKey(Position.class.getName()));
		positionMapper.insert(position);
		return position;
	}

	@Override
	public List<Position> findByNameAndType(Long orgId, Integer positionType, String positionName) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(positionType);
		BizAssert.notBlank(positionName);
		return positionMapper.findByNameAndType(orgId, positionType, positionName);
	}

}
