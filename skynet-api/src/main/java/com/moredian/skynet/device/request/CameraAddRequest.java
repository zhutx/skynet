package com.moredian.skynet.device.request;

import java.io.Serializable;

import com.moredian.skynet.device.enums.DeviceProviderType;

public class CameraAddRequest implements Serializable {

	private static final long serialVersionUID = -6866297619442270357L;
	
	/** 机构id */
	private Long orgId; // required
	/** 位置 */
	private String position; // optional
	/** 供应商类型 {@link DeviceProviderType} */
	private Integer providerType; // required
	/** 摄像机视频流地址 */
	private String videoStream; // required
	/** 摄像机NVR地址 */
	private String nvr; // optional
	/** 摄像机IP */// 
	private String ip; // optional
	private Integer port; // optional
	/** 摄像机用户名 */// 
	private String username; // optional
	/** 摄像机密码  */
	private String password; // optional
	/** 摄像机分辨率 */
	private String resolution; // optional
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public String getNvr() {
		return nvr;
	}
	public void setNvr(String nvr) {
		this.nvr = nvr;
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
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
}
