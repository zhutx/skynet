package com.moredian.fishnet.device.service;

import java.util.List;
import java.util.Map;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.device.model.AlarmInfo;
import com.moredian.fishnet.device.model.DeviceImageVersion;
import com.moredian.fishnet.device.model.DeviceStateInfo;
import com.moredian.fishnet.device.request.AlarmQueryRequest;
import com.moredian.fishnet.device.request.AlarmUpdateRequest;
import com.moredian.fishnet.device.request.RebootRequest;
import com.moredian.fishnet.device.request.StatusRequest;
import com.moredian.fishnet.device.request.TransferRequest;
import com.moredian.fishnet.device.request.UpgradeRequest;

public interface DeviceInnerService {	
	/**
	 * 设备状态情况
	 * @param serialNumber：设备条码，对老设备用设备uniqueNumber
	 * @return 设备在线情况
	 */
	DeviceStateInfo getStatus(String serialNumber) ;
	
	/**
	 * 多设备状态情况
	 * @param list：设备条码列表，对老设备用设备uniqueNumber列表
	 * @return 设备在线情况Map
	 */
	Map<String,DeviceStateInfo> getStatusList(StatusRequest request) ;
	
	/**
	 * 升级设备
	 * @param request
	 * @return 返回设备状态
	 */
	DeviceStateInfo upgradeDevice(UpgradeRequest request);

	/**
	 * 获取设备版本
	 * @param request
	 * @return 
	 */
	DeviceImageVersion getDeviceVersion(String serialNumber);
	
	/**
	 * 重启设备
	 * @param request
	 * @return  返回设备状态
	 */
	DeviceStateInfo rebootDevice(RebootRequest request);

	/**
	 * 透传命令
	 * @param request
	 * @return  返回设备状态
	 */
	DeviceStateInfo transferDevice(TransferRequest request);
	
//	/**
//	 * 根据条码获取故障信息
//	 * @param serialNumber
//	 * @return
//	 */
//	AlarmInfo getAlarmInfoBySerialNumber(String serialNumber);
	
	/**
	 * 分页获取故障列表
	 * @param request
	 * @return
	 */
	Pagination<AlarmInfo> getPaginationAlarm(Pagination<AlarmInfo> pagination, AlarmQueryRequest request);
	
	/**
	 * 更新故障信息
	 * @param request
	 * @return
	 */
	boolean updateAalarm(AlarmUpdateRequest request);
	 
}
