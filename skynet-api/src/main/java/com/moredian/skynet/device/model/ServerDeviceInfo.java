package com.moredian.skynet.device.model;

import java.util.Date;

import java.io.Serializable;

public class ServerDeviceInfo implements Serializable {
	
	private static final long serialVersionUID = 1836618971518937385L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 设备名
	private String deviceName;
	// 设备SN
	private String deviceSn;
	// ip
	private String host;
	// 端口
	private Integer port;
	// 外网ip
	private String outerHost;
	// 外网端口
	private Integer outerPort;
	// 第三方id
	private String tpHost;
	// 第三方端口
	private Integer tpPort;
	// 最大支持机构数
	private Integer maxOrgNum;
	// 最大底库人员数
	private Integer maxPersonNum;
	// 激活时间
	private Date activeTime;
	// 状态
	private Integer status;
	// 创建时间
	private Date gmtCreate;
	
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getOuterHost() {
		return outerHost;
	}
	public void setOuterHost(String outerHost) {
		this.outerHost = outerHost;
	}
	public Integer getOuterPort() {
		return outerPort;
	}
	public void setOuterPort(Integer outerPort) {
		this.outerPort = outerPort;
	}
	public String getTpHost() {
		return tpHost;
	}
	public void setTpHost(String tpHost) {
		this.tpHost = tpHost;
	}
	public Integer getTpPort() {
		return tpPort;
	}
	public void setTpPort(Integer tpPort) {
		this.tpPort = tpPort;
	}
	public Integer getMaxOrgNum() {
		return maxOrgNum;
	}
	public void setMaxOrgNum(Integer maxOrgNum) {
		this.maxOrgNum = maxOrgNum;
	}
	public Integer getMaxPersonNum() {
		return maxPersonNum;
	}
	public void setMaxPersonNum(Integer maxPersonNum) {
		this.maxPersonNum = maxPersonNum;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
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
	
}
