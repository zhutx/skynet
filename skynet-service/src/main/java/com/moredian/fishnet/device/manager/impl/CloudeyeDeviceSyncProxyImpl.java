package com.moredian.fishnet.device.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.ExtendModel;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.conf.ExtendKeyConstants;
import com.moredian.cloudeye.core.api.conf.device.DeviceConfigProvider;
import com.moredian.cloudeye.core.api.conf.device.DeviceInfo;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.enums.DeviceErrorCode;
import com.moredian.fishnet.device.enums.DeviceStatus;
import com.moredian.fishnet.device.enums.DeviceType;
import com.moredian.fishnet.device.manager.CloudeyeDeviceSyncProxy;
import com.moredian.fishnet.device.manager.DeviceManager;
import com.moredian.fishnet.device.model.CameraDeviceExtendsInfo;
import com.moredian.fishnet.device.model.ServerDeviceExtendsInfo;
import com.moredian.fishnet.org.enums.BizType;
import com.moredian.fishnet.org.response.PositionInfo;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.fishnet.org.service.PositionService;

@Service
public class CloudeyeDeviceSyncProxyImpl implements CloudeyeDeviceSyncProxy {
	
	private static Logger logger = LoggerFactory.getLogger(CloudeyeDeviceSyncProxyImpl.class);
	
	@SI
	private DeviceConfigProvider deviceConfigProvider;
	@SI
	private PositionService positionService;
	@Autowired
	private DeviceManager deviceManager;
	@SI
	private OrgService orgService;
	
	/**
	 * 设备是否已激活
	 * @param device
	 * @return
	 */
	private boolean isActived(Device device) {
		if(DeviceStatus.UNACTIVE.getValue() == device.getStatus()) return false;
		return true;
	}
	
	/**
	 * 是否云眼需要的设备
	 * @param device
	 * @return
	 */
	private boolean isCloudeyeNeed(Device device){
		
		if(DeviceType.isCloudeyeNeedDevice(device.getDeviceType())) {
			return true;
		}
		
    	return false;
	}
	
	private DeviceInfo buildDeviceInfo(Device device){
    	
    	if(device.getDeviceType() == DeviceType.SERVER_FIREANT.getValue()) { // 火蚁服务器
    		return this.buildServerDeviceInfo(device);
    	}
    	
    	if(device.getDeviceType() == DeviceType.CAMERA.getValue()) { // 摄像机
    		return this.buildCameraDeviceInfo(device);
    	}
    	
    	if(DeviceType.isVerifyBoardDevice(device.getDeviceType())) { // 安卓识别主板
    		return this.buildBoardDeviceInfo(device);
    	}
    	
    	return null;
    	
    }
	
	private PositionInfo getPositionInfo(Device device) {
		return positionService.getPositionById(device.getOrgId(), device.getPositionId());
	}
	
	private DeviceInfo buildServerDeviceInfo(Device device) {
    	DeviceInfo deviceInfo = new DeviceInfo();
    	deviceInfo.setOrgId(device.getOrgId());
    	deviceInfo.setDeviceId(device.getDeviceSn());
    	deviceInfo.setDeviceName(device.getDeviceName());
    	deviceInfo.setPosition(this.getPositionInfo(device).getPositionCode());
    	
    	ServerDeviceExtendsInfo extendsInfo = JsonUtils.fromJson(ServerDeviceExtendsInfo.class, device.getExtendsInfo());
    	
    	deviceInfo.setHost(extendsInfo.getC_host()); // 内网host
    	deviceInfo.setPort(extendsInfo.getC_port()); // 内网port
    	deviceInfo.setDeviceType(com.moredian.cloudeye.core.api.conf.device.DeviceType.FIREANT.intValue());
    	
    	ExtendModel extend = new ExtendModel();
    	extend.put(ExtendKeyConstants.DEVICE_OUTER_HOST, extendsInfo.getC_outer_host()); // 外网host 
    	extend.put(ExtendKeyConstants.DEVICE_OUTER_PORT, extendsInfo.getC_outer_port()); // 外网port
    	extend.put(ExtendKeyConstants.DEVICE_TP_HOST, extendsInfo.getC_tp_host()); // 第三方识别服务器host
    	extend.put(ExtendKeyConstants.DEVICE_TP_PORT, extendsInfo.getC_tp_port()); // 第三方识别服务器port
    	extend.put(ExtendKeyConstants.RD_MAX_ORGNUM, extendsInfo.getC_rd_maxorgnum());// 最大识别机构数
    	extend.put(ExtendKeyConstants.RD_MAX_PERSONNUM, extendsInfo.getC_rd_maxpersonnum());// 最大底库人员数
    	deviceInfo.setExtend(extend);
    	return deviceInfo;
    }
    
