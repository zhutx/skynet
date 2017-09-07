package com.moredian.fishnet.member.service;

import java.util.ArrayList;
import java.util.List;

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
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.FishnetCoreApplication;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.GroupService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FishnetCoreApplication.class)
@Transactional
public class GroupMemberRelationServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(GroupMemberRelationServiceTest.class);
	
	@SI
	private  GroupService groupService;
	@Autowired
	private GroupMemberRelationService groupMemberRelationService;
	
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	
	@Test
	public void testFindGroupIdByMember() {
		
		Long memberId = adminMemberId;
		List<Long> groupIdList = groupMemberRelationService.findGroupIdByMember(orgId, memberId);
		logger.info(JsonUtils.toJson(groupIdList));
		
	}
	
	@Test
	@Rollback(false) // 服务内部有发MQ消息，因此测试数据不回滚，以免造成不一致
	public void testResetGroupRelation() {
		
		Long memberId = adminMemberId; // 朱田祥
		List<Long> groups = new ArrayList<>();
		groups.add(this.fetchQYGroup().getGroupId()); // 全员组
		ServiceResponse<Boolean> sr = groupMemberRelationService.resetGroupRelation(orgId, memberId, groups);
		Assert.isTrue(sr.isSuccess());
	}
	
	/**
	 * ----------------------------Private Helper Method-----------------------------------
	 */
	private GroupInfo fetchQYGroup() {
		return groupService.getQYGroup(orgId);
	}
	

}
