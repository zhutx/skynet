/**
 * 
 */
package com.moredian.skynet.device.service.adapter.dto;

import java.io.Serializable;

/**
 * @author erxiao 2017年4月1日
 */
public class SubOrgDeviceRelationDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 940715505857226860L;

	private Long orgId;
	
	private String subOrgCode;
	
	private Long subOrgId;
	
	private Long deviceId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getSubOrgCode() {
		return subOrgCode;
	}

	public void setSubOrgCode(String subOrgCode) {
		this.subOrgCode = subOrgCode;
	}

	public Long getSubOrgId() {
		return subOrgId;
	}

	public void setSubOrgId(Long subOrgId) {
		this.subOrgId = subOrgId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
