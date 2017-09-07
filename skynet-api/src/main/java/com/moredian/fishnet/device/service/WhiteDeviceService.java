package com.moredian.fishnet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.device.model.WhiteDeviceInfo;
import com.moredian.fishnet.device.request.WhiteDeviceAddRequest;
import com.moredian.fishnet.device.request.WhiteDeviceQueryRequest;

public interface WhiteDeviceService {
	
	Pagination<WhiteDeviceInfo> findPaginationWhiteDevice(Pagination<WhiteDeviceInfo> pagination, WhiteDeviceQueryRequest request);
	
	ServiceResponse<Boolean> addWhiteDevice(WhiteDeviceAddRequest request);
	
	ServiceResponse<Boolean> deleteWhiteDevice(String deviceSn);

}
