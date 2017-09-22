package com.moredian.skynet.device.response;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class GetActivityStatusResponse implements Serializable{
	private static final long serialVersionUID = -2347208770132829643L;
	private String serialNumber;
    private int statusCode;
    private String message;
    private Long deviceId;
    private String deviceSn;
    
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
