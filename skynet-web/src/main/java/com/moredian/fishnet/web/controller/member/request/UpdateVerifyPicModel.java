package com.moredian.fishnet.web.controller.member.request;

public class UpdateVerifyPicModel {
	
	private Long orgId;
	private Long memberId;
	private String verifyFaceUrl;
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
	public String getVerifyFaceUrl() {
		return verifyFaceUrl;
	}
	public void setVerifyFaceUrl(String verifyFaceUrl) {
		this.verifyFaceUrl = verifyFaceUrl;
	}
	public boolean isTransform() {
		return transform;
	}
	public void setTransform(boolean transform) {
		this.transform = transform;
	}

}
