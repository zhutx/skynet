package com.moredian.fishnet.member.manager;

import java.util.List;

import com.moredian.fishnet.org.request.GroupMemberBatchAddRequest;
import com.moredian.fishnet.org.request.GroupMemberBatchRemoveRequest;

public interface GroupPersonManager {
	
	boolean addGroupMembers(GroupMemberBatchAddRequest request, Integer personType);
	
	boolean removeGroupMembers(GroupMemberBatchRemoveRequest request, Integer personType);
	
	List<Long> findPersonIdByGroup(Long orgId, Long groupId, Integer personType);
	
	boolean removeByGroupId(Long orgId, Long groupId);
	
	List<Long> findGroupIdByMember(Long orgId, Long personId, Integer personType);
	
	boolean resetGroupRelation(Long orgId, Long memberId, List<Long> groups, Integer personType);
	
	int getMemberSize(Long orgId, Long groupId, Integer personType);
	
}
