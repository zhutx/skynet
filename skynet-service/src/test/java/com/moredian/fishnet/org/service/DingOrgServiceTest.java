package com.moredian.fishnet.org.service;

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
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.org.request.OrgEnterpriseBind;
import com.moredian.fishnet.org.request.OrgEnterpriseNotBindRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class DingOrgServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(DingOrgServiceTest.class);
 
	@Autowired
	private DingOrgService dingOrgService;
	
	private Long orgId = 1569091801116049408L;
	
	@Test
	@Rollback(false)
	public void testAddOrg() {
		
		OrgEnterpriseNotBindRequest request = new OrgEnterpriseNotBindRequest();
		request.setOrgName("测试机构");
		request.setProvinceId(330000);
		request.setCityId(330100);
		request.setDistrictId(330110);
		request.setDetailedAddress("海智创业茂3幢8楼");
		ServiceResponse<Long> sr = dingOrgService.addOrgEnterpriseNotBind(request);
		logger.info(String.valueOf(sr.getData()));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

	}
	
	@Test
	@Rollback(false)
	public void testBindOrg() {
		OrgEnterpriseBind request = new OrgEnterpriseBind();
		request.setOrgId(1570793845115846656L);
		ServiceResponse<Boolean> sr = dingOrgService.bingOrgEnterpriseNotBind(request);
        Assert.isTrue(sr.getData());
	}
	
}