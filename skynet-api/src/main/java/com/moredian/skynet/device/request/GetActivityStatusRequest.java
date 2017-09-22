package com.moredian.skynet.device.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2016/12/7.
 */
public class GetActivityStatusRequest implements Serializable {

	private static final long serialVersionUID = -4167857541872318022L;
	private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
