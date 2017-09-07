package com.moredian.fishnet.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moredian.fishnet.device.domain.DeviceConfig;

@Mapper
public interface DeviceConfigMapper {
	
	int insertOrUpdate(DeviceConfig deviceConfig);
	
	DeviceConfig load(String deviceSn);
	
	int deleteBySn(String deviceSn);

}
