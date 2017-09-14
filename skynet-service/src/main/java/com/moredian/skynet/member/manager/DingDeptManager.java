package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.skynet.member.request.DingDeleteDepartmentRequest;
import com.moredian.skynet.member.request.DingDepartmentRequest;
import com.moredian.skynet.member.request.DingDepartmentsSearchRequest;

public interface DingDeptManager {
	
	Long synDingDepartment(DingDepartmentRequest request);
	
	boolean deleteDingDepartment(DingDeleteDepartmentRequest request);
	
	List<Long> getDepartmentByOrgId(DingDepartmentsSearchRequest request);

}
