package com.moredian.skynet.web.controller.device.request;

import com.moredian.skynet.model.PageModel;

public class SearchDeviceModel extends PageModel {
	
	private Long orgId;
	private Integer equipmentType;
	private String macAddress;
	private Long subOrgId;
	private Integer status;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Long getSubOrgId() {
		return subOrgId;
	}
	public void setSubOrgId(Long subOrgId) {
		this.subOrgId = subOrgId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
