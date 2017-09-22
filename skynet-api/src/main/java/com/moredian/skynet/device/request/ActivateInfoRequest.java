package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/1.
 */
public class ActivateInfoRequest implements Serializable {

	private static final long serialVersionUID = 8060804874664700868L;

	private String sn;

    private String checkCode;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
