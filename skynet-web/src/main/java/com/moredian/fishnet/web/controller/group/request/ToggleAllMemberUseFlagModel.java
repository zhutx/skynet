package com.moredian.fishnet.web.controller.group.request;

public class ToggleAllMemberUseFlagModel {
	
	private Long orgId;
	private Long groupId;
	private Integer allMemberFlag;
	
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
	public Integer getAllMemberFlag() {
		return allMemberFlag;
	}
	public void setAllMemberFlag(Integer allMemberFlag) {
		this.allMemberFlag = allMemberFlag;
	}

}
