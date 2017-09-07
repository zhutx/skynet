package com.moredian.fishnet.device.manager;

import java.util.List;

public interface DeviceGroupManager {
	
	List<Long> findDeviceIdByGroupIds(Long orgId, List<Long> groupIds);
	
	boolean addDefaultDeviceGroup(Long orgId, Long deviceId);
	
	List<String> findGroupCodeByDeviceId(Long orgId, Long deviceId);
	
	List<Long> findGroupIdByDeviceId(Long orgId, Long deviceId);
	
	List<Long> findDeviceIdByGroupId(Long orgId, Long groupId);
	
	boolean resetDeviceGroupRelation(Long orgId, Long deviceId, List<Long> groupIdList);
	
	boolean deleteByDevice(Long orgId, Long deviceId);

}
