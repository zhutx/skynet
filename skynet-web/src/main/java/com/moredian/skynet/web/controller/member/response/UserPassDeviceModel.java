package com.moredian.skynet.web.controller.member.response;

import java.util.List;

/**
 * Created by wuyb on 2017/4/8.
 */
public class UserPassDeviceModel {

    private List<String> groupName;
    private List<String> deviceAliasName;
    
	public List<String> getGroupName() {
		return groupName;
	}
	public void setGroupName(List<String> groupName) {
		this.groupName = groupName;
	}
	public List<String> getDeviceAliasName() {
		return deviceAliasName;
	}
	public void setDeviceAliasName(List<String> deviceAliasName) {
		this.deviceAliasName = deviceAliasName;
	}

}
