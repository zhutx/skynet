package com.moredian.fishnet.member.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.manager.DingDeptManager;
import com.moredian.fishnet.member.request.DingDeleteDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentsSearchRequest;
import com.moredian.fishnet.member.service.DingDeptService;

@SI
public class DingDeptServiceImpl implements DingDeptService {
	
	private static final Logger logger = LoggerFactory.getLogger(DingDeptServiceImpl.class);
	
	@Autowired
	private DingDeptManager dingDeptManager;

	@Override
	public ServiceResponse<Long> synDingDepartment(DingDepartmentRequest request) {
		logger.info("##########synDingDepartment#############");
		Long deptId = dingDeptManager.synDingDepartment(request);
		return new ServiceResponse<Long>(true, null, deptId);
	}

	@Override
	public ServiceResponse<Boolean> deleteDingDepartment(DingDeleteDepartmentRequest request) {
		logger.info("##########deleteDingDepartment#############");
		boolean result = dingDeptManager.deleteDingDepartment(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Set<Long>> getDepartmentByOrgId(DingDepartmentsSearchRequest request) {
		logger.info("##########getDepartmentByOrgId#############");
		List<Long> tpIdList = dingDeptManager.getDepartmentByOrgId(request);
		return new ServiceResponse<Set<Long>>(new HashSet<Long>(tpIdList));
	}

}
