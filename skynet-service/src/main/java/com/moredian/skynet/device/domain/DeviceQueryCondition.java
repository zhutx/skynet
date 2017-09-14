package com.moredian.skynet.device.domain;

import java.io.Serializable;
import java.util.List;

public class DeviceQueryCondition implements Serializable {
	
	private static final long serialVersionUID = 5933210213130365233L;
	
	// 机构id
	private Long orgId;
	// 子机构id
	private Long childOrgId;
	// 设备名
	private String deviceName;
	// 位置id
	private Long positionId;
	// 供应商类型
	private Integer providerType;
	// 设备类型
	private Integer deviceType;
	// 设备SN
	private String deviceSn;
	// 关键字
	private String keywords;
	// 状态集 
	private List<Integer> statusList;

	//设备类型集合
	private List<Integer> deviceTypeList;
	
	private Integer startRow;
	private Integer pageSize;
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getChildOrgId() {
		return childOrgId;
	}
	public void setChildOrgId(Long childOrgId) {
		this.childOrgId = childOrgId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	public List<Integer> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}
	public List<Integer> getDeviceTypeList() {
		return deviceTypeList;
	}
	public void setDeviceTypeList(List<Integer> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	

}
