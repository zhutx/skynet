package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

public class AuthRecordMsg implements Serializable {
	
	private static final long serialVersionUID = 4516988729690491898L;
	
	private Long authRecordId;
	private Integer authWay;
	private String catchImageUrl;
	private String matchImageUrl;
	private Long deviceId;
	private String deviceSn;
	private Integer orgType;
	private Long orgId;
	private String orgName;
	private Integer provinceCode;
	private Integer cityCode;
	private Integer districtCode;
	private Integer townCode;
	private String orgAddress;
	private Long authTime;
	private Float quality;
	private Float confidence;
	private String realName;
	private Integer sex;
	private Integer nature;
	private String birthday;
	private String address;
	private String certNo;
	private String signOrg;
	private String validBeginDate;
	private String validEndDate;
	private Long provPoliceOrgId;
	private String provPoliceOrgName;
	private Long cityPoliceOrgId;
	private String cityPoliceOrgName;
	private Long districtPoliceOrgId;
	private String districtPoliceOrgName;
	private Long townPoliceOrgId;
	private String townPoliceOrgName;
	private Integer status;
	
	public Long getAuthRecordId() {
		return authRecordId;
	}
	public void setAuthRecordId(Long authRecordId) {
		this.authRecordId = authRecordId;
	}
	public Integer getAuthWay() {
		return authWay;
	}
	public void setAuthWay(Integer authWay) {
		this.authWay = authWay;
	}
	public String getCatchImageUrl() {
		return catchImageUrl;
	}
	public void setCatchImageUrl(String catchImageUrl) {
		this.catchImageUrl = catchImageUrl;
	}
	public String getMatchImageUrl() {
		return matchImageUrl;
	}
	public void setMatchImageUrl(String matchImageUrl) {
		this.matchImageUrl = matchImageUrl;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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
	public Integer getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Integer getCityCode() {
		return cityCode;
	}
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	public Integer getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}
	public Integer getTownCode() {
		return townCode;
	}
	public void setTownCode(Integer townCode) {
		this.townCode = townCode;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public Long getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Long authTime) {
		this.authTime = authTime;
	}
	public Float getQuality() {
		return quality;
	}
	public void setQuality(Float quality) {
		this.quality = quality;
	}
	public Float getConfidence() {
		return confidence;
	}
	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getNature() {
		return nature;
	}
	public void setNature(Integer nature) {
		this.nature = nature;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getSignOrg() {
		return signOrg;
	}
	public void setSignOrg(String signOrg) {
		this.signOrg = signOrg;
	}
	public String getValidBeginDate() {
		return validBeginDate;
	}
	public void setValidBeginDate(String validBeginDate) {
		this.validBeginDate = validBeginDate;
	}
	public String getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}
	public Long getProvPoliceOrgId() {
		return provPoliceOrgId;
	}
	public void setProvPoliceOrgId(Long provPoliceOrgId) {
		this.provPoliceOrgId = provPoliceOrgId;
	}
	public String getProvPoliceOrgName() {
		return provPoliceOrgName;
	}
	public void setProvPoliceOrgName(String provPoliceOrgName) {
		this.provPoliceOrgName = provPoliceOrgName;
	}
	public Long getCityPoliceOrgId() {
		return cityPoliceOrgId;
	}
	public void setCityPoliceOrgId(Long cityPoliceOrgId) {
		this.cityPoliceOrgId = cityPoliceOrgId;
	}
	public String getCityPoliceOrgName() {
		return cityPoliceOrgName;
	}
	public void setCityPoliceOrgName(String cityPoliceOrgName) {
		this.cityPoliceOrgName = cityPoliceOrgName;
	}
	public Long getDistrictPoliceOrgId() {
		return districtPoliceOrgId;
	}
	public void setDistrictPoliceOrgId(Long districtPoliceOrgId) {
		this.districtPoliceOrgId = districtPoliceOrgId;
	}
	public String getDistrictPoliceOrgName() {
		return districtPoliceOrgName;
	}
	public void setDistrictPoliceOrgName(String districtPoliceOrgName) {
		this.districtPoliceOrgName = districtPoliceOrgName;
	}
	public Long getTownPoliceOrgId() {
		return townPoliceOrgId;
	}
	public void setTownPoliceOrgId(Long townPoliceOrgId) {
		this.townPoliceOrgId = townPoliceOrgId;
	}
	public String getTownPoliceOrgName() {
		return townPoliceOrgName;
	}
	public void setTownPoliceOrgName(String townPoliceOrgName) {
		this.townPoliceOrgName = townPoliceOrgName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
