package com.moredian.skynet.org.mapper;

import com.moredian.skynet.org.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper {
	
	int insert(Dept dept);
	
	int update(Dept dept);
	
	List<Dept> findDepts(@Param("orgId")Long orgId, @Param("parentId")Long parentId);
	
	List<String> findDeptNamesByIds(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList);
	
	List<String> findDeptNamesWithoutRootByIds(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList);
	
	Dept loadByName(@Param("orgId")Long orgId, @Param("deptName")String deptName);
	
	List<Dept> findDeptByIds(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList);
	
	int count(Long orgId);
	
	List<Long> findDeptIdByDeptName(@Param("orgId")Long orgId, @Param("deptName")String deptName);
	
	Dept loadRoot(@Param("orgId")Long orgId);
	
	Dept loadByTpId(@Param("orgId")Long orgId, @Param("tpId")Long tpId);
	
	int deleteByTpId(@Param("orgId")Long orgId, @Param("tpId")Long tpId);

	int deleteByDeptId(@Param("orgId")Long orgId, @Param("deptId")Long deptId);
	
	List<Long> findTpId(@Param("orgId")Long orgId);
	
	List<Long> findTpIdByIds(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList);
	
	Dept load(@Param("orgId")Long orgId, @Param("deptId")Long deptId);
	
}
