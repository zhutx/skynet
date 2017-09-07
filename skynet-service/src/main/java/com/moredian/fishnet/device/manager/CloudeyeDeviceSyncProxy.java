package com.moredian.fishnet.device.manager;

import com.moredian.fishnet.device.domain.Device;

public interface CloudeyeDeviceSyncProxy {
	
	/**
	 * 添加云眼设备信息
	 * @param deviceInfo
	 */
	void addCloudeyeDevice(Device device);
	
	/**
	 * 修改云眼设备信息
	 * @param device
	 * @return
	 */
	void updateCloudeyeDevice(Device device);
	
	/**
	 * 删除云眼设备
	 * @param device
	 * @return
	 */
	void deleteCloudeyeDevice(Device device);
	
	/**
	 * 修改主板设备的ip
	 * @param deviceSn
	 * @param ip
	 */
	void updateBoardIp(String deviceSn, String ip);

}
