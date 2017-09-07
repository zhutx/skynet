package com.moredian.fishnet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.member.domain.UserRelation;

@Mapper
public interface UserRelationMapper {
	
	UserRelation loadOne(@Param("orgId")Long orgId, @Param("userId")Long userId, @Param("positionId")Long positionId);
	
	List<Long> findUserId(@Param("orgId")Long orgId, @Param("positionId")Long positionId, @Param("userRelationType")Integer userRelationType);
	
	int updateRelation(@Param("orgId")Long orgId, @Param("userRelationId")Long userRelationId, @Param("userRelationType")Integer userRelationType);
	
	int disableStatus(@Param("orgId")Long orgId, @Param("userRelationId")Long userRelationId);
	
	List<UserRelation> findVisibleRelation(@Param("orgId")Long orgId, @Param("positionId")Long positionId, @Param("userRelationType")Integer userRelationType);
	
	int insertOrUpdate(UserRelation userRelation);
	
	List<Long> findReceptionist(@Param("orgId")Long orgId, @Param("userRelationType")Integer userRelationType);

	int deleteReceptionist(@Param("orgId")Long orgId, @Param("userId")Long memberId);
	
}
