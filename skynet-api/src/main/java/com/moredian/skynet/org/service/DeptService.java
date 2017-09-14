package com.moredian.skynet.org.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.org.model.DeptInfo;
import com.moredian.skynet.org.request.EnterDeptRequest;
import com.moredian.skynet.org.request.UpdateDeptRequest;

import java.util.List;

public interface DeptService {
	
	ServiceResponse<Boolean> addDept(Long orgId, String deptName, Long parentId);
	
	ServiceResponse<Long> enterDept(EnterDeptRequest request);
	
	ServiceResponse<Boolean> updateDept(UpdateDeptRequest request);
	
	List<DeptInfo> findDepts(Long orgId, Long parentId);
	
	List<Long> findAllChildrenId(Long orgId, Long parentId);
	
	List<Long> findAllChildrenIdByParentIds(Long orgId, List<Long> parentIds);
	
	List<String> findDeptNamesByIds(Long orgId, List<Long> deptIds);
	
	List<String> findDeptNamesWithoutRootByIds(Long orgId, List<Long> deptIds);
	
	DeptInfo getDeptByName(Long orgId, String deptName);
	
	List<Long> findDeptIdByDeptName(Long orgId, String deptName);
	
	List<DeptInfo> findDeptByIds(Long orgId, List<Long> deptIds);
	
	int countDept(Long orgId);
	
	DeptInfo getRootDept(Long orgId);
	
	DeptInfo getByTpId(Long orgId, Long tpId);
	
	ServiceResponse<Boolean> removeByTpId(Long orgId, Long tpId);

	ServiceResponse<Boolean> removeByDeptId(Long orgId, Long deptId);
	
	List<Long> findTpId(Long orgId);
	
	List<Long> findTpIdByIds(Long orgId, List<Long> deptIds);

}
