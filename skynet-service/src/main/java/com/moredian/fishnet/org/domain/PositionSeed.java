package com.moredian.fishnet.org.domain;

import java.util.Date;

import java.io.Serializable;

public class PositionSeed implements Serializable {
	
	private static final long serialVersionUID = -1889193951140786623L;
	
	private Long positionSeedId;
	private Long orgId;
	private Long parentId;
	private String parentCode;
	private Integer parentLevel;
	private Integer childSeed;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getPositionSeedId() {
		return positionSeedId;
	}
	public void setPositionSeedId(Long positionSeedId) {
		this.positionSeedId = positionSeedId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Integer getParentLevel() {
		return parentLevel;
	}
	public void setParentLevel(Integer parentLevel) {
		this.parentLevel = parentLevel;
	}
	public Integer getChildSeed() {
		return childSeed;
	}
	public void setChildSeed(Integer childSeed) {
		this.childSeed = childSeed;
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
