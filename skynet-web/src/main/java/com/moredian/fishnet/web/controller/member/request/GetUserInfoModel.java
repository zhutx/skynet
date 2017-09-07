package com.moredian.fishnet.web.controller.member.request;

/**
 * Created by wuyb on 2017/4/8.
 */
public class GetUserInfoModel {
    //主机构id
    private Long orgId;
    //用户id
    private Long userId;

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
}
