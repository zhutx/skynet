package com.moredian.fishnet.web.controller.device;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.moredian.fishnet.device.enums.DeviceType;
import com.moredian.fishnet.device.model.DeviceInfo;
import com.moredian.fishnet.device.model.DeviceStateInfo;
import com.moredian.fishnet.device.model.GroupInfo;
import com.moredian.fishnet.device.request.DeviceQueryRequest;
import com.moredian.fishnet.device.request.DeviceUpdateRequest;
import com.moredian.fishnet.device.request.RebootRequest;
import com.moredian.fishnet.device.request.UpgradeRequest;
import com.moredian.fishnet.device.service.DeviceGroupRelationService;
import com.moredian.fishnet.device.service.DeviceService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.device.request.UpdateDeviceModel;
import com.moredian.fishnet.web.controller.device.response.DeviceData;
import com.moredian.fishnet.web.controller.device.response.PaginationDeviceData;
import com.moredian.fishnet.web.controller.model.HttpBaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@SuppressWarnings("deprecation")
@RestController
@Api(value="Device API", description = "设备接口")
@RequestMapping(value="/device") 
public class DeviceController extends BaseController {
	 
	@SI
	private DeviceService deviceService;
	@SI
	private DeviceGroupRelationService deviceGroupRelationService;
 	 	 
	@ApiOperation(value = "设备升级", notes = "设备升级")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备升级请求", required = true, dataType = "UpgradeRequest", paramType = "body") })
	@RequestMapping(value = "/upgrade", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> upgradeDevice(@RequestBody UpgradeRequest request) {
		return new HttpBaseResponse<DeviceStateInfo>(deviceService.upgradeDevice(request));
	}

	@ApiOperation(value = "设备重启", notes = "设备重启")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "request", value = "设备重启请求", required = true, dataType = "RebootRequest", paramType = "body") })
	@RequestMapping(value = "/reboot", method = RequestMethod.POST)
	@Deprecated
	public HttpBaseResponse<DeviceStateInfo> rebootDevice(@RequestBody RebootRequest request) {
		return new HttpBaseResponse<DeviceStateInfo>(deviceService.rebootDevice(request));
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改设备", notes="修改设备")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse update(@RequestBody UpdateDeviceModel model) {
		BaseResponse br = new BaseResponse();
		DeviceUpdateRequest request = new DeviceUpdateRequest();
		request.setOrgId(model.getOrgId());
		request.setDeviceId(model.getDeviceId());
		request.setDeviceName(model.getDeviceName());
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
	@ApiOperation(value="删除设备", notes="删除设备")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deviceId") Long deviceId) {
		
		deviceService.deleteDeviceById(orgId, deviceId).pickDataThrowException();
		
		return new BaseResponse();
    }
	
}
