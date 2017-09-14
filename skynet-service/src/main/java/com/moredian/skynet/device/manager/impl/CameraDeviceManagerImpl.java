package com.moredian.skynet.device.manager.impl;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceMatch;
import com.moredian.skynet.device.domain.DeviceQueryCondition;
import com.moredian.skynet.device.enums.DeviceErrorCode;
import com.moredian.skynet.device.enums.DeviceStatus;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.CameraDeviceManager;
import com.moredian.skynet.device.manager.CloudeyeDeviceSyncProxy;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.mapper.DeviceMapper;
import com.moredian.skynet.device.model.CameraDeviceExtendsInfo;
import com.moredian.skynet.device.model.CameraDeviceInfo;
import com.moredian.skynet.device.request.CameraDeviceAddRequest;
import com.moredian.skynet.device.request.CameraDeviceQueryRequest;
import com.moredian.skynet.device.request.CameraDeviceUpdateRequest;
import com.moredian.skynet.device.request.DeviceMatchRequest;
import com.moredian.skynet.device.utils.RtspUtil;
import com.moredian.skynet.org.response.PositionInfo;
import com.moredian.skynet.org.service.PositionService;
import com.moredian.idgenerator.service.IdgeneratorService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CameraDeviceManagerImpl implements CameraDeviceManager {
	
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private CloudeyeDeviceSyncProxy cloudeyeDeviceSyncFacade;
	@SI
	private IdgeneratorService idgeneratorService;

	@Autowired
	private DeviceMatchManager deviceMatchManager;

	@SI
	private PositionService positionService;
	
	private void fillFieldForCamera(Device device, CameraDeviceExtendsInfo extendsInfo){
		Map<String, String> map = RtspUtil.parseVideoStream(extendsInfo.getProvider_type(), extendsInfo.getCamera_stream());
		if(extendsInfo.getCamera_ip() == null && map.containsKey(RtspUtil.KEY_IP)) {
			extendsInfo.setCamera_ip(map.get(RtspUtil.KEY_IP));
		}
		if(extendsInfo.getCamera_username() == null && map.containsKey(RtspUtil.KEY_USERNAME)) {
			extendsInfo.setCamera_username(map.get(RtspUtil.KEY_USERNAME));
		}
		if(extendsInfo.getCamera_password() == null && map.containsKey(RtspUtil.KEY_PASSWORD)) {
			extendsInfo.setCamera_password(map.get(RtspUtil.KEY_PASSWORD));
		}
	}
	
	private Device cameraDeviceAddRequestToDevice(CameraDeviceAddRequest request) {
		if (request == null) return null;
		Device device = new Device();
		device.setOrgId(request.getOrgId());
		device.setDeviceName(DeviceType.CAMERA.getName());
		CameraDeviceExtendsInfo extendsInfo = new CameraDeviceExtendsInfo();
		extendsInfo.setProvider_type(request.getProviderType());
		extendsInfo.setCamera_nvr(request.getCameraNvr());
		extendsInfo.setCamera_ip(request.getCameraIp());
		extendsInfo.setCamera_username(request.getCameraUsername());
		extendsInfo.setCamera_password(request.getCameraPassword());
		extendsInfo.setCamera_stream(request.getCameraStream());
		extendsInfo.setCamera_resolution(request.getCameraResolution());
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		device.setDeviceType(DeviceType.CAMERA.getValue());
		device.setStatus(DeviceStatus.UNACTIVE.getValue());
		this.fillFieldForCamera(device, extendsInfo); // 摄像机设备设置额外属性值
		
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		return device;
	}
	
	private void deviceAutoActive(Device device){
		device.setStatus(DeviceStatus.USABLE.getValue());
		device.setActiveTime(new Date());
		deviceMapper.update(device);
	}
	
	private Long getDeviceLogId() {
		return idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.DeviceLog").getData();
	}

	@Override
	@Transactional
	public Device addDevice(CameraDeviceAddRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getProviderType(), "providerType must not be null");
		BizAssert.notNull(request.getCameraStream(), "cameraStream must not be null");
		
		Device device = this.cameraDeviceAddRequestToDevice(request);
		if(device.getPositionId() == null) {
			device.setPositionId(this.getRootPosition(request.getOrgId()).getPositionId());
		}
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.Device").getData();
		device.setDeviceId(id);
		device.setDeviceSn(Long.toString(id));
		deviceMapper.insert(device);
		this.deviceAutoActive(device); //自动激活
		
		// 同步添加云眼设备
		cloudeyeDeviceSyncFacade.addCloudeyeDevice(device);
		
		return device;
	}

	private PositionInfo getRootPosition(Long orgId) {
		return positionService.getRootPosition(orgId);
	}
	
	private Device cameraDeviceUpdateRequestToDevice(CameraDeviceUpdateRequest request) {
		Device device = deviceMapper.load(request.getOrgId(), request.getDeviceId());
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_NOT_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), request.getDeviceId()));
		

		CameraDeviceExtendsInfo extendsInfo = JsonUtils.fromJson(CameraDeviceExtendsInfo.class, device.getExtendsInfo());
		extendsInfo.setProvider_type(request.getProviderType());
		extendsInfo.setCamera_ip(request.getCameraIp());
		extendsInfo.setCamera_nvr(request.getCameraNvr());
		extendsInfo.setCamera_username(request.getCameraUsername());
		extendsInfo.setCamera_password(request.getCameraPassword());
		extendsInfo.setCamera_stream(request.getCameraStream());
		extendsInfo.setCamera_resolution(request.getCameraResolution());
		
		this.fillFieldForCamera(device, extendsInfo); // 摄像机设备设置额外属性值
		
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		
		return device;
	}

	@Override
	@Transactional
	public boolean updateDevice(CameraDeviceUpdateRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getDeviceId(), "deviceId must not be null");
		
		Device device = this.cameraDeviceUpdateRequestToDevice(request);
		
		deviceMapper.update(device);
		
		cloudeyeDeviceSyncFacade.updateCloudeyeDevice(device);
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteDeviceById(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");

		// 先查看该摄像头是否和魔点盒子有绑定

		DeviceMatch deviceMatch=deviceMatchManager.getByCameraId(deviceId,orgId);
		if(deviceMatch!=null && deviceMatch.getBoxId()!=null){
			DeviceMatchRequest request=new DeviceMatchRequest();
			request.setCameraId(deviceMatch.getCameraId());
			request.setOrgId(deviceMatch.getOrgId());
			request.setBoxId(deviceMatch.getBoxId());
			boolean unBindCamera=deviceMatchManager.disMatchDevice(request);
			if(!unBindCamera){
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CAMERA_BINDING_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), deviceId));
			}
		}

		Device device = deviceMapper.load(orgId, deviceId);
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_NOT_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), deviceId));
	
		deviceMapper.deleteById(orgId, deviceId);
		
		// 同步删除云眼摄像机
		cloudeyeDeviceSyncFacade.deleteCloudeyeDevice(device);
		
		return true;
	}

	@Override
	public Device getDeviceById(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");
		return deviceMapper.load(orgId, deviceId);
	}
	
	private static DeviceQueryCondition cameraDeviceQueryRequestToDeviceQueryCondition(CameraDeviceQueryRequest request) {
		DeviceQueryCondition condition = new DeviceQueryCondition();
		
		condition.setOrgId(request.getOrgId());
//		condition.setPositionId(request.getPositionId());
		condition.setProviderType(request.getProviderType());
		//camera type
		condition.setDeviceType(10);
		
		List<Integer> statusList = new ArrayList<>();
		if(request.getStatus() != null) {
			statusList.add(request.getStatus());
		} else {
			for(DeviceStatus deviceStatus : DeviceStatus.values()) {
				statusList.add(deviceStatus.getValue());
			}
		}
		
		condition.setStatusList(statusList);
		
		return condition;
	}
	
	private Device cameraDeviceInfoToDevice(CameraDeviceInfo cameraDeviceInfo) {
		if (cameraDeviceInfo == null) return null;
		Device device = new Device();
		device.setDeviceId(cameraDeviceInfo.getDeviceId());
		device.setOrgId(cameraDeviceInfo.getOrgId());
		device.setDeviceName(cameraDeviceInfo.getDeviceName());
		device.setPositionId(cameraDeviceInfo.getPositionId());
		
		CameraDeviceExtendsInfo extendsInfo = new CameraDeviceExtendsInfo();
		extendsInfo.setProvider_type(cameraDeviceInfo.getProviderType());
		extendsInfo.setCamera_ip(cameraDeviceInfo.getCameraIp());
		extendsInfo.setCamera_username(cameraDeviceInfo.getCameraUsername());
		extendsInfo.setCamera_password(cameraDeviceInfo.getCameraPassword());
		extendsInfo.setCamera_stream(cameraDeviceInfo.getCameraStream());
		extendsInfo.setCamera_nvr(cameraDeviceInfo.getCameraNvr());
		extendsInfo.setCamera_resolution(cameraDeviceInfo.getCameraResolution());
		
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		device.setStatus(cameraDeviceInfo.getStatus());
		device.setGmtCreate(cameraDeviceInfo.getGmtCreate());
		return device;
	}
	
	private List<Device> cameraDeviceInfoListToDeviceList(List<CameraDeviceInfo> cameraDeviceInfoList) {
		if (cameraDeviceInfoList == null) return null;
		
		List<Device> deviceList = new ArrayList<>();
		for(CameraDeviceInfo cameraDeviceInfo : cameraDeviceInfoList) {
			deviceList.add(cameraDeviceInfoToDevice(cameraDeviceInfo));
		}
		
		return deviceList;
	}
	
	private PaginationDomain<Device> paginationCameraDeviceInfoToPaginationDevice(Pagination<CameraDeviceInfo> fromPagination) {
		PaginationDomain<Device> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Device>());
		if (toPagination == null)
			return null;
		toPagination.setData(cameraDeviceInfoListToDeviceList(fromPagination.getData()));
		return toPagination;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, DeviceQueryCondition condition) {
        int totalCount = (Integer) deviceMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(deviceMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Device> findPaginationDevice(CameraDeviceQueryRequest request,
			Pagination<CameraDeviceInfo> pagination) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		DeviceQueryCondition condition = cameraDeviceQueryRequestToDeviceQueryCondition(request);
		PaginationDomain<Device> devicePagination = paginationCameraDeviceInfoToPaginationDevice(pagination);
		
		return this.getPagination(devicePagination, condition);
	}

}
