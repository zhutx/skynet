package com.moredian.skynet.device.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.device.model.GroupInfo;

public interface DeviceGroupRelationService {
	
	List<Long> findDeviceIdByGroupIds(Long orgId, List<Long> groupIds);
	
	List<GroupInfo> findGroupByDeviceId(Long orgId, Long deviceId);
	
	List<Long> findGroupIdByDeviceId(Long orgId, Long deviceId);
	
	List<String> findGroupNameByDeviceId(Long orgId, Long deviceId);
	
	List<Long> findDeviceIdByGroupId(Long orgId, Long groupId);
	
	ServiceResponse<Boolean> resetDeviceGroupRelation(Long orgId, Long deviceId, List<Long> groupIdList);

}
