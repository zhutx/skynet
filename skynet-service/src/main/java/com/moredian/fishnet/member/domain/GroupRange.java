package com.moredian.fishnet.member.domain;

import java.io.Serializable;
import java.util.Date;

public class GroupRange implements Serializable {

	private static final long serialVersionUID = -180149190166033477L;
	
	private Long groupRangeId;
	private Long orgId;
	private Long groupId;
	private Integer rangeType;
	private Long rangeId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getGroupRangeId() {
		return groupRangeId;
	}
	public void setGroupRangeId(Long groupRangeId) {
		this.groupRangeId = groupRangeId;
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
	public Integer getRangeType() {
		return rangeType;
	}
	public void setRangeType(Integer rangeType) {
		this.rangeType = rangeType;
	}
	public Long getRangeId() {
		return rangeId;
	}
	public void setRangeId(Long rangeId) {
		this.rangeId = rangeId;
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
