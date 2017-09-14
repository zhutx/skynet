package com.moredian.skynet.device.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class OrgiEquipmentDto implements Serializable {

	private static final long serialVersionUID = 3793063864958786395L;

	//原设备编号
    private Long orgiEquipmentId;

    //主机构编号
    private Long orgId;

    //子机构编号
    private Long subOrgId;

    //设备名称
    private String equipmentName;

    //设备类型
    private Integer equipmentType;

    //设备sn码
    private String equipmentSn;

    //设备编码
    private String uniqueNumber;

    // MAC地址
    private String macAddress;
    
    // 软件版本号
    private Integer version;

    //状态
    private Integer status;

    //激活码
    private String activeCode;

    //激活时间
    private Date gmtActiveCode;
    
    //布控标记
    private Integer monitorFlag;
    
    //挂载至某原始设备id
    private Long mountOrgiEId;
    
    //备注
    private String memo;

    //创建时间
    private Date gmtCreate;

    //修改时间
    private Date gmtModify;


    //===========camera============//
    private String cameraUser;
    private String cameraPass;
    private Integer companyType;
    private String internalIp;

    public String getCameraUser() {
        return cameraUser;
    }

    public void setCameraUser(String cameraUser) {
        this.cameraUser = cameraUser;
    }

    public String getCameraPass() {
        return cameraPass;
    }

    public void setCameraPass(String cameraPass) {
        this.cameraPass = cameraPass;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Long getOrgiEquipmentId() {
        return orgiEquipmentId;
    }

    public void setOrgiEquipmentId(Long orgiEquipmentId) {
        this.orgiEquipmentId = orgiEquipmentId;
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

    public String getEquipmentSn() {
        return equipmentSn;
    }

    public void setEquipmentSn(String equipmentSn) {
        this.equipmentSn = equipmentSn;
    }

    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public Date getGmtActiveCode() {
        return gmtActiveCode;
    }

    public void setGmtActiveCode(Date gmtActiveCode) {
        this.gmtActiveCode = gmtActiveCode;
    }

    public Integer getMonitorFlag() {
		return monitorFlag;
	}

	public void setMonitorFlag(Integer monitorFlag) {
		this.monitorFlag = monitorFlag;
	}

	public Long getMountOrgiEId() {
		return mountOrgiEId;
	}

	public void setMountOrgiEId(Long mountOrgiEId) {
		this.mountOrgiEId = mountOrgiEId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}