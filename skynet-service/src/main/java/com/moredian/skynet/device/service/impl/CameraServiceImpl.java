package com.moredian.skynet.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceMatch;
import com.moredian.skynet.device.manager.CameraManager;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.model.CameraDeviceExtendsInfo;
import com.moredian.skynet.device.model.CameraInfo;
import com.moredian.skynet.device.request.CameraAddRequest;
import com.moredian.skynet.device.request.CameraQueryRequest;
import com.moredian.skynet.device.request.CameraUpdateRequest;
import com.moredian.skynet.device.service.CameraService;

@SI
public class CameraServiceImpl implements CameraService {
	
	@Autowired
	private CameraManager cameraManager;

	@Autowired
	private DeviceMatchManager deviceMatchManager;
	
	@Override
	public ServiceResponse<Long> addDevice(CameraAddRequest request) {
		Device device = cameraManager.addDevice(request);
		return new ServiceResponse<Long>(true, null, device.getDeviceId());
	}

	@Override
	public ServiceResponse<Boolean> updateDevice(CameraUpdateRequest request) {
		boolean result = cameraManager.updateDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteDevice(Long orgId, Long deviceId) {
		boolean result = cameraManager.deleteDeviceById(orgId, deviceId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public CameraInfo getDeviceById(Long orgId, Long deviceId) {
		Device device = cameraManager.getDeviceById(orgId, deviceId);
		return deviceToCameraDeviceInfo(device);
	}
	
	private CameraInfo deviceToCameraDeviceInfo(Device device) {
		if(device == null) return null;
		CameraInfo cameraDeviceInfo = new CameraInfo();
		cameraDeviceInfo.setDeviceId(device.getDeviceId());
		cameraDeviceInfo.setPosition(device.getPosition());
		cameraDeviceInfo.setDeviceName(device.getDeviceName());
		
		CameraDeviceExtendsInfo deviceExtendsInfo = JsonUtils.fromJson(CameraDeviceExtendsInfo.class, device.getExtendsInfo());
		cameraDeviceInfo.setProviderType(deviceExtendsInfo.getProvider_type());
		cameraDeviceInfo.setNvr(deviceExtendsInfo.getCamera_nvr());
		cameraDeviceInfo.setIp(deviceExtendsInfo.getCamera_ip());
		cameraDeviceInfo.setUsername(deviceExtendsInfo.getCamera_username());
		cameraDeviceInfo.setPassword(deviceExtendsInfo.getCamera_password());
		cameraDeviceInfo.setVideoStream(deviceExtendsInfo.getCamera_stream());
		cameraDeviceInfo.setResolution(deviceExtendsInfo.getCamera_resolution());
		
		return cameraDeviceInfo;
	}
	
	private List<CameraInfo> deviceListToCameraDeviceInfoList(List<Device> deviceList) {
		if (deviceList == null) return null;
		
		List<CameraInfo> response = new ArrayList<>();
		for(Device device : deviceList) {
			response.add(deviceToCameraDeviceInfo(device));
		}
		
		return response;
	}
	
	private Pagination<CameraInfo> paginationDeviceToPaginationCameraDeviceInfo(PaginationDomain<Device> fromPagination) {
		Pagination<CameraInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<CameraInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceListToCameraDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<CameraInfo> findPaginationDevice(CameraQueryRequest request,
			Pagination<CameraInfo> pagination) {
		PaginationDomain<Device> paginationDevice = cameraManager.findPaginationDevice(request, pagination);
		return this.paginationDeviceToPaginationCameraDeviceInfo(paginationDevice);
	}

	@Override
	public CameraInfo getCameraDeviceByBoxId(Long boxId, Long orgId) {
		DeviceMatch deviceMatch=deviceMatchManager.getByBoxId(boxId,orgId);
		if(deviceMatch!=null && deviceMatch.getCameraId()!=null){
			Device device = cameraManager.getDeviceById(orgId, deviceMatch.getCameraId());
			return deviceToCameraDeviceInfo(device);
		}else{
			return null;
		}

	}

	@Override
	public ServiceResponse<Boolean> bindBox(Long orgId, Long cameraId, Long boxId) {
		boolean result = deviceMatchManager.matchDevice(orgId, cameraId, boxId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> unbindBox(Long orgId, Long cameraId) {
		boolean result = deviceMatchManager.unMatchDevice(orgId, cameraId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	
}
