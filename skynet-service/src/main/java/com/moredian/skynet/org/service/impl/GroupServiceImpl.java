package com.moredian.skynet.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.manager.GroupManager;
import com.moredian.skynet.org.model.GroupInfo;
import com.moredian.skynet.org.request.GroupAddRequest;
import com.moredian.skynet.org.request.GroupQueryRequest;
import com.moredian.skynet.org.service.GroupService;

@SI
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupManager groupManager;

	@Override
	public ServiceResponse<Long> addSimpleGroup(Long orgId, String groupName, Integer allMemberFlag) {
		Long groupId = groupManager.addSimpleGroup(orgId, groupName, allMemberFlag);
		return new ServiceResponse<Long>(true, null, groupId);
	}

	@Override
	public ServiceResponse<Long> addGroup(GroupAddRequest request) {
		Long groupId = groupManager.addGroup(request);
		return new ServiceResponse<Long>(true, null, groupId);
	}

	@Override
	public ServiceResponse<Boolean> deleteGroup(Long orgId, Long groupId) {
		boolean result = groupManager.deleteGroup(orgId, groupId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateGroupName(Long orgId, Long groupId, String groupName) {
		boolean result = groupManager.updateGroupName(orgId, groupId, groupName);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public int countGroup(Long orgId) {
		return groupManager.countGroup(orgId);
	}
	
	private GroupInfo groupToGroupInfo(Group group) {
		return BeanUtils.copyProperties(GroupInfo.class, group);
	}

	@Override
	public GroupInfo getGroupInfo(Long orgId, Long groupId) {
		Group group = groupManager.getGroupById(orgId, groupId);
		return groupToGroupInfo(group);
	}
	
	private List<GroupInfo> groupListToGroupInfoList(List<Group> groupList) {
		List<GroupInfo> result = new ArrayList<>();
		if(CollectionUtils.isEmpty(groupList)) return result;
		for(Group group : groupList) {
			result.add(this.groupToGroupInfo(group));
		}
		return result;
	}

	@Override
	public List<GroupInfo> findGroup(Long orgId) {
		List<Group> groupList = groupManager.findGroup(orgId);
		return groupListToGroupInfoList(groupList);
	}
	
	public List<GroupInfo> findGroup(GroupQueryRequest request) {
		List<Group> groupList = groupManager.findGroup(request);
		return groupListToGroupInfoList(groupList);
	}
	
	private Pagination<GroupInfo> paginationGroupToPaginationGroupInfo(PaginationDomain<Group> fromPagination) {
		Pagination<GroupInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<GroupInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(groupListToGroupInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<GroupInfo> findPaginationGroup(Pagination<GroupInfo> pagination, GroupQueryRequest request) {
		
		PaginationDomain<Group> paginationGroup = groupManager.findPaginationGroup(request, pagination);
		
		return paginationGroupToPaginationGroupInfo(paginationGroup);
	}

	@Override
	public ServiceResponse<Boolean> updateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag) {
		boolean result = groupManager.updateAllMemberFlag(orgId, groupId, allMemberFlag);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> justUpdateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag) {
		boolean result = groupManager.justUpdateAllMemberFlag(orgId, groupId, allMemberFlag);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public List<Long> findAllMemberUseGroupIds(Long orgId) {
		return groupManager.findAllMemberUseGroupIds(orgId);
	}

	@Override
	public ServiceResponse<Boolean> updateMemberSize(Long orgId, Long groupId, boolean inc, int incOrDecSize) {
		boolean result = groupManager.updateMemberSize(orgId, groupId, inc, incOrDecSize);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> resetMemberSize(Long orgId, Long groupId, int memberSize) {
		boolean result = groupManager.resetMemberSize(orgId, groupId, memberSize);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public GroupInfo getGroupByName(Long orgId, String groupName) {
		Group group = groupManager.getGroupByName(orgId, groupName);
		return groupToGroupInfo(group);
	}

	@Override
	public List<GroupInfo> findGroupByIds(Long orgId, List<Long> groupIdList) {
		List<Group> groupList = groupManager.findGroupByIds(orgId, groupIdList);
		return groupListToGroupInfoList(groupList);
	}

	@Override
	public List<String> findGroupNameByIds(Long orgId, List<Long> groupIdList) {
		return groupManager.findGroupNameByIds(orgId, groupIdList);
	}


}
