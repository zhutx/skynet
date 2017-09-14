package com.moredian.skynet.device.response;

import java.util.Date;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/11/18.
 */
public class ActivateDeviceResponse implements Serializable {
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

    //city name
    private String cityName;
    //ip地址
    //private String ipAddress;
    //mac地址
   // private String macAddress;
    //设备位置描述
    private String addressDesc;
    //设备唯一号（通过 cpu 和 mac 计算出设备唯一号）
    private String serialNumber;
    //设备token 用于和三分接口的通讯
   // private String equipmentToken;
    //cpu_id
    //private String cpuId;
    //设备授权密钥
    private String accessKeySecret;
    //创建时间
    private Date gmtCreate;
    //更新时间
    //private Date gmtModify;
 
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
 

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
 
    public String getSubOrgCode() {
        return subOrgCode;
    }

    public void setSubOrgCode(String subOrgCode) {
        this.subOrgCode = subOrgCode;
    }

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
