package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

public class AlarmRecordMsg implements Serializable {
	
	private static final long serialVersionUID = -1636577622112376503L;
	
	private Long alarmRecordId;
	private Integer alarmType;
	private Long authRecordId;
	private Long orgId;
	private String orgName;
	private String orgAddress;
	private Integer provinceCode;
	private Integer cityCode;
	private Integer districtCode;
	private Integer townCode;
	private Long alarmTime;
	private Float confidence;
	private Long provPoliceOrgId;
	private Long cityPoliceOrgId;
	private Long districtPoliceOrgId;
	private Long townPoliceOrgId;
	private Long libraryPoliceOrgId;
	private String libraryPoliceOrgName;
	private Long libraryId;
	private String libraryName;
	private Long personId;
	private String personName;
	private String verifyFaceUrl;
	private String certNo;
	private Integer sex;
	private Integer nature;
	private Integer criminalFlag;
	private String memo;
	
	public Long getAlarmRecordId() {
		return alarmRecordId;
	}
	public void setAlarmRecordId(Long alarmRecordId) {
		this.alarmRecordId = alarmRecordId;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public Long getAuthRecordId() {
		return authRecordId;
	}
	public void setAuthRecordId(Long authRecordId) {
		this.authRecordId = authRecordId;
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
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
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
	public Long getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Long alarmTime) {
		this.alarmTime = alarmTime;
	}
	public Float getConfidence() {
		return confidence;
	}
	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}
	public Long getProvPoliceOrgId() {
		return provPoliceOrgId;
	}
	public void setProvPoliceOrgId(Long provPoliceOrgId) {
		this.provPoliceOrgId = provPoliceOrgId;
	}
	public Long getCityPoliceOrgId() {
		return cityPoliceOrgId;
	}
	public void setCityPoliceOrgId(Long cityPoliceOrgId) {
		this.cityPoliceOrgId = cityPoliceOrgId;
	}
	public Long getDistrictPoliceOrgId() {
		return districtPoliceOrgId;
	}
	public void setDistrictPoliceOrgId(Long districtPoliceOrgId) {
		this.districtPoliceOrgId = districtPoliceOrgId;
	}
	public Long getTownPoliceOrgId() {
		return townPoliceOrgId;
	}
	public void setTownPoliceOrgId(Long townPoliceOrgId) {
		this.townPoliceOrgId = townPoliceOrgId;
	}
	public Long getLibraryPoliceOrgId() {
		return libraryPoliceOrgId;
	}
	public void setLibraryPoliceOrgId(Long libraryPoliceOrgId) {
		this.libraryPoliceOrgId = libraryPoliceOrgId;
	}
	public String getLibraryPoliceOrgName() {
		return libraryPoliceOrgName;
	}
	public void setLibraryPoliceOrgName(String libraryPoliceOrgName) {
		this.libraryPoliceOrgName = libraryPoliceOrgName;
	}
	public Long getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(Long libraryId) {
		this.libraryId = libraryId;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getVerifyFaceUrl() {
		return verifyFaceUrl;
	}
	public void setVerifyFaceUrl(String verifyFaceUrl) {
		this.verifyFaceUrl = verifyFaceUrl;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
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
	public Integer getCriminalFlag() {
		return criminalFlag;
	}
	public void setCriminalFlag(Integer criminalFlag) {
		this.criminalFlag = criminalFlag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
