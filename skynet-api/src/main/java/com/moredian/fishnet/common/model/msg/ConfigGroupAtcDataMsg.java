package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

public class ConfigGroupAtcDataMsg implements Serializable {
	
	private static final long serialVersionUID = -5158173759897398050L;
	
	private Long groupId;
    private String groupName;
    private Long orgId;
    private Integer groupType;
    private String groupCode;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }
}
