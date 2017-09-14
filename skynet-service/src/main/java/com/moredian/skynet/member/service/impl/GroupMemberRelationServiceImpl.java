package com.moredian.skynet.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.manager.GroupPersonManager;
import com.moredian.skynet.member.service.GroupMemberRelationService;
import com.moredian.skynet.org.enums.PersonType;

@SI
public class GroupMemberRelationServiceImpl implements GroupMemberRelationService {
	
	@Autowired
	private GroupPersonManager groupPersonManager;

	@Override
	public List<Long> findMemberIdByGroup(Long orgId, Long groupId) {
		return groupPersonManager.findPersonIdByGroup(orgId, groupId, PersonType.MEMBER.getValue());
	}

	@Override
	public List<Long> findPersonId(Long orgId, Long groupId, Integer personType) {
		return groupPersonManager.findPersonIdByGroup(orgId, groupId, personType);
	}

	@Override
	public List<Long> findMemberId(Long orgId, Long groupId) {
		return groupPersonManager.findPersonIdByGroup(orgId, groupId, PersonType.MEMBER.getValue());
	}

	@Override
	public List<Long> findGroupIdByMember(Long orgId, Long memberId) {
		return groupPersonManager.findGroupIdByMember(orgId, memberId, PersonType.MEMBER.getValue());
	}

	@Override
	public ServiceResponse<Boolean> resetGroupRelation(Long orgId, Long memberId, List<Long> groups) {
		boolean result = groupPersonManager.resetGroupRelation(orgId, memberId, groups, PersonType.MEMBER.getValue());
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
