package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.model.CameraDeviceInfo;
import com.moredian.skynet.device.request.CameraDeviceAddRequest;
import com.moredian.skynet.device.request.CameraDeviceQueryRequest;
import com.moredian.skynet.device.request.CameraDeviceUpdateRequest;

public interface CameraDeviceManager {
	
	Device addDevice(CameraDeviceAddRequest request);
	
	boolean updateDevice(CameraDeviceUpdateRequest request);
	
	boolean deleteDeviceById(Long orgId, Long deviceId);
	
	Device getDeviceById(Long orgId, Long deviceId);
	
	PaginationDomain<Device> findPaginationDevice(CameraDeviceQueryRequest request, Pagination<CameraDeviceInfo> pagination);


}
