package com.moredian.skynet.device.manager.impl;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.convertor.DeviceMatchConvertor;
import com.moredian.skynet.device.domain.Deploy;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceMatch;
import com.moredian.skynet.device.enums.CloudeyeDeployAction;
import com.moredian.skynet.device.enums.DeviceErrorCode;
import com.moredian.skynet.device.manager.CameraDeviceManager;
import com.moredian.skynet.device.manager.DeployManager;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.mapper.DeviceMatchMapper;
import com.moredian.skynet.device.request.BoxUpdateRequest;
import com.moredian.skynet.device.request.CameraDeviceAddRequest;
import com.moredian.skynet.device.request.CameraDeviceUpdateRequest;
import com.moredian.skynet.device.request.DeviceMatchRequest;
import com.moredian.idgenerator.service.IdgeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceMatchManagerImpl implements DeviceMatchManager {
	
	@Autowired
	private DeviceMatchMapper deviceMatchMapper;
	@SI
	private IdgeneratorService idgeneratorService;

	@Autowired
	private DeployManager deployManager;

	@Autowired
	private CameraDeviceManager cameraDeviceManager;

	@Override
	@Transactional
	public boolean matchDevice(DeviceMatchRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getBoxId(), "boxId must not be null");
		BizAssert.notNull(request.getCameraId(), "cameraId must not be null");
		
		DeviceMatch existOne = deviceMatchMapper.loadByCameraId(request.getCameraId(), request.getOrgId());
		if(existOne != null) {
			ExceptionUtils.throwException(DeviceErrorCode.DEVICE_MATCH_REPEAT, DeviceErrorCode.DEVICE_MATCH_REPEAT.getMessage());
		}
		
		Long id = idgeneratorService.getNextIdByTypeName("com.xier.skynet.device.DeviceMatch").getData();
		
		DeviceMatch deviceMatch = DeviceMatchConvertor.deviceMatchRequestToDeviceMatch(request);
		deviceMatch.setDeviceMatchId(id);
		deviceMatchMapper.insert(deviceMatch);

		//布控设备

		Deploy deploy=deployManager.getDeployByDevice(request.getOrgId(),request.getBoxId());
		if(deploy!=null) {
			boolean deployResult = deployManager.deployCloudeye(deploy.getOrgId(), deploy.getDeployId(), CloudeyeDeployAction.CAMERA_BIND.getValue());
			if (!deployResult) {
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR, DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR.getMessage());
			}
		}
		return true;
	}

	@Override
	public DeviceMatch getByCameraId(Long cameraId, Long orgId) {
		return deviceMatchMapper.loadByCameraId(cameraId, orgId);
	}


	@Override
	public DeviceMatch getByBoxId(Long boxId, Long orgId) {
		return deviceMatchMapper.loadByBoxId(boxId,orgId);
	}

	@Override
	@Transactional
	public boolean disMatchDevice(DeviceMatchRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getBoxId(), "boxId must not be null");
		BizAssert.notNull(request.getCameraId(), "cameraId must not be null");
		DeviceMatch deviceMatch = DeviceMatchConvertor.deviceMatchRequestToDeviceMatch(request);
		int result = deviceMatchMapper.delete(deviceMatch);

		//布控设备

		Deploy deploy=deployManager.getDeployByDevice(request.getOrgId(),request.getBoxId());
		if(deploy!=null) {
			boolean deployResult = deployManager.deployCloudeye(deploy.getOrgId(), deploy.getDeployId(), CloudeyeDeployAction.CAMERA_UNBIND.getValue());
			if (!deployResult) {
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR, DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR.getMessage());
			}
		}
		
		return result > 0 ? true : false;
		
	}

	@Override
	public boolean bindCameraWithDevice(Long orgId,Long cameraId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "boxId must not be null");
		BizAssert.notNull(cameraId, "cameraId must not be null");
		DeviceMatch deviceMatch = new DeviceMatch();
		deviceMatch.setOrgId(orgId);
		deviceMatch.setCameraId(cameraId);
		deviceMatch.setBoxId(deviceId);
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.DeviceMatch").getData();
		deviceMatch.setDeviceMatchId(id);
		int result = deviceMatchMapper.insert(deviceMatch);

		return result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean updateBoxCamera(BoxUpdateRequest boxUpdateRequest) {

		BizAssert.notNull(boxUpdateRequest.getOrgId(), "orgId must not be null");
		BizAssert.notNull(boxUpdateRequest.getBoxId(), "boxId must not be null");
		//need to create camera first then bind it to box
		if(boxUpdateRequest.getCameraId()==null){
			//first, create camera
			CameraDeviceAddRequest addRequest = new CameraDeviceAddRequest();
			addRequest.setOrgId(boxUpdateRequest.getOrgId());
			addRequest.setCameraStream(boxUpdateRequest.getCameraStream());
			addRequest.setCameraResolution(boxUpdateRequest.getCameraResolution());
			addRequest.setProviderType(boxUpdateRequest.getProviderType());

			Device cameraDevice=cameraDeviceManager.addDevice(addRequest);
			if(cameraDevice==null){
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CREATE_FAILED, DeviceErrorCode.DEVICE_CREATE_FAILED.getMessage());
			}
			//bind camera with box
			DeviceMatchRequest matchRequest = new DeviceMatchRequest();
			matchRequest.setOrgId(addRequest.getOrgId());
			matchRequest.setCameraId(cameraDevice.getDeviceId());
			matchRequest.setBoxId(boxUpdateRequest.getBoxId());


			DeviceMatch existOne = deviceMatchMapper.loadByCameraId(matchRequest.getCameraId(), matchRequest.getOrgId());
			if(existOne != null) {
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_MATCH_REPEAT, DeviceErrorCode.DEVICE_MATCH_REPEAT.getMessage());
			}

			Long id = idgeneratorService.getNextIdByTypeName("com.xier.skynet.device.DeviceMatch").getData();

			DeviceMatch deviceMatch = DeviceMatchConvertor.deviceMatchRequestToDeviceMatch(matchRequest);
			deviceMatch.setDeviceMatchId(id);
			deviceMatchMapper.insert(deviceMatch);
			//布控设备

			Deploy deploy=deployManager.getDeployByDevice(matchRequest.getOrgId(),matchRequest.getBoxId());
			if(deploy!=null) {
				boolean deployResult = deployManager.deployCloudeye(deploy.getOrgId(), deploy.getDeployId(), CloudeyeDeployAction.CAMERA_BIND.getValue());
				if (!deployResult) {
					ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR, DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR.getMessage());
				}
			}

		}else{
			//binding relation is existed
			//only update camera
			CameraDeviceUpdateRequest updateCameraRequest=new CameraDeviceUpdateRequest();
			updateCameraRequest.setOrgId(boxUpdateRequest.getOrgId());
			updateCameraRequest.setProviderType(boxUpdateRequest.getProviderType());
			updateCameraRequest.setDeviceId(boxUpdateRequest.getCameraId());
			updateCameraRequest.setCameraStream(boxUpdateRequest.getCameraStream());
			updateCameraRequest.setCameraResolution(boxUpdateRequest.getCameraResolution());
			boolean updateResult=cameraDeviceManager.updateDevice(updateCameraRequest);
			if(!updateResult){
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_BOX_UPDATED_FAILED, DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR.getMessage());
			}

		}
		return true;
	}
}
