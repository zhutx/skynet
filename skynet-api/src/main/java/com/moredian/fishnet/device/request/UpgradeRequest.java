package com.moredian.fishnet.device.request;

import java.io.Serializable;

public class UpgradeRequest  implements Serializable {
	
	private static final long serialVersionUID = 193748358046734880L;
	private Long orgId;
	private String serialNumber;
	private String uri;
	private String deviceVersion;
	private String userName;
	private String password;
	private String sign;
	private int delaySeconds;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
