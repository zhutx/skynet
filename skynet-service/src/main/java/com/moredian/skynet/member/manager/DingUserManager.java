package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.skynet.member.request.ModifyDingUserUnionRequest;
import com.moredian.skynet.member.request.UserCloseRequest;
import com.moredian.skynet.member.request.UserDingUserIdRequest;
import com.moredian.skynet.member.request.UserSearchForSynRequest;
import com.moredian.skynet.member.request.UserWithOutFaceRequest;

public interface DingUserManager {
	
	boolean closeUser(UserCloseRequest userCloseRequest);
	
	boolean modifyDingUserIsAdmin(ModifyDingUserIsAdminRequest request);
	
	List<String> getAllDingUserId(UserDingUserIdRequest request);
	
	Member getSynUserForDing(UserSearchForSynRequest request);
	
	boolean modifyDingUserUnionId(ModifyDingUserUnionRequest request);
	
	Long synDingUserWithOutFace(UserWithOutFaceRequest request);

}
