package com.moredian.skynet.device.request;

import java.io.Serializable;

public class TransferRequest  implements Serializable {
	
	private static final long serialVersionUID = 123748358046734880L;
	private Long orgId;
	private String serialNumber;
	private int exclusive;    
	private int delaySeconds;    
	private String body;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getExclusive() {
		return exclusive;
	}
	public void setExclusive(int exclusive) {
		this.exclusive = exclusive;
	}
	public int getDelaySeconds() {
		return delaySeconds;
	}
	public void setDelaySeconds(int delaySeconds) {
		this.delaySeconds = delaySeconds;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
