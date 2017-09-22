package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.InventoryDevice;
import com.moredian.skynet.device.model.WhiteDeviceInfo;
import com.moredian.skynet.device.request.DeviceWhiteAddRequest;
import com.moredian.skynet.device.request.WhiteDeviceQueryRequest;

public interface WhiteDeviceManager {
	
	PaginationDomain<InventoryDevice> findPaginationWhiteDevice(WhiteDeviceQueryRequest request, Pagination<WhiteDeviceInfo> pagination);
	
	boolean addWhiteDevice(DeviceWhiteAddRequest request);
	
	boolean deleteWhiteDevice(String deviceSn);

}
