package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class StatusRequest implements Serializable {

    private String snArray;
    private Long orgId;

	public String getSnArray() {
		return snArray;
	}

	public void setSnArray(String snArray) {
		this.snArray = snArray;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
 
}
