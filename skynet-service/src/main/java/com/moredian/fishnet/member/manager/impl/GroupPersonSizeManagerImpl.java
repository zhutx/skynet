package com.moredian.fishnet.member.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.fishnet.member.manager.GroupPersonManager;
import com.moredian.fishnet.member.manager.GroupPersonSizeManager;
import com.moredian.fishnet.org.enums.GroupType;
import com.moredian.fishnet.org.enums.PersonType;
import com.moredian.fishnet.org.manager.GroupManager;

@Service
public class GroupPersonSizeManagerImpl implements GroupPersonSizeManager {
	
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private GroupPersonManager groupPersonManager;

	@Override
	public boolean resetPersonSize(Long orgId, Long groupId, Integer personType) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(groupId);
		
		int memberSize = groupPersonManager.getMemberSize(orgId, groupId, personType);
		groupManager.resetMemberSize(orgId, groupId, memberSize);
		
		return true;
	}

	@Override
	public boolean batchResetPersonSize(Long orgId, List<Long> groupIdList, Integer personType) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(groupIdList);
		
		for(Long groupId : groupIdList) {
			this.resetPersonSize(orgId, groupId, personType);
		}
		
		return true;
	}

	@Override
	public boolean resetPersonSizeForAllMemberGroup(Long orgId) {
		
		List<Integer> groupTypeList = new ArrayList<>();
		groupTypeList.add(GroupType.ALLMEMBER.getValue()); // 全员组
		groupTypeList.add(GroupType.CUSTOM.getValue()); // 自定义组
		List<Long> groupIdList = groupManager.findGroupIdByTypes(orgId, groupTypeList);
		
		this.batchResetPersonSize(orgId, groupIdList, PersonType.MEMBER.getValue());
		
		return true;
	}

}
