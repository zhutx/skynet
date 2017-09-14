package com.moredian.skynet.member.service.adapter.response;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/2/15.
 */
public class DingDepartmentResponse implements Serializable {
    private Long departmentId;//部门id
    private Long orgId;//机构id
    private Long dingDepartmentId;//部门id
    private String dingDepartmentName;//部门名称

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

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
}
