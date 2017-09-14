package com.moredian.skynet.device.response;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class UpdateQrCodeStatusResponse implements Serializable{
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
