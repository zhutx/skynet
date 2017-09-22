package com.moredian.skynet.web.controller.device.response;

import java.util.List;

import com.moredian.skynet.model.PageData;

public class PaginationDeviceData extends PageData {
	
	private List<DeviceData> deviceList;

	public List<DeviceData> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceData> deviceList) {
		this.deviceList = deviceList;
	}

}
