package com.moredian.fishnet.member.domain;

import java.util.Date;

import java.io.Serializable;

public class GroupPerson implements Serializable {

	private static final long serialVersionUID = -3342506918168518252L;
	
	private Long groupPersonId;
	private Long orgId;
	private Long groupId;
	private Integer personType;
	private Long personId;
	private String personName;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getGroupPersonId() {
		return groupPersonId;
	}
	public void setGroupPersonId(Long groupPersonId) {
		this.groupPersonId = groupPersonId;
	}
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
	public Integer getPersonType() {
		return personType;
	}
	public void setPersonType(Integer personType) {
		this.personType = personType;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
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
