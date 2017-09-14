package com.moredian.skynet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;

public interface GroupMemberRelationService {
	
	List<Long> findMemberId(Long orgId, Long groupId);
	
	List<Long> findMemberIdByGroup(Long orgId, Long groupId);
	
	List<Long> findPersonId(Long orgId, Long groupId, Integer personType);
	
	List<Long> findGroupIdByMember(Long orgId, Long memberId);
	
	ServiceResponse<Boolean> resetGroupRelation(Long orgId, Long memberId, List<Long> groups);

}
