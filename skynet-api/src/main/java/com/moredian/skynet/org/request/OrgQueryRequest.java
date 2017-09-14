package com.moredian.skynet.org.request;

import java.io.Serializable;
import com.moredian.skynet.org.enums.OrgStatus;
import com.moredian.skynet.org.enums.OrgType;

public class OrgQueryRequest implements Serializable {

	private static final long serialVersionUID = -2273360917182256786L;
	
	/** 机构类型  参考{@link OrgType}　*/
	private Integer orgType;
	/** 机构名　*/
	private String orgName;
	/** 省id　*/
	private Integer provinceId;
	/** 市id　*/
	private Integer cityId;
	/** 区县id　*/
	private Integer districtId;
	/** 机构状态  参考{@link OrgStatus}　*/
	private Integer status;
	
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

}
