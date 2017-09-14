package com.moredian.skynet.device.domain;

import java.util.Date;

import java.io.Serializable;

public class DeviceConfig implements Serializable {

	private static final long serialVersionUID = 7305693399014168896L;
	
	private Long deviceConfigId;
	private String deviceSn;
	private String xmlConfig;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeviceConfigId() {
		return deviceConfigId;
	}
	public void setDeviceConfigId(Long deviceConfigId) {
		this.deviceConfigId = deviceConfigId;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getXmlConfig() {
		return xmlConfig;
	}
	public void setXmlConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
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
