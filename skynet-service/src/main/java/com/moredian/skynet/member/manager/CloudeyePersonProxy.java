package com.moredian.skynet.member.manager;

import java.util.List;
import java.util.Map;

import com.moredian.cloudeye.core.api.lib.PersonFullInfo;
import com.moredian.skynet.member.domain.Member;

public interface CloudeyePersonProxy {
	
	void addMembersToGroup(Long orgId, Long groupId, List<Long> members);
	
	void removeMembersToGroup(Long orgId, Long groupId, List<Long> members);
	
	boolean isPersonExist(Long orgId, Long memberId);
	
	PersonFullInfo getPerson(Long orgId, Long memberId);
	
	void addSimplePerson(Long orgId, Long memberId, Integer userType, String userName);
	
	void addPerson(Member member);
	
	void changeMemberGroupRelation(Member member, List<Long> groupIdList);
	
	void putPersonExtend(Long orgId, Long memberId, Map<String, Object> extend);
	
	void changeMemberHead(Long orgId, Long memberId, String memberName, String relativeHeadUrl);
	
	void replaceVerifyImage(Long orgId, Long memberId, String fullVerifyUrl);
	
	//void deletePerson(Long orgId, Long memberId);
	
	void disablePerson(Long orgId, Long memberId);
	
	void activePerson(Long orgId, Long memberId);
	
}
