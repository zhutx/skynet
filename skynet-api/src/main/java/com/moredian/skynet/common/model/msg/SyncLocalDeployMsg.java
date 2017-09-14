package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

/**
 * 本地布控实例同步消息
 * @author zhutx
 *
 */
public class SyncLocalDeployMsg implements Serializable {
	
	private static final long serialVersionUID = -7989739858818434692L;
	
	private Long orgId;
	private Integer deviceAction;
	private Long deviceId;
	private Integer deviceType;
	private String deviceSn;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getDeviceAction() {
		return deviceAction;
	}
	public void setDeviceAction(Integer deviceAction) {
		this.deviceAction = deviceAction;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

}
