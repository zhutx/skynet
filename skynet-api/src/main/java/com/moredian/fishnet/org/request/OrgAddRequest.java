package com.moredian.fishnet.org.request;

import java.io.Serializable;
import com.moredian.fishnet.org.enums.OrgStatus;
import com.moredian.fishnet.org.enums.OrgType;

public class OrgAddRequest implements Serializable {

	private static final long serialVersionUID = -6241661386378385242L;
	
    /** 机构名　*/
	private String orgName; // required
	/** 机构类型  [参考{@link OrgType}]　*/
	private Integer orgType; // required
	/** 机构层级 */
	private Integer orgLevel = 1; // optional
	/** 父机构id */
	private Long parentId = 0L; // optional
	/** 省id　*/
	private Integer provinceId; //optional
	/** 市id　*/
	private Integer cityId; //optional
	/** 区县id　*/
	private Integer districtId; //optional
	/** 街道id */
	private Integer townId; // optional
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
	public Integer getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	public Integer getTownId() {
		return townId;
	}
	public void setTownId(Integer townId) {
		this.townId = townId;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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
