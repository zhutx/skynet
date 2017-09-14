package com.moredian.skynet.member.service.adapter.response;

import java.io.Serializable;

public class DepartmentMemberResponse implements Serializable {
	
	private static final long serialVersionUID = -7374430294841798905L;
	
	private String dingAvatar;
    private String dingName;
    private Long userId;

    private String faceImgUrl;

    public String getFaceImgUrl() {
        return faceImgUrl;
    }

    public void setFaceImgUrl(String faceImgUrl) {
        this.faceImgUrl = faceImgUrl;
    }

    public String getDingAvatar() {
        return dingAvatar;
    }

    public void setDingAvatar(String dingAvatar) {
        this.dingAvatar = dingAvatar;
    }

    public String getDingName() {
        return dingName;
    }

    public void setDingName(String dingName) {
        this.dingName = dingName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
