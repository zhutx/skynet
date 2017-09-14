package com.moredian.skynet.web.controller.device;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.request.*;
import com.moredian.skynet.device.response.*;
import com.moredian.skynet.device.service.ActivityService;
import com.moredian.skynet.device.service.InventoryDeviceService;
import com.moredian.skynet.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xxu on 2017/3/20.
 */
@RestController
@Api(value="Device Active API", description = "设备激活接口")
@RequestMapping("/service/device")
public class DeviceActiveController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DeviceActiveController.class);

    @Reference
    private ActivityService activityService;

    @SI
    private InventoryDeviceService inventoryDeviceService;

    /**
     *3/修改扫码状态
     * 上报机构，设备位置，是否自动布控
     *具体描述见ActiveEquipmentUpdateQrCodeInfoRequest
     *
     */
    @ApiOperation(value="更新二维码状态", notes="应用->平台:应用端扫描二维码更新二维码状态，以确保设备进入激活动作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "request", value = "更新二维码状态实体", required = true, dataType = "UpdateQrCodeStatusRequest",paramType = "body")
    })
    @RequestMapping(value="/qrcode/update", method=RequestMethod.POST)
    public BaseResponse<UpdateQrCodeStatusResponse> updateQrCodeStatus(@Validated @RequestBody UpdateQrCodeStatusRequest request){
       logger.info(String.format("/attence/device/qrcode/update post is called.ordId is %s and qrcode is %s" , request.getOrgId(),request.getQrCode()));
        return new BaseResponse<UpdateQrCodeStatusResponse>(activityService.updateQrCodeStatus(request));
    }

    /**
     *4/获取激活状态
     *具体描述见ActivityEquipmentQrCodeScanRequest
     *
     */
//    @ApiOperation(value="获取激活状态", notes="应用->平台:获取设备激活状态")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
//            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "request", value = "设备激活状态实体", required = true, dataType = "GetActivityStatusRequest",paramType = "body")
//    })
    @RequestMapping(value="/activitystatus", method=RequestMethod.POST)
    public BaseResponse<GetActivityStatusResponse> getActivityStatus(@Validated @RequestBody GetActivityStatusRequest request){
        logger.info(String.format("/attence/device/activitystatus post is called. qrcode is %s" ,request.getQrCode()));
        return new BaseResponse<GetActivityStatusResponse>(activityService.getActivityStatus(request));
    }


    /**
     *6/完善信息接口
     *具体描述见ActivityEquipmentByQrCodeRequest
     *
     */
    @ApiOperation(value="信息完善命令", notes="应用->平台:信息完善命令")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "request", value = "完善信息实体", required = true, dataType = "PerfectInfoRequest",paramType = "body")
    })
    @RequestMapping(value="/info", method=RequestMethod.POST)
    public BaseResponse<PerfectInfoResponse> perfectDevice(@Validated @RequestBody PerfectInfoRequest request){
        logger.info(String.format("/attence/device/info post is called.ordId is %s and deviceName is %s" , request.getOrgId(),request.getDeviceName()));
        return new BaseResponse<PerfectInfoResponse>(activityService.perfectDevice(request));
    }

//    public HttpBaseResponse<List<BindDeviceResponse>> bindDevices(@Validated @RequestBody List<BindDeviceRequest> request) {
//        return new HttpBaseResponse<List<BindDeviceResponse>>(activityService.bindDevices(request));
//    }

    /**
     *5/二维码激活接口
     *具体描述见ActivityEquipmentByQrCodeRequest
     *
     */
//    @ApiOperation(value="设备激活命令", notes="设备->平台:设备激活命令")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
//            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
//            @ApiImplicitParam(name = "request", value = "设备激活实体", required = true, dataType = "ActivateDeviceRequest",paramType = "body")
//    })
    @RequestMapping(value="/action", method=RequestMethod.POST)
    public BaseResponse<ActivateDeviceResponse> activateDevice(@Validated @RequestBody ActivateDeviceRequest request){
        return new BaseResponse<ActivateDeviceResponse>(activityService.activateDevice(request));
    }

    /**
     *1、生成激活码接口:获取设备激活码接口
     *     设备调用：根据返回的二维码串生成二维码
     *具体描述见GenActivityEquipmentQrCodeRequest
     *
     */
    @ApiOperation(value="生成二维码", notes="设备->平台:设备向平台请求二维码串生成命令")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "request", value = "二维码生成实体", required = true, dataType = "GenerateQrCodeRequest",paramType = "body")
    })
    @RequestMapping(value="/qrcode/generate", method=RequestMethod.POST)
    public BaseResponse<GenerateQrCodeResponse> generateQrCode(@Validated @RequestBody GenerateQrCodeRequest request){
        return new BaseResponse<GenerateQrCodeResponse>(activityService.generateQrCode(request));
    }

    /**
     *2/获取扫码状态
     *具体描述见ActivityEquipmentQrCodeScanRequest
     *
     */
    @ApiOperation(value="扫描二维码状态", notes="设备->平台:设备向平台定时请求二维码状态，以确定是否可激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auth-deviceId", value = "应用标识", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-appVersion", value = "应用版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-timestamp", value = "当前时间戳(1970年1月1号开始的秒数)", required = true, dataType = "Long",paramType = "header"),
            @ApiImplicitParam(name = "auth-signVersion", value = "当前签名规则版本", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-sign", value = "签名", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "auth-nonce", value = "随机字符串", required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name = "request", value = "扫描二维码实体", required = true, dataType = "ScanQrCodeRequest",paramType = "body")
    })
    @RequestMapping(value="/qrcode/scan", method=RequestMethod.POST)
    public BaseResponse<ScanQrCodeResponse> scanQrCode(@Validated @RequestBody ScanQrCodeRequest request){
        return new BaseResponse<ScanQrCodeResponse>(activityService.scanQrCode(request));
    }

    /**
     *20/添加白名单设备
     *
     */
    @RequestMapping(value="/whiteDevice/add", method=RequestMethod.POST)
    public BaseResponse<Boolean> addWhiteDevice(@Validated @RequestBody WhiteDeviceAddRequest request){
        return new BaseResponse<Boolean>(inventoryDeviceService.addWhiteDevice(request));
    }

    /**
     *20/用激活码把设备激活
     *
     */
    @RequestMapping(value="/whiteDevice/activated", method=RequestMethod.POST)
    public BaseResponse<ActivateDeviceResponse> activedDeviceWithCode(@Validated @RequestBody ActivationCodeRequest request){
        return new BaseResponse<ActivateDeviceResponse>(activityService.activateDeviceWithActivationCode((request)));
    }


    //this method used for dev,qa to check the status of devices
    //will move into hbk
    @RequestMapping(value="/whiteDevice/stats", method=RequestMethod.POST)
    public BaseResponse<Boolean> getWhiteDeviceStatus(@Validated @RequestBody WhiteDeviceAddRequest request){
        return new BaseResponse<Boolean>(inventoryDeviceService.addWhiteDevice(request));
    }


}
