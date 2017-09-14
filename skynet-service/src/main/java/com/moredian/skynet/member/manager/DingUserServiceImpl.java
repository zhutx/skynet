package com.moredian.skynet.member.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.enums.MemberErrorCode;
import com.moredian.skynet.member.manager.impl.DingUserManagerImpl;
import com.moredian.skynet.member.model.DingUserExtend;
import com.moredian.skynet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.skynet.member.request.ModifyDingUserUnionRequest;
import com.moredian.skynet.member.request.UserCloseRequest;
import com.moredian.skynet.member.request.UserDingUserIdRequest;
import com.moredian.skynet.member.request.UserForSynDingResponse;
import com.moredian.skynet.member.request.UserSearchForSynRequest;
import com.moredian.skynet.member.request.UserWithOutFaceRequest;
import com.moredian.skynet.member.service.DingUserService;
import com.moredian.skynet.member.service.adapter.response.DingUserResponse;
import com.moredian.skynet.member.service.adapter.response.MemberIdMapping;
import com.moredian.skynet.member.service.adapter.response.MemberIdMappingResponse;
import com.moredian.skynet.org.enums.YesNoFlag;

@SI
public class DingUserServiceImpl implements DingUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(DingUserServiceImpl.class);
	
	@Autowired
	private DingUserManager dingUserManager;
	@Autowired
	private MemberManager memberManager;

	@Override
	public ServiceResponse<Boolean> closeUser(UserCloseRequest userCloseRequest) {
		logger.info("##########closeUser#############");
		boolean result = dingUserManager.closeUser(userCloseRequest);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> modifyDingUserIsAdmin(ModifyDingUserIsAdminRequest request) {
		logger.info("##########modifyDingUserIsAdmin#############");
		boolean result = dingUserManager.modifyDingUserIsAdmin(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Set<String>> getAllDingUserId(UserDingUserIdRequest request) {
		logger.info("##########getAllDingUserId#############");
		List<String> tpIdList = dingUserManager.getAllDingUserId(request);
		return new ServiceResponse<Set<String>>(new HashSet<>(tpIdList));
	}

	@Override
	public ServiceResponse<UserForSynDingResponse> getSynUserForDing(UserSearchForSynRequest request) {
		logger.info("##########getSynUserForDing#############");
		Member member = dingUserManager.getSynUserForDing(request);
		if(member == null) {
			return new ServiceResponse<UserForSynDingResponse>(true, null, null);
		}
		
		UserForSynDingResponse response = new UserForSynDingResponse();
		response.setOrgId(member.getOrgId());
		response.setUserId(member.getMemberId());
		response.setUserStatus(member.getStatus());
		
		DingUserExtend dingUserExtend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
		
		response.setDingName(dingUserExtend.getDing_name());
		response.setDingId(dingUserExtend.getDing_id());
		response.setDingUserId(dingUserExtend.getDing_userid());
		response.setDingJobNum(dingUserExtend.getDing_job_num());
		if(dingUserExtend.getDing_isAdmin() != null){
			boolean dingIsAdmin = dingUserExtend.getDing_isAdmin()==YesNoFlag.YES.getValue()?true:false;
			response.setDingIsAdmin(dingIsAdmin);
		}
		if(dingUserExtend.getDing_isBoss() != null){
			boolean dingIsBoss = dingUserExtend.getDing_isBoss()==YesNoFlag.YES.getValue()?true:false;
			response.setDingIsBoss(dingIsBoss);
		}
		response.setDingUnionId(dingUserExtend.getDing_unionid());
		response.setDingIsLeaderInDepts(dingUserExtend.getDing_isLeaderInDepts());
		
		return new ServiceResponse<UserForSynDingResponse>(true, null, response);
	}

	@Override
	public ServiceResponse<Boolean> modifyDingUserUnionId(ModifyDingUserUnionRequest request) {
		logger.info("##########modifyDingUserUnionId#############");
		boolean result = dingUserManager.modifyDingUserUnionId(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Long> synDingUserWithOutFace(UserWithOutFaceRequest request) {
		Long memberId = dingUserManager.synDingUserWithOutFace(request);
		return new ServiceResponse<Long>(true, null, memberId);
	}

	@Override
	public ServiceResponse<MemberIdMappingResponse> findIdMappingByIds(Long orgId, List<Long> memberIdList) {
		logger.info("##########findIdMappingByIds#############");
		List<MemberIdMapping> mappingList = memberManager.findIdMappingByIds(orgId, memberIdList);
		MemberIdMappingResponse response = new MemberIdMappingResponse();
		response.setMappingList(mappingList);
		return new ServiceResponse<MemberIdMappingResponse>(true, null, response);
	}

	@Override
	public ServiceResponse<MemberIdMappingResponse> findIdMappingByTpIds(Long orgId, List<String> tpIdList) {
		List<MemberIdMapping> mappingList = memberManager.findIdMappingByTpIds(orgId, tpIdList);
		MemberIdMappingResponse response = new MemberIdMappingResponse();
		response.setMappingList(mappingList);
		return new ServiceResponse<MemberIdMappingResponse>(true, null, response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceResponse<DingUserResponse> getTpUserInfoById(Long orgId, Long memberId) {
		logger.info("##########getTpUserInfoById#############");
		Member member = memberManager.getMember(orgId, memberId);
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		
		String tpExtend = member.getTpExtend();
		DingUserExtend dingUserExtend = JsonUtils.fromJson(DingUserExtend.class, tpExtend);
		if(dingUserExtend == null) return new ServiceResponse<DingUserResponse>(true, null, null);
		
		DingUserResponse response = new DingUserResponse();
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
		
		return new ServiceResponse<DingUserResponse>(true, null, response);
	}
	
	private boolean intToBool(Integer value) {
		if(value == null || YesNoFlag.NO.getValue() == value) return false;
		return true;
	}


}
