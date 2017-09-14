package com.moredian.fishnet.member.service;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.fishnet.member.request.ModifyDingUserUnionRequest;
import com.moredian.fishnet.member.request.UserCloseRequest;
import com.moredian.fishnet.member.request.UserDingUserIdRequest;
import com.moredian.fishnet.member.request.UserForSynDingResponse;
import com.moredian.fishnet.member.request.UserSearchForSynRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class DingUserServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(DingUserServiceTest.class);
	
	@Autowired
	private DingUserService dingUserService;
	
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	private String adminTpId = "4416421026347814";
	
	@Test
	public void testGetAllDingUser() {
		
		UserDingUserIdRequest request = new UserDingUserIdRequest();
		request.setOrgId(orgId);
		ServiceResponse<Set<String>> set = dingUserService.getAllDingUserId(request);
		logger.info(JsonUtils.toJson(set));
		
	}
	
	@Test
	public void testGetSyncUserForDing() {
		
		UserSearchForSynRequest request = new UserSearchForSynRequest();
		request.setOrgId(orgId);
		request.setDingUserId(adminTpId);
		ServiceResponse<UserForSynDingResponse> sr = dingUserService.getSynUserForDing(request);
		logger.info(JsonUtils.toJson(sr.getData()));
		
	}
	
	@Test
	@Rollback(false)
	public void testCloseUser() {
		
		UserCloseRequest request = new UserCloseRequest();
		request.setOrgId(orgId);
		request.setDingUserId("1048632945646915");
		ServiceResponse<Boolean> sr = dingUserService.closeUser(request);
		Assert.isTrue(sr.isSuccess());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback(false)
	public void testModifyDingUserUnionId() {
		
		ModifyDingUserUnionRequest request = new ModifyDingUserUnionRequest();
		request.setOrgId(orgId);
		request.setDingUserId(adminTpId);
		request.setUnionId("S8VBLXGB61bbno29iiUY47QiEiE");
		ServiceResponse<Boolean> sr = dingUserService.modifyDingUserUnionId(request);
		Assert.isTrue(sr.isSuccess());
		
		
	}
	
	@Test
	@Rollback(false)
	public void testModifyDingUserIsAdmin() {
		
		ModifyDingUserIsAdminRequest request = new ModifyDingUserIsAdminRequest();
		request.setOrgId(orgId);
		request.setDingUserId(adminTpId);
		request.setDingIsAdmin(false);
		ServiceResponse<Boolean> sr = dingUserService.modifyDingUserIsAdmin(request);
		Assert.isTrue(sr.isSuccess());
		
		
	}
	
	
}
