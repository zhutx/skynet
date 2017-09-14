package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.CertificationDevice;
import com.moredian.skynet.device.request.CertificationDeviceSearchRequest;
import com.moredian.skynet.device.response.CertificationDeviceInfo;

public interface CertificationDeviceManager {
	
	PaginationDomain<CertificationDevice> findDevice(Pagination<CertificationDeviceInfo> pagination, CertificationDeviceSearchRequest request);

}
