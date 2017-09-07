package com.moredian.fishnet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.member.domain.GroupRange;

@Mapper
public interface GroupRangeMapper {
	
	int delete(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("rangeType")Integer groupRangeType, @Param("rangeId")Long rangeId);
	
	int insert(GroupRange groupRange);
	
	List<Long> findRangeIdByGroupId(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("rangeType")Integer groupRangeType);
	
	int deleteByGroupId(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	List<Long> findGroupIdByRanges(@Param("orgId")Long orgId, @Param("rangeType")Integer groupRangeType, @Param("rangeIdList")List<Long> rangeIdList);
	
	int deleteByRangeId(@Param("orgId")Long orgId, @Param("rangeId")Long rangeId, @Param("rangeType")Integer groupRangeType);

}
