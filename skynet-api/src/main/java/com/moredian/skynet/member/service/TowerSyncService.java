package com.moredian.skynet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.member.model.DingUserInfo;
import com.moredian.skynet.member.service.adapter.response.MemberIdMapping;

public interface TowerSyncService {
	
	ServiceResponse<List<MemberIdMapping>> findMemberIdMapping(Long orgId, List<Long> memberIdList);
	
	ServiceResponse<DingUserInfo> getDingUserByMemberId(Long orgId, Long memberId);
	
	ServiceResponse<List<Long>> findMemberIdByRelation(Long orgId);
	
	ServiceResponse<Boolean> deleteMemberRelation(Long orgId, Long memberId);
	
	ServiceResponse<Boolean> addMemberRelation(Long orgId, Long memberId);

}
