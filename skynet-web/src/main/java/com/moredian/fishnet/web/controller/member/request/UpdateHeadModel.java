package com.moredian.fishnet.web.controller.member.request;

public class UpdateHeadModel {
	
	private Long orgId;
	private Long memberId;
	private String headUrl;
	private boolean transform = true;
	
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
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public boolean isTransform() {
		return transform;
	}
	public void setTransform(boolean transform) {
		this.transform = transform;
	}

}
