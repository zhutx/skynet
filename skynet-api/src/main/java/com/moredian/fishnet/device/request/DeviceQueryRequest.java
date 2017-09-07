package com.moredian.fishnet.device.request;

import java.io.Serializable;
import java.util.List;

public class DeviceQueryRequest implements Serializable {
	
	private static final long serialVersionUID = 8318720461530978962L;
	
	// 机构id
	private Long orgId;
	// 位置id
	private Long positionId;
	// 供应商类型
	private Integer providerType;
	// 设备名
	private String deviceName;
	// 设备类型
	private Integer deviceType;
	// 设备SN
	private String deviceSn;
	// 关键字
	private String keywords;
	// 状态 
	private Integer status;

	//设备类型list
	private List<Integer> deviceTypeList;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Integer getProviderType() {
		return providerType;
	}

	public void setProviderType(Integer providerType) {
		this.providerType = providerType;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Integer> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<Integer> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}
	

}
