package com.moredian.fishnet.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.manager.GroupRangeManager;
import com.moredian.fishnet.member.service.GroupRangeService;
import com.moredian.fishnet.org.enums.GroupRangeType;

@SI
public class GroupRangeServiceImpl implements GroupRangeService {
	
	@Autowired
	private GroupRangeManager groupScopeManager;

	@Override
	public ServiceResponse<Boolean> resetGroupRange(Long orgId, Long groupId, List<Long> depts, List<Long> members) {
		boolean result = groupScopeManager.resetGroupRange(orgId, groupId, depts, members);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public List<Long> findDeptId(Long orgId, Long groupId) {
		return groupScopeManager.findRangeId(orgId, groupId, GroupRangeType.RANGE_DEPT.getValue());
	}

	@Override
	public List<Long> findMemberId(Long orgId, Long groupId) {
		return groupScopeManager.findRangeId(orgId, groupId, GroupRangeType.RANGE_MEMBER.getValue());
	}

	@Override
	public List<Long> findGroupIdByDepts(Long orgId, List<Long> deptIdList) {
		return groupScopeManager.findGroupIdByRanges(orgId, GroupRangeType.RANGE_DEPT.getValue(), deptIdList);
	}

}
