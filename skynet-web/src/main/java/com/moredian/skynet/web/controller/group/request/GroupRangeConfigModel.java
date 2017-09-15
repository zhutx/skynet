package com.moredian.skynet.web.controller.group.request;

import java.util.ArrayList;
import java.util.List;

public class GroupRangeConfigModel {
	
	private Long orgId;
	private Long groupId;
	private List<Long> deptIds = new ArrayList<>();
	private List<Long> memberIds = new ArrayList<>();
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public List<Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	public List<Long> getMemberIds() {
		return memberIds;
	}
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

}
