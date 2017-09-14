package com.moredian.skynet.member.service;

import java.util.List;

public interface DeptMemberRelationService {
	
	List<Long> findMemberIdByDeptId(Long orgId, Long deptId);
	
	List<Long> findMemberIdByDepts(Long orgId, List<Long> deptIds);
	
	List<String> findDeptName(Long orgId, Long memberId);
	
	List<Long> findDeptId(Long orgId, Long memberId);

}
