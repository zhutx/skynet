package com.moredian.fishnet.org.service;

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
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.org.model.AreaInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class AreaServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(AreaServiceTest.class);
 
	@Autowired
	private AreaService areaService;
	 
	@Test
	public void testFindChildren() {
		Integer areaCode = 330000;
		List<AreaInfo> areaInfoList = areaService.findChildren(areaCode);
		Assert.notEmpty(areaInfoList);
		logger.info(JsonUtils.toJson(areaInfoList));

	}
	
}