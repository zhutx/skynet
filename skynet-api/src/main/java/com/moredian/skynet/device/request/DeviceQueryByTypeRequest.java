package com.moredian.skynet.device.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxu on 2017/6/29.
 */
public class DeviceQueryByTypeRequest implements Serializable {

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
    private List<Integer> deviceTypeLists;
    // 设备SN
    private String deviceSn;
    // 关键字
    private String keywords;
    // 状态
    private Integer status;
    
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
	public List<Integer> getDeviceTypeLists() {
		return deviceTypeLists;
	}
	public void setDeviceTypeLists(List<Integer> deviceTypeLists) {
		this.deviceTypeLists = deviceTypeLists;
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
}
