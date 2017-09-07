package com.moredian.fishnet.device.domain;

import java.io.Serializable;

public class WhiteDeviceQueryCondition implements Serializable {
	
	private static final long serialVersionUID = 5933210213130365233L;

	//sn号
	private String serialNumber;

	//mac地址
	private String macAddress;
	//mac地址2，如果有线无线一起用，需要配置2个mac地址
	private String macAddress2;
	private Integer deviceType;
	//私钥
	private String secretKey;
	private Integer patchFlag;
	private Long orgId;
	private Integer activityStatus;
	private String activationCode;
	private Integer startRow;
	private Integer pageSize;
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
	public String getMacAddress2() {
		return macAddress2;
	}
	public void setMacAddress2(String macAddress2) {
		this.macAddress2 = macAddress2;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
