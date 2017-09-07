package com.moredian.fishnet.member.service;

import java.util.List;
import java.util.Set;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.fishnet.member.request.ModifyDingUserUnionRequest;
import com.moredian.fishnet.member.request.UserCloseRequest;
import com.moredian.fishnet.member.request.UserDingUserIdRequest;
import com.moredian.fishnet.member.request.UserForSynDingResponse;
import com.moredian.fishnet.member.request.UserSearchForSynRequest;
import com.moredian.fishnet.member.request.UserWithOutFaceRequest;
import com.moredian.fishnet.member.service.adapter.response.DingUserResponse;
import com.moredian.fishnet.member.service.adapter.response.MemberIdMappingResponse;

public interface DingUserService {
	
	ServiceResponse<Boolean> closeUser(UserCloseRequest userCloseRequest);
	
	ServiceResponse<Boolean> modifyDingUserIsAdmin(ModifyDingUserIsAdminRequest request);
	
	ServiceResponse<Set<String>> getAllDingUserId(UserDingUserIdRequest request);
	
	ServiceResponse<UserForSynDingResponse> getSynUserForDing(UserSearchForSynRequest request);
	
	ServiceResponse<Boolean> modifyDingUserUnionId(ModifyDingUserUnionRequest request);
	
	ServiceResponse<Long> synDingUserWithOutFace(UserWithOutFaceRequest request);
	
	ServiceResponse<MemberIdMappingResponse> findIdMappingByIds(Long orgId, List<Long> memberIdList);
	
	ServiceResponse<MemberIdMappingResponse> findIdMappingByTpIds(Long orgId, List<String> tpIdList);
	
	/**
	 * 根据用户id查第三方用户信息
	 * @param orgId
	 * @param memberId
	 * @return
	 */
	ServiceResponse<DingUserResponse> getTpUserInfoById(Long orgId, Long memberId);
	

}
