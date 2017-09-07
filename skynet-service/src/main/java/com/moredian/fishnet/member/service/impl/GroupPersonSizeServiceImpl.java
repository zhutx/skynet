package com.moredian.fishnet.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.manager.GroupPersonSizeManager;
import com.moredian.fishnet.member.service.GroupPersonSizeService;

@SI
public class GroupPersonSizeServiceImpl implements GroupPersonSizeService {
	
	@Autowired
	private GroupPersonSizeManager groupPersonSizeManager;

	@Override
	public ServiceResponse<Boolean> resetPersonSize(Long orgId, Long groupId, Integer personType) {
		boolean result = groupPersonSizeManager.resetPersonSize(orgId, groupId, personType);
		return new ServiceResponse<Boolean>(true ,null, result);
	}

	@Override
	public ServiceResponse<Boolean> batchResetPersonSize(Long orgId, List<Long> groupIdList, Integer personType) {
		boolean result = groupPersonSizeManager.batchResetPersonSize(orgId, groupIdList, personType);
		return new ServiceResponse<Boolean>(true ,null, result);
	}

	@Override
	public ServiceResponse<Boolean> resetPersonSizeForAllMemberGroup(Long orgId) {
		boolean result = groupPersonSizeManager.resetPersonSizeForAllMemberGroup(orgId);
		return new ServiceResponse<Boolean>(true ,null, result);
	}

}
