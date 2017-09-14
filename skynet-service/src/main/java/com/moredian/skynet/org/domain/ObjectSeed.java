package com.moredian.skynet.org.domain;

import java.util.Date;

import java.io.Serializable;

public class ObjectSeed implements Serializable {
	
	private static final long serialVersionUID = -7362198740132130923L;
	
	private Long seedId;
	private Integer objectRange;
	private Integer objectCode;
	private Long objectSeed;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getSeedId() {
		return seedId;
	}
	public void setSeedId(Long seedId) {
		this.seedId = seedId;
	}
	public Integer getObjectRange() {
		return objectRange;
	}
	public void setObjectRange(Integer objectRange) {
		this.objectRange = objectRange;
	}
	public Integer getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(Integer objectCode) {
		this.objectCode = objectCode;
	}
	public Long getObjectSeed() {
		return objectSeed;
	}
	public void setObjectSeed(Long objectSeed) {
		this.objectSeed = objectSeed;
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
