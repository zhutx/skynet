package com.moredian.fishnet.device.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.manager.DeviceGroupManager;
import com.moredian.fishnet.device.model.GroupInfo;
import com.moredian.fishnet.device.service.DeviceGroupRelationService;
import com.moredian.fishnet.org.service.GroupService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceGroupRelationServiceImpl implements DeviceGroupRelationService {
	
	@Autowired
	private DeviceGroupManager deviceGroupManager;
	@SI
	private GroupService groupService;

	@Override
	public List<Long> findDeviceIdByGroupIds(Long orgId, List<Long> groupIds) {
		return deviceGroupManager.findDeviceIdByGroupIds(orgId, groupIds);
	}
	
	private List<GroupInfo> groupInfoListToGroupInfoList(List<com.moredian.fishnet.org.model.GroupInfo> groupInfoList) {
		if(CollectionUtils.isEmpty(groupInfoList)) return new ArrayList<>();
		return BeanUtils.copyListProperties(GroupInfo.class, groupInfoList);
	}

	@Override
	public List<GroupInfo> findGroupByDeviceId(Long orgId, Long deviceId) {
		List<Long> groupIdList = deviceGroupManager.findGroupIdByDeviceId(orgId, deviceId);
		List<com.moredian.fishnet.org.model.GroupInfo> groupInfoList = groupService.findGroupByIds(orgId, groupIdList);
		return groupInfoListToGroupInfoList(groupInfoList);
	}

	@Override
	public List<String> findGroupNameByDeviceId(Long orgId, Long deviceId) {
		List<Long> groupIdList = deviceGroupManager.findGroupIdByDeviceId(orgId, deviceId);
		return null;//groupService.findGroupNameByIds(orgId, groupIdList);
	}

	@Override
	public List<Long> findGroupIdByDeviceId(Long orgId, Long deviceId) {
		return deviceGroupManager.findGroupIdByDeviceId(orgId, deviceId);
	}

	@Override
	public List<Long> findDeviceIdByGroupId(Long orgId, Long groupId) {
		return deviceGroupManager.findDeviceIdByGroupId(orgId, groupId);
	}

	@Override
	public ServiceResponse<Boolean> resetDeviceGroupRelation(Long orgId, Long deviceId, List<Long> groupIdList) {
		boolean result = deviceGroupManager.resetDeviceGroupRelation(orgId, deviceId, groupIdList);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
