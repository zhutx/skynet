package com.moredian.fishnet.member.service.adapter.response;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/2/8.
 */
public class ProsceniumUserResponse implements Serializable {

    //主机构id
    private Long orgId;
    //用户id
    private String dingUserId;

    private Long userId;

    private String dingName;

    private String dingAvatar;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
}
