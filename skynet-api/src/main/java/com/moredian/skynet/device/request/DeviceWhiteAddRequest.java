package com.moredian.skynet.device.request;

import java.io.Serializable;

public class DeviceWhiteAddRequest implements Serializable {
	
	private static final long serialVersionUID = -1981097298321652435L;
	
	private Integer deviceType;
	private String deviceSn;
	private String deviceMac;
	private String secretKey;
	private Long bindOrgId;
	
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

}
