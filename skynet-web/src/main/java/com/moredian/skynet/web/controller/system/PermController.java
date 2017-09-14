package com.moredian.skynet.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.auth.model.PermInfo;
import com.moredian.skynet.auth.request.PermAddRequest;
import com.moredian.skynet.auth.request.PermQueryRequest;
import com.moredian.skynet.auth.request.PermUpdateRequest;
import com.moredian.skynet.auth.service.PermService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.system.req.ActivePermModel;
import com.moredian.skynet.web.controller.system.req.AddPermModel;
import com.moredian.skynet.web.controller.system.req.DeletePermModel;
import com.moredian.skynet.web.controller.system.req.DisablePermModel;
import com.moredian.skynet.web.controller.system.req.EditPermModel;
import com.moredian.skynet.web.controller.system.req.ListPermModel;
import com.moredian.skynet.web.controller.system.resp.PermData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Perm API", description = "权限接口")
@RequestMapping("/perm")
public class PermController extends BaseController {
	
	@SI
	private PermService permService;
	
	private PermAddRequest buildPermAddRequest(AddPermModel model) {
		PermAddRequest request = new PermAddRequest();
		request.setPermType(model.getPermType());
		request.setPermName(model.getPermName());
		request.setPermAction(model.getPermAction());
		request.setPermUrl(model.getPermUrl());
		request.setPermDesc(model.getPermDesc());
		request.setParentId(model.getParentId());
		request.setModuleType(model.getModuleType());
		return request;
	}
	
	@ApiOperation(value="添加权限", notes="添加权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse add(@RequestBody AddPermModel model) {
		
		permService.addPerm(this.buildPermAddRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private PermUpdateRequest buildPermUpdateRequest(EditPermModel model){
		PermUpdateRequest request = new PermUpdateRequest();
		request.setPermId(model.getPermId());
		request.setPermName(model.getPermName());
		request.setPermUrl(model.getPermUrl());
		request.setPermDesc(model.getPermDesc());
		return request;
	}
	
	@ApiOperation(value="编辑权限", notes="编辑权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse edit(@RequestBody EditPermModel model) {
		
		permService.updatePerm(this.buildPermUpdateRequest(model));
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="删除权限", notes="删除权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse delete(@RequestBody DeletePermModel model) {
		
		permService.deletePerm(model.getPermId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="禁用权限", notes="禁用权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/disable", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse disable(@RequestBody DisablePermModel model) {
		
		permService.disablePerm(model.getPermId());
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="启用权限", notes="启用权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/enable", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse enable(@RequestBody ActivePermModel model) {
		
		permService.activePerm(model.getPermId());
		
		return new BaseResponse();
    }
	
	private PermQueryRequest buildPermQueryRequest(ListPermModel model) {
		PermQueryRequest request = new PermQueryRequest();
		request.setModuleType(model.getModuleType());
		request.setParentId(model.getParentId());
		request.setPermName(model.getPermName());
		return request;
	}
	
	private PermData permInfoToPermData(PermInfo permInfo){
		return BeanUtils.copyProperties(PermData.class, permInfo);
	}
	
	private List<PermData> buildPermDataList(List<PermInfo> permList) {
		List<PermData> permDataList = new ArrayList<>();
		for(PermInfo permInfo : permList) {
			permDataList.add(this.permInfoToPermData(permInfo));
		}
		return permDataList;
	}
	
	@ApiOperation(value="查询权限", notes="查询权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse list(@RequestBody ListPermModel model) {
		
		BaseResponse<List<PermData>> bdr = new BaseResponse<>();
		
		List<PermInfo> permList = permService.findPerm(this.buildPermQueryRequest(model));
		
		bdr.setData(this.buildPermDataList(permList));
		
		return bdr;
    }
	

}