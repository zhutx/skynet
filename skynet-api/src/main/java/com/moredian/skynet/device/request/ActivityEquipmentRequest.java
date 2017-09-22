package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/11/18.
 */
public class ActivityEquipmentRequest implements Serializable {

	private static final long serialVersionUID = 2601694766081336791L;
	// 激活码
    private String activeCode;
    // cpu
    private String cpuId;
    // ip地址
    private String ipAddress;
    // mac地址
    private String macAddress;
    // 软件版本号
    private Integer version;
    // 设备类型
    private Integer equipmentType;
    // 自动布控
    private boolean autoDeploy;

    public boolean isAutoDeploy() {
        return autoDeploy;
    }

    public void setAutoDeploy(boolean autoDeploy) {
        this.autoDeploy = autoDeploy;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }
}
