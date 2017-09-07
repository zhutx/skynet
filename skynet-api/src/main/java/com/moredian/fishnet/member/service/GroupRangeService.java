package com.moredian.fishnet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;

public interface GroupRangeService {
	
	ServiceResponse<Boolean> resetGroupRange(Long orgId, Long groupId, List<Long> depts, List<Long> members);
	
	List<Long> findDeptId(Long orgId, Long groupId);
	
	List<Long> findMemberId(Long orgId, Long groupId);
	
	List<Long> findGroupIdByDepts(Long orgId, List<Long> deptIdList);

}