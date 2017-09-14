package com.moredian.skynet.device.response;

import java.util.Date;

import java.io.Serializable;

public class EquipmentInventoryResponse implements Serializable {

	private static final long serialVersionUID = -0L;
	
	//主键id 
	private Long equipmentInventoryId;
	
	//sn号 
	private String sn;
	
	//mac地址 
	private String macAddress;
	
	//私钥 
	private String privateKey;
	
	//原始设备表id 
	private Long orgiEquipmentId;
	
	//设备sn号，mac地址，私钥使用状态 
	private Integer equipmentInventoryStatus;
	
	// 
	private Date gmtCreate;
	
	// 
	private Date gmtModify;
	

	public Long getEquipmentInventoryId() {
		return equipmentInventoryId;
	}

	public void setEquipmentInventoryId(Long equipmentInventoryId) {
		this.equipmentInventoryId = equipmentInventoryId;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	public Long getOrgiEquipmentId() {
		return orgiEquipmentId;
	}

	public void setOrgiEquipmentId(Long orgiEquipmentId) {
		this.orgiEquipmentId = orgiEquipmentId;
	}
	
	public Integer getEquipmentInventoryStatus() {
		return equipmentInventoryStatus;
	}

	public void setEquipmentInventoryStatus(Integer equipmentInventoryStatus) {
		this.equipmentInventoryStatus = equipmentInventoryStatus;
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
