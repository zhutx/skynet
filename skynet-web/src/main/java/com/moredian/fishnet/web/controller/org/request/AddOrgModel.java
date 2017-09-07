package com.moredian.fishnet.web.controller.org.request;

import com.moredian.fishnet.org.enums.OrgStatus;
import com.moredian.fishnet.org.enums.OrgType;

public class AddOrgModel {
	
	/** 机构名　*/
	private String orgName; // required
	/** 机构类型  [参考{@link OrgType}]　*/
	private Integer orgType; // required
	/** 状态　[参考{@link OrgStatus}] */
	private Integer status; // required
	/** 是否启用人脸识别业务 */
	private boolean enableRecognizeBiz = true; //optional
	/** 省id　*/
	private Integer provinceId; //optional
	/** 市id　*/
	private Integer cityId; //optional
	/** 区县id　*/
	private Integer districtId; //optional
	/** 联系人　*/
	private String contact; // optional
	/** 联系电话　*/
	private String phone; // optional
	/** 地址　*/
	private String address; // optional
	/** 说明　*/
	private String memo; // optional
	/** 经度　*/
	private Double lon; //  optional
	/** 维度　*/
	private Double lat; // optional
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public boolean isEnableRecognizeBiz() {
		return enableRecognizeBiz;
	}
	public void setEnableRecognizeBiz(boolean enableRecognizeBiz) {
		this.enableRecognizeBiz = enableRecognizeBiz;
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

}
