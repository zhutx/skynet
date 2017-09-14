package com.moredian.skynet.device.request;

import java.io.Serializable;

public class CameraDeviceQueryRequest implements Serializable {
	
	private static final long serialVersionUID = 8318720461530978962L;
	
	// 机构id
	private Long orgId;
	// 供应商类型
	private Integer providerType;
	// 状态
	private Integer status;
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getProviderType() {
		return providerType;
	}
	public void setProviderType(Integer providerType) {
		this.providerType = providerType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

	
}
