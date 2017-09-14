package com.moredian.skynet.org.manager;

import com.moredian.skynet.org.domain.Dept;
import com.moredian.skynet.org.request.EnterDeptRequest;
import com.moredian.skynet.org.request.UpdateDeptRequest;

import java.util.List;

public interface DeptManager {
	
	boolean addDept(Long orgId, String deptName, Long parentId);
	
	List<Dept> findDepts(Long orgId, Long parentId);
	
	List<String> findDeptNamesByIds(Long orgId, List<Long> deptIds);
	
	List<String> findDeptNamesWithoutRootByIds(Long orgId, List<Long> deptIds);
	
	List<Long> findAllChildrenId(Long orgId, Long parentId);
	
	List<Dept> findAllChildren(Long orgId, Long parentId);
	
	List<Long> findAllChildrenIdByParentIds(Long orgId, List<Long> parentIds);
	
	Dept getDeptByName(Long orgId, String deptName);
	
	List<Dept> findDeptByIds(Long orgId, List<Long> deptIds);
	
	int countDept(Long orgId);
	
	List<Long> findDeptIdByDeptName(Long orgId, String deptName);
	
	Dept getRootDept(Long orgId);
	
	Long enterDept(EnterDeptRequest request);
	
	boolean updateDept(UpdateDeptRequest request);
	
	boolean updateDeptName(Long orgId, Long deptId, String deptName);
	
	Dept getDept(Long orgId, Long deptId);
	
	Dept getDeptByTpId(Long orgId, Long tpId);
	
	boolean removeByTpId(Long orgId, Long tpId);

	boolean removeByDeptId(Long orgId, Long deptId);
	
	List<Long> findTpId(Long orgId);
	
	List<Long> findTpIdByIds(Long orgId, List<Long> deptIds);

}
