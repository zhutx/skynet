package com.moredian.skynet.org.manager.impl;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Dept;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.enums.TpType;
import com.moredian.skynet.org.manager.DeptManager;
import com.moredian.skynet.org.mapper.DeptMapper;
import com.moredian.skynet.org.request.EnterDeptRequest;
import com.moredian.skynet.org.request.UpdateDeptRequest;
import com.moredian.idgenerator.service.IdgeneratorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptManagerImpl implements DeptManager {
	
	@Autowired
	private DeptMapper deptMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	
	private Long genPrimaryKey(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}
	
	@Override
	@Transactional
	public boolean addDept(Long orgId, String deptName, Long parentId) {
		BizAssert.notNull(orgId);
		BizAssert.notBlank(deptName);
		BizAssert.notNull(parentId);
		
		Dept existDept = deptMapper.loadByName(orgId, deptName);
		if(existDept != null) ExceptionUtils.throwException(OrgErrorCode.DEPT_EXIST, OrgErrorCode.DEPT_EXIST.getMessage());
		
		if(parentId == 0L) {
			Dept rootDept = deptMapper.loadRoot(orgId);
			if(rootDept != null) ExceptionUtils.throwException(OrgErrorCode.DEPT_EXIST, OrgErrorCode.DEPT_EXIST.getMessage());
		} else {
			Dept parentDept = deptMapper.load(orgId, parentId);
			if(parentDept == null) ExceptionUtils.throwException(OrgErrorCode.PARENT_DEPT_NOTEXIST, OrgErrorCode.PARENT_DEPT_NOTEXIST.getMessage());
		}
		
		Dept dept = new Dept();
		dept.setOrgId(orgId);
		dept.setDeptId(this.genPrimaryKey(Dept.class.getName()));
		dept.setDeptName(deptName);
		dept.setParentId(parentId);
		dept.setTpType(TpType.SELF.getValue());
		dept.setTpId(String.valueOf(dept.getDeptId()));
		
		deptMapper.insert(dept);
		
		return true;
	}

	@Override
	public List<Dept> findDepts(Long orgId, Long parentId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(parentId, "parentId must not be null");
		return deptMapper.findDepts(orgId, parentId);
	}

	@Override
	public List<String> findDeptNamesByIds(Long orgId, List<Long> deptIds) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(deptIds)) return new ArrayList<>();
		return deptMapper.findDeptNamesByIds(orgId, deptIds);
	}
	
	@Override
	public List<String> findDeptNamesWithoutRootByIds(Long orgId, List<Long> deptIds) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(deptIds)) return new ArrayList<>();
		return deptMapper.findDeptNamesWithoutRootByIds(orgId, deptIds);
	}

	/**
	 * 从部门集中提取部门id集
	 * @param deptList
	 * @return
	 */
	private List<Long> fetchDeptIds(List<Dept> deptList) {
		List<Long> result = new ArrayList<>();
		if(CollectionUtils.isEmpty(deptList)) return result;
		for(Dept dept : deptList) {
			result.add(dept.getDeptId());
		}
		return result;
	}
	
	@Override
	public List<Long> findAllChildrenId(Long orgId, Long parentId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(parentId);
		
		List<Long> result = new ArrayList<>();
		
		List<Dept> childrenList = deptMapper.findDepts(orgId, parentId);
		
		if(CollectionUtils.isEmpty(childrenList)) return result;
		
		List<Long> deptIdList = this.fetchDeptIds(childrenList);
		result.addAll(deptIdList);
		
		result.addAll(this.findAllChildrenIdByParentIds(orgId, deptIdList));
		
		return result;
	}

	@Override
	public List<Long> findAllChildrenIdByParentIds(Long orgId, List<Long> parentIds) {
		BizAssert.notNull(orgId);
		BizAssert.notEmpty(parentIds);
		
		List<Long> result = new ArrayList<>();
		
		for(Long parentId : parentIds) {
			result.addAll(this.findAllChildrenId(orgId, parentId));
		}
		
		return result;
	}

	@Override
	public Dept getDeptByName(Long orgId, String deptName) {
		BizAssert.notNull(orgId);
		BizAssert.notBlank(deptName);
		return deptMapper.loadByName(orgId, deptName);
	}

	@Override
	public List<Dept> findDeptByIds(Long orgId, List<Long> deptIds) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(deptIds)) return new ArrayList<>();
		
		return deptMapper.findDeptByIds(orgId, deptIds);
		
	}

	@Override
	public int countDept(Long orgId) {
		BizAssert.notNull(orgId);
		return deptMapper.count(orgId);
	}

	@Override
	public List<Long> findDeptIdByDeptName(Long orgId, String deptName) {
		BizAssert.notNull(orgId);
		BizAssert.notBlank(deptName);
		return deptMapper.findDeptIdByDeptName(orgId, deptName);
	}

	@Override
	public Dept getRootDept(Long orgId) {
		BizAssert.notNull(orgId);
		return deptMapper.loadRoot(orgId);
	}
	
	private Dept buildDept(EnterDeptRequest request) {
		Dept dept = BeanUtils.copyProperties(Dept.class, request);
		dept.setDeptId(this.genPrimaryKey(Dept.class.getName()));
		return dept;
	}

	@Override
	public Long enterDept(EnterDeptRequest request) {
		Dept dept = this.buildDept(request);
		deptMapper.insert(dept);
		return dept.getDeptId();
	}
	
	private Dept buildDept(UpdateDeptRequest request) {
		return BeanUtils.copyProperties(Dept.class, request);
	}

	@Override
	public boolean updateDept(UpdateDeptRequest request) {
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getDeptId());
		BizAssert.notNull(request.getParentId());
		BizAssert.notBlank(request.getDeptName());
		
		if(request.getParentId() != 0) {
			Dept parentDept = deptMapper.load(request.getOrgId(), request.getParentId());
			if(parentDept == null) ExceptionUtils.throwException(OrgErrorCode.PARENT_DEPT_NOTEXIST, OrgErrorCode.PARENT_DEPT_NOTEXIST.getMessage());
		}
		
		Dept dept = this.buildDept(request);
		deptMapper.update(dept);
		
		return true;
	}

	@Override
	public Dept getDeptByTpId(Long orgId, Long tpId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(tpId);
		return deptMapper.loadByTpId(orgId, tpId);
	}

	@Override
	public boolean removeByTpId(Long orgId, Long tpId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(tpId);
		deptMapper.deleteByTpId(orgId, tpId);
		return true;
	}

	@Override
	public boolean removeByDeptId(Long orgId, Long deptId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deptId);
		deptMapper.deleteByDeptId(orgId, deptId);
		return true;
	}


	@Override
	public List<Long> findTpId(Long orgId) {
		BizAssert.notNull(orgId);
		return deptMapper.findTpId(orgId);
	}

	@Override
	public List<Long> findTpIdByIds(Long orgId, List<Long> deptIds) {
		BizAssert.notNull(orgId);
		BizAssert.notEmpty(deptIds);
		return deptMapper.findTpIdByIds(orgId, deptIds);
	}

	@Override
	public List<Dept> findAllChildren(Long orgId, Long parentId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(parentId);
		List<Long> childrenIds = this.findAllChildrenId(orgId, parentId);
		if(CollectionUtils.isEmpty(childrenIds)) return new ArrayList<>();
		return deptMapper.findDeptByIds(orgId, childrenIds);
	}

	@Override
	public Dept getDept(Long orgId, Long deptId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deptId);
		return deptMapper.load(orgId, deptId);
	}

	@Override
	public boolean updateDeptName(Long orgId, Long deptId, String deptName) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deptId);
		BizAssert.notBlank(deptName);
		deptMapper.updateDeptName(orgId, deptId, deptName);
		return true;
	}

}
