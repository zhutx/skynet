package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.member.domain.DeptMember;
import com.moredian.skynet.member.request.BindDeptRelationRequest;
import com.moredian.skynet.member.request.DeptRelationQueryRequest;

public interface DeptMemberManager {
	
	List<Long> findMemberIdByDeptId(Long orgId, Long deptId);
	
	PaginationDomain<DeptMember> findPaginationDeptRelation(Pagination<?> pagination, DeptRelationQueryRequest request);
	
	List<Long> findMemberIdByDepts(Long orgId, List<Long> deptIds);
	
	List<String> findDeptName(Long orgId, Long memberId);
	
	List<String> findDeptNameWithoutRoot(Long orgId, Long memberId);
	
	List<Long> findDeptId(Long orgId, Long memberId);
	
	void addMemberToDept(DeptMember deptRelation);
	
	boolean addDeptRelation(BindDeptRelationRequest request);
	
	List<Long> findLeaderIds(Long orgId, Long deptId, Integer leaderFlag, Integer status);
	
	boolean clearLeaderRelation(Long orgId, Long deptId, Long memberId);
	
	boolean disableRelation(Long orgId, Long memberId);
	
	boolean disableRelation(Long orgId, Long memberId, Long deptId);
	
	boolean fillDeptIdByTpDeptId(Long orgId, Long tpDeptId, Long deptId);
	
	int findRelationCount(Long orgId, Long memberId, Long deptId);
	
}
