package com.moredian.fishnet.member.manager.impl;

public class DingDeptExtend {
	
	private Long dingDepartmentId;//部门id
    private String dingDepartmentName;//部门名称
    private Long dingDepartmentParentId;//部门父id
    private Long dingDepartmentOrder;//部门顺序
    private Integer dingCreateDeptGroup;//是否同步创建一个关联此部门的企业群, true表示是, false表示不是
    private Integer dingAutoAddUser; //当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是
    private Integer dingDeptHiding;//是否隐藏部门, true表示隐藏, false表示显示
    private String dingDeptPermits;//可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用|符号进行分割
    private String dingUserPermits;//可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用|符号进行分割
    private Integer dingOuterDept;//是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己
    private String dingOuterPermitsDepts;//本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割
    private String dingOuterPermitUsers;//本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用| 符号进行分割'
    private String dingOrgDeptOwner;//企业群群主',
    private String dingDeptManagerUseridList;//部门的主管列表,取值为由主管的userid组成的字符串，不同的userid使用|符号进行分割'
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
