package com.moredian.fishnet.device.domain;

import java.io.Serializable;

public class DeployQueryCondition implements Serializable {

	private static final long serialVersionUID = 8361547985494215798L;
	
	private Long orgId;
	private Long deviceId;
	private Integer status;
	private Integer startRow;
	private Integer pageSize;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
