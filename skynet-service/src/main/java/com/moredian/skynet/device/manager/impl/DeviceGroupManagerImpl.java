package com.moredian.skynet.device.manager.impl;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceGroup;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.DeviceGroupManager;
import com.moredian.skynet.device.mapper.DeviceGroupMapper;
import com.moredian.skynet.device.mapper.DeviceMapper;
import com.moredian.skynet.org.model.GroupInfo;
import com.moredian.skynet.org.service.GroupService;
import com.moredian.idgenerator.service.IdgeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceGroupManagerImpl implements DeviceGroupManager {
	
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private DeviceMapper deviceMapper;
	@SI
	private GroupService groupService;

	@Override
	public List<Long> findDeviceIdByGroupIds(Long orgId, List<Long> groupIds) {
		return deviceGroupMapper.findDeviceIdByGroupIds(orgId, groupIds);
	}
	
	/**
	 * 设备是否需要绑定群组
	 * @param device
	 * @return
	 */
	private boolean isNeedBindGroup(Device device){
		
    	if(DeviceType.isNeedGroupDevice(device.getDeviceType())) {
    		return true;
    	}
    	
    	return false;
	}
	
	private Long genDeviceGroupId() {
		return idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.DeviceGroup").getData();
	}
	
	private void bindQYGroup(Long orgId, Long deviceId) {
		GroupInfo groupInfo = groupService.getGroupByName(orgId, "全员组");
		DeviceGroup deviceGroup = new DeviceGroup();
		deviceGroup.setDeviceGroupId(this.genDeviceGroupId());
		deviceGroup.setOrgId(orgId);
		deviceGroup.setDeviceId(deviceId);
		deviceGroup.setGroupId(groupInfo.getGroupId());
		deviceGroupMapper.insert(deviceGroup);
	}
	
	private void bindVisitorGroup(Long orgId, Long deviceId) {
		GroupInfo groupInfo = groupService.getGroupByName(orgId, "访客组");
		DeviceGroup deviceGroup = new DeviceGroup();
		deviceGroup.setDeviceGroupId(this.genDeviceGroupId());
		deviceGroup.setOrgId(orgId);
		deviceGroup.setDeviceId(deviceId);
		deviceGroup.setGroupId(groupInfo.getGroupId());
		deviceGroupMapper.insert(deviceGroup);
	}

	@Override
	public boolean addDefaultDeviceGroup(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");
		
		Device device = deviceMapper.load(orgId, deviceId);
		if(!this.isNeedBindGroup(device)) return true;
		
		if(DeviceType.BOARD_ATTENDANCE.getValue() == device.getDeviceType()
				|| DeviceType.BOARD_BOX.getValue() == device.getDeviceType()
				||DeviceType.BOARD_ATTENDANCE_DUALEYE.getValue() == device.getDeviceType()) {
			this.bindQYGroup(orgId, deviceId);
			this.bindVisitorGroup(orgId, deviceId);
		}
		
		if(DeviceType.BOARD_VISITOR.getValue() == device.getDeviceType()) {
			this.bindVisitorGroup(orgId, deviceId);
		}
		
		if(DeviceType.BOARD_GATE.getValue() == device.getDeviceType()) {
			this.bindQYGroup(orgId, deviceId);
		}
		
		return true;
	}
	
	@Override
	public List<Long> findGroupIdByDeviceId(Long orgId, Long deviceId) {
		return deviceGroupMapper.findGroupIdByDeviceId(orgId, deviceId);
	}

	@Override
	public List<Long> findDeviceIdByGroupId(Long orgId, Long groupId) {
		return deviceGroupMapper.findDeviceIdByGroupId(orgId, groupId);
	}

	@Override
	@Transactional
	public boolean resetDeviceGroupRelation(Long orgId, Long deviceId, List<Long> finalGroupIdList) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(deviceId);
		BizAssert.notNull(finalGroupIdList);
		
		List<Long> existGroupIdList = deviceGroupMapper.findGroupIdByDeviceId(orgId, deviceId);
		
		List<Long> incGroupIdList = new ArrayList<>();
		incGroupIdList.addAll(finalGroupIdList);
		incGroupIdList.removeAll(existGroupIdList);
		for(Long groupId : incGroupIdList) {
			DeviceGroup deviceGroup = new DeviceGroup();
			deviceGroup.setDeviceGroupId(this.genDeviceGroupId());
			deviceGroup.setOrgId(orgId);
			deviceGroup.setDeviceId(deviceId);
			deviceGroup.setGroupId(groupId);
			deviceGroupMapper.insert(deviceGroup);
		}
		
		List<Long> decGroupIdList = new ArrayList<>();
		decGroupIdList.addAll(existGroupIdList);
		decGroupIdList.removeAll(finalGroupIdList);
		for(Long groupId : decGroupIdList) {
			deviceGroupMapper.delete(orgId, deviceId, groupId);
		}
		
		return true;
	}

	@Override
	public boolean deleteByDevice(Long orgId, Long deviceId) {
		deviceGroupMapper.deleteByDevice(orgId, deviceId);
		return true;
	}

	@Override
	public boolean removeByGroupId(Long orgId, Long groupId) {
		deviceGroupMapper.deleteByGroup(orgId, groupId);
		return true;
	}

}
