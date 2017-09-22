package com.moredian.skynet.device.response;

import java.io.Serializable;
 
public class BindDeviceResponse implements Serializable{

	private static final long serialVersionUID = -5267360200445137012L;
	private String serialNumber;
    private Boolean result;
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
 
}
