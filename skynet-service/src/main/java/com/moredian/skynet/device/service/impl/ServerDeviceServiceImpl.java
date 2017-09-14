package com.moredian.skynet.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.ServerDeviceManager;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.model.ServerDeviceExtendsInfo;
import com.moredian.skynet.device.model.ServerDeviceInfo;
import com.moredian.skynet.device.request.ServerDeviceAddRequest;
import com.moredian.skynet.device.request.ServerDeviceQueryRequest;
import com.moredian.skynet.device.request.ServerDeviceUpdateRequest;
import com.moredian.skynet.device.service.ServerDeviceService;

@SI
public class ServerDeviceServiceImpl implements ServerDeviceService {
	
	@Autowired
	private ServerDeviceManager serverDeviceManager;
	
	private DeviceInfo deviceToDeviceInfo(Device device) {
		return BeanUtils.copyProperties(DeviceInfo.class, device);
	}
	
	@Override
	public ServiceResponse<DeviceInfo> addDevice(ServerDeviceAddRequest request) {
		Device device = serverDeviceManager.addDevice(request);
		return new ServiceResponse<DeviceInfo>(true, null, deviceToDeviceInfo(device));
	}

	@Override
	public ServiceResponse<Boolean> updateDevice(ServerDeviceUpdateRequest request) {
		boolean result = serverDeviceManager.updateDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> deleteDeviceById(Long orgId, Long deviceId) {
		boolean result = serverDeviceManager.deleteDeviceById(orgId, deviceId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> deleteDeviceBySn(Long orgId, String deviceSn) {
		boolean result = serverDeviceManager.deleteDeviceBySn(orgId, deviceSn);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServerDeviceInfo getDeviceById(Long orgId, Long deviceId) {
		Device device = serverDeviceManager.getDeviceById(orgId, deviceId);
		return deviceToServerDeviceInfo(device);
	}
	
	@Override
	public ServerDeviceInfo getDeviceBySn(Long orgId, String deviceSn) {
		Device device = serverDeviceManager.getDeviceBySn(orgId, deviceSn);
		return deviceToServerDeviceInfo(device);
	}
	
	@Override
	public ServerDeviceInfo getDeviceOnlyBySn(String deviceSn) {
		Device device = serverDeviceManager.getDeviceBySn(deviceSn);
		return deviceToServerDeviceInfo(device);
	}
	
	private ServerDeviceInfo deviceToServerDeviceInfo(Device device) {
		if(device == null) return null;
		ServerDeviceInfo serverDeviceInfo = new ServerDeviceInfo();
		serverDeviceInfo.setDeviceId(device.getDeviceId());
		serverDeviceInfo.setOrgId(device.getOrgId());
		serverDeviceInfo.setDeviceName(device.getDeviceName());
		serverDeviceInfo.setDeviceSn(device.getDeviceSn());
		
		ServerDeviceExtendsInfo deviceExtendsInfo = JsonUtils.fromJson(ServerDeviceExtendsInfo.class, device.getExtendsInfo());
		serverDeviceInfo.setHost(deviceExtendsInfo.getC_host());
		serverDeviceInfo.setPort(deviceExtendsInfo.getC_port());
		serverDeviceInfo.setOuterHost(deviceExtendsInfo.getC_outer_host());
		serverDeviceInfo.setOuterPort(deviceExtendsInfo.getC_outer_port());
		serverDeviceInfo.setTpHost(deviceExtendsInfo.getC_tp_host());
		serverDeviceInfo.setTpPort(deviceExtendsInfo.getC_tp_port());
		serverDeviceInfo.setMaxOrgNum(deviceExtendsInfo.getC_rd_maxorgnum());
		serverDeviceInfo.setMaxPersonNum(deviceExtendsInfo.getC_rd_maxpersonnum());
		
		serverDeviceInfo.setStatus(device.getStatus());
		serverDeviceInfo.setGmtCreate(device.getGmtCreate());
		return serverDeviceInfo;
	}
	
	public List<ServerDeviceInfo> deviceListToServerDeviceInfoList(List<Device> deviceList) {
		if (deviceList == null) return null;
		
		List<ServerDeviceInfo> response = new ArrayList<>();
		for(Device device : deviceList) {
			response.add(deviceToServerDeviceInfo(device));
		}
		
		return response;
	}
	
	public Pagination<ServerDeviceInfo> paginationDeviceToPaginationServerDeviceInfo(PaginationDomain<Device> fromPagination) {
		Pagination<ServerDeviceInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<ServerDeviceInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceListToServerDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}
	
	@Override
	public Pagination<ServerDeviceInfo> findPaginationDevice(
			ServerDeviceQueryRequest request, Pagination<ServerDeviceInfo> pagination) {
		PaginationDomain<Device> paginationDevice = serverDeviceManager.findPaginationDevice(request, pagination);
		return this.paginationDeviceToPaginationServerDeviceInfo(paginationDevice);
	}

}
