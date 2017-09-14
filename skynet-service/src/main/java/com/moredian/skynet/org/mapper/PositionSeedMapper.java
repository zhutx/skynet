package com.moredian.skynet.org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.org.domain.PositionSeed;

@Mapper
public interface PositionSeedMapper {
	
	int insert(PositionSeed positionSeed);
	
	int updateChildSeed(@Param("positionSeedId")Long positionSeedId, @Param("oldChildSeed")Integer oldChildSeed, @Param("childSeed")Integer childSeed);
	
	PositionSeed getByParentId(@Param("parentId")Long parentId, @Param("orgId")Long orgId);

}
