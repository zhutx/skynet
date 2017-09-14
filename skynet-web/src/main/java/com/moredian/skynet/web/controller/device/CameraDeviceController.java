package com.moredian.skynet.web.controller.device;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.model.CameraDeviceInfo;
import com.moredian.skynet.device.request.*;
import com.moredian.skynet.device.service.CameraDeviceService;
import com.moredian.skynet.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xxu on 2017/6/21.
 */
@RestController
@Api(value="Camera Device API", description = "摄像头设备接口")
@RequestMapping("/service/device")
public class CameraDeviceController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CameraDeviceController.class);

    @SI
    private CameraDeviceService cameraDeviceService;


    /**
     *Add Camera
     */
    @ApiOperation(value="添加摄像头", notes="")
    @RequestMapping(value="/camera", method= RequestMethod.POST)
    public BaseResponse<Long> addCameraDevice(@Validated @RequestBody CameraDeviceAddRequest request){
        logger.info(String.format("/service/device/camera post is called.ordId is %s and cameraStream is %s" , request.getOrgId(),request.getCameraStream()));
        return new BaseResponse<Long>(cameraDeviceService.addDevice(request));
    }

    /**
     *Get Camera Lists
     */
    @ApiOperation(value="获取摄像头列表", notes="")
    @RequestMapping(value="/camera/list", method= RequestMethod.GET)
    public BaseResponse<CameraDeviceInfo> getCameraDeviceLists(@RequestParam(required = false, value = "providerType") Integer providerType,
                                                   @RequestParam(required = false, value = "status") Integer status,
                                                   @RequestParam(required = true, value = "orgId") Long orgId,
                                                   @RequestParam(required = true, value = "pageNo") Integer pageNo,
                                                   @RequestParam(required = true, value = "pageSize") Integer pageSize){
        logger.info(String.format("/service/device/camera/list get is called.ordId is %s and providerType is %s" , orgId,providerType));
        CameraDeviceQueryRequest request=new CameraDeviceQueryRequest();
        request.setOrgId(orgId);
        request.setProviderType(providerType);
        request.setStatus(status);
        Pagination<CameraDeviceInfo> pagination =new Pagination<CameraDeviceInfo>();
        pagination.setPageSize(pageSize);
        pagination.setPageNo(pageNo);

        BaseResponse baseResponse=new BaseResponse();
        Pagination<CameraDeviceInfo> result=cameraDeviceService.findPaginationDevice(request,pagination);
        if(result!=null) {

            baseResponse.setData(result);
            baseResponse.setResult("0");
            baseResponse.setMessage("Get camera list successfully");
        }else {
            baseResponse.setResult("1");
            baseResponse.setMessage("Failed to get the list of camera");
        }

        return baseResponse;
    }

    /**
     *Get Camera Lists
     */
    @ApiOperation(value="获取摄像头", notes="")
    @RequestMapping(value="/camera", method= RequestMethod.GET)
    public BaseResponse<CameraDeviceInfo> getCameraDeviceByOrgIdAndDeviceId(
            @RequestParam(required = true, value = "orgId") Long orgId,
            @RequestParam(required = true, value = "deviceId") Long deviceId){
        logger.info(String.format("/service/device/camera/list get is called.ordId is %s and deviceId is %s" , orgId,deviceId));

        BaseResponse baseResponse=new BaseResponse();
        CameraDeviceInfo result=cameraDeviceService.getDeviceById(orgId,deviceId);
        if(result!=null) {

            baseResponse.setData(result);
            baseResponse.setResult("0");
            baseResponse.setMessage("Get camera successfully");
        }else {
            baseResponse.setResult("1");
            baseResponse.setMessage("Failed to get the camera");
        }
        return baseResponse;
    }


    /**
     * 修改camera的配置
     * @param request
     * @return
     */
    @RequestMapping(
            value = {"/camera/setting"},
            method = RequestMethod.PUT
    )
    @ResponseBody
    public BaseResponse updateCameraSetting(@RequestBody CameraDeviceUpdateRequest request) {

        logger.info(String.format("/service/device/camera/list get is called.ordId is %s and providerType is %s" , request.getOrgId(),request.getProviderType()));

        return new BaseResponse<Boolean>(cameraDeviceService.updateDevice(request));
    }

    /**
     * 删除camera
     * @return
     */
    @RequestMapping(
            value = {"/camera"},
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public BaseResponse deleteCameraByOrgIdAndDeviceId(
            @RequestParam(required = true, value = "orgId") Long orgId,
            @RequestParam(required = true, value = "deviceId") Long deviceId) {

        logger.info(String.format("/service/device/camera delete is called.ordId is %d and deviceId is %d" , orgId,deviceId));

        return new BaseResponse<Boolean>(cameraDeviceService.deleteDeviceById(orgId,deviceId));
    }

    /**
     *Add Camera
     */
    @ApiOperation(value="把摄像头和魔点盒子绑定", notes="")
    @RequestMapping(value="/camera/binding", method= RequestMethod.PUT)
    public BaseResponse<Boolean> addCameraDevice(@Validated @RequestBody CameraDeviceBindingRequest request){
        logger.info(String.format("/service/device/camera post is called.ordId is %s and cameraId is %s" , request.getOrgId(),request.getCameraId()));
        return new BaseResponse<Boolean>(cameraDeviceService.bindCameraWithDevice(request.getOrgId(),request.getCameraId(),request.getDeviceId()));
    }




    /**
     *Add Camera
     */
    @ApiOperation(value="把摄像头和魔点盒子绑定或更新摄像头", notes="")
    @RequestMapping(value="/box/info", method= RequestMethod.PUT)
    public BaseResponse<Boolean> updateBoxDevice(@Validated @RequestBody BoxUpdateRequest request){
        logger.info(String.format("/service/device/box/info put is called.ordId is %s and cameraId is %s" , request.getOrgId(),request.getCameraId()));
        return new BaseResponse<Boolean>(cameraDeviceService.updateBoxCamera(request));
    }

    /**
     *Add Camera
     */
    @ApiOperation(value="把摄像头和魔点盒子解绑", notes="")
    @RequestMapping(value="/camera/binding", method= RequestMethod.DELETE)
    public BaseResponse<Boolean> removeCameraDevice(@Validated @RequestBody CameraDeviceBindingRequest request){
        logger.info(String.format("/service/device/camera post is called.ordId is %s and cameraId is %s" , request.getOrgId(),request.getCameraId()));
        return new BaseResponse<Boolean>(cameraDeviceService.unBindCameraWithDevice(request.getOrgId(),request.getCameraId(),request.getDeviceId()));
    }

    }
