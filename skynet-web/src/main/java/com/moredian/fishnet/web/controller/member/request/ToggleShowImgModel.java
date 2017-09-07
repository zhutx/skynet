package com.moredian.fishnet.web.controller.member.request;

public class ToggleShowImgModel {
	
	private Long orgId;
	private Long memberId;
	private Integer showVerifyFlag;
	
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
	public Integer getShowVerifyFlag() {
		return showVerifyFlag;
	}
	public void setShowVerifyFlag(Integer showVerifyFlag) {
		this.showVerifyFlag = showVerifyFlag;
	}

}
