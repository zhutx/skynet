package com.moredian.skynet.web.controller.device;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.request.DeviceUpdateRequest;
import com.moredian.skynet.device.request.DeviceWhiteAddRequest;
import com.moredian.skynet.device.service.DeviceService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.device.request.ActiveDeviceModel;
import com.moredian.skynet.web.controller.device.request.ConfigGroupModel;
import com.moredian.skynet.web.controller.device.request.CreateDeviceQRCodeModel;
import com.moredian.skynet.web.controller.device.request.CreateDeviceWhiteModel;
import com.moredian.skynet.web.controller.device.request.UpdateDeviceModel;
import com.moredian.skynet.web.controller.device.response.ActiveDeviceData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Device API", description = "主板设备接口")
@RequestMapping(value="/device") 
public class DeviceController extends BaseController {
	 
	@SI
	private DeviceService deviceService;
	
    private DeviceWhiteAddRequest buildRequest(CreateDeviceWhiteModel model) {
    	return BeanUtils.copyProperties(DeviceWhiteAddRequest.class, model);
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="添加设备白名单", notes="添加设备白名单")
	@RequestMapping(value="/createWhite", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse createWhite(@RequestBody CreateDeviceWhiteModel model) {
		deviceService.addDeviceWhite(this.buildRequest(model)).pickDataThrowException();
		return new BaseResponse();
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="生成设备二维码", notes="生成设备二维码")
	@RequestMapping(value="/createQRCode", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse createQRCode(@RequestBody CreateDeviceQRCodeModel model) {
		BaseResponse<String> br = new BaseResponse<>();
		String qrCode = ""; // TODO
		br.setData(qrCode);
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="扫码请求激活", notes="扫码请求激活")
	@RequestMapping(value="/active", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse active(@RequestBody ActiveDeviceModel model) {
		BaseResponse<ActiveDeviceData> br = new BaseResponse<>();
		ActiveDeviceData data = null; // TODO
		br.setData(data);
		return br;
    }
	
	private DeviceUpdateRequest buildRequest(UpdateDeviceModel model) {
		return BeanUtils.copyProperties(DeviceUpdateRequest.class, model);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改设备", notes="修改设备")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public BaseResponse update(@RequestBody UpdateDeviceModel model) {
		deviceService.updateDevice(this.buildRequest(model)).pickDataThrowException();
		return new BaseResponse();
    }
	
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="配置群组", notes="配置群组")
	@RequestMapping(value="/groupConfig", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse groupConfig(@RequestBody ConfigGroupModel model) {
		deviceService.groupConfig(model.getOrgId(), model.getDeviceId(), model.getGroupIds()).pickDataThrowException();
		return new BaseResponse();
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="查询设备", notes="查询设备")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceTypes", required = false) List<Integer> deviceTypes, @RequestParam(value = "deviceSn", required = false) String deviceSn, @RequestParam(value = "deviceMac", required = false) String deviceMac, @RequestParam(value = "onlineFlag", required = false) Integer onlineFlag) {
		
		return null;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="删除设备", notes="删除设备")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
		deviceService.deleteDeviceById(orgId, deviceId).pickDataThrowException();
		return new BaseResponse();
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="重启设备", notes="重启设备")
	@RequestMapping(value="/reboot", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse reboot(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
		
		return null;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="升级设备", notes="升级设备")
	@RequestMapping(value="/upgrade", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse upgrade(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
		
		return null;
    }
	
	
	
}
