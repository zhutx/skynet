package com.moredian.skynet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.member.model.DingUserInfo;
import com.moredian.skynet.member.request.DingDeptSyncRequest;
import com.moredian.skynet.member.request.DingUserSyncRequest;
import com.moredian.skynet.org.request.DingOrgAddRequest;

public interface DingCallbackService {
	
	/**
	 * 创建钉机构
	 * @param request
	 * @return
	 */
	ServiceResponse<Long> addDingOrg(DingOrgAddRequest request);
	
	/**
	 * 激活钉机构
	 * @param orgId
	 * @return
	 */
	ServiceResponse<Boolean> activeDingOrg(Long orgId);
	
	
	
	
	
	/**
	 * 同步钉部门
	 * @param request
	 * @return
	 */
	ServiceResponse<Long> syncDingDept(DingDeptSyncRequest request);
	
	/**
	 * 删除钉部门
	 * @param orgId
	 * @param dingDeptId
	 * @return
	 */
	ServiceResponse<Boolean> deleteDingDept(Long orgId, Long dingDeptId);
	
	/**
	 * 解绑部门业务权限
	 * @param orgId
	 * @param dingDeptId
	 * @param bizType
	 * @return
	 */
	ServiceResponse<Boolean> unbindDingDeptBiz(Long orgId, Long dingDeptId, Integer bizType);
	
	/**
	 * 查询钉部门id
	 * @param orgId
	 * @return
	 */
	ServiceResponse<List<Long>> findDingDeptId(Long orgId);
	
	/**
	 * 按业务查询钉部门id
	 * @param orgId
	 * @param bizType
	 * @return
	 */
	ServiceResponse<List<Long>> findDingDeptIdByBiz(Long orgId, Integer bizType);
	
	
	
	
	
	/**
	 * 同步钉用户
	 * @param request
	 * @return
	 */
	ServiceResponse<Long> syncDingUser(DingUserSyncRequest request);
	
	/**
	 * 按业务权限查询钉用户id
	 * @param orgId
	 * @param bizType
	 * @return
	 */
	ServiceResponse<List<String>> findDingUserIdByBiz(Long orgId, Integer bizType);
	
	/**
	 * 删除钉用户
	 * @param orgId
	 * @param dingUserId
	 * @return
	 */
	ServiceResponse<Boolean> deleteDingUser(Long orgId, String dingUserId);
	
	/**
	 * 解绑钉用户业务权限
	 * @param orgId
	 * @param dingUserId
	 * @param bizType
	 * @return
	 */
	ServiceResponse<Boolean> unbindDingUserBiz(Long orgId, String dingUserId, Integer bizType);
	
	/**
	 * 修改钉用户unionId
	 * @param orgId
	 * @param dingUserId
	 * @param dingUnionId
	 * @return
	 */
	ServiceResponse<Boolean> modifyDingUserUnionId(Long orgId, String dingUserId, String dingUnionId);
	
	/**
	 * 切换钉用户管理员标识
	 * @param orgId
	 * @param dingUserId
	 * @param adminFlag
	 * @return
	 */
	ServiceResponse<Boolean> switchAdminFlag(Long orgId, String dingUserId, Integer adminFlag);
	
	/**
	 * 获取钉用户信息
	 * @param orgId
	 * @param dingUserId
	 * @return
	 */
	ServiceResponse<DingUserInfo> getDingUser(Long orgId, String dingUserId);
	
	/**
	 * 按业务权限获取钉用户信息
	 * @param orgId
	 * @param dingUserId
	 * @param bizType
	 * @return
	 */
	ServiceResponse<DingUserInfo> getDingUserByBiz(Long orgId, String dingUserId, Integer bizType);
	
}
