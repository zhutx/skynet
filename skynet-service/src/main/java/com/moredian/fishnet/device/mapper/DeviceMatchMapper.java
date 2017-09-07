package com.moredian.fishnet.device.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.device.domain.DeviceMatch;

@Mapper
public interface DeviceMatchMapper {
	
	int insert(DeviceMatch deviceMatch);
	
	DeviceMatch loadByCameraId(@Param("cameraId") Long cameraId, @Param("orgId")Long orgId);

	DeviceMatch loadByBoxId(@Param("boxId") Long boxId, @Param("orgId")Long orgId);

	int delete(DeviceMatch deviceMatch);
	
}
