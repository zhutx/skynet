package com.moredian.skynet.device.domain;

import java.util.Date;

public class DeviceWhite {

	private Long deviceWhiteId;
	private Integer deviceType;
	private String deviceSn;
	private String deviceMac;
	private String secretKey;
	private Long bindOrgId;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeviceWhiteId() {
		return deviceWhiteId;
	}
	public void setDeviceWhiteId(Long deviceWhiteId) {
		this.deviceWhiteId = deviceWhiteId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Long getBindOrgId() {
		return bindOrgId;
	}
	public void setBindOrgId(Long bindOrgId) {
		this.bindOrgId = bindOrgId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
