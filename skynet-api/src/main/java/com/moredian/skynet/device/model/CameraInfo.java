package com.moredian.skynet.device.model;

import java.io.Serializable;

public class CameraInfo implements Serializable {

	private static final long serialVersionUID = -6286525335262227487L;
	
	// 设备id
	private Long deviceId;
	// 设备名
	private String deviceName;
	// 位置
	private String position;
	// 供应商类型
	private Integer providerType;
	// 摄像机视频流地址
	private String videoStream;
	// 摄像机IP
	private String ip;
	// 摄像机端口
	private Integer port;
	// 摄像机用户名
	private String username;
	// 摄像机密码 
	private String password;
	// 摄像机NVR地址
	private String nvr;
	// 摄像机分辨率
	private String resolution;
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	public String getVideoStream() {
		return videoStream;
	}
	public void setVideoStream(String videoStream) {
		this.videoStream = videoStream;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNvr() {
		return nvr;
	}
	public void setNvr(String nvr) {
		this.nvr = nvr;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
}
