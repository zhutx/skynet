package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class ActivateDeviceRequest implements Serializable {

	private static final long serialVersionUID = -4783993654544350156L;
	// 二维码
    private String qrCode;
    // cpu
    private String cpuId;
    // ip地址
    private String ipAddress;
    // 软件版本号
    private Integer version;
    // 设备类型
    private Integer equipmentType;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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
