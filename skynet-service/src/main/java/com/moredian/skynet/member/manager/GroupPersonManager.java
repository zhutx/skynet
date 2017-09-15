package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.skynet.org.request.GroupMemberBatchAddRequest;
import com.moredian.skynet.org.request.GroupMemberBatchRemoveRequest;

public interface GroupPersonManager {
	
	boolean addGroupPersons(GroupMemberBatchAddRequest request, Integer personType);
	
	boolean removeGroupPersons(GroupMemberBatchRemoveRequest request, Integer personType);
	
	List<Long> findPersonIdByGroup(Long orgId, Long groupId, Integer personType);
	
	boolean removeByGroupId(Long orgId, Long groupId);
	
	List<Long> findGroupIdByMember(Long orgId, Long personId, Integer personType);
	
	boolean resetGroupRelation(Long orgId, Long memberId, List<Long> groups, Integer personType);
	
	int getMemberSize(Long orgId, Long groupId, Integer personType);
	
	boolean resetGroupPersons(Long orgId, Long groupId, List<Long> existMemberIds, List<Long> finalMemberIds, Integer personType);
	
}
