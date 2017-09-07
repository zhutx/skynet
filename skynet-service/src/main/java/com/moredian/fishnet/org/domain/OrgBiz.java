package com.moredian.fishnet.org.domain;

import java.util.Date;

import java.io.Serializable;

public class OrgBiz implements Serializable {

	private static final long serialVersionUID = -2782119033671973149L;
	
	private Long orgBizId;
	
	private Long orgId;
	
	private Integer bizType;
	
	private Integer status;
	
	private Date gmtCreate;
	
	private Date gmtModify;

	public Long getOrgBizId() {
		return orgBizId;
	}

	public void setOrgBizId(Long orgBizId) {
		this.orgBizId = orgBizId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
