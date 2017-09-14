package com.moredian.skynet.org.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.org.model.GroupInfo;
import com.moredian.skynet.org.request.GroupAddRequest;
import com.moredian.skynet.org.request.GroupQueryRequest;

public interface GroupService {
	
	ServiceResponse<Long> addSimpleGroup(Long orgId, String groupName, boolean allMemberUse);
	
	ServiceResponse<Long> addGroup(GroupAddRequest request);
	
	ServiceResponse<Boolean> deleteGroup(Long orgId, Long groupId);
	
	ServiceResponse<Boolean> editGroup(Long orgId, Long groupId, String groupName);
	
	int countGroup(Long orgId);
	
	GroupInfo getGroupInfo(Long orgId, Long groupId);
	
	List<GroupInfo> findGroup(Long orgId);
	
	Pagination<GroupInfo> findPaginationGroup(Pagination<GroupInfo> pagination, GroupQueryRequest request);
	
	ServiceResponse<Boolean> updateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag);
	
	ServiceResponse<Boolean> justUpdateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag);
	
	List<Long> findAllMemberUseGroupIds(Long orgId);
	
	GroupInfo getQYGroup(Long orgId);
	
	GroupInfo getVisitorGroup(Long orgId);
	
	ServiceResponse<Boolean> updateMemberSize(Long orgId, Long groupId, boolean inc, int incOrDecSize);
	
	ServiceResponse<Boolean> resetMemberSize(Long orgId, Long groupId, int memberSize);
	
	GroupInfo getGroupByName(Long orgId, String groupName);
	
	List<GroupInfo> findGroupByIds(Long orgId, List<Long> groupIdList);
	
	List<String> findGroupNameByIds(Long orgId, List<Long> groupIdList);
	
	GroupInfo getGroupByCode(Long orgId, String groupCode);
	
	List<Long> findGroupIdByTypes(Long orgId, List<Integer> groupTypeList);
	
}
