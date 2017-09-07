package com.moredian.fishnet.org.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.org.domain.Group;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.request.GroupAddRequest;
import com.moredian.fishnet.org.request.GroupQueryRequest;

public interface GroupManager {
	
	Long addSimpleGroup(Long orgId, String groupName, boolean allMemberUse);
	
	Long addGroup(GroupAddRequest request);
	
	boolean editGroup(Long orgId, Long groupId, String groupName);
	
	int countGroup(Long orgId);
	
	Group getGroupById(Long orgId, Long groupId);
	
	boolean deleteGroup(Long orgId, Long groupId);
	
	List<Group> findGroup(Long orgId);
	
	List<Group> findGroup(GroupQueryRequest request);
	
	boolean updateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag);
	
	boolean justUpdateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag);
	
	List<Long> findAllMemberUseGroupIds(Long orgId);
	
	Group getQYGroup(Long orgId);
	
	Group getVisitorGroup(Long orgId);
	
	boolean updateMemberSize(Long orgId, Long groupId, boolean inc, int incOrDecSize);
	
	boolean resetMemberSize(Long orgId, Long groupId, int memberSize);
	
	Group getGroupByName(Long orgId, String groupName);
	
	Group getGroupByCode(Long orgId, String groupCode);
	
	List<Group> findGroupByIds(Long orgId, List<Long> groupIdList);
	
	List<String> findGroupNameByIds(Long orgId, List<Long> groupIdList);
	
	PaginationDomain<Group> findPaginationGroup(GroupQueryRequest request, Pagination<GroupInfo> pagination);
	
	List<Long> findGroupIdByTypes(Long orgId, List<Integer> groupTypeList);

}
