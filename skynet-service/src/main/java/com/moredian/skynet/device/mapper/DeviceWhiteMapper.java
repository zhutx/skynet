package com.moredian.skynet.device.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moredian.skynet.device.domain.DeviceWhite;

@Mapper
public interface DeviceWhiteMapper {
	
	int insert(DeviceWhite deviceWhite);
	
}
