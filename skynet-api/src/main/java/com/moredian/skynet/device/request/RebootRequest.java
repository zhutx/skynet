package com.moredian.skynet.device.request;

import java.io.Serializable;

 
public class RebootRequest  implements Serializable {
	
	private static final long serialVersionUID = 193008358046734880L;
	private Long orgId;
	private String serialNumber;
	private int delaySeconds;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getDelaySeconds() {
		return delaySeconds;
	}
	public void setDelaySeconds(int delaySeconds) {
		this.delaySeconds = delaySeconds;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}    
	
	
}
