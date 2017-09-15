package com.moredian.skynet.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.device.domain.DeviceGroup;

@Mapper
public interface DeviceGroupMapper {
	
	int insert(DeviceGroup deviceGroup);
	
	int deleteByGroup(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	List<Long> findDeviceIdByGroupIds(@Param("orgId")Long orgId, @Param("groupIdList")List<Long> groupIdList);
	
	List<Long> findGroupIdByDeviceId(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId);
	
	List<Long> findDeviceIdByGroupId(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	int delete(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId, @Param("groupId") Long groupId);
	
	int deleteByDevice(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId);

}
