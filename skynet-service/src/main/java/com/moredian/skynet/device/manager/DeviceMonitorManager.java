package com.moredian.skynet.device.manager;

import java.util.List;

import com.moredian.skynet.device.model.DeviceImageVersion;
import com.moredian.skynet.device.model.DeviceStateInfo;
import com.moredian.skynet.device.request.RebootRequest;
import com.moredian.skynet.device.request.StatusRequest;
import com.moredian.skynet.device.request.TransferRequest;
import com.moredian.skynet.device.request.UpgradeRequest;

@SuppressWarnings("deprecation")
public interface DeviceMonitorManager{
	
	DeviceStateInfo getStatus(String serialNumber) ;
	
	List<DeviceStateInfo> getStatusList(StatusRequest request);
	
	DeviceStateInfo upgradeDevice(UpgradeRequest request);

	DeviceImageVersion getDeviceVersion(String serialNumber);
	
	DeviceStateInfo rebootDevice(RebootRequest request);
	
	DeviceStateInfo transferDevice(TransferRequest request) ;
}
