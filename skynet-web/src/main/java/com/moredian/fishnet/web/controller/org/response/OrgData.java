package com.moredian.fishnet.web.controller.org.response;

import java.util.Date;

public class OrgData {
	
	private Long orgId;
	private String orgName;
	private Integer provinceId;
	private String provinceStr;
	private Integer cityId;
	private String cityStr;
	private Integer districtId;
	private String districtStr;
	private String contact;
	private String phone;
	private String address;
	private String memo;
	private Double lon;
	private Double lat;
	private String logoUrl;
	private Integer status;
	private Date gmtCreate;
	
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
	public String getProvinceStr() {
		return provinceStr;
	}
	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}
	public String getCityStr() {
		return cityStr;
	}
	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}
	public String getDistrictStr() {
		return districtStr;
	}
	public void setDistrictStr(String districtStr) {
		this.districtStr = districtStr;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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

}
