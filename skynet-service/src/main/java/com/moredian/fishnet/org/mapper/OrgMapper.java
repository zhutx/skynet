package com.moredian.fishnet.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.org.domain.Org;
import com.moredian.fishnet.org.domain.OrgQueryCondition;

@Mapper
public interface OrgMapper {
	
	List<Long> findAllId();
	
	int insert(Org org);
	
	Org load(Long orgId);
	
	int update(Org org);
	
	int updateStatus(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	int getTotalCountByCondition(OrgQueryCondition condition);
	
	List<Org> findPaginationByCondition(OrgQueryCondition condition);
	
	List<Org> findChildren(@Param("orgIdList")List<Long> orgIdList);
	
	Org loadExist(@Param("orgType")Integer orgType, @Param("orgName")String orgName);
	
	Org loadByName(String orgName);
	
	Org getOneOrg(@Param("orgType")Integer orgType, @Param("orgLevel")Integer orgLevel, @Param("areaCode")Integer areaCode);
	
}
