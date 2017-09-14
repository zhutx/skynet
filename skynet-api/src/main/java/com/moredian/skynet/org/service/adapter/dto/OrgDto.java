package com.moredian.skynet.org.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class OrgDto implements Serializable {

	private static final long serialVersionUID = 8730765001617295611L;

	//机构id
	private Long orgId;

	//机构编码 【zipcode 6】【机构类型 2 】【主机构编号6】【子3】【子3】【子3】【子3】【子2】【子2】【子2】【子2】【子2】
	private String orgCode;
	
	//名称 
	private String orgName;
	
	//拼音码
	private String engName;
	
	//省ID 
	private Integer provinceId;
	
	//市ID 
	private Integer cityId;
	
	//区ID 
	private Integer districtId;
	
	//机构类型 
	private Integer orgType;
	
	private Integer orgLevel;
	
	private String contact;
	
	private String phone;
	
	//详细地址 
	private String detailedAddress;
	
	//备注 
	private String remark;
	
	private Long parentOrgId;
	
	private Long belongOrgId;
	
	//地理坐标LOG 
	private Double lon;
	
	//地理坐标LAT 
	private Double lat;
	
	//hashCode29 
	private Long geoBit29;
	
	//hashCode27 
	private Long geoBit27;
	
	private Integer status;
	
	//创建时间 
	private Date gmtCreate;
	
	//更新时间 
	private Date gmtModify;
	
	private String supportedModules;

	public String getSupportedModules() {
		return supportedModules;
	}

	public void setSupportedModules(String supportedModules) {
		this.supportedModules = supportedModules;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	
	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	
	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Long getGeoBit29() {
		return geoBit29;
	}

	public void setGeoBit29(Long geoBit29) {
		this.geoBit29 = geoBit29;
	}
	
	public Long getGeoBit27() {
		return geoBit27;
	}

	public void setGeoBit27(Long geoBit27) {
		this.geoBit27 = geoBit27;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Long getBelongOrgId() {
		return belongOrgId;
	}

	public void setBelongOrgId(Long belongOrgId) {
		this.belongOrgId = belongOrgId;
	}

	
}
