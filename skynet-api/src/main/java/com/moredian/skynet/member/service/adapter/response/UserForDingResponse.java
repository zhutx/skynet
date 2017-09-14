package com.moredian.skynet.member.service.adapter.response;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/27.
 */
public class UserForDingResponse implements Serializable {

	private static final long serialVersionUID = 4745276623566835119L;
	
	private Long userId;
    private Boolean dingIsAdmin;
    private String dingUserId;
    private String dingJobNum;
    private String dingName;
    private String faceImgUrl; //识别头像
    private String dingDepartment;
    private int relation;
    private Long orgId;
    private String dingIsLeaderInDepts;

    public Boolean getDingIsAdmin() {
        return dingIsAdmin;
    }

    public void setDingIsAdmin(Boolean dingIsAdmin) {
        this.dingIsAdmin = dingIsAdmin;
    }

    public String getDingIsLeaderInDepts() {
        return dingIsLeaderInDepts;
    }

    public void setDingIsLeaderInDepts(String dingIsLeaderInDepts) {
        this.dingIsLeaderInDepts = dingIsLeaderInDepts;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getDingDepartment() {
        return dingDepartment;
    }

    public void setDingDepartment(String dingDepartment) {
        this.dingDepartment = dingDepartment;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDingJobNum() {
        return dingJobNum;
    }

    public void setDingJobNum(String dingJobNum) {
        this.dingJobNum = dingJobNum;
    }

    public String getDingName() {
        return dingName;
    }

    public void setDingName(String dingName) {
        this.dingName = dingName;
    }

    public String getFaceImgUrl() {
        return faceImgUrl;
    }

    public void setFaceImgUrl(String faceImgUrl) {
        this.faceImgUrl = faceImgUrl;
    }
}
