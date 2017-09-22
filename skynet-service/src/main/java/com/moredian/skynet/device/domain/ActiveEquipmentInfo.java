package com.moredian.skynet.device.domain;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class ActiveEquipmentInfo implements Serializable{
	private static final long serialVersionUID = 584888223722051481L;
	//sn码
    private String sn;
    //mac地址
    private String macAddress;
    //私钥
    private String privateKey;
    private String qrCode;
    private Long orgId;
    private Long subOrgId;
    private Integer modelType;

    private String cityName;
    // 设备类型
    private Integer equipmentType;

    private boolean autoDeploy;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public boolean isAutoDeploy() {
        return autoDeploy;
    }

    public void setAutoDeploy(boolean autoDeploy) {
        this.autoDeploy = autoDeploy;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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
}
