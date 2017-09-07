package com.moredian.fishnet.device.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class EquipmentDto implements Serializable {

	private static final long serialVersionUID = 7737743486263833982L;

	//设备ID 
	private Long equipmentId;
	
	//主机构ID 
	private Long orgId;
	
	//子机构编号
	private Long subOrgId;


	//子机构码
	private String subOrgCode;
	
	//设备名称 
	private String equipmentName;
	
	//设备类型 
	private Integer equipmentType;
	
	//ip地址 
	private String ipAddress;
	
	//mac地址 
	private String macAddress;
	
	//设备位置描述 
	private String addressDesc;
	
	//设备唯一号（通过 cpu 和 mac 计算出设备唯一号） 
	private String uniqueNumber;

	//设备token 用于和三分接口的通讯
	private String equipmentToken;

	//cpu_id 
	private String cpuId;
	
	//设备授权密钥
	private String accessKeySecret;
	
	//创建时间 
	private Date gmtCreate;
	
	//更新时间 
	private Date gmtModify;

	public String getEquipmentToken() {
		return equipmentToken;
	}

	public void setEquipmentToken(String equipmentToken) {
		this.equipmentToken = equipmentToken;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
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
	
	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
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
	
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
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

	public String getSubOrgCode() {
		return subOrgCode;
	}

	public void setSubOrgCode(String subOrgCode) {
		this.subOrgCode = subOrgCode;
	}
	
}
