package com.moredian.fishnet.device.service;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.device.request.CertificationDeviceSearchRequest;
import com.moredian.fishnet.device.response.CertificationDeviceInfo;

public interface CertificationDeviceService {
	
	Pagination<CertificationDeviceInfo> findDevice(Pagination<CertificationDeviceInfo> pagination, CertificationDeviceSearchRequest request);

}
