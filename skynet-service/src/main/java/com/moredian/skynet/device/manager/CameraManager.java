package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.model.CameraInfo;
import com.moredian.skynet.device.request.CameraAddRequest;
import com.moredian.skynet.device.request.CameraQueryRequest;
import com.moredian.skynet.device.request.CameraUpdateRequest;

public interface CameraManager {
	
	Device addDevice(CameraAddRequest request);
	
	boolean updateDevice(CameraUpdateRequest request);
	
	boolean deleteDeviceById(Long orgId, Long deviceId);
	
	Device getDeviceById(Long orgId, Long deviceId);
	
	PaginationDomain<Device> findPaginationDevice(CameraQueryRequest request, Pagination<CameraInfo> pagination);


}
