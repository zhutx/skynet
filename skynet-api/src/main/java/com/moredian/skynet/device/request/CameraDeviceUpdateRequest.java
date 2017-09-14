package com.moredian.skynet.device.request;

import java.io.Serializable;

import com.moredian.skynet.device.enums.DeviceProviderType;

public class CameraDeviceUpdateRequest implements Serializable {
	
	private static final long serialVersionUID = -6193748358046734880L;
	
	/** 设备id */
	private Long deviceId; // required
	/** 机构id */
	private Long orgId; // required
//	/** 位置id */
//	private Long positionId; // required
	/** 供应商类型 {@link DeviceProviderType} */
	private Integer providerType; // required
	/** 摄像机视频流地址 */
	private String cameraStream; // required
	/** 摄像机NVR地址 */
	private String cameraNvr; // optional
	/** 摄像机IP */// 
	private String cameraIp; // optional
	/** 摄像机用户名 */// 
	private String cameraUsername; // optional
	/** 摄像机密码  */
	private String cameraPassword; // optional
	/** 摄像机分辨率 */
	private String cameraResolution; // optional
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
	public Integer getProviderType() {
		return providerType;
	}
	public void setProviderType(Integer providerType) {
		this.providerType = providerType;
	}
	public String getCameraStream() {
		return cameraStream;
	}
	public void setCameraStream(String cameraStream) {
		this.cameraStream = cameraStream;
	}
	public String getCameraNvr() {
		return cameraNvr;
	}
	public void setCameraNvr(String cameraNvr) {
		this.cameraNvr = cameraNvr;
	}
	public String getCameraIp() {
		return cameraIp;
	}
	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
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
	public String getCameraResolution() {
		return cameraResolution;
	}
	public void setCameraResolution(String cameraResolution) {
		this.cameraResolution = cameraResolution;
	}
	

}
