package com.moredian.fishnet.device.model;

import java.io.Serializable;
import java.util.Date;

public class WhiteDeviceInfo implements Serializable {

	private static final long serialVersionUID = -2246865273005796729L;

	//sn号
	private String serialNumber;
	//mac地址
	private String macAddress;
	//私钥
	private String secretKey;

	private Integer patchFlag;

	private Long orgId;

	private Integer activityStatus;

	private String activationCode;

	private Date gmtCreate;
	private Date gmtModify;
	
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}


}
