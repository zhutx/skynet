package com.moredian.fishnet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.device.request.*;
import com.moredian.fishnet.device.response.*;

import java.util.List;

public interface ActivityService {

    /**
     *1、生成激活码接口:获取设备激活码接口
     *     设备调用：根据返回的二维码串生成二维码
     *具体描述见GenActivityEquipmentQrCodeRequest
     *
     */
    ServiceResponse<GenerateQrCodeResponse> generateQrCode(GenerateQrCodeRequest request);

    /**
     *2/获取扫码状态
     *具体描述见ActivityEquipmentQrCodeScanRequest
     *
     */
    ServiceResponse<ScanQrCodeResponse> scanQrCode(ScanQrCodeRequest request);

    /**
     *3/修改扫码状态
     * 上报机构，设备位置，是否自动布控
     *具体描述见ActiveEquipmentUpdateQrCodeInfoRequest
     *
     */
    ServiceResponse<UpdateQrCodeStatusResponse> updateQrCodeStatus(UpdateQrCodeStatusRequest request);
  
    /**
     *4/获取激活状态
     *具体描述见ActivityEquipmentQrCodeScanRequest
     *
     */
    ServiceResponse<GetActivityStatusResponse> getActivityStatus(GetActivityStatusRequest request);

    
    /**
     *5/二维码激活接口
     *具体描述见ActivityEquipmentByQrCodeRequest
     *
     */
    ServiceResponse<ActivateDeviceResponse> activateDevice(ActivateDeviceRequest request);

    /**
     *8/二维码激活接口
     *具体描述见ActivityEquipmentByQrCodeRequest
     *
     */
    ServiceResponse<ActivateDeviceResponse> activateDeviceWithActivationCode(ActivationCodeRequest request);

    

    /**
     *6/完善信息接口
     *具体描述见PerfectInfoRequest
     *
     */
    ServiceResponse<PerfectInfoResponse> perfectDevice(PerfectInfoRequest request);


    ServiceResponse<List<BindDeviceResponse>> bindDevices(List<BindDeviceRequest> request) ;


    /**
     *7/设备获取激活状态
     *如果已激活，返回秘钥
     */
    ServiceResponse<ActivateDeviceResponse> getActivateInfo(ActivateInfoRequest request);


    /**
     * 清除设备激活状态
     * @param serialNumber
     * @return
     */
    ServiceResponse<Boolean> deActivateDevice(String serialNumber);

}