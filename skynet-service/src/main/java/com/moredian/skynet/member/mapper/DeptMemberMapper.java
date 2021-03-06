package com.moredian.skynet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.member.domain.DeptMember;
import com.moredian.skynet.member.domain.DeptMemberQueryCondition;

@Mapper
public interface DeptMemberMapper {
	
	int insertOrUpdate(DeptMember deptMember);
	
	List<Long> findMemberIdsByDeptId(@Param("orgId")Long orgId, @Param("deptId")Long deptId);
	
	List<Long> findMemberIdByDepts(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList, @Param("status")Integer status);
	
	List<Long> findDeptIdByMemberId(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("status")Integer status);
	
	List<Long> findLeaderIds(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("leaderFlag")Integer leaderFlag, @Param("status")Integer status);
	
	int updateLeaderFlag(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("memberId")Long memberId, @Param("leaderFlag")Integer leaderFlag);
	
	//int updateStatusByMember(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("status")Integer status);
	
	int findRelationCount(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("deptId")Long deptId);
	
	//int updateOneStatus(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("deptId")Long deptId, @Param("status")Integer status);
	
	int getTotalCountByCondition(DeptMemberQueryCondition condition);
	
	List<DeptMember> findPaginationByCondition(DeptMemberQueryCondition condition);
	
	int fillDeptIdByTpDeptId(@Param("orgId")Long orgId, @Param("tpDeptId")Long tpDeptId, @Param("deptId")Long deptId);
	
	int deleteByMemberId(@Param("orgId")Long orgId, @Param("memberId")Long memberId);
	
	int deleteOne(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("memberId")Long memberId);
	
}
