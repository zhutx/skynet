package com.moredian.skynet.device.request;

import java.io.Serializable;

public class GenerateQrCodeRequest implements Serializable {

	private static final long serialVersionUID = 5124696802825388123L;
	
    //sn码
    private String sn;
    //校验码：mac+privateKey做签名
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
