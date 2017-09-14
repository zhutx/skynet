package com.moredian.skynet.device.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.request.DeviceActiveRequest;
import com.moredian.skynet.device.request.DeviceAddRequest;
import com.moredian.skynet.device.request.DeviceQueryRequest;
import com.moredian.skynet.device.request.DeviceUpdateRequest;
import com.moredian.skynet.device.response.DeviceActiveResponse;

public interface DeviceManager {
	
	Device getDeviceBySn(String deviceSn);
	
	Device getDeviceById(Long orgId, Long deviceId);
	
	boolean updateXmlConfig(Long orgId, Long deviceId, String xml);
	
	String getXmlConfig(String deviceSn);
	
	Long addDeviceForOld(DeviceAddRequest request);
	
	String genActiveCode(Long orgId, Long deviceId);
	
	Long addDevice(DeviceAddRequest request);
	
	boolean updateDevice(DeviceUpdateRequest request);
	
	boolean deleteDeviceById(Long orgId, Long deviceId);
	
	int getCount(DeviceQueryRequest request);
	
	PaginationDomain<Device> findPaginationDevice(DeviceQueryRequest request, Pagination<DeviceInfo> pagination);
	
	DeviceActiveResponse activeDevice(DeviceActiveRequest request);

	boolean activeDevice(Long orgId, String deviceSn);
	
	List<String> findDeviceNameByIds(List<Long> deviceIdList);
	
	List<Device> findDeviceByType(Long orgId, Integer deviceType);
	
	List<Device> findDeviceByTypes(Long orgId, List<Integer> deviceTypes);
	
	List<Long> findDeviceIdByType(Long orgId, Integer deviceType);
	
}
