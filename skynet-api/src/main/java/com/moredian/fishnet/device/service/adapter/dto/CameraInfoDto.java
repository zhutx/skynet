package com.moredian.fishnet.device.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class CameraInfoDto implements Serializable {

	private static final long serialVersionUID = -0L;
	
	// 
	private Long cameraId;
	
	// 
	private Long orgiEId;
	
	// 
	private Long orgId;
	
	// 
	private Long subOrgId;
	
	// 
	private String cameraUser;
	
	// 
	private String cameraPass;
	
	// 
	private String internalIp;
	
	// 
	private String internetIp;
	
	//分辨率 
	private String resolution;
	
	//视频流地址 
	private String videoStream;
	
	// 
	private Integer companyType;
	
	// 
	private Integer status;
	
	// 
	private Date gmtCreate;
	
	// 
	private Date gmtModify;
	

	public Long getCameraId() {
		return cameraId;
	}

	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}
	
	public Long getOrgiEId() {
		return orgiEId;
	}

	public void setOrgiEId(Long orgiEId) {
		this.orgiEId = orgiEId;
	}
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Long getSubOrgId() {
		return subOrgId;
	}

	public void setSubOrgId(Long subOrgId) {
		this.subOrgId = subOrgId;
	}
	
	public String getCameraUser() {
		return cameraUser;
	}

	public void setCameraUser(String cameraUser) {
		this.cameraUser = cameraUser;
	}
	
	public String getCameraPass() {
		return cameraPass;
	}

	public void setCameraPass(String cameraPass) {
		this.cameraPass = cameraPass;
	}
	
	public String getInternalIp() {
		return internalIp;
	}

	public void setInternalIp(String internalIp) {
		this.internalIp = internalIp;
	}
	
	public String getInternetIp() {
		return internetIp;
	}

	public void setInternetIp(String internetIp) {
		this.internetIp = internetIp;
	}
	
	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public String getVideoStream() {
		return videoStream;
	}

	public void setVideoStream(String videoStream) {
		this.videoStream = videoStream;
	}
	
	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
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
