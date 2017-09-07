package com.moredian.fishnet.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.device.domain.DeployDetail;

@Mapper
public interface DeployDetailMapper {
	
	int insert(DeployDetail deployDetail);
	
	List<DeployDetail> findByDeployId(@Param("deployId")Long deployId, @Param("orgId")Long orgId);
	
	int deleteOne(@Param("libraryCode")String libraryCode, @Param("deployId")Long deployId, @Param("orgId")Long orgId);
	
}
