package com.moredian.fishnet.org.manager;

import java.util.List;

import com.moredian.fishnet.org.domain.Position;
import com.moredian.fishnet.org.request.FlatPositionAddRequest;
import com.moredian.fishnet.org.request.TreePositionAddRequest;

public interface PositionManager {
	
	Position getRootPosition(Long orgId);
	
	Position getPositionById(Long orgId, Long positionId);
	
	Position addFlatPosition(FlatPositionAddRequest request);
	
	Position addTreePosition(TreePositionAddRequest request);
	
	boolean updatePosition(Long orgId, Long positionId, String newPositionName);
	
	boolean deletePosition(Long orgId, Long positionId);
	
	Position getParentPosition(Long orgId, Long positionId);
	
	Long getParentPositionId(Long orgId, Long positionId);
	
	List<Position> findPlatPositions(Long orgId, Integer positionType);
	
	List<Position> findDirectChildrens(Long orgId, Long parentId);
	
	List<Long> findDirectChildrenIds(Long orgId, Long parentId);
	
	List<Long> findAllChildrenIds(Long orgId, Long parentId);
	
	Position addRootPosition(Long orgId, String positionName);
	
	List<Position> findByNameAndType(Long orgId, Integer positionType, String positionName);

}
