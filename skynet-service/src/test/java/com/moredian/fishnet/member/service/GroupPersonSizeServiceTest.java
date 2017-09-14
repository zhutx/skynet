package com.moredian.fishnet.member.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.member.enums.PersonType;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.GroupService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class GroupPersonSizeServiceTest {
	
	@SI
	private GroupService groupService;
	@Autowired
	private GroupPersonSizeService groupPersonSizeService;
	
	private Long orgId = 1569091801116049408L;
	
	@Test
	public void testResetPersonSize() {
		Long groupId = this.fetchQYGroup().getGroupId();
		Integer personType = PersonType.MEMBER.getValue();
		ServiceResponse<Boolean> sr = groupPersonSizeService.resetPersonSize(orgId, groupId, personType);
		Assert.isTrue(sr.isSuccess());
	}
	
	@Test
	public void testBatchResetPersonSize() {
		List<Long> groupIdList = new ArrayList<>();
		groupIdList.add(this.fetchQYGroup().getGroupId());
		Integer personType = PersonType.MEMBER.getValue();
		ServiceResponse<Boolean> sr = groupPersonSizeService.batchResetPersonSize(orgId, groupIdList, personType);
		Assert.isTrue(sr.isSuccess());
	}
	
	@Test
	public void testResetPersonSizeForAllMemberGroup() {
		ServiceResponse<Boolean> sr = groupPersonSizeService.resetPersonSizeForAllMemberGroup(orgId);
		Assert.isTrue(sr.isSuccess());
	}
	
	/**
	 * ----------------------------Private Helper Method-----------------------------------
	 */
	private GroupInfo fetchQYGroup() {
		return groupService.getQYGroup(orgId);
	}

}
