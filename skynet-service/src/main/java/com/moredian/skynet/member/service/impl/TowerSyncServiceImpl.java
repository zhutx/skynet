package com.moredian.skynet.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.enums.MemberErrorCode;
import com.moredian.skynet.member.manager.MemberManager;
import com.moredian.skynet.member.model.DingUserExtend;
import com.moredian.skynet.member.model.DingUserInfo;
import com.moredian.skynet.member.service.TowerSyncService;
import com.moredian.skynet.member.service.adapter.response.MemberIdMapping;
import com.moredian.skynet.org.enums.YesNoFlag;

@SI
public class TowerSyncServiceImpl implements TowerSyncService {
	
	@Autowired
	private MemberManager memberManager;

	@Override
	public ServiceResponse<List<MemberIdMapping>> findMemberIdMapping(Long orgId, List<Long> memberIdList) {
		List<MemberIdMapping> mappingList = memberManager.findIdMappingByIds(orgId, memberIdList);
		return new ServiceResponse<List<MemberIdMapping>>(true, null, mappingList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ServiceResponse<DingUserInfo> getDingUserByMemberId(Long orgId, Long memberId) {
		Member member = memberManager.getMember(orgId, memberId);
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		
		String tpExtend = member.getTpExtend();
		DingUserExtend dingUserExtend = JsonUtils.fromJson(DingUserExtend.class, tpExtend);
		if(dingUserExtend == null) return new ServiceResponse<DingUserInfo>(true, null, null);
		
		DingUserInfo response = new DingUserInfo();
		response.setOrgId(member.getOrgId());
		response.setUserStatus(member.getStatus());
		response.setDingUserId(dingUserExtend.getDing_userid());
		response.setDingJobNum(dingUserExtend.getDing_job_num());
		
		String dingExtattr = dingUserExtend.getDing_extattr();
		if(StringUtils.isNotBlank(dingExtattr)) {
			response.setDingExtattrMap(JsonUtils.fromJson(Map.class, dingExtattr));
		} else {
			response.setDingExtattrMap(new HashMap<String, String>());
		}
		response.setDingId(dingUserExtend.getDing_id());
		response.setDingOrderInDepts(dingUserExtend.getDing_orderInDepts());
		response.setDingOrgEmail(dingUserExtend.getDing_orgEmail());
		response.setDingEmail(dingUserExtend.getDing_email());
		response.setDingPosition(dingUserExtend.getDing_position());
		response.setDingDepartment(dingUserExtend.getDing_department());
		response.setDingActive(this.intToBool(dingUserExtend.getDing_active()));
		response.setDingName(dingUserExtend.getDing_name());
		response.setDingAvatar(dingUserExtend.getDing_avatar());
		response.setDingIsAdmin(this.intToBool(dingUserExtend.getDing_isAdmin()));
		response.setDingIsHide(this.intToBool(dingUserExtend.getDing_isHide()));
		response.setDingIsBoss(this.intToBool(dingUserExtend.getDing_isBoss()));
		response.setDingIsLeaderInDepts(dingUserExtend.getDing_isLeaderInDepts());
		response.setDingUnionId(dingUserExtend.getDing_unionid());
		
		return new ServiceResponse<DingUserInfo>(true, null, response);
	}
	
	private boolean intToBool(Integer value) {
		if(value == null || YesNoFlag.NO.getValue() == value) return false;
		return true;
	}
	
	@Override
	public ServiceResponse<List<Long>> findMemberIdByRelation(Long orgId) {
		List<Long> memberIdList = memberManager.findReceptionist(orgId);
		return new ServiceResponse<List<Long>>(true, null, memberIdList);
	}
	
	@Override
	public ServiceResponse<Boolean> deleteMemberRelation(Long orgId, Long memberId) {
		boolean result = memberManager.deleteReceptionist(orgId, memberId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> addMemberRelation(Long orgId, Long memberId) {
		boolean result = memberManager.addReception(orgId, memberId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
