package com.moredian.fishnet.web.controller.device;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.enums.DeviceType;
import com.moredian.fishnet.device.model.DeviceInfo;
import com.moredian.fishnet.device.model.DeviceStateInfo;
import com.moredian.fishnet.device.model.GroupInfo;
import com.moredian.fishnet.device.request.*;
import com.moredian.fishnet.device.service.DeviceGroupRelationService;
import com.moredian.fishnet.device.service.DeviceService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.device.request.UpdateDeviceNameModel;
import com.moredian.fishnet.web.controller.device.request.UpdateDevicePositionModel;
import com.moredian.fishnet.web.controller.device.response.DeviceData;
import com.moredian.fishnet.web.controller.device.response.DeviceTypeData;
import com.moredian.fishnet.web.controller.device.response.PaginationDeviceData;
import com.moredian.fishnet.web.controller.device.response.SimpleDeviceData;
import com.moredian.fishnet.web.controller.model.HttpBaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@RestController
@Api(value="Device API", description = "设备接口")
@RequestMapping(value="/device") 
public class DeviceController extends BaseController {
	 
	@SI
	private DeviceService deviceService;
	@SI
	private DeviceGroupRelationService deviceGroupRelationService;
 	 	 
	/**
	 * 设备状态情况
	 * 
	 * @param serialNumber：设备条码，对老设备用设备uniqueNumber
	 * @return 设备在线情况
	 */
	@ApiOperation(value = "设备在线查询", notes = "根据设备条码查询设备状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serialNumber", value = "设备条码", required = true, dataType = "String", paramType = "body") })
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> getStatus(@RequestBody String serialNumber) {
		DeviceStateInfo   stateInfo =  deviceService.getStatus(serialNumber);
		return new HttpBaseResponse<DeviceStateInfo>(stateInfo);
	}

	/**
	 * 多设备状态情况
	 *
	 *
	 * @param request：设备条码列表，对老设备用设备uniqueNumber列表
	 * @return 设备在线情况Map
	 */
	@ApiOperation(value = "批量设备在线查询", notes = "根据设备条码列表查询设备状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备条码列表", required = true, dataType = "StatusRequest", paramType = "body") })
	@RequestMapping(value = "/list/status", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<List<DeviceStateInfo>> getStatusList(@RequestBody StatusRequest request) {
		return new HttpBaseResponse<List<DeviceStateInfo>>(deviceService.getStatusList(request));
	}

	/**
	 * 升级设备
	 * 
	 * @param request
	 * @return 返回设备状态
	 */
	@ApiOperation(value = "设备升级", notes = "对设备进行升级")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备升级请求", required = true, dataType = "UpgradeRequest", paramType = "body") })
	@RequestMapping(value = "/upgrade", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> upgradeDevice(@RequestBody UpgradeRequest request) {
		return new HttpBaseResponse<DeviceStateInfo>(deviceService.upgradeDevice(request));
	}

	/**
	 * 重启设备
	 * 
	 * @param request
	 * @return 返回设备状态
	 */
	@ApiOperation(value = "设备重启", notes = "对设备进行重启")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备重启请求", required = true, dataType = "RebootRequest", paramType = "body") })
	@RequestMapping(value = "/reboot", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> rebootDevice(@RequestBody RebootRequest request) {
		return new HttpBaseResponse<DeviceStateInfo>(deviceService.rebootDevice(request));
	}

	/**
	 * 透传命令
	 * 
	 * @param request
	 * @return 返回设备状态
	 */
	@ApiOperation(value = "设备透传命令", notes = "对设备进行透传请求")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备透传请求", required = true, dataType = "TransferRequest", paramType = "body") })
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> transferDevice(@RequestBody TransferRequest request) {
		return new HttpBaseResponse<DeviceStateInfo>(deviceService.transferDevice(request));
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取门禁系列设备类型", notes="获取门禁系列设备类型")
	@RequestMapping(value="/doorDeviceTypes", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse doorDeviceTypes(@RequestParam(value = "orgId") Long orgId) {
		BaseResponse<List<DeviceTypeData>> br = new BaseResponse<>();
		
		List<DeviceTypeData> deviceTypes = new ArrayList<>();
		
		List<Integer> doorDeviceList = DeviceType.getDoorDevices();
		for(int deviceType : doorDeviceList) {
			DeviceTypeData deviceTypeData = new DeviceTypeData();
			deviceTypeData.setDeviceType(deviceType);
			deviceTypeData.setDeviceTypeName(DeviceType.getName(deviceType));
			deviceTypes.add(deviceTypeData);
		}
		
		br.setData(deviceTypes);
		
		return br;
    }
	
	private List<SimpleDeviceData> buildSimpleDeviceDataList(List<DeviceInfo> deviceInfoList) {
		List<SimpleDeviceData> simpleDeviceDataList = new ArrayList<>();
		if(CollectionUtils.isEmpty(deviceInfoList)) return simpleDeviceDataList;
		return BeanUtils.copyListProperties(SimpleDeviceData.class, deviceInfoList);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取某类设备", notes="获取某类设备")
	@RequestMapping(value="/someTypeDevices", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse someTypeDevices(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceTypes") String deviceTypes) {
		BaseResponse<List<SimpleDeviceData>> br = new BaseResponse<>();
		
		String[] deviceTypeArr = deviceTypes.split(",");
		List<Integer> deviceTypeList = new ArrayList<>();
		for(String str : deviceTypeArr) {
			deviceTypeList.add(Integer.parseInt(str));
		}
		List<DeviceInfo> deviceInfoList = deviceService.findDeviceByTypes(orgId, deviceTypeList);
		br.setData(this.buildSimpleDeviceDataList(deviceInfoList));
		
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改设备名", notes="修改设备名")
	@RequestMapping(value="/deviceName", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse updateDeviceName(@RequestBody UpdateDeviceNameModel model) {
		BaseResponse br = new BaseResponse();
		DeviceUpdateRequest request = new DeviceUpdateRequest();
		request.setOrgId(model.getOrgId());
		request.setDeviceId(model.getDeviceId());
		request.setDeviceName(model.getDeviceName());
		deviceService.updateDevice(request).pickDataThrowException();
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改设备位置", notes="修改设备位置")
	@RequestMapping(value="/position", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse updatePosition(@RequestBody UpdateDevicePositionModel model) {
		BaseResponse br = new BaseResponse();
		DeviceUpdateRequest request = new DeviceUpdateRequest();
		request.setOrgId(model.getOrgId());
		request.setDeviceId(model.getDeviceId());
		request.setPosition(model.getPosition());
		deviceService.updateDevice(request).pickDataThrowException();
		return br;
    }
	
	private Pagination<DeviceInfo> buildPagination(int pageNo, int pageSize) {
		Pagination<DeviceInfo> pagination = new Pagination<>();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		return pagination;
	}
	
	private DeviceQueryRequest buildRequest(Long orgId, Integer deviceType, String deviceSn, Integer status) {
		DeviceQueryRequest request = new DeviceQueryRequest();
		request.setOrgId(orgId);
		//Board means attence and D1 attence
		if(deviceType==DeviceType.BOARD_ATTENDANCE.getValue()){
			List<Integer> deviceTypeList=new ArrayList<>();
			deviceTypeList.add(deviceType);
			deviceTypeList.add(DeviceType.BOARD_ATTENDANCE_DUALEYE.getValue());
			request.setDeviceTypeList(deviceTypeList);
		}else
		{
			request.setDeviceType(deviceType);
		}

		request.setDeviceSn(deviceSn);
		request.setStatus(status);
		return request;
	}
	
	private DeviceData deviceInfoToDeviceData(DeviceInfo deviceInfo, boolean mergeGroupName) {
		if(deviceInfo == null) return null;
		DeviceData deviceData = BeanUtils.copyProperties(DeviceData.class, deviceInfo);
		if(mergeGroupName) {
			
			List<String> groupNames = new ArrayList<>();
			List<GroupInfo> groupInfoList = deviceGroupRelationService.findGroupByDeviceId(deviceInfo.getOrgId(), deviceData.getDeviceId());
			for(GroupInfo groupInfo : groupInfoList) {
				groupNames.add(groupInfo.getGroupName());
			}
			deviceData.setGroupNames(groupNames);
		}
		return deviceData;
	}
	
	private List<DeviceData> deviceInfoListToDeviceDataList(List<DeviceInfo> deviceInfoList, boolean mergeGroupName) {
		
		List<DeviceData> deviceDataList = new ArrayList<>();
		if(CollectionUtils.isEmpty(deviceInfoList)) return deviceDataList;
		
		for(DeviceInfo deviceInfo : deviceInfoList) {
			deviceDataList.add(deviceInfoToDeviceData(deviceInfo, mergeGroupName));
		}
		
		return deviceDataList;
	}
	
	private PaginationDeviceData paginationDeviceInfoToPaginationDevice(Pagination<DeviceInfo> paginationDeviceInfo, boolean mergeGroupName) {
		PaginationDeviceData paginationDevice = new PaginationDeviceData();
		paginationDevice.setPageNo(paginationDeviceInfo.getPageNo());
		paginationDevice.setTotalCount(paginationDeviceInfo.getTotalCount());
		paginationDevice.setDeviceList(deviceInfoListToDeviceDataList(paginationDeviceInfo.getData(), mergeGroupName));
		return paginationDevice;
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="查询设备", notes="查询设备")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId") Long orgId, @RequestParam(required =false,value = "deviceType") Integer deviceType, @RequestParam(value = "deviceSn", required = false) String deviceSn, @RequestParam(value="status", required = false) Integer status, @RequestParam(value="pageNo") Integer pageNo, @RequestParam(value="pageSize") Integer pageSize) {
		
		BaseResponse<PaginationDeviceData> br = new BaseResponse<>();
		
		Pagination<DeviceInfo> paginationDeviceInfo = deviceService.findPaginationDevice(this.buildPagination(pageNo, pageSize), this.buildRequest(orgId, deviceType, deviceSn, status));
		
		br.setData(paginationDeviceInfoToPaginationDevice(paginationDeviceInfo, false));
		
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="查询设备（含群组信息）", notes="查询设备（含群组信息）")
	@RequestMapping(value="/listWithGroup", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse listWithGroup(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceType") Integer deviceType, @RequestParam(value = "deviceSn", required = false) String deviceSn, @RequestParam(value="status", required = false) Integer status, @RequestParam(value="pageNo") Integer pageNo, @RequestParam(value="pageSize") Integer pageSize) {
		
		BaseResponse<PaginationDeviceData> br = new BaseResponse<>();
		
		Pagination<DeviceInfo> paginationDeviceInfo = deviceService.findPaginationDevice(this.buildPagination(pageNo, pageSize), this.buildRequest(orgId, deviceType, deviceSn, status));
		
		br.setData(paginationDeviceInfoToPaginationDevice(paginationDeviceInfo, true));
		
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="删除设备", notes="删除设备")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
		
		deviceService.deleteDeviceById(orgId, deviceId).pickDataThrowException();
		
		return new BaseResponse();
    }
	
}
