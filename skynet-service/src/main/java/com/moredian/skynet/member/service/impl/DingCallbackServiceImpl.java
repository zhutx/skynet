package com.moredian.skynet.member.service.impl;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.model.DingUserInfo;
import com.moredian.skynet.member.request.DingDeptSyncRequest;
import com.moredian.skynet.member.request.DingUserSyncRequest;
import com.moredian.skynet.member.service.DingCallbackService;
import com.moredian.skynet.org.request.DingOrgAddRequest;

@SI
public class DingCallbackServiceImpl implements DingCallbackService {

	@Override
	public ServiceResponse<Long> addDingOrg(DingOrgAddRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> activeDingOrg(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Long> syncDingDept(DingDeptSyncRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> deleteDingDept(Long orgId, Long dingDeptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> unbindDingDeptBiz(Long orgId, Long dingDeptId, Integer bizType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<List<Long>> findDingDeptId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<List<Long>> findDingDeptIdByBiz(Long orgId, Integer bizType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Long> syncDingUser(DingUserSyncRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<List<String>> findDingUserIdByBiz(Long orgId, Integer bizType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> deleteDingUser(Long orgId, String dingUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> unbindDingUserBiz(Long orgId, String dingUserId, Integer bizType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> modifyDingUserUnionId(Long orgId, String dingUserId, String dingUnionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<Boolean> switchAdminFlag(Long orgId, String dingUserId, Integer adminFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<DingUserInfo> getDingUser(Long orgId, String dingUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<DingUserInfo> getDingUserByBiz(Long orgId, String dingUserId, Integer bizType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
