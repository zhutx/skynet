package com.moredian.skynet.org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.org.domain.ObjectSeed;

@Mapper
public interface ObjectSeedMapper {
	
	public static final int ORG_SEED_CODE = 1;
	
	int insert(ObjectSeed objectSeed);
	
	int updateObjectSeed(@Param("seedId")Long seedId, @Param("oldObjectSeed")Long oldObjectSeed, @Param("objectSeed")Long objectSeed);
	
	ObjectSeed getByRangeAndCode(@Param("objectRange")Integer objectRange, @Param("objectCode")Integer objectCode);

}