package com.moredian.fishnet.member.service.adapter.response;

import java.io.Serializable;

public class UserForSynDingResponse implements Serializable {

	private static final long serialVersionUID = -7608539661224133345L;
	
	private Long orgId; //机构id,必填
    private Long userId;
    private String dingName;
    private String dingId;
    private Integer userStatus;
    private String dingUserId;//不允许为空
    private String dingJobNum;
    private String dingIsLeaderInDepts;
    private Boolean dingIsAdmin;
    private Boolean dingIsBoss;
    private String dingUnionId;

    public String getDingName() {
        return dingName;
    }

    public void setDingName(String dingName) {
        this.dingName = dingName;
    }

    public String getDingIsLeaderInDepts() {
        return dingIsLeaderInDepts;
    }

    public void setDingIsLeaderInDepts(String dingIsLeaderInDepts) {
        this.dingIsLeaderInDepts = dingIsLeaderInDepts;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getDingIsAdmin() {
        return dingIsAdmin;
    }

    public void setDingIsAdmin(Boolean dingIsAdmin) {
        this.dingIsAdmin = dingIsAdmin;
    }

    public Boolean getDingIsBoss() {
        return dingIsBoss;
    }

    public void setDingIsBoss(Boolean dingIsBoss) {
        this.dingIsBoss = dingIsBoss;
    }

    public String getDingUnionId() {
        return dingUnionId;
    }

    public void setDingUnionId(String dingUnionId) {
        this.dingUnionId = dingUnionId;
    }
}
