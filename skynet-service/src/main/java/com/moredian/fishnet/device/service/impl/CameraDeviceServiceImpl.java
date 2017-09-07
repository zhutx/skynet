package com.moredian.fishnet.device.service.impl;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.domain.DeviceMatch;
import com.moredian.fishnet.device.manager.CameraDeviceManager;
import com.moredian.fishnet.device.manager.DeviceMatchManager;
import com.moredian.fishnet.device.model.CameraDeviceExtendsInfo;
import com.moredian.fishnet.device.model.CameraDeviceInfo;
import com.moredian.fishnet.device.request.*;
import com.moredian.fishnet.device.service.CameraDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SI
public class CameraDeviceServiceImpl implements CameraDeviceService {
	
	@Autowired
	private CameraDeviceManager cameraDeviceManager;

	@Autowired
	private DeviceMatchManager deviceMatchManager;
	
	@Override
	public ServiceResponse<Long> addDevice(CameraDeviceAddRequest request) {
		Device device = cameraDeviceManager.addDevice(request);
		return new ServiceResponse<Long>(true, null, device.getDeviceId());
	}

	@Override
	public ServiceResponse<Boolean> updateDevice(CameraDeviceUpdateRequest request) {
		boolean result = cameraDeviceManager.updateDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteDeviceById(Long orgId, Long deviceId) {
		boolean result = cameraDeviceManager.deleteDeviceById(orgId, deviceId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public CameraDeviceInfo getDeviceById(Long orgId, Long deviceId) {
		Device device = cameraDeviceManager.getDeviceById(orgId, deviceId);
		return deviceToCameraDeviceInfo(device);
	}
	
	private CameraDeviceInfo deviceToCameraDeviceInfo(Device device) {
		if(device == null) return null;
		CameraDeviceInfo cameraDeviceInfo = new CameraDeviceInfo();
		cameraDeviceInfo.setDeviceId(device.getDeviceId());
		cameraDeviceInfo.setOrgId(device.getOrgId());
		cameraDeviceInfo.setPositionId(device.getPositionId());
		cameraDeviceInfo.setDeviceName(device.getDeviceName());
		
		CameraDeviceExtendsInfo deviceExtendsInfo = JsonUtils.fromJson(CameraDeviceExtendsInfo.class, device.getExtendsInfo());
		cameraDeviceInfo.setProviderType(deviceExtendsInfo.getProvider_type());
		cameraDeviceInfo.setCameraNvr(deviceExtendsInfo.getCamera_nvr());
		cameraDeviceInfo.setCameraIp(deviceExtendsInfo.getCamera_ip());
		cameraDeviceInfo.setCameraUsername(deviceExtendsInfo.getCamera_username());
		cameraDeviceInfo.setCameraPassword(deviceExtendsInfo.getCamera_password());
		cameraDeviceInfo.setCameraStream(deviceExtendsInfo.getCamera_stream());
		cameraDeviceInfo.setCameraResolution(deviceExtendsInfo.getCamera_resolution());
		
		cameraDeviceInfo.setStatus(device.getStatus());
		cameraDeviceInfo.setGmtCreate(device.getGmtCreate());
		return cameraDeviceInfo;
	}
	
	private List<CameraDeviceInfo> deviceListToCameraDeviceInfoList(List<Device> deviceList) {
		if (deviceList == null) return null;
		
		List<CameraDeviceInfo> response = new ArrayList<>();
		for(Device device : deviceList) {
			response.add(deviceToCameraDeviceInfo(device));
		}
		
		return response;
	}
	
	private Pagination<CameraDeviceInfo> paginationDeviceToPaginationCameraDeviceInfo(PaginationDomain<Device> fromPagination) {
		Pagination<CameraDeviceInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<CameraDeviceInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceListToCameraDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<CameraDeviceInfo> findPaginationDevice(CameraDeviceQueryRequest request,
			Pagination<CameraDeviceInfo> pagination) {
		PaginationDomain<Device> paginationDevice = cameraDeviceManager.findPaginationDevice(request, pagination);
		return this.paginationDeviceToPaginationCameraDeviceInfo(paginationDevice);
	}

	@Override
	public ServiceResponse<Boolean> bindCameraWithDevice(Long orgId, Long cameraId, Long deviceId) {
		DeviceMatchRequest  request=new DeviceMatchRequest();
		request.setOrgId(orgId);
		request.setBoxId(deviceId);
		request.setCameraId(cameraId);
		boolean result= deviceMatchManager.matchDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> unBindCameraWithDevice(Long orgId, Long cameraId, Long deviceId) {
		DeviceMatchRequest  request=new DeviceMatchRequest();
		request.setOrgId(orgId);
		request.setBoxId(deviceId);
		request.setCameraId(cameraId);
		boolean result= deviceMatchManager.disMatchDevice(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateBoxCamera(BoxUpdateRequest boxUpdateRequest) {
		boolean result=deviceMatchManager.updateBoxCamera(boxUpdateRequest);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public CameraDeviceInfo getCameraDeviceByBoxId(Long boxId, Long orgId) {
		DeviceMatch deviceMatch=deviceMatchManager.getByBoxId(boxId,orgId);
		if(deviceMatch!=null && deviceMatch.getCameraId()!=null){
			Device device = cameraDeviceManager.getDeviceById(orgId, deviceMatch.getCameraId());
			return deviceToCameraDeviceInfo(device);
		}else{
			return null;
		}

	}
}
