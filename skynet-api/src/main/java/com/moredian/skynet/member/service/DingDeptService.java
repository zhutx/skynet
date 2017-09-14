package com.moredian.skynet.member.service;

import java.util.Set;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.member.request.DingDeleteDepartmentRequest;
import com.moredian.skynet.member.request.DingDepartmentRequest;
import com.moredian.skynet.member.request.DingDepartmentsSearchRequest;

public interface DingDeptService {
	
	ServiceResponse<Long> synDingDepartment(DingDepartmentRequest request);
	
	ServiceResponse<Boolean> deleteDingDepartment(DingDeleteDepartmentRequest request);
	
	ServiceResponse<Set<Long>> getDepartmentByOrgId(DingDepartmentsSearchRequest request);

}
