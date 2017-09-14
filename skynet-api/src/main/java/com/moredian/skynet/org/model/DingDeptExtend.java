package com.moredian.skynet.org.model;

import java.io.Serializable;

public class DingDeptExtend implements Serializable {
	
	private static final long serialVersionUID = -150116185607932029L;
	
	private Long dingDepartmentId;
	private String dingDepartmentName;
	private Long dingDepartmentParentId;
	private Long dingDepartmentOrder;
	private Integer dingCreateDeptGroup;
	private Integer dingAutoAddUser;
	private Integer dingDeptHiding;
	private String dingDeptPermits;
	private String dingUserPermits;
	private Integer dingOuterDept;
	private String dingOuterPermitsDepts;
	private String dingOuterPermitUsers;
	private String dingOrgDeptOwner;
	private String dingDeptManagerUseridList;
	
	public Long getDingDepartmentId() {
		return dingDepartmentId;
	}
	public void setDingDepartmentId(Long dingDepartmentId) {
		this.dingDepartmentId = dingDepartmentId;
	}
	public String getDingDepartmentName() {
		return dingDepartmentName;
	}
	public void setDingDepartmentName(String dingDepartmentName) {
		this.dingDepartmentName = dingDepartmentName;
	}
	public Long getDingDepartmentParentId() {
		return dingDepartmentParentId;
	}
	public void setDingDepartmentParentId(Long dingDepartmentParentId) {
		this.dingDepartmentParentId = dingDepartmentParentId;
	}
	public Long getDingDepartmentOrder() {
		return dingDepartmentOrder;
	}
	public void setDingDepartmentOrder(Long dingDepartmentOrder) {
		this.dingDepartmentOrder = dingDepartmentOrder;
	}
	public Integer getDingCreateDeptGroup() {
		return dingCreateDeptGroup;
	}
	public void setDingCreateDeptGroup(Integer dingCreateDeptGroup) {
		this.dingCreateDeptGroup = dingCreateDeptGroup;
	}
	public Integer getDingAutoAddUser() {
		return dingAutoAddUser;
	}
	public void setDingAutoAddUser(Integer dingAutoAddUser) {
		this.dingAutoAddUser = dingAutoAddUser;
	}
	public Integer getDingDeptHiding() {
		return dingDeptHiding;
	}
	public void setDingDeptHiding(Integer dingDeptHiding) {
		this.dingDeptHiding = dingDeptHiding;
	}
	public String getDingDeptPermits() {
		return dingDeptPermits;
	}
	public void setDingDeptPermits(String dingDeptPermits) {
		this.dingDeptPermits = dingDeptPermits;
	}
	public String getDingUserPermits() {
		return dingUserPermits;
	}
	public void setDingUserPermits(String dingUserPermits) {
		this.dingUserPermits = dingUserPermits;
	}
	public Integer getDingOuterDept() {
		return dingOuterDept;
	}
	public void setDingOuterDept(Integer dingOuterDept) {
		this.dingOuterDept = dingOuterDept;
	}
	public String getDingOuterPermitsDepts() {
		return dingOuterPermitsDepts;
	}
	public void setDingOuterPermitsDepts(String dingOuterPermitsDepts) {
		this.dingOuterPermitsDepts = dingOuterPermitsDepts;
	}
	public String getDingOuterPermitUsers() {
		return dingOuterPermitUsers;
	}
	public void setDingOuterPermitUsers(String dingOuterPermitUsers) {
		this.dingOuterPermitUsers = dingOuterPermitUsers;
	}
	public String getDingOrgDeptOwner() {
		return dingOrgDeptOwner;
	}
	public void setDingOrgDeptOwner(String dingOrgDeptOwner) {
		this.dingOrgDeptOwner = dingOrgDeptOwner;
	}
	public String getDingDeptManagerUseridList() {
		return dingDeptManagerUseridList;
	}
	public void setDingDeptManagerUseridList(String dingDeptManagerUseridList) {
		this.dingDeptManagerUseridList = dingDeptManagerUseridList;
	}
	

}
