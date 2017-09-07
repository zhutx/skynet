package com.moredian.fishnet.member.model;

import java.io.Serializable;
import java.util.Map;

public class DingUserInfo implements Serializable {

	private static final long serialVersionUID = 6375632342776154440L;
	
	private Long orgId;
	private Long memberId;
	
	private Integer userStatus;
    private String dingUserId;//不允许为空
    private String dingJobNum;
    private Map<String, String> dingExtattrMap;
    private String dingId;
    private String dingOrderInDepts;
    private String dingOrgEmail;
    private String dingEmail;
    private String dingPosition;
    private String dingDepartment;
    private Boolean dingActive;
    private String dingName;
    private String dingAvatar;
    private Boolean dingIsAdmin;
    private Boolean dingIsHide;
    private Boolean dingIsBoss;
    private String dingIsLeaderInDepts;
    private String dingUnionId;
    
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getDingUserId() {
		return dingUserId;
	}
	public void setDingUserId(String dingUserId) {
		this.dingUserId = dingUserId;
	}
	public String getDingJobNum() {
		return dingJobNum;
	}
	public void setDingJobNum(String dingJobNum) {
		this.dingJobNum = dingJobNum;
	}
	public Map<String, String> getDingExtattrMap() {
		return dingExtattrMap;
	}
	public void setDingExtattrMap(Map<String, String> dingExtattrMap) {
		this.dingExtattrMap = dingExtattrMap;
	}
	public String getDingId() {
		return dingId;
	}
	public void setDingId(String dingId) {
		this.dingId = dingId;
	}
	public String getDingOrderInDepts() {
		return dingOrderInDepts;
	}
	public void setDingOrderInDepts(String dingOrderInDepts) {
		this.dingOrderInDepts = dingOrderInDepts;
	}
	public String getDingOrgEmail() {
		return dingOrgEmail;
	}
	public void setDingOrgEmail(String dingOrgEmail) {
		this.dingOrgEmail = dingOrgEmail;
	}
	public String getDingEmail() {
		return dingEmail;
	}
	public void setDingEmail(String dingEmail) {
		this.dingEmail = dingEmail;
	}
	public String getDingPosition() {
		return dingPosition;
	}
	public void setDingPosition(String dingPosition) {
		this.dingPosition = dingPosition;
	}
	public String getDingDepartment() {
		return dingDepartment;
	}
	public void setDingDepartment(String dingDepartment) {
		this.dingDepartment = dingDepartment;
	}
	public Boolean getDingActive() {
		return dingActive;
	}
	public void setDingActive(Boolean dingActive) {
		this.dingActive = dingActive;
	}
	public String getDingName() {
		return dingName;
	}
	public void setDingName(String dingName) {
		this.dingName = dingName;
	}
	public String getDingAvatar() {
		return dingAvatar;
	}
	public void setDingAvatar(String dingAvatar) {
		this.dingAvatar = dingAvatar;
	}
	public Boolean getDingIsAdmin() {
		return dingIsAdmin;
	}
	public void setDingIsAdmin(Boolean dingIsAdmin) {
		this.dingIsAdmin = dingIsAdmin;
	}
	public Boolean getDingIsHide() {
		return dingIsHide;
	}
	public void setDingIsHide(Boolean dingIsHide) {
		this.dingIsHide = dingIsHide;
	}
	public Boolean getDingIsBoss() {
		return dingIsBoss;
	}
	public void setDingIsBoss(Boolean dingIsBoss) {
		this.dingIsBoss = dingIsBoss;
	}
	public String getDingIsLeaderInDepts() {
		return dingIsLeaderInDepts;
	}
	public void setDingIsLeaderInDepts(String dingIsLeaderInDepts) {
		this.dingIsLeaderInDepts = dingIsLeaderInDepts;
	}
	public String getDingUnionId() {
		return dingUnionId;
	}
	public void setDingUnionId(String dingUnionId) {
		this.dingUnionId = dingUnionId;
	}
    
}