    private DeviceInfo buildCameraDeviceInfo(Device device) {
    	DeviceInfo deviceInfo = new DeviceInfo();
    	deviceInfo.setOrgId(device.getOrgId());
    	deviceInfo.setDeviceId(device.getDeviceSn());
    	deviceInfo.setDeviceName(device.getDeviceName());
    	deviceInfo.setPosition(this.getPositionInfo(device).getPositionCode());
    	
    	CameraDeviceExtendsInfo extendsInfo = JsonUtils.fromJson(CameraDeviceExtendsInfo.class, device.getExtendsInfo());
    	
    	deviceInfo.setHost(extendsInfo.getCamera_ip());
    	deviceInfo.setPort(0);
    	deviceInfo.setDeviceType(com.moredian.cloudeye.core.api.conf.device.DeviceType.IPCAM.intValue());
    	
    	
    	ExtendModel extend = new ExtendModel();
    	extend.put(ExtendKeyConstants.DEVICE_IPCAM_URL, extendsInfo.getCamera_stream()); // 视频流地址
    	deviceInfo.setExtend(extend);
    	
    	return deviceInfo;
    }
    
    private DeviceInfo buildBoardDeviceInfo(Device device) {
    	DeviceInfo deviceInfo = new DeviceInfo();
    	deviceInfo.setOrgId(device.getOrgId());
    	deviceInfo.setDeviceId(device.getDeviceSn());
    	deviceInfo.setDeviceName(device.getDeviceName());
    	deviceInfo.setPosition(this.getPositionInfo(device).getPositionCode());
    	deviceInfo.setHost("127.0.0.1");
    	deviceInfo.setPort(0);
    	deviceInfo.setDeviceType(com.moredian.cloudeye.core.api.conf.device.DeviceType.TURNIP.intValue());
    	return deviceInfo;
    }

	@Override
	public void addCloudeyeDevice(Device device) {
		
		if(!orgService.isBizEnable(device.getOrgId(), BizType.RECOGNIZE.getValue())) return; // 机构未开启识别业务
		
		if(!(this.isActived(device) && this.isCloudeyeNeed(device))) return; // 设备未激活或者设备不是云眼需要的
		
		DeviceInfo deviceInfo = deviceConfigProvider.getDeviceInfo(device.getDeviceSn());
		if(deviceInfo != null) return; // 云眼本设备已存在，无需同步
		
		deviceInfo = this.buildDeviceInfo(device);
		
		boolean result = deviceConfigProvider.addDeviceInfo(deviceInfo); // 设备同步云眼
		if(!result) {
			ExceptionUtils.throwException(DeviceErrorCode.CE_DEVICE_ADD_FAIL, DeviceErrorCode.CE_DEVICE_ADD_FAIL.getMessage());
		}
		
	}
	
	@Override
	public void updateCloudeyeDevice(Device device) {
		
		if(!(this.isActived(device) && this.isCloudeyeNeed(device))) return; // 设备未激活或者设备不是云眼需要的，无需同步
		
		DeviceInfo deviceInfo = this.buildDeviceInfo(device);
		
    	boolean result = deviceConfigProvider.updateDeviceInfo(deviceInfo);
    	if(!result) {
    		ExceptionUtils.throwException(DeviceErrorCode.CE_DEVICE_UPDATE_FAIL, DeviceErrorCode.CE_DEVICE_UPDATE_FAIL.getMessage());
    	} 
    	
	}

	@Override
	public void deleteCloudeyeDevice(Device device) {
		
		if(!(this.isActived(device) && this.isCloudeyeNeed(device))) return; // 设备未激活或者设备不是云眼需要的，无需同步
			
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setOrgId(device.getOrgId());
		deviceInfo.setDeviceId(device.getDeviceSn());
		
		boolean result = deviceConfigProvider.removeDeviceInfo(deviceInfo);
		
		if(!result) {
			ExceptionUtils.throwException(DeviceErrorCode.CE_DEVICE_DELETE_FAIL, DeviceErrorCode.CE_DEVICE_DELETE_FAIL.getMessage());
		}
		
	}

	@Override
	public void updateBoardIp(String deviceSn, String ip) {
		
		Device device = deviceManager.getDeviceBySn(deviceSn);
		if(device == null) return;
		this.updateCloudeyeDevice(device);
		
	}
	

}
