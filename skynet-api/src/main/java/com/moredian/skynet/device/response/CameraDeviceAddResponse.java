package com.moredian.skynet.device.response;

import java.io.Serializable;

public class CameraDeviceAddResponse implements Serializable {

	private static final long serialVersionUID = -4139388831733403451L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 位置id
	private Long positionId;
	// 供应商类型
	private Integer providerType;
	// 设备类型
	private Integer deviceType;
	// 设备名
	private String deviceName;
	// 摄像机NVR地址
	private String cameraNvr;
	// 摄像机IP
	private String ip;
	// 摄像机用户名
	private String cameraUsername;
	// 摄像机密码 
	private String cameraPassword;
	// 摄像机视频流地址
	private String cameraStream;
	// 摄像机分辨率
	private String cameraResolution;
	// 状态
	private Integer status;
	
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
	public String getCameraNvr() {
		return cameraNvr;
	}
	public void setCameraNvr(String cameraNvr) {
		this.cameraNvr = cameraNvr;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
