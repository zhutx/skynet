package com.moredian.fishnet.web.controller.member.request;

import java.util.List;

public class ConfigDeptModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//部门
	private List<Long> relationDepts;
	
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
	public List<Long> getRelationDepts() {
		return relationDepts;
	}
	public void setRelationDepts(List<Long> relationDepts) {
		this.relationDepts = relationDepts;
	}


}
