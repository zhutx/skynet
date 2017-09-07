package com.moredian.fishnet.org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.org.domain.OrgBiz;

@Mapper
public interface OrgBizMapper {
	
	int insert(OrgBiz orgBiz);
	
	OrgBiz loadByBizType(@Param("orgId")Long orgId, @Param("bizType")Integer bizType);
	
	int updateStatus(@Param("orgId")Long orgId, @Param("bizType")Integer bizType, @Param("status")Integer status);
	
	int countEnable(@Param("orgId")Long orgId, @Param("status")Integer status);

}
