package com.moredian.skynet.device.request;

import java.io.Serializable;

public class CameraQueryRequest implements Serializable {
	
	private static final long serialVersionUID = 8318720461530978962L;
	
	// 机构id
	private Long orgId;
	// 供应商类型
	private Integer providerType;
	
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
	
}
