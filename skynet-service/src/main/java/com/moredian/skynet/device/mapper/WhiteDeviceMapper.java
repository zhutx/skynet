package com.moredian.skynet.device.mapper;

import com.moredian.skynet.device.domain.InventoryDevice;
import com.moredian.skynet.device.domain.WhiteDevice;
import com.moredian.skynet.device.domain.WhiteDeviceQueryCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WhiteDeviceMapper {
	
	int getTotalCountByCondition(WhiteDeviceQueryCondition condition);
	
	List<WhiteDevice> findPaginationByCondition(WhiteDeviceQueryCondition condition);
	
	int insert(InventoryDevice whiteDevice);
	
	int delete(String deviceSn);

}
