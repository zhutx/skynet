package com.moredian.skynet.device.request;

import java.io.Serializable;

import com.moredian.skynet.device.enums.DeviceProviderType;

/**
 * Created by xxu on 2017/6/28.
 */
public class BoxUpdateRequest implements Serializable{

	private static final long serialVersionUID = -7956313535647961510L;

	private Long orgId; // required

    private Long boxId;//moredian box id

    private Long cameraId;//camera id. It the value is not null,it's update operation

    /** 供应商类型 {@link DeviceProviderType} */
    private Integer providerType; // required
    /** 摄像机视频流地址 */
    private String cameraStream; // required
    /** 摄像机分辨率 */
    private String cameraResolution; // optional
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getBoxId() {
		return boxId;
	}
	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}
	public Long getCameraId() {
		return cameraId;
	}
	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
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
