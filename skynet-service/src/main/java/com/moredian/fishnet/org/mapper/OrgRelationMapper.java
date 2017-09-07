package com.moredian.fishnet.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.org.domain.OrgRelation;

@Mapper
public interface OrgRelationMapper {
	
	int insert(OrgRelation orgRelation);
	
	int delete(@Param("orgId")Long orgId, @Param("relationOrgId")Long relationOrgId, @Param("relationType")Integer relationType);
	
	List<Long> findChildrenIds(@Param("orgId")Long orgId, @Param("relationType")Integer relationType);
	
	Long getParentId(@Param("relationOrgId")Long relationOrgId, @Param("relationType")Integer relationType);
	
}
