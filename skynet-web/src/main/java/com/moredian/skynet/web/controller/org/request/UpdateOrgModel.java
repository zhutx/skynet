package com.moredian.skynet.web.controller.org.request;

public class UpdateOrgModel {
	
	/** 机构id　*/
	private Long orgId; // required
	/** 机构名　*/
	private String orgName; // optional
	/** 省行政编码　*/
	private Integer provinceId; // optional
	/** 市行政编码　*/
	private Integer cityId; // optional
	/** 区县行政编码　*/
	private Integer districtId; // optional
	/** 联系人　*/
	private String contact; // optional
	/** 联系电话　*/
	private String phone; // optional
	/** 地址　*/
	private String address; // optional
	/** 说明　*/
	private String memo; // optional
	
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

}
