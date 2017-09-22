package com.moredian.skynet.device.manager;

import java.util.List;

public interface DeviceGroupManager {
	
	List<Long> findDeviceIdByGroupIds(Long orgId, List<Long> groupIds);
	
	boolean addDefaultDeviceGroup(Long orgId, Long deviceId);
	
	List<Long> findGroupIdByDeviceId(Long orgId, Long deviceId);
	
	List<Long> findDeviceIdByGroupId(Long orgId, Long groupId);
	
	boolean resetDeviceGroupRelation(Long orgId, Long deviceId, List<Long> groupIds);
	
	boolean deleteByDevice(Long orgId, Long deviceId);
	
	boolean removeByGroupId(Long orgId, Long groupId);

}
