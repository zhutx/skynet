package com.moredian.skynet.device.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.model.DeviceImageVersion;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.model.DeviceStateInfo;
import com.moredian.skynet.device.request.DeviceActiveRequest;
import com.moredian.skynet.device.request.DeviceAddRequest;
import com.moredian.skynet.device.request.DeviceQueryRequest;
import com.moredian.skynet.device.request.DeviceUpdateRequest;
import com.moredian.skynet.device.request.DeviceWhiteAddRequest;
import com.moredian.skynet.device.request.RebootRequest;
import com.moredian.skynet.device.request.StatusRequest;
import com.moredian.skynet.device.request.TransferRequest;
import com.moredian.skynet.device.request.UpgradeRequest;
import com.moredian.skynet.device.response.DeviceActiveResponse;

@SuppressWarnings("deprecation")
public interface DeviceService {
	
	ServiceResponse<Boolean> addDeviceWhite(DeviceWhiteAddRequest request);
	
	/**
	 * 设备状态情况
	 * @param serialNumber：设备条码，对老设备用设备uniqueNumber
	 * @return 设备在线情况
	 */
	DeviceStateInfo getStatus(String serialNumber) ;
	
	/**
	 * 多设备状态情况
	 * @param request：设备条码列表，对老设备用设备uniqueNumber列表
	 * @return 设备在线情况Map
	 */
	List<DeviceStateInfo> getStatusList(StatusRequest request) ;
	
	/**
	 * 升级设备
	 * @param request
	 * @return 返回设备状态
	 */
	DeviceStateInfo upgradeDevice(UpgradeRequest request);

	/**
	 * 获取设备版本
	 * @param serialNumber
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
	 
	DeviceInfo getDeviceBySn(String deviceSn);
	
	DeviceInfo getDeviceById(Long orgId, Long deviceId);
	
	ServiceResponse<Boolean> updateXmlConfig(Long orgId, Long deviceId, String xml);
	
	String getXmlConfig(String deviceSn);
	
	ServiceResponse<Long> addDeviceForOld(DeviceAddRequest request);
	
	String genActiveCode(Long orgId, Long deviceId);
	
	/**
	 * 添加设备
	 * @param request
	 * @return
	 * <li>DEVICE_EXIST</li>
	 * <li>CE_DEVICE_ADD_FAIL</li>
	 */
	ServiceResponse<Long> addDevice(DeviceAddRequest request);
	
	/**
	 * 修改设备
	 * @param request
	 * @return
	 */
	ServiceResponse<Boolean> updateDevice(DeviceUpdateRequest request);
	
	/**
	 * 删除设备
	 * @param orgId
	 * @param deviceId
	 * @return
	 */
	ServiceResponse<Boolean> deleteDeviceById(Long orgId, Long deviceId);


	/**
	 * 删除设备
	 * @param serialNumber
	 * @return
	 */
	ServiceResponse<Boolean> deleteDeviceFromDase(String serialNumber);
	
	/**
	 * 分页查询设备
	 * @param pagination
	 * @param request
	 * @return
	 */
	Pagination<DeviceInfo> findPaginationDevice(Pagination<DeviceInfo> pagination, DeviceQueryRequest request);
	
	/**
	 * 设备激活码激活
	 * @param request
	 * @return
	 */
	ServiceResponse<DeviceActiveResponse> activeDevice(DeviceActiveRequest request);
	
	List<String> findDeviceNameByIds(List<Long> deviceIdList);
	
	List<DeviceInfo> findDeviceByType(Long orgId, Integer deviceType);
	
	List<DeviceInfo> findDeviceByTypes(Long orgId, List<Integer> deviceTypes);
	
	List<Long> findDeviceIdByType(Long orgId, Integer deviceType);

	Boolean activeDeviceWithOrgIdAndDeviceSn(Long orgId, String deviceSn);

}
