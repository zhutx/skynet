package com.moredian.skynet.device.request;

import java.io.Serializable;

public class DeviceActiveRequest implements Serializable {

	private static final long serialVersionUID = -3801504106604278300L;
	
	// 激活码
    private String activeCode;
    // ip地址
    private String ip;
    // cpu
    private String cpu;
    // mac地址
    private String mac;
    // 软件版本号
    private Integer version;
    // 设备类型
    private Integer deviceType;
    
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}
