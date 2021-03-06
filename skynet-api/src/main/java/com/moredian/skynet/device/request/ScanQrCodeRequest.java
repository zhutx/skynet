package com.moredian.skynet.device.request;

import java.io.Serializable;

public class ScanQrCodeRequest implements Serializable {

	private static final long serialVersionUID = 4919507424822293441L;
	//生成的qrCode原串通过privateKey进行aes加密返回给设备，设备通过privateKey解密生成qrCode
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
