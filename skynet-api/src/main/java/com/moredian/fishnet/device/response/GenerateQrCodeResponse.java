package com.moredian.fishnet.device.response;

import java.io.Serializable;

public class GenerateQrCodeResponse implements Serializable {

	private static final long serialVersionUID = 4124696802825388123L;
	
	private Boolean generate;
	
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

	public Boolean getGenerate() {
		return generate;
	}

	public void setGenerate(Boolean generate) {
		this.generate = generate;
	}
 
}
