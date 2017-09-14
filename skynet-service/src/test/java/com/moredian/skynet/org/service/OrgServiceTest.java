package com.moredian.skynet.org.service;

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

import com.moredian.bee.common.exception.BizException;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.CloudeyeErrorCode;
import com.moredian.cloudeye.core.api.conf.huber.OHuberConfigProvider;
import com.moredian.skynet.SkynetApplication;
import com.moredian.skynet.org.enums.BizType;
import com.moredian.skynet.org.model.OrgInfo;
import com.moredian.skynet.org.request.OrgUpdateRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class OrgServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(OrgServiceTest.class);
 
	@SI
	private OHuberConfigProvider oHuberConfigProvider;
	@Autowired
	private OrgService orgService;
	
	private Long orgId = 1577596179959513088L;
	
	@Test
	public void testRepeatInitOHuberConfig() {
		
		try {
			oHuberConfigProvider.initOHuberConfig(orgId, true);
		} catch (BizException e) {
			Assert.isTrue(e.getErrorContext().equalsErrorCode(CloudeyeErrorCode.OHC_REPEAT_INIT));
		}

	}
	
	@Test
	public void testGetOrgInfo() {
		
		OrgInfo org = orgService.getOrgInfo(orgId);
		Assert.notNull(org);
		logger.info(JsonUtils.toJson(org));

	}
	
	@Test
	public void testUpdateOrg() {
		
		OrgUpdateRequest request = new OrgUpdateRequest();
		request.setOrgId(orgId);
		request.setLat(80.0);
		request.setLon(80.0);
		ServiceResponse<Boolean> sr = orgService.updateOrg(request);
		Assert.isTrue(sr.isSuccess());

	}
	
	@Test
	@Rollback(false)
	public void testEnableBiz() {
		
		ServiceResponse<Boolean> sr = orgService.enableBiz(orgId, BizType.RECOGNIZE.getValue());
		Assert.isTrue(sr.isSuccess());
		
		sr = orgService.enableBiz(orgId, BizType.OPENDOOR.getValue());
		Assert.isTrue(sr.isSuccess());

	}
	
}