package com.moredian.fishnet.device.manager;

import java.util.List;

import com.moredian.fishnet.device.model.DeviceImageVersion;
import com.moredian.fishnet.device.model.DeviceStateInfo;
import com.moredian.fishnet.device.request.RebootRequest;
import com.moredian.fishnet.device.request.StatusRequest;
import com.moredian.fishnet.device.request.TransferRequest;
import com.moredian.fishnet.device.request.UpgradeRequest;

public interface DeviceMonitorManager{
	
	DeviceStateInfo getStatus(String serialNumber) ;
	
	List<DeviceStateInfo> getStatusList(StatusRequest request);
	
	DeviceStateInfo upgradeDevice(UpgradeRequest request);

	DeviceImageVersion getDeviceVersion(String serialNumber);
	
	DeviceStateInfo rebootDevice(RebootRequest request);
	
	DeviceStateInfo transferDevice(TransferRequest request) ;
}
