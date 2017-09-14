package com.moredian.skynet.device.mapper;

import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceMapper {
	
	int insert(Device device);
	
	int update(Device device);
	
	Device loadBySnOnly(String deviceSn);
	
	Device load(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId);
	
	int deleteById(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId);
	
	int getTotalCountByCondition(DeviceQueryCondition condition);
	
	List<Device> findPaginationByCondition(DeviceQueryCondition condition);
	
	Device loadBySn(@Param("orgId")Long orgId, @Param("deviceSn")String deviceSn);
	
	int deleteBySn(@Param("orgId")Long orgId, @Param("deviceSn")String deviceSn);
	
	Device loadByActiveCode(String activeCode);
	
	int updateActiveCode(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId, @Param("activeCode")String activeCode);
	
	int updateByActive(@Param("orgId")Long orgId, @Param("deviceId")Long deviceId, @Param("deviceSn")String deviceSn, @Param("version")Integer version, @Param("status")Integer status);
	
	List<String> findDeviceNameByIds(@Param("deviceIdList")List<Long> deviceIdList);
	
	List<Device> findDeviceByType(@Param("orgId")Long orgId, @Param("deviceType")Integer deviceType);
	
	List<Device> findDeviceByTypes(@Param("orgId")Long orgId, @Param("deviceTypeList")List<Integer> deviceTypes);
	
	List<Long> findDeviceIdByType(@Param("orgId")Long orgId, @Param("deviceType")Integer deviceType);

	int updateStatusBySn(@Param("orgId")Long orgId, @Param("deviceSn")String deviceSn, @Param("status")Integer status);

}
