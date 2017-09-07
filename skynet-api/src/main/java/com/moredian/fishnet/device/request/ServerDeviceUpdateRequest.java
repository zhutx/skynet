package com.moredian.fishnet.device.request;

import java.io.Serializable;

public class ServerDeviceUpdateRequest implements Serializable {
	
	private static final long serialVersionUID = -6193748358046734880L;
	
	/** 设备id */
	private Long deviceId; //required
	/** 机构id */
	private Long orgId; //required
	/** 设备名称 */
	private String deviceName; //optional
	/** 内网ip */
	private String host; //optional
	/** 内网端口 */
	private Integer port; //optional
	/** 外网ip */
	private String outerHost; //optional
	/** 外网端口 */
	private Integer outerPort; //optional
	/** 第三方ip */
	private String tpHost = "127.0.0.1"; //optional
	/** 第三方端口 */
	private Integer tpPort; //optional
	/** 最大支持机构数 */
	private Integer rdMaxorgnum = Integer.MAX_VALUE; //optional
	/** 最大底库人员数 */
	private Integer rdMaxpersonnum = Integer.MAX_VALUE; //optional
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getOuterHost() {
		return outerHost;
	}
	public void setOuterHost(String outerHost) {
		this.outerHost = outerHost;
	}
	public Integer getOuterPort() {
		return outerPort;
	}
	public void setOuterPort(Integer outerPort) {
		this.outerPort = outerPort;
	}
	public String getTpHost() {
		return tpHost;
	}
	public void setTpHost(String tpHost) {
		this.tpHost = tpHost;
	}
	public Integer getTpPort() {
		return tpPort;
	}
	public void setTpPort(Integer tpPort) {
		this.tpPort = tpPort;
	}
	public Integer getRdMaxorgnum() {
		return rdMaxorgnum;
	}
	public void setRdMaxorgnum(Integer rdMaxorgnum) {
		this.rdMaxorgnum = rdMaxorgnum;
	}
	public Integer getRdMaxpersonnum() {
		return rdMaxpersonnum;
	}
	public void setRdMaxpersonnum(Integer rdMaxpersonnum) {
		this.rdMaxpersonnum = rdMaxpersonnum;
	}

}
