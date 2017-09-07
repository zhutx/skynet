package com.moredian.fishnet.device.model;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/29.
 */
public class CameraDeviceInfoVo  implements Serializable {

    private static final long serialVersionUID = -6286525335262227487L;

    // 设备id
    private Long cameraId;
    // 机构id
    private Long orgId;
    // 设备名
    private String cameraName;
    // 供应商类型
    private Integer providerType;
    // 摄像机视频流地址
    private String cameraStream;
    // 摄像机分辨率
    private String cameraResolution;
	public Long getCameraId() {
		return cameraId;
	}
	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
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
	public String getCameraResolution() {
		return cameraResolution;
	}
	public void setCameraResolution(String cameraResolution) {
		this.cameraResolution = cameraResolution;
	}
	
}
