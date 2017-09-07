package com.moredian.fishnet.member.manager;

import java.util.List;

import com.moredian.fishnet.member.domain.Member;
import com.moredian.fishnet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.fishnet.member.request.ModifyDingUserUnionRequest;
import com.moredian.fishnet.member.request.UserCloseRequest;
import com.moredian.fishnet.member.request.UserDingUserIdRequest;
import com.moredian.fishnet.member.request.UserSearchForSynRequest;
import com.moredian.fishnet.member.request.UserWithOutFaceRequest;

public interface DingUserManager {
	
	boolean closeUser(UserCloseRequest userCloseRequest);
	
	boolean modifyDingUserIsAdmin(ModifyDingUserIsAdminRequest request);
	
	List<String> getAllDingUserId(UserDingUserIdRequest request);
	
	Member getSynUserForDing(UserSearchForSynRequest request);
	
	boolean modifyDingUserUnionId(ModifyDingUserUnionRequest request);
	
	Long synDingUserWithOutFace(UserWithOutFaceRequest request);

}
