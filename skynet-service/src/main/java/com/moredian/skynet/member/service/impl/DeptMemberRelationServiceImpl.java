package com.moredian.skynet.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.manager.DeptMemberManager;
import com.moredian.skynet.member.service.DeptMemberRelationService;

@SI
public class DeptMemberRelationServiceImpl implements DeptMemberRelationService {
	
	@Autowired
	private DeptMemberManager deptMemberManager;
	
	
	@Override
	public List<Long> findMemberIdByDeptId(Long orgId, Long deptId) {
		return deptMemberManager.findMemberIdByDeptId(orgId, deptId);
	}
	
	@Override
	public List<Long> findMemberIdByDepts(Long orgId, List<Long> deptIds) {
		return deptMemberManager.findMemberIdByDepts(orgId, deptIds);
	}

	@Override
	public List<String> findDeptName(Long orgId, Long memberId) {
		return deptMemberManager.findDeptName(orgId, memberId);
	}

	@Override
	public List<Long> findDeptId(Long orgId, Long memberId) {
		return deptMemberManager.findDeptId(orgId, memberId);
	}

}
