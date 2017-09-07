package com.moredian.fishnet.member.manager;

import java.util.List;

import com.moredian.fishnet.member.request.DingDeleteDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentsSearchRequest;

public interface DingDeptManager {
	
	Long synDingDepartment(DingDepartmentRequest request);
	
	boolean deleteDingDepartment(DingDeleteDepartmentRequest request);
	
	List<Long> getDepartmentByOrgId(DingDepartmentsSearchRequest request);

}
