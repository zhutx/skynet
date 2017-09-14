package com.moredian.fishnet.org.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.org.enums.BizType;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.request.ModuleBindRequest;
import com.moredian.fishnet.org.request.OrgAddRequest;
import com.moredian.fishnet.org.request.OrgQueryRequest;
import com.moredian.fishnet.org.request.OrgUpdateRequest;

/**
 * 机构服务
 * @author zhutx
 *
 */
public interface OrgService {
	
	ServiceResponse<Long> addOrg(OrgAddRequest request);
	
	
	
	
	
	
	
	
	
	
	List<Long> findAllOrgId();
	
	/**
	 * 绑定系统模块
	 * @param request
	 * @return
	 */
	ServiceResponse<Boolean> bindModule(ModuleBindRequest request);
	
	/**
	 * 修改机构
	 * @param request
	 * @return
	 * <li>OrgErrorCode.ORG_NOT_EXIST</li>
	 */
	ServiceResponse<Boolean> updateOrg(OrgUpdateRequest request);
	
	/**
	 * 启用机构
	 * @param orgId
	 * @return
	 */
	ServiceResponse<Boolean> enableOrg(Long orgId);
	
	/**
	 * 禁用机构
	 * @param orgId
	 * @return
	 */
	ServiceResponse<Boolean> disableOrg(Long orgId);
	
	/**
	 * 删除机构
	 * @param orgId
	 * @return
	 * <li>OrgErrorCode.SOME_BIZ_NOT_STOP</li>
	 */
	ServiceResponse<Boolean> deleteOrg(Long orgId);
	
	/**
	 * 开启某一业务
	 * @param orgId
	 * @param bizType {@link BizType}
	 * @return
	 */
	ServiceResponse<Boolean> enableBiz(Long orgId, Integer bizType);
	
	/**
	 * 开启某些业务
	 * @param orgId
	 * @param bizTypes {@link BizType}
	 * @return
	 */
	ServiceResponse<Boolean> enableBizs(Long orgId, List<Integer> bizTypes);
	
	/**
	 * 停止某一业务
	 * @param orgId
	 * @param bizType {@link BizType}
	 * @return
	 */
	ServiceResponse<Boolean> disableBiz(Long orgId, Integer bizType);
	
	/**
	 * 停止某些业务
	 * @param orgId
	 * @param bizTypes {@link BizType}
	 * @return
	 */
	ServiceResponse<Boolean> disableBizs(Long orgId, List<Integer> bizTypes);
	
	/**
	 * 检查某业务是否开启
	 * @param orgId
	 * @param bizType {@link BizType}
	 * @return
	 */
	boolean isBizEnable(Long orgId, Integer bizType);
	
	/**
	 * 获取机构信息
	 * @param orgId
	 * @return
	 */
	OrgInfo getOrgInfo(Long orgId);
	
	/**
	 * 分页获取机构列表
	 * @param pagination
	 * @param request
	 * @return
	 */
	Pagination<OrgInfo> findPaginationOrg(Pagination<OrgInfo> pagination, OrgQueryRequest request);
	
	/**
	 * 绑定机构间关系
	 * @param upstreamOrgId 上游机构id
	 * @param downstreamOrgId 下游机构id
	 * @param relationType 关系类型  [参考{@link OrgRelationType}]
	 * @return
	 */
	ServiceResponse<Boolean> bindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	
	/**
	 * 解绑机构间关系
	 * @param upstreamOrgId 上游机构id
	 * @param downstreamOrgId 下游机构id
	 * @param relationType 关系类型  [参考{@link OrgRelationType}]
	 * @return
	 */
	ServiceResponse<Boolean> unbindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	
	/**
	 * 获取机构某关系类型的上游机构
	 * @param downstreamOrgId 下游机构id
	 * @param relationType 关系类型  [参考{@link OrgRelationType}]
	 * @return
	 */
	OrgInfo getUpstream(Long downstreamOrgId, Integer relationType);
	
	/**
	 * 查询机构某关系类型的下游机构
	 * @param upstreamOrgId 上游机构id
	 * @param relationType 关系类型  [参考{@link OrgRelationType}]
	 * @return
	 */
	List<OrgInfo> findDownstream(Long upstreamOrgId, Integer relationType);
	
	OrgInfo getOrgByName(String orgName);
	
	OrgInfo getOneOrg(Integer orgType, Integer orgLevel, Integer areaCode);
	
	List<OrgInfo> findMonitorPoliceOrg(Integer districtCode);
	
	
}
