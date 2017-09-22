package com.moredian.skynet.device.response;

import java.io.Serializable;

public class ScanQrCodeResponse implements Serializable {

	private static final long serialVersionUID = -3991857472098906219L;
	private int statusCode;
    private String message;

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
}
