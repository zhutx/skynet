package com.moredian.fishnet.device.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class DEquipmentDto implements Serializable {

	private static final long serialVersionUID = 2157280373188769932L;

	// 
	private Long dEquipmentId;
	
	// 
	private Long orgId;
	
	// 
	private Long subOrgId;
	
	// 
	private String equipmentName;
	
	// 
	private Integer equipmentType;
	
	// 
	private String ipAddress;
	
	// 
	private String macAddress;
	
	// 
	private String uniqueNumber;

	//设备token 用于和三方平台通讯
	private String equipmentToken;
	
	// 
	private String cpuId;
	
	// 
	private Date gmtCreate;
	
	// 
	private Date gmtModify;

	public Long getdEquipmentId() {
		return dEquipmentId;
	}

	public void setdEquipmentId(Long dEquipmentId) {
		this.dEquipmentId = dEquipmentId;
	}

	public String getEquipmentToken() {
		return equipmentToken;
	}

	public void setEquipmentToken(String equipmentToken) {
		this.equipmentToken = equipmentToken;
	}

	public Long getDEquipmentId() {
		return dEquipmentId;
	}

	public void setDEquipmentId(Long dEquipmentId) {
		this.dEquipmentId = dEquipmentId;
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
	
	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public String getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
	
	public String getCpuId() {
		return cpuId;
	}

	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
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
