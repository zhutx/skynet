package com.moredian.fishnet.device.response;

import java.util.Date;

import java.io.Serializable;

public class DeviceAddResponse implements Serializable {
	
	private static final long serialVersionUID = 7756165391088052393L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 位置id
	private Long positionId;
	// 位置
	private String position;
	// 供应商类型
	private Integer providerType;
	// 设备类型
	private Integer deviceType;
	// 设备名
	private String deviceName;
	// 设备SN
	private String deviceSn;
	// 摄像机NVR地址
	private String cameraNvr;
	// 摄像机用户名
	private String cameraUsername;
	// 摄像机密码
	private String cameraPassword;
	// 摄像机视频流地址
	private String cameraStream;
	// 摄像机分辨率
	private String cameraResolution;
	// 激活码
	private String activeCode;
	// 设备扩展信息
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getProviderType() {
		return providerType;
	}
	public void setProviderType(Integer providerType) {
		this.providerType = providerType;
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
	public String getCameraNvr() {
		return cameraNvr;
	}
	public void setCameraNvr(String cameraNvr) {
		this.cameraNvr = cameraNvr;
	}
	public String getCameraUsername() {
		return cameraUsername;
	}
	public void setCameraUsername(String cameraUsername) {
		this.cameraUsername = cameraUsername;
	}
	public String getCameraPassword() {
		return cameraPassword;
	}
	public void setCameraPassword(String cameraPassword) {
		this.cameraPassword = cameraPassword;
	}
	public String getCameraStream() {
		return cameraStream;
	}
	public void setCameraStream(String cameraStream) {
		this.cameraStream = cameraStream;
	}
	public String getCameraResolution() {
		return cameraResolution;
	}
	public void setCameraResolution(String cameraResolution) {
		this.cameraResolution = cameraResolution;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
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
	
}
