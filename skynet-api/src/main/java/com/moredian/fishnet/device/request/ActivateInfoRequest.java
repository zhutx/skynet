package com.moredian.fishnet.device.request;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/1.
 */
public class ActivateInfoRequest implements Serializable {

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
