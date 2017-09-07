package com.moredian.fishnet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.device.domain.CertificationDevice;
import com.moredian.fishnet.device.request.CertificationDeviceSearchRequest;
import com.moredian.fishnet.device.response.CertificationDeviceInfo;

public interface CertificationDeviceManager {
	
	PaginationDomain<CertificationDevice> findDevice(Pagination<CertificationDeviceInfo> pagination, CertificationDeviceSearchRequest request);

}
