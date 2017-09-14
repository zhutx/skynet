package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class UpdateQrCodeStatusRequest implements Serializable{
    private String qrCode;
    private Long orgId;
   // private Long subOrgId;
    // 自动布控
    //private boolean autoDeploy;
    // 设备类型
 //   private Integer equipmentType;

//    public Integer getEquipmentType() {
//        return equipmentType;
//    }
//
//    public void setEquipmentType(Integer equipmentType) {
//        this.equipmentType = equipmentType;
//    }

//    public boolean isAutoDeploy() {
//        return autoDeploy;
//    }
//
//    public void setAutoDeploy(boolean autoDeploy) {
//        this.autoDeploy = autoDeploy;
//    }


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

//    public Long getSubOrgId() {
//        return subOrgId;
//    }
//
//    public void setSubOrgId(Long subOrgId) {
//        this.subOrgId = subOrgId;
//    }
}
