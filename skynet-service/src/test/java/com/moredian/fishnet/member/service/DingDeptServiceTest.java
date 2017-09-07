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
import com.moredian.fishnet.FishnetCoreApplication;
import com.moredian.fishnet.member.request.DingDeleteDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentsSearchRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FishnetCoreApplication.class)
@Transactional
public class DingDeptServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(DingDeptServiceTest.class);
	
	@Autowired
	private DingDeptService dingDeptService;
	
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	private String adminTpId = "4416421026347814";
	
	@Test
	public void testGetDepartmentByOrgId() {
		
		DingDepartmentsSearchRequest request = new DingDepartmentsSearchRequest();
		request.setOrgId(orgId);
		ServiceResponse<Set<Long>> set = dingDeptService.getDepartmentByOrgId(request);
		logger.info(JsonUtils.toJson(set));
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteDingDepartment() {
		
		DingDeleteDepartmentRequest request = new DingDeleteDepartmentRequest();
		request.setOrgId(orgId);
		request.setDingDepartmentId(38840510L);
		ServiceResponse<Boolean> sr = dingDeptService.deleteDingDepartment(request);
		Assert.isTrue(sr.isSuccess());
		
	}
	
	
}
