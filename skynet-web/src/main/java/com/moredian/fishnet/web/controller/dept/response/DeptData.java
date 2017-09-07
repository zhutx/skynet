package com.moredian.fishnet.web.controller.dept.response;

public class DeptData {
	
	private Long deptId;
	private String deptName;
	private Integer memberSize;
	// 应前端要求而追加的多余参数
	private String commonName;
	private Integer dataFlag = 1;
	private String commonId;
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getMemberSize() {
		return memberSize;
	}
	public void setMemberSize(Integer memberSize) {
		this.memberSize = memberSize;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public Integer getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getCommonId() {
		return commonId;
	}
	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

}
