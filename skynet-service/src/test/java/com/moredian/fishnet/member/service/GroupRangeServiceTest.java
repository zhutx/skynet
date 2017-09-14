package com.moredian.fishnet.member.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.DeptService;
import com.moredian.fishnet.org.service.GroupService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class GroupRangeServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(GroupRangeServiceTest.class);
	
	@SI
	private DeptService deptService;
	@SI
	private GroupService groupService;
	@Autowired
	private GroupRangeService groupRangeService;
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	
	@Test
	public void testFindDeptId() {
		
		Long groupId = this.fetchQYGroup().getGroupId();
		List<Long> deptIdList = groupRangeService.findDeptId(orgId, groupId);
		logger.info(JsonUtils.toJson(deptIdList));
		
	}
	
	@Test
	public void testFindMemberId() {
		
		Long groupId = this.fetchQYGroup().getGroupId();
		List<Long> memberIdList = groupRangeService.findMemberId(orgId, groupId);
		logger.info(JsonUtils.toJson(memberIdList));
		
	}
	
	@Test
	@Rollback(false) // 服务内部有发MQ消息，因此测试数据不回滚，以免造成不一致
	public void testResetGroupRange() {
		
		GroupInfo group = this.fetchOneCustomGroup();
		if(group != null) {
			List<Long> depts = new ArrayList<>();
			depts.add(this.fetchOneDeptId());
			List<Long> members = new ArrayList<>();
			members.add(adminMemberId);
			ServiceResponse<Boolean> sr = groupRangeService.resetGroupRange(orgId, group.getGroupId(), depts, members); // 修改群组范围配置
			Assert.isTrue(sr.isSuccess());
		}
		
	}
	
	/**
	 * ----------------------------Private Helper Method-----------------------------------
	 */
	private GroupInfo fetchQYGroup() {
		return groupService.getQYGroup(orgId);
	}
	
	private GroupInfo fetchOneCustomGroup() {
		List<GroupInfo> groupList = groupService.findGroup(orgId);
		if(CollectionUtils.isEmpty(groupList)) return null;
		
		for(GroupInfo group : groupList) {
			if(group.getSystemDefault() == YesNoFlag.YES.getValue()) continue;
			return group;
		}
		return null;
	}
	
	private Long fetchOneDeptId() {
		List<Long> deptIdList = deptService.findAllChildrenId(orgId, 1L);
		int index = (int)Math.round(Math.random()*(deptIdList.size()-1));
		return deptIdList.get(index);
	}
	

}
