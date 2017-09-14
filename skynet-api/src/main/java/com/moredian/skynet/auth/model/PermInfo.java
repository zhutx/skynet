package com.moredian.skynet.auth.model;

import java.util.Date;

import java.io.Serializable;

public class PermInfo implements Serializable {

	private static final long serialVersionUID = 8132007687453290656L;
	
	private Long permId;
	private Integer moduleType;
	private Integer permType;
	private String permName;
	private Integer permAction;
	private String permUrl;
	private String permDesc;
	private Integer permLevel;
	private Long parentId;
	private Integer status;
	private Date gmtCreate;
	
	public Long getPermId() {
		return permId;
	}
	public void setPermId(Long permId) {
		this.permId = permId;
	}
	public Integer getPermType() {
		return permType;
	}
	public void setPermType(Integer permType) {
		this.permType = permType;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public Integer getPermAction() {
		return permAction;
	}
	public void setPermAction(Integer permAction) {
		this.permAction = permAction;
	}
	public String getPermUrl() {
		return permUrl;
	}
	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}
	public String getPermDesc() {
		return permDesc;
	}
	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}
	public Integer getPermLevel() {
		return permLevel;
	}
	public void setPermLevel(Integer permLevel) {
		this.permLevel = permLevel;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
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
	
}
