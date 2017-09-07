package com.moredian.fishnet.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.device.domain.Deploy;
import com.moredian.fishnet.device.domain.DeployQueryCondition;

@Mapper
public interface DeployMapper {
	
	int insert(Deploy deploy);
	
	int update(Deploy deploy);
	
	int updateStatus(@Param("deployId")Long deployId, @Param("orgId")Long orgId, @Param("status")Integer status);
	
	Deploy load(@Param("deployId")Long deployId, @Param("orgId")Long orgId);
	
	Deploy loadByDevice(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId);
	
	int getTotalCountByCondition(DeployQueryCondition condition);
	
	List<Object> findPaginationByCondition(DeployQueryCondition condition);
	
	List<Deploy> findForStop(Integer status);
	
	List<Deploy> findForStart(Integer status);
	
	int updateStatusByDeviceId(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId, @Param("status")Integer status);
	
}
