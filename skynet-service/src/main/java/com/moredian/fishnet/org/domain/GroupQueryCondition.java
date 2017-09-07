package com.moredian.fishnet.org.domain;

import java.io.Serializable;

public class GroupQueryCondition implements Serializable {

	private static final long serialVersionUID = 5783152219732270638L;
	
	private Long orgId;
	private String groupName;
	
	private Integer startRow;
	private Integer pageSize;
	
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
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
