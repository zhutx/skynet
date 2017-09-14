package com.moredian.fishnet.org.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.moredian.bee.common.exception.BizException;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.fishnet.SkynetApplication;
import com.moredian.fishnet.org.enums.GroupType;
import com.moredian.fishnet.org.enums.OrgErrorCode;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.model.GroupInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkynetApplication.class)
@Transactional
public class GroupServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(GroupServiceTest.class);
 
	@Autowired
	private  GroupService groupService;
	
	private static Long orgId = 1569091801116049408L; // 机构id

	@Test
	public void testCountGroup() {	
		
		int count = groupService.countGroup(orgId);
		logger.info(String.valueOf(count));

	}
	
	@Test
	public void testGetGroupInfo() {	
		
		GroupInfo groupInfo = groupService.getGroupInfo(orgId, this.fetchOneGroup().getGroupId());
		Assert.notNull(groupInfo);
		logger.info(JsonUtils.toJson(groupInfo));

	}
	
	@Test
	public void testFindGroup() {	
		
		List<GroupInfo> groupInfoList = groupService.findGroup(orgId);
		Assert.notEmpty(groupInfoList);;
		logger.info(JsonUtils.toJson(groupInfoList));

	}
	
	@Test
	public void testEditSystemGroup() {
		
		GroupInfo group = this.fetchOneSystemGroup();
		String groupName = group.getGroupName()+"UT";
		try {
			groupService.editGroup(orgId, group.getGroupId(), groupName);
		} catch (BizException e) {
			Assert.isTrue(e.getErrorContext().equalsErrorCode(OrgErrorCode.GROUP_NAME_UPDATE_REFUSE));
		}

	}
	
	@Test
	public void testEditGroup() {
		GroupInfo group = this.fetchOneCustomGroup();
		if(group != null) {
			String groupName = group.getGroupName()+"UT";
			ServiceResponse<Boolean> sr = groupService.editGroup(orgId, group.getGroupId(), groupName);
			Assert.isTrue(sr.isSuccess());
		}

	}
	
	@Test
	public void testUpdateAllMemberFlag() {	
		
		GroupInfo group = this.fetchOneCustomGroup();
		Integer allMemberFlag = 0;
		ServiceResponse<Boolean> sr = groupService.updateAllMemberFlag(orgId, group.getGroupId(), allMemberFlag);
		Assert.isTrue(sr.isSuccess());;

	}
	
	/**
	 * 提取群组id集
	 * @param groupList
	 * @return
	 */
	private List<Long> fetchGroupIds(Long orgId) {
		
		List<Long> groupIdList = new ArrayList<>();
		
		List<GroupInfo> groupList = groupService.findGroup(orgId);
		
		if(CollectionUtils.isEmpty(groupList)) return groupIdList;
		
		for(GroupInfo group : groupList) {
			groupIdList.add(group.getGroupId());
		}
		return groupIdList;
	}
	
	@Test
	public void testFindGroupByIds() {	
		
		List<Long> groupIdList = this.fetchGroupIds(orgId);
		List<GroupInfo> groupInfoList = groupService.findGroupByIds(orgId, groupIdList);
		Assert.notEmpty(groupInfoList);;
		logger.info(JsonUtils.toJson(groupInfoList));

	}
	
	@Test
	public void testFindGroupNameByIds() {	
		
		List<Long> groupIdList = this.fetchGroupIds(orgId);
		List<String> groupNameList = groupService.findGroupNameByIds(orgId, groupIdList);
		Assert.notEmpty(groupNameList);
		logger.info(JsonUtils.toJson(groupNameList));

	}
	
	@Test
	public void testGetGroupByCode() {	
		
		GroupInfo group = this.fetchOneGroup();
		String groupCode = group.getGroupCode();
		
		GroupInfo groupInfo = groupService.getGroupByCode(orgId, groupCode);
		Assert.notNull(groupInfo);
		logger.info(JsonUtils.toJson(groupInfo));

	}
	
	@Test
	public void testFindGroupIdByTypes() {	
		
		List<Integer> groupTypeList = new ArrayList<>();
		groupTypeList.add(GroupType.ALLMEMBER.getValue());
		groupTypeList.add(GroupType.VISITOR.getValue());
		groupTypeList.add(GroupType.CUSTOM.getValue());
		List<Long> groupIdList = groupService.findGroupIdByTypes(orgId, groupTypeList);
		Assert.notNull(groupIdList);
		logger.info(JsonUtils.toJson(groupIdList));

	}
	
	
	
	/**
	 * ----------------------------Private Helper Method-----------------------------------
	 */
	private GroupInfo fetchOneGroup() {
		List<GroupInfo> groupList = groupService.findGroup(orgId);
		if(CollectionUtils.isEmpty(groupList)) return null;
		
		int index = (int)Math.round(Math.random()*(groupList.size()-1));
		return groupList.get(index);
	}
	
	private GroupInfo fetchOneSystemGroup() {
		List<GroupInfo> groupList = groupService.findGroup(orgId);
		if(CollectionUtils.isEmpty(groupList)) return null;
		
		for(GroupInfo group : groupList) {
			if(group.getSystemDefault() == YesNoFlag.NO.getValue()) continue;
			return group;
		}
		return null;
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

	
}