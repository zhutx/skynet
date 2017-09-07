package com.moredian.fishnet.web.controller.member.request;

import java.util.List;

public class ConfigGroupsModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//群组
	private List<Long> groupIds;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public List<Long> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}

}
