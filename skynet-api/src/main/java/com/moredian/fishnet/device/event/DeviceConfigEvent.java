package com.moredian.fishnet.device.event;

import java.io.Serializable;

/** 
* 类说明 
* @author 作者 陈智帅  创建时间：2017年3月28日 上午10:18:08 
*/
public class DeviceConfigEvent implements Serializable {
	private static final long serialVersionUID = 6664696802825388123L;
	private Long orgId;
	private String serialNumber;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
