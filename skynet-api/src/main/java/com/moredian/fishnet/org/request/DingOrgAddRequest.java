package com.moredian.fishnet.org.request;

import java.io.Serializable;

public class DingOrgAddRequest implements Serializable {

	private static final long serialVersionUID = 7925304965295863959L;
	
	private String orgName; // required
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private String address;
    
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


}
