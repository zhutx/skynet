package com.moredian.skynet.device.service;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.request.CertificationDeviceSearchRequest;
import com.moredian.skynet.device.response.CertificationDeviceInfo;

public interface CertificationDeviceService {
	
	Pagination<CertificationDeviceInfo> findDevice(Pagination<CertificationDeviceInfo> pagination, CertificationDeviceSearchRequest request);

}
