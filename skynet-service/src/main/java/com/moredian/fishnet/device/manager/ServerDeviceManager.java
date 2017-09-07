package com.moredian.fishnet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.model.ServerDeviceInfo;
import com.moredian.fishnet.device.request.ServerDeviceAddRequest;
import com.moredian.fishnet.device.request.ServerDeviceQueryRequest;
import com.moredian.fishnet.device.request.ServerDeviceUpdateRequest;

public interface ServerDeviceManager {
	
	Device addDevice(ServerDeviceAddRequest request);
	
	boolean updateDevice(ServerDeviceUpdateRequest request);
	
	boolean deleteDeviceById(Long orgId, Long deviceId);
	
	boolean deleteDeviceBySn(Long orgId, String deviceSn);
	
	Device getDeviceById(Long orgId, Long deviceId);
	
	Device getDeviceBySn(Long orgId, String deviceSn);
	
	Device getDeviceBySn(String deviceSn);
	
	PaginationDomain<Device> findPaginationDevice(ServerDeviceQueryRequest request, Pagination<ServerDeviceInfo> pagination);

}
