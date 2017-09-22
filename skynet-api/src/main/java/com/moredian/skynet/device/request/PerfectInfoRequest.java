package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class PerfectInfoRequest implements Serializable {

	private static final long serialVersionUID = -366211752984997227L;
	//设备条码
    private String serialNumber;
    // 机构Id
    private Long orgId ;
    // 位置信息
    private String position;
    // 设备名称
    private String deviceName;
    
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
