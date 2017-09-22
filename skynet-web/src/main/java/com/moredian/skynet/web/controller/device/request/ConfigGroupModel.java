package com.moredian.skynet.web.controller.device.request;

import java.util.ArrayList;
import java.util.List;

public class ConfigGroupModel {
	
	private Long orgId;
	private Long deviceId;
	private List<Long> groupIds = new ArrayList<>();
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public List<Long> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}

}
