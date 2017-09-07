package com.moredian.fishnet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.member.domain.DeptRelation;
import com.moredian.fishnet.member.domain.DeptRelationQueryCondition;

@Mapper
public interface DeptRelationMapper {
	
	int insertOrUpdate(DeptRelation deptRelation);
	
	List<Long> findMemberIdsByDeptId(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("status")Integer status);
	
	List<Long> findMemberIdByDepts(@Param("orgId")Long orgId, @Param("deptIdList")List<Long> deptIdList, @Param("status")Integer status);
	
	List<Long> findDeptIdByMemberId(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("status")Integer status);
	
	List<Long> findLeaderIds(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("leaderFlag")Integer leaderFlag, @Param("status")Integer status);
	
	int updateLeaderFlag(@Param("orgId")Long orgId, @Param("deptId")Long deptId, @Param("memberId")Long memberId, @Param("leaderFlag")Integer leaderFlag);
	
	int updateStatusByMember(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("status")Integer status);
	
	int findRelationCount(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("deptId")Long deptId);
	
	int updateOneStatus(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("deptId")Long deptId, @Param("status")Integer status);
	
	int getTotalCountByCondition(DeptRelationQueryCondition condition);
	
	List<DeptRelation> findPaginationByCondition(DeptRelationQueryCondition condition);
	
	int fillDeptIdByTpDeptId(@Param("orgId")Long orgId, @Param("tpDeptId")Long tpDeptId, @Param("deptId")Long deptId);
	
}
