package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/13.
 */
public class ActivationCodeRequest implements Serializable {

    //激活码
    private String activationCode;

    //设备序列号
    private String deviceSn;

    private String deviceMacaddress;

    // 软件版本号
    private Integer version;
    // 设备类型
    private Integer equipmentType;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getDeviceMacaddress() {
        return deviceMacaddress;
    }

    public void setDeviceMacaddress(String deviceMacaddress) {
        this.deviceMacaddress = deviceMacaddress;
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
