package com.moredian.fishnet.member.manager;

import java.util.List;

public interface GroupRangeManager {
	
//	boolean addGroupDeptsRange(GroupDeptBatchAddRequest request);
//	
//	boolean removeGroupDeptsRange(GroupDeptBatchRemoveRequest request);
	
	boolean removeByGroupId(Long orgId, Long groupId);
	
	boolean resetGroupRange(Long orgId, Long groupId, List<Long> depts, List<Long> members);
	
	List<Long> findRangeId(Long orgId, Long groupId, Integer rangeType);
	
	List<Long> findGroupIdByRanges(Long orgId, Integer rangeType, List<Long> rangeIdList);
	
	boolean deleteByRangeId(Long orgId, Long rangeId, Integer rangeType);

}
