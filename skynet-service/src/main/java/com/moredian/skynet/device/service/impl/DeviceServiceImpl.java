package com.moredian.skynet.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.ActivityManager;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.manager.DeviceMonitorManager;
import com.moredian.skynet.device.model.DeviceImageVersion;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.model.DeviceStateInfo;
import com.moredian.skynet.device.request.DeviceActiveRequest;
import com.moredian.skynet.device.request.DeviceAddRequest;
import com.moredian.skynet.device.request.DeviceQueryRequest;
import com.moredian.skynet.device.request.DeviceUpdateRequest;
import com.moredian.skynet.device.request.RebootRequest;
import com.moredian.skynet.device.request.StatusRequest;
import com.moredian.skynet.device.request.TransferRequest;
import com.moredian.skynet.device.request.UpgradeRequest;
import com.moredian.skynet.device.response.DeviceActiveResponse;
import com.moredian.skynet.device.service.DeviceService;

@SI
public class DeviceServiceImpl implements DeviceService {
	
	@Autowired
	private DeviceMonitorManager deviceMonitorManager;

	@Autowired
	private DeviceManager deviceManager;

	@Autowired
	private ActivityManager activityManager;

	@Autowired
	private DeviceMatchManager deviceMatchManager;

	@Value("${spider.web.address}")
	private String spiderWebAddress;
	
	@Override
	public DeviceStateInfo getStatus(String serialNumber) {
		return deviceMonitorManager.getStatus(serialNumber);
	}

	@Override
	public List<DeviceStateInfo> getStatusList(StatusRequest request) {
		return deviceMonitorManager.getStatusList(request);
	}

	@Override
	public DeviceStateInfo upgradeDevice(UpgradeRequest request) {
		return deviceMonitorManager.upgradeDevice(request);
	}
	
	@Override
	public DeviceImageVersion getDeviceVersion(String serialNumber) {
		return deviceMonitorManager.getDeviceVersion(serialNumber);
	}

	@Override
	public DeviceStateInfo rebootDevice(RebootRequest request) {
		return deviceMonitorManager.rebootDevice(request);
	}

	@Override
	public DeviceStateInfo transferDevice(TransferRequest request) {
		return deviceMonitorManager.transferDevice(request);
	}


	
	private DeviceInfo deviceToDeviceInfo(Device device) {
		if (device == null) return null;
		return BeanUtils.copyProperties(DeviceInfo.class, device);
	}

	@Override
	public DeviceInfo getDeviceById(Long orgId, Long deviceId) {
		Device device = deviceManager.getDeviceById(orgId, deviceId);
		return deviceToDeviceInfo(device);
	}

	@Override
	public DeviceInfo getDeviceBySn(String deviceSn) {
		Device device = deviceManager.getDeviceBySn(deviceSn);
		return deviceToDeviceInfo(device);
	}

	@Override
	public ServiceResponse<Boolean> updateXmlConfig(Long orgId, Long deviceId, String xml) {
		boolean result = deviceManager.updateXmlConfig(orgId, deviceId, xml);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public String getXmlConfig(String deviceSn) {
		return deviceManager.getXmlConfig(deviceSn);
	}
	
	@Override
	public ServiceResponse<Long> addDeviceForOld(DeviceAddRequest request) {
		Long deviceId = deviceManager.addDeviceForOld(request);
		return new ServiceResponse<Long>(true, null, deviceId);
	}

	@Override
	public String genActiveCode(Long orgId, Long deviceId) {
		return deviceManager.genActiveCode(orgId, deviceId);
	}

	@Override
	public ServiceResponse<Long> addDevice(DeviceAddRequest request) {
		Long deviceId = deviceManager.addDevice(request);
		return new ServiceResponse<Long>(true, null, deviceId);
	}

	@Override
	public ServiceResponse<Boolean> updateDevice(DeviceUpdateRequest request) {
		boolean result = deviceManager.updateDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteDeviceById(Long orgId, Long deviceId) {
		boolean result = deviceManager.deleteDeviceById(orgId, deviceId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteDeviceFromDase(String serialNumber) {
		return activityManager.deleteDeviceFromBaseDevice(serialNumber);
	}

	private List<DeviceInfo> deviceListToDeviceInfoList(List<Device> deviceList) {
		if (deviceList == null) return null;
		
		List<DeviceInfo> response = new ArrayList<>();
		for(Device device : deviceList) {
			response.add(deviceToDeviceInfo(device));
		}
		
		return response;
	}
	
	private Pagination<DeviceInfo> paginationDeviceToPaginationDeviceInfo(PaginationDomain<Device> fromPagination) {
		Pagination<DeviceInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<DeviceInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceListToDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<DeviceInfo> findPaginationDevice(Pagination<DeviceInfo> pagination, DeviceQueryRequest request) {
		
		PaginationDomain<Device> paginationDevice = deviceManager.findPaginationDevice(request, pagination);
		
		return paginationDeviceToPaginationDeviceInfo(paginationDevice);
		
	}

//	@Override
//	public Pagination<DeviceInfo> findPaginationDeviceByType(Pagination<DeviceInfo> pagination, DeviceQueryByTypeRequest request) {
//
//		PaginationDomain<Device> paginationDevice = deviceManager.findPaginationDevice(request, pagination);
//
//		return paginationDeviceToPaginationDeviceInfo(paginationDevice);
//
//	}


	@Override
	public ServiceResponse<DeviceActiveResponse> activeDevice(DeviceActiveRequest request) {
		DeviceActiveResponse result = deviceManager.activeDevice(request);
		return new ServiceResponse<DeviceActiveResponse>(true, null, result);
	}

	@Override
	public List<String> findDeviceNameByIds(List<Long> deviceIdList) {
		if(CollectionUtils.isEmpty(deviceIdList)) return new ArrayList<>();
		return deviceManager.findDeviceNameByIds(deviceIdList);
	}

	@Override
	public List<DeviceInfo> findDeviceByType(Long orgId, Integer deviceType) {
		List<Device> deviceList = deviceManager.findDeviceByType(orgId, deviceType);
		return this.deviceListToDeviceInfoList(deviceList);
	}

	@Override
	public List<DeviceInfo> findDeviceByTypes(Long orgId, List<Integer> deviceTypes) {
		List<Device> deviceList = deviceManager.findDeviceByTypes(orgId, deviceTypes);
		return this.deviceListToDeviceInfoList(deviceList);
	}

	@Override
	public List<Long> findDeviceIdByType(Long orgId, Integer deviceType) {
		return deviceManager.findDeviceIdByType(orgId, deviceType);
	}

	@Override
	public Boolean activeDeviceWithOrgIdAndDeviceSn(Long orgId, String deviceSn) {
		return deviceManager.activeDevice(orgId,deviceSn);
	}

}
