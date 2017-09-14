package com.moredian.skynet.org.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.skynet.SkynetApplication;
import com.moredian.skynet.org.model.DeptInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class DeptServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(DeptServiceTest.class);
 
	@Autowired
	private DeptService deptService;
	
	private Long orgId = 1569091801116049408L;
	 
	@Test
	public void testFindDepts() {	
		
		Long parentDeptId = 1L;
		
		List<DeptInfo> deptInfoList = deptService.findDepts(orgId, parentDeptId);

		Assert.notEmpty(deptInfoList);
		logger.info(JsonUtils.toJson(deptInfoList));
		
	}
	
	@Test
	public void testFindDeptNamesByIds() {	
		
		List<Long> deptIds = new ArrayList<>();
		deptIds.add(1L);
		
		List<String> deptNameList = deptService.findDeptNamesByIds(orgId, deptIds);

		Assert.notEmpty(deptNameList);
		logger.info(JsonUtils.toJson(deptNameList));
		
	}
	
	@Test
	public void testFindDeptByIds() {	
		
		List<Long> deptIds = new ArrayList<>();
		deptIds.add(1L);
		
		List<DeptInfo> deptInfoList = deptService.findDeptByIds(orgId, deptIds);

		Assert.notEmpty(deptInfoList);
		logger.info(JsonUtils.toJson(deptInfoList));
		
	}
	
	@Test
	public void testGetRootDept() {	
		
		DeptInfo deptInfo = deptService.getRootDept(orgId);
		Assert.notNull(deptInfo);
		logger.info(JsonUtils.toJson(deptInfo));
		
	}
	
}