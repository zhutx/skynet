package com.moredian.fishnet.org.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.enums.MemberErrorCode;
import com.moredian.fishnet.member.manager.DeptMemberManager;
import com.moredian.fishnet.org.domain.Dept;
import com.moredian.fishnet.org.manager.DeptManager;
import com.moredian.fishnet.org.model.DeptInfo;
import com.moredian.fishnet.org.request.EnterDeptRequest;
import com.moredian.fishnet.org.request.UpdateDeptRequest;
import com.moredian.fishnet.org.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SI
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptManager deptManager;

	@Autowired
	private DeptMemberManager deptMemberManager;
	
	@Override
	public ServiceResponse<Boolean> addDept(Long orgId, String deptName, Long parentId) {
		boolean result = deptManager.addDept(orgId, deptName, parentId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	private DeptInfo deptToDeptInfo(Dept dept) {
		if(dept == null) return null;
		return BeanUtils.copyProperties(DeptInfo.class, dept);
	}
	
	private List<DeptInfo> deptListToDeptInfoList(List<Dept> deptList) {
		if(deptList == null) return null;
		List<DeptInfo> deptInfoList = new ArrayList<>();
		for(Dept dept : deptList) {
			deptInfoList.add(this.deptToDeptInfo(dept));
		}
		return deptInfoList;
	}

	@Override
	public List<DeptInfo> findDepts(Long orgId, Long parentId) {
		List<Dept> deptList = deptManager.findDepts(orgId, parentId);
		return deptListToDeptInfoList(deptList);
	}
	
	@Override
	public List<Long> findAllChildrenId(Long orgId, Long parentId) {
		return deptManager.findAllChildrenId(orgId, parentId);
	}

	@Override
	public List<Long> findAllChildrenIdByParentIds(Long orgId, List<Long> parentIds) {
		return deptManager.findAllChildrenIdByParentIds(orgId, parentIds);
	}

	@Override
	public List<String> findDeptNamesByIds(Long orgId, List<Long> deptIds) {
		return deptManager.findDeptNamesByIds(orgId, deptIds);
	}
	
	@Override
	public List<String> findDeptNamesWithoutRootByIds(Long orgId, List<Long> deptIds) {
		return deptManager.findDeptNamesWithoutRootByIds(orgId, deptIds);
	}

	@Override
	public DeptInfo getDeptByName(Long orgId, String deptName) {
		Dept dept = deptManager.getDeptByName(orgId, deptName);
		return this.deptToDeptInfo(dept);
	}

	@Override
	public List<DeptInfo> findDeptByIds(Long orgId, List<Long> deptIds) {
		List<Dept> deptList = deptManager.findDeptByIds(orgId, deptIds);
		return this.deptListToDeptInfoList(deptList);
	}

	@Override
	public int countDept(Long orgId) {
		return deptManager.countDept(orgId);
	}

	@Override
	public List<Long> findDeptIdByDeptName(Long orgId, String deptName) {
		return deptManager.findDeptIdByDeptName(orgId, deptName);
	}

	@Override
	public DeptInfo getRootDept(Long orgId) {
		Dept dept = deptManager.getRootDept(orgId);
		return this.deptToDeptInfo(dept);
	}

	@Override
	public ServiceResponse<Long> enterDept(EnterDeptRequest request) {
		Long deptId = deptManager.enterDept(request);
		return new ServiceResponse<Long>(true, null, deptId);
	}

	@Override
	public ServiceResponse<Boolean> updateDept(UpdateDeptRequest request) {
		boolean result = deptManager.updateDept(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public DeptInfo getByTpId(Long orgId, Long tpId) {
		Dept dept = deptManager.getDeptByTpId(orgId, tpId);
		return deptToDeptInfo(dept);
	}

	@Override
	public ServiceResponse<Boolean> removeByTpId(Long orgId, Long tpId) {
		boolean result = deptManager.removeByTpId(orgId, tpId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> removeByDeptId(Long orgId, Long deptId) {
		List<Long> memberIds=deptMemberManager.findMemberIdByDeptId(orgId,deptId);
		if(CollectionUtils.isNotEmpty(memberIds)){
			 ExceptionUtils.throwException(MemberErrorCode.DEPT_USER_RELATION_EXIST, MemberErrorCode.DEPT_USER_RELATION_EXIST.getMessage());
		}
		List<Dept> depts=deptManager.findDepts(orgId,deptId);
		if(CollectionUtils.isNotEmpty(depts)){
			ExceptionUtils.throwException(MemberErrorCode.CHILD_DEPT_RELATION_EXIST, MemberErrorCode.CHILD_DEPT_RELATION_EXIST.getMessage());
		}

		boolean result=deptManager.removeByDeptId(orgId,deptId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public List<Long> findTpId(Long orgId) {
		return deptManager.findTpId(orgId);
	}

	@Override
	public List<Long> findTpIdByIds(Long orgId, List<Long> deptIds) {
		return deptManager.findTpIdByIds(orgId, deptIds);
	}

}
