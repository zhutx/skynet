package com.moredian.skynet.device.manager;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.device.request.*;
import com.moredian.skynet.device.response.*;

import java.util.List;

public interface ActivityManager { 
    ServiceResponse<GenerateQrCodeResponse> generateQrCode(GenerateQrCodeRequest request);

    ServiceResponse<ScanQrCodeResponse> scanQrCode(ScanQrCodeRequest request);

    ServiceResponse<UpdateQrCodeStatusResponse> updateQrCodeScan(UpdateQrCodeStatusRequest request);

    ServiceResponse<GetActivityStatusResponse> getActivityStatus(GetActivityStatusRequest request);

    ServiceResponse<ActivateDeviceResponse> activateDevice(ActivateDeviceRequest request);
    
    ServiceResponse<List<BindDeviceResponse>> bindDevices(List<BindDeviceRequest> request) ;

    ServiceResponse<PerfectInfoResponse> perfectDevice(PerfectInfoRequest request);

    ServiceResponse<ActivateDeviceResponse> getActivateInfo(ActivateInfoRequest request);


    ServiceResponse<ActivateDeviceResponse> activateDeviceWithActivationCode(ActivationCodeRequest request);



    ServiceResponse<Boolean> deleteDeviceFromBaseDevice(String serialNumber);


    ServiceResponse<Boolean> deActivateDevice(String serialNumber);

}
