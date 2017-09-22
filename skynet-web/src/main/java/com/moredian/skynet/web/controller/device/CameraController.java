package com.moredian.skynet.web.controller.device;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.model.CameraInfo;
import com.moredian.skynet.device.request.CameraAddRequest;
import com.moredian.skynet.device.request.CameraQueryRequest;
import com.moredian.skynet.device.request.CameraUpdateRequest;
import com.moredian.skynet.device.service.CameraService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.device.request.BindBoxModel;
import com.moredian.skynet.web.controller.device.request.CreateCameraModel;
import com.moredian.skynet.web.controller.device.request.UnbindBoxModel;
import com.moredian.skynet.web.controller.device.request.UpdateCameraModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Camera API", description = "摄像头设备接口")
@RequestMapping("/camera")
public class CameraController extends BaseController {

    @SI
    private CameraService cameraService;


    private CameraAddRequest buildRequest(CreateCameraModel model) {
    	return BeanUtils.copyProperties(CameraAddRequest.class, model);
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value="创建摄像机", notes="创建摄像机")
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public BaseResponse create(@RequestBody CreateCameraModel model){
    	cameraService.addDevice(this.buildRequest(model)).pickDataThrowException();
        return new BaseResponse();
    }
    
    private CameraUpdateRequest buildRequest(UpdateCameraModel model) {
    	return BeanUtils.copyProperties(CameraUpdateRequest.class, model);
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value="修改摄像机", notes="修改摄像机")
    @RequestMapping(value="/update", method= RequestMethod.PUT)
    public BaseResponse update(@RequestBody UpdateCameraModel model){
    	cameraService.updateDevice(this.buildRequest(model)).pickDataThrowException();
        return new BaseResponse();
    }
    
    private CameraQueryRequest buildRequest(Long orgId, Integer providerType) {
    	CameraQueryRequest request = new CameraQueryRequest();
		request.setOrgId(orgId);
		request.setProviderType(providerType);
		return request;
    }
    
    private Pagination<CameraInfo> buildPagination(Integer pageNo, Integer pageSize) {
    	Pagination<CameraInfo> pagination = new Pagination<>();
    	pagination.setPageNo(pageNo);
    	pagination.setPageSize(pageSize);
    	return pagination;
    }

    @SuppressWarnings("rawtypes")
	@ApiOperation(value="查询摄像机", notes="查询摄像机")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "providerType", required = false) Integer providerType, @RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
    	cameraService.findPaginationDevice(this.buildRequest(orgId, providerType), this.buildPagination(pageNo, pageSize));
		return null;
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value="删除摄像机", notes="删除摄像机")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
    	cameraService.deleteDevice(orgId, deviceId).pickDataThrowException();
		return new BaseResponse();
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value="绑定盒子", notes="绑定盒子")
    @RequestMapping(value="/bindBox", method= RequestMethod.POST)
    public BaseResponse bindBox(@RequestBody BindBoxModel model){
    	
        return new BaseResponse();
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value="解绑盒子", notes="解绑盒子")
    @RequestMapping(value="/unbindBox", method= RequestMethod.POST)
    public BaseResponse unbindBox(@RequestBody UnbindBoxModel model){
    	
        return new BaseResponse();
    }

}