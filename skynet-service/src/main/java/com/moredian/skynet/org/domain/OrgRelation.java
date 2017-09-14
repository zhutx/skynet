package com.moredian.skynet.org.domain;

import java.util.Date;

import java.io.Serializable;

public class OrgRelation implements Serializable {

	private static final long serialVersionUID = -8737634596979356153L;
	
	private Long orgRelationId;
	private Long orgId;
	private Long relationOrgId;
	private Integer relationType;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getOrgRelationId() {
		return orgRelationId;
	}
	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getRelationOrgId() {
		return relationOrgId;
	}
	public void setRelationOrgId(Long relationOrgId) {
		this.relationOrgId = relationOrgId;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
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
