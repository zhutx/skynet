package com.moredian.skynet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.model.WhiteDeviceInfo;
import com.moredian.skynet.device.request.WhiteDeviceAddRequest;
import com.moredian.skynet.device.request.WhiteDeviceQueryRequest;

public interface WhiteDeviceService {
	
	Pagination<WhiteDeviceInfo> findPaginationWhiteDevice(Pagination<WhiteDeviceInfo> pagination, WhiteDeviceQueryRequest request);
	
	ServiceResponse<Boolean> addWhiteDevice(WhiteDeviceAddRequest request);
	
	ServiceResponse<Boolean> deleteWhiteDevice(String deviceSn);

}
