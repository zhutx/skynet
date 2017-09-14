package com.moredian.skynet.member.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.enums.MemberStatus;
import com.moredian.skynet.member.manager.MemberManager;
import com.moredian.skynet.member.model.DingUserExtend;
import com.moredian.skynet.member.service.adapter.UserSearchService;
import com.moredian.skynet.member.service.adapter.request.SelectUserRelationRequest;
import com.moredian.skynet.member.service.adapter.request.UserForDingByUserIdsRequest;
import com.moredian.skynet.member.service.adapter.request.UserSearchForSynRequest;
import com.moredian.skynet.member.service.adapter.request.UserSearchForVisitorRequest;
import com.moredian.skynet.member.service.adapter.response.ProsceniumUserResponse;
import com.moredian.skynet.member.service.adapter.response.UserForDingResponse;
import com.moredian.skynet.member.service.adapter.response.UserForSynDingResponse;
import com.moredian.skynet.org.enums.YesNoFlag;

@SI
public class UserSearchServiceImpl implements UserSearchService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);
	
	@Autowired
	private MemberManager memberManager;
	
	@Override
	public ServiceResponse<Boolean> judgeProscenium(SelectUserRelationRequest request) {
		logger.debug("###################judgeProscenium###################");
		boolean result = memberManager.judgeProscenium(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<UserForDingResponse> getVisibleDdUser(UserSearchForVisitorRequest searchBean) {
		
		logger.debug("###################getVisibleDdUser###################");
		
		Member member = memberManager.getMember(searchBean.getOrgId(), searchBean.getUserId());
		
		if(member == null || member.getStatus() != MemberStatus.USABLE.getValue()) {
			return new ServiceResponse<UserForDingResponse>(true, null, null);
		}
		
		UserForDingResponse userForDingDing = new UserForDingResponse();
		userForDingDing.setUserId(member.getMemberId());
		userForDingDing.setFaceImgUrl(member.getVerifyFaceUrl());
		//userForDingDing.setRelation(relation);
		userForDingDing.setOrgId(member.getOrgId());
		
		DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
		boolean dingIsAdmin = false;
		if(extend.getDing_isAdmin() != null && YesNoFlag.YES.getValue() == extend.getDing_isAdmin()) {
			dingIsAdmin = true;
		}
		userForDingDing.setDingIsAdmin(dingIsAdmin);
		userForDingDing.setDingUserId(extend.getDing_userid());
		userForDingDing.setDingJobNum(extend.getDing_job_num());
		userForDingDing.setDingName(extend.getDing_name());
		userForDingDing.setDingDepartment(extend.getDing_department());
		userForDingDing.setDingIsLeaderInDepts(extend.getDing_isLeaderInDepts());
		
		return new ServiceResponse<UserForDingResponse>(true, null, userForDingDing);
		
	}
	
	private boolean intToBool(Integer value) {
		if(value == null || YesNoFlag.NO.getValue() == value) return false;
		return true;
	}
	
	private List<UserForDingResponse> memberListToUserForDingResponse(List<Member> memberList) {
		List<UserForDingResponse> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(memberList)) return list;
		
		for(Member member : memberList) {
			UserForDingResponse item = new UserForDingResponse();
			item.setUserId(member.getMemberId());
			item.setFaceImgUrl(member.getVerifyFaceUrl());
			//item.setRelation(relation);
			item.setOrgId(member.getOrgId());
			
			DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
			item.setDingIsAdmin(intToBool(extend.getDing_isAdmin()));
			item.setDingUserId(extend.getDing_userid());
			item.setDingJobNum(extend.getDing_job_num());
			item.setDingName(extend.getDing_name());
			item.setDingDepartment(extend.getDing_department());
			item.setDingIsLeaderInDepts(extend.getDing_isLeaderInDepts());
			
			list.add(item);
		}
		
		return list;
	}

	@Override
	public ServiceResponse<List<UserForDingResponse>> getVisibleUserByIds(UserForDingByUserIdsRequest request) {
		logger.debug("###################getVisibleUserByIds###################");
		List<Long> memberIdList = new ArrayList<>();
		memberIdList.addAll(request.getUserIds());
		List<Member> memberList = memberManager.findMemberByIds(request.getOrgId(), memberIdList);
		
		List<UserForDingResponse> response = memberListToUserForDingResponse(memberList);
		
		return new ServiceResponse<List<UserForDingResponse>>(true, null, response);
	}

	@Override
	public ServiceResponse<UserForSynDingResponse> getSynUserForDing(UserSearchForSynRequest request) {
		logger.info("##########getSynUserForDing#############");
		Member member = memberManager.getMemberByTpId(request.getOrgId(), request.getDingUserId());
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
	public ServiceResponse<List<ProsceniumUserResponse>> selectProscenium(SelectUserRelationRequest request) {
		logger.debug("###################selectProscenium###################");
		List<ProsceniumUserResponse> list = memberManager.selectProscenium(request);
		
		return new ServiceResponse<List<ProsceniumUserResponse>>(true, null, list);
	}

	

}
