package com.moredian.fishnet.device.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.model.DeviceInfo;
import com.moredian.fishnet.device.request.DeviceActiveRequest;
import com.moredian.fishnet.device.request.DeviceAddRequest;
import com.moredian.fishnet.device.request.DeviceQueryRequest;
import com.moredian.fishnet.device.request.DeviceUpdateRequest;
import com.moredian.fishnet.device.response.DeviceActiveResponse;

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
