package com.moredian.skynet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
	
	private static final long serialVersionUID = 541504973948415989L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 设备名
	private String deviceName;
	// 设备类型
	private Integer deviceType;
	// 位置
	private String position;
	// 设备SN
	private String deviceSn;
	// 设备MAC
	private String deviceMac;
	// 激活码
	private String activeCode;
	// 激活时间
	private Date activeTime;
	// 在线标识
	private Integer onlineFlag;
	// 设备扩展信息
	private String extendsInfo;
	// 状态
	private Integer status;
	// 创建时间
	private Date gmtCreate;
	// 修改时间
	private Date gmtModify;
	
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
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public Integer getOnlineFlag() {
		return onlineFlag;
	}
	public void setOnlineFlag(Integer onlineFlag) {
		this.onlineFlag = onlineFlag;
	}
	public String getExtendsInfo() {
		return extendsInfo;
	}
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
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
