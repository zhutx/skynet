package com.moredian.skynet.org.model;

import java.util.Date;

import java.io.Serializable;

public class GroupInfo implements Serializable {

	private static final long serialVersionUID = 1226602237195498970L;
	
	private Long groupId;
	private String groupCode;
	private String groupName;
	private Long orgId;
	private Integer systemDefault;
	private Integer allMemberFlag;
	private Integer blackFlag;
	private Integer personSize;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getSystemDefault() {
		return systemDefault;
	}
	public void setSystemDefault(Integer systemDefault) {
		this.systemDefault = systemDefault;
	}
	public Integer getAllMemberFlag() {
		return allMemberFlag;
	}
	public void setAllMemberFlag(Integer allMemberFlag) {
		this.allMemberFlag = allMemberFlag;
	}
	public Integer getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(Integer blackFlag) {
		this.blackFlag = blackFlag;
	}
	public Integer getPersonSize() {
		return personSize;
	}
	public void setPersonSize(Integer personSize) {
		this.personSize = personSize;
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
