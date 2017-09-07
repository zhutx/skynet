package com.moredian.fishnet.device.service.impl;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.manager.ActivityManager;
import com.moredian.fishnet.device.request.*;
import com.moredian.fishnet.device.response.*;
import com.moredian.fishnet.device.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wuyb on 2016/11/18.
 */
@SI
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityManager activeEquipmentManager;
  
	@Override
	public ServiceResponse<GenerateQrCodeResponse> generateQrCode(GenerateQrCodeRequest request) {
        return activeEquipmentManager.generateQrCode(request);
	}

	@Override
	public ServiceResponse<ScanQrCodeResponse> scanQrCode(ScanQrCodeRequest request) {
        return activeEquipmentManager.scanQrCode(request);
	}

	@Override
	public ServiceResponse<UpdateQrCodeStatusResponse> updateQrCodeStatus(UpdateQrCodeStatusRequest request) {
		 return activeEquipmentManager.updateQrCodeScan(request);
	}

	@Override
	public ServiceResponse<GetActivityStatusResponse> getActivityStatus(GetActivityStatusRequest request) {
		return activeEquipmentManager.getActivityStatus(request);
	}

	@Override
	public ServiceResponse<ActivateDeviceResponse> activateDevice(ActivateDeviceRequest request) {
		return activeEquipmentManager.activateDevice(request);
	}

	@Override
	public ServiceResponse<ActivateDeviceResponse> activateDeviceWithActivationCode(ActivationCodeRequest request) {
		return activeEquipmentManager.activateDeviceWithActivationCode(request);
	}

	@Override
	public ServiceResponse<List<BindDeviceResponse>> bindDevices(List<BindDeviceRequest> request) {
		return activeEquipmentManager.bindDevices(request);
	}

	@Override
	public ServiceResponse<PerfectInfoResponse> perfectDevice(PerfectInfoRequest request) {
		return activeEquipmentManager.perfectDevice(request);
	}

	@Override
	public ServiceResponse<ActivateDeviceResponse> getActivateInfo(ActivateInfoRequest request) {
		return activeEquipmentManager.getActivateInfo(request);
	}

	@Override
	public ServiceResponse<Boolean> deActivateDevice(String serialNumber) {
		return activeEquipmentManager.deActivateDevice(serialNumber);
	}
}