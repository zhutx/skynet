package com.moredian.fishnet.member.service;

import java.util.Set;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.member.request.DingDeleteDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentsSearchRequest;

public interface DingDeptService {
	
	ServiceResponse<Long> synDingDepartment(DingDepartmentRequest request);
	
	ServiceResponse<Boolean> deleteDingDepartment(DingDeleteDepartmentRequest request);
	
	ServiceResponse<Set<Long>> getDepartmentByOrgId(DingDepartmentsSearchRequest request);

}
