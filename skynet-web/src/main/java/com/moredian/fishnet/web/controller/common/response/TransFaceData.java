package com.moredian.fishnet.web.controller.common.response;

public class TransFaceData {
	
	private boolean facePic = false;
	private String failMsg;
	private String verifyFaceUrl;
	
	public boolean isFacePic() {
		return facePic;
	}
	public void setFacePic(boolean facePic) {
		this.facePic = facePic;
	}
	public String getFailMsg() {
		return failMsg;
	}
	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
	public String getVerifyFaceUrl() {
		return verifyFaceUrl;
	}
	public void setVerifyFaceUrl(String verifyFaceUrl) {
		this.verifyFaceUrl = verifyFaceUrl;
	}

}
