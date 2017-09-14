package com.moredian.skynet.device.request;

import java.io.Serializable;

public class WhiteDeviceQueryRequest implements Serializable {

	private static final long serialVersionUID = 897828573606903731L;

	private String serialNumber;

	//mac地址
	private String macAddress;
	//私钥
	private String secretKey;
	private Integer patchFlag;
	private Long orgId;
	private Integer activityStatus;
	private String activationCode;
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Integer getPatchFlag() {
		return patchFlag;
	}
	public void setPatchFlag(Integer patchFlag) {
		this.patchFlag = patchFlag;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	

}
