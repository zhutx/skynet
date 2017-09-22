package com.moredian.skynet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionUser implements Serializable {
	
	private static final long serialVersionUID = 382729077766219382L;
	
	private Integer moduleType;
	private Long accountId;
	private String accountName;
	private Integer orgType;
	private Long orgId;
	private String orgName;
	private Long operId;
	private String operName;
	private String mobile;
	private String email;
	private Integer orgLevel;
	private Integer tpType;
	private Integer provCode;
	private Integer cityCode;
	private Integer districtCode;
	private Integer townCode;
	private SessionExtends extend = new SessionExtends();
	private List<String> permUrls = new ArrayList<>();
	
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Integer getTpType() {
		return tpType;
	}
	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}
	public Integer getProvCode() {
		return provCode;
	}
	public void setProvCode(Integer provCode) {
		this.provCode = provCode;
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
	public List<String> getPermUrls() {
		return permUrls;
	}
	public void setPermUrls(List<String> permUrls) {
		this.permUrls = permUrls;
	}
	public SessionExtends getExtend() {
		return extend;
	}
	public void setExtend(SessionExtends extend) {
		this.extend = extend;
	}
	

}
