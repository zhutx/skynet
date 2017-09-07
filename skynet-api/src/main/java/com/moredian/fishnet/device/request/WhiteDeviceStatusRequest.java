package com.moredian.fishnet.device.request;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/6.
 */
public class WhiteDeviceStatusRequest implements Serializable {

    private static final long serialVersionUID = 1108096896971913735L;

    private String deviceSn;
    private String mac;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
