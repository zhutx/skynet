package com.moredian.skynet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class WhiteDevice implements Serializable {

	private static final long serialVersionUID = -5191168712388032829L;
	
	private Long whiteDeviceId;
	private String deviceSn;
	//use mac to do privilege check, wireless and wire have different mac.
	private String mac;
	private String mac2;
	private Integer deviceType;
	private String privateKey;
	private Long deviceId;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	public Long getWhiteDeviceId() {
		return whiteDeviceId;
	}
	public void setWhiteDeviceId(Long whiteDeviceId) {
		this.whiteDeviceId = whiteDeviceId;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getMac2() {
		return mac2;
	}
	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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
