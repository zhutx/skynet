package com.moredian.fishnet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class DeviceMatch implements Serializable {

	private static final long serialVersionUID = 6794960796896561507L;
	
	private Long deviceMatchId;
	private Long orgId;
	private Long boxId;
	private Long cameraId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeviceMatchId() {
		return deviceMatchId;
	}
	public void setDeviceMatchId(Long deviceMatchId) {
		this.deviceMatchId = deviceMatchId;
	}
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
