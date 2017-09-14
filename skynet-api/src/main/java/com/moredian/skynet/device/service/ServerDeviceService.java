package com.moredian.skynet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.model.ServerDeviceInfo;
import com.moredian.skynet.device.request.ServerDeviceAddRequest;
import com.moredian.skynet.device.request.ServerDeviceQueryRequest;
import com.moredian.skynet.device.request.ServerDeviceUpdateRequest;

/**
 * 识别服务器设备服务
 * @author zhutx
 *
 */
public interface ServerDeviceService {
	
	/**
	 * 添加设备
	 * @param request
	 * @return
	 */
	ServiceResponse<DeviceInfo> addDevice(ServerDeviceAddRequest request);
	
	/**
	 * 修改设备
	 * @param request
	 * @return
	 * <li>DEVICE_NOT_EXIST</li>
	 */
	ServiceResponse<Boolean> updateDevice(ServerDeviceUpdateRequest request);
	
	/**
	 * 删除设备
	 * @param orgId
	 * @param deviceId
	 * @return
	 * <li>DEVICE_NOT_EXIST</li>
	 * <li>CE_DEVICE_DELETE_FAIL</li>
	 */
	ServiceResponse<Boolean> deleteDeviceById(Long orgId, Long deviceId);
	
	/**
	 * 删除设备
	 * @param orgId
	 * @param deviceSn
	 * @return
	 * <li>DEVICE_NOT_EXIST</li>
	 * <li>CE_DEVICE_DELETE_FAIL</li>
	 */
	ServiceResponse<Boolean> deleteDeviceBySn(Long orgId, String deviceSn);

	/**
	 * 获取设备信息
	 * @param orgId
	 * @param deviceId
	 * @return
	 */
	ServerDeviceInfo getDeviceById(Long orgId, Long deviceId);
	
	/**
	 * 获取设备信息
	 * @param orgId
	 * @param deviceSn
	 * @return
	 */
	ServerDeviceInfo getDeviceBySn(Long orgId, String deviceSn);
	
	/**
	 * 获取设备信息
	 * @param deviceSn
	 * @return
	 */
	ServerDeviceInfo getDeviceOnlyBySn(String deviceSn);
	
	/**
	 * 分页查询设备
	 * @param request
	 * @param pagination
	 * @return
	 */
	Pagination<ServerDeviceInfo> findPaginationDevice(ServerDeviceQueryRequest request, Pagination<ServerDeviceInfo> pagination);

}
