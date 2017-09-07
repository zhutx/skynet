package com.moredian.fishnet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.device.domain.InventoryDevice;
import com.moredian.fishnet.device.model.WhiteDeviceInfo;
import com.moredian.fishnet.device.request.WhiteDeviceAddRequest;
import com.moredian.fishnet.device.request.WhiteDeviceQueryRequest;

public interface WhiteDeviceManager {
	
	PaginationDomain<InventoryDevice> findPaginationWhiteDevice(WhiteDeviceQueryRequest request, Pagination<WhiteDeviceInfo> pagination);
	
	boolean addWhiteDevice(WhiteDeviceAddRequest request);
	
	boolean deleteWhiteDevice(String deviceSn);

}
