package com.moredian.skynet.web.controller.device.request;

public class CreateDeviceQRCodeModel {
	
	private String deviceSn;
	private String deivceMac;
	
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getDeivceMac() {
		return deivceMac;
	}
	public void setDeivceMac(String deivceMac) {
		this.deivceMac = deivceMac;
	}

}
