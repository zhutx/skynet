package com.moredian.fishnet.device.model;

import java.io.Serializable;
import java.util.Date;

public class DeviceInfo implements Serializable {
	
	private static final long serialVersionUID = 1836618971518937385L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 位置id
	private Long positionId;
	// 位置
	private String position;
	// 设备类型
	private Integer deviceType;
	// 设备名
	private String deviceName;
	// 设备SN
	private String deviceSn;
	// 激活码
	private String activeCode;
	// 激活时间
	private Date activeTime;
	// ip
	private String ip;
	// 端口
	private Integer port;
	// mac
	private String mac;
	// cpu
	private String cpu;
	// 软件版本号
	private Integer version;
	// 网络状态
	private Integer netState;
	// 扩展信息
	private String extendsInfo;
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
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	public Integer getNetState() {
		return netState;
	}
	public void setNetState(Integer netState) {
		this.netState = netState;
	}
	public String getExtendsInfo() {
		return extendsInfo;
	}
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
	}
	
}
