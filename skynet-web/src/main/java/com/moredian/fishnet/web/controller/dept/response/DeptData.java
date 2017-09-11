package com.moredian.fishnet.web.controller.dept.response;

public class DeptData {
	
	private Long deptId;
	private String deptName;
	private Integer memberSize;
	
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

}
