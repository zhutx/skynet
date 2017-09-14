package com.moredian.skynet.device.request;

import java.io.Serializable;

public class WhiteDeviceAddRequest implements Serializable {

	private static final long serialVersionUID = 1108096896971913735L;

	private String serialNumber;
	private String macAddress;
	private String secretKey;
	private Integer patchFlag;
	private Long orgId;
	private String activationCode;
	private Integer equipmentType;
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
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}


}
