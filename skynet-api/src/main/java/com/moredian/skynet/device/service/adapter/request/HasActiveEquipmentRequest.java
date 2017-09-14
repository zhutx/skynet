package com.moredian.skynet.device.service.adapter.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/1/3.
 */
public class HasActiveEquipmentRequest implements Serializable {

	private static final long serialVersionUID = 2726463402839355462L;

	private Long orgId;

    private Integer equipmentType;

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
}
