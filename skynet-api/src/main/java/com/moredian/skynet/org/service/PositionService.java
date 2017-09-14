package com.moredian.skynet.org.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.org.enums.PositionType;
import com.moredian.skynet.org.request.FlatPositionAddRequest;
import com.moredian.skynet.org.request.TreePositionAddRequest;
import com.moredian.skynet.org.response.PositionInfo;
import com.moredian.skynet.org.response.PositionNode;

/**
 * 位置服务
 * @author zhutx
 *
 */
public interface PositionService {
	
	/**
	 * 添加树型位置节点
	 * @param request
	 * @return
	 * <li>OrgErrorCode.ROOT_POSITION_NOT_EXIST</li>
	 * <li>OrgErrorCode.PARENT_POSITION_NOT_EXIST</li>
	 */
	ServiceResponse<PositionInfo> addTreePosition(TreePositionAddRequest request);
	
	/**
	 * 添加平型位置节点
	 * @param request
	 * @return
	 */
	ServiceResponse<PositionInfo> addFlatPosition(FlatPositionAddRequest request);
	
	/**
	 * 获取机构根位置
	 * @param orgId
	 * @return
	 */
	PositionInfo getRootPosition(Long orgId);
	
	/**
	 * 获取某一位置信息
	 * @param positionId
	 * @param orgId
	 * @return
	 */
	PositionInfo getPositionById(Long orgId, Long positionId);
	
	/**
	 * 更新位置名
	 * @param positionId
	 * @param orgId
	 * @param newPositionName
	 * @return
	 * <li>OrgErrorCode.POSITION_NOT_EXIST</li>
	 */
	ServiceResponse<Boolean> updatePosition(Long orgId, Long positionId, String newPositionName);
	
	/**
	 * 删除位置
	 * @param positionId
	 * @param orgId
	 * @return
	 * <li>OrgErrorCode.POSITION_NOT_EXIST</li>
	 * <li>OrgErrorCode.TREE_POSITION_REFUSE_DELETE</li>
	 */
	ServiceResponse<Boolean> deletePosition(Long orgId, Long positionId);
	
	/**
	 * 查询平型位置集
	 * @param orgId
	 * @param positionType {@link PositionType}
	 * @return
	 */
	List<PositionInfo> findPlatPositions(Long orgId, Integer positionType);
	
	/**
	 * 获取父位置信息
	 * @param orgId
	 * @param positionId
	 * @return
	 */
	PositionInfo getParentPosition(Long orgId, Long positionId);
	
	/**
	 * 获取父位置id
	 * @param orgId
	 * @param positionId
	 * @return
	 */
	Long getParentPositionId(Long orgId, Long positionId);
	
	/**
	 * 查询某位置的直属下级位置集
	 * @param parentId
	 * @param orgId
	 * @return
	 */
	List<PositionInfo> findDirectChildrens(Long orgId, Long parentId);
	
	/**
	 * 查询某位置的直属下级位置id集
	 * @param parentId
	 * @param orgId
	 * @return
	 */
	List<Long> findDirectChildrenIds(Long orgId, Long parentId);
	
	/**
	 * 查询某位置的所有下级位置id集
	 * @param parentId
	 * @param orgId
	 * @return
	 */
	List<Long> findAllChildrenIds(Long orgId, Long parentId);
	
	/**
	 * 获取完整位置树
	 * @param orgId
	 * @param positionType {@link PositionType}
	 * @return
	 */
	PositionNode getPositionTree(Long orgId, Integer positionType);

}