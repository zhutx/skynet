package com.moredian.fishnet.member.domain;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable {

	private static final long serialVersionUID = -2968732175636658463L;
	
	private Long loginLogId;
	private Long orgId;
	private Long memberId;
	private Integer moduleType;
	private Date loginTime;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getLoginLogId() {
		return loginLogId;
	}
	public void setLoginLogId(Long loginLogId) {
		this.loginLogId = loginLogId;
	}
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
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

}
