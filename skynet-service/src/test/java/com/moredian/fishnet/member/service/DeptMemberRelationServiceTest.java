package com.moredian.fishnet.member.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.fishnet.SkynetApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class DeptMemberRelationServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(DeptMemberRelationServiceTest.class);
	
	@Autowired
	private DeptMemberRelationService deptMemberRelationService;
	
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	
	@Test
	public void testFindDeptId() {
		Long memberId = adminMemberId;
		List<Long> deptIdList = deptMemberRelationService.findDeptId(orgId, memberId);
		logger.info(JsonUtils.toJson(deptIdList));
	}

}
