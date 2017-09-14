package com.moredian.skynet.device.manager;

public interface CloudeyeHuberConfigProxy {
	
	void initHuberConfig(long orgId, boolean isUseCeRd);
	
	void mountHuberNode(long orgId, long deployId);
	
	void disMountHuberNode(long orgId, long deployId);
	
	void clearOHuberConfig(long orgId);
	
	void bindCameraToBox(long orgId, long deployId);
	
	void unbindCameraToBox(long orgId, long deployId);
	
	//boolean removeMatchDevicesFromServer(long orgId, long deployId);

}
