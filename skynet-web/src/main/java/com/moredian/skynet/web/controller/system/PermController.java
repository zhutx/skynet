package com.moredian.skynet.web.controller.system;

import java.util.ArrayList;
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
import com.moredian.skynet.auth.model.PermInfo;
import com.moredian.skynet.auth.request.PermAddRequest;
import com.moredian.skynet.auth.request.PermQueryRequest;
import com.moredian.skynet.auth.request.PermUpdateRequest;
import com.moredian.skynet.auth.service.PermService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.system.req.ActivePermModel;
import com.moredian.skynet.web.controller.system.req.CreatePermModel;
import com.moredian.skynet.web.controller.system.req.DisablePermModel;
import com.moredian.skynet.web.controller.system.req.UpdatePermModel;
import com.moredian.skynet.web.controller.system.resp.PermData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Perm API", description = "权限接口")
@RequestMapping("/perm")
public class PermController extends BaseController {
	
	@SI
	private PermService permService;
	
	private PermAddRequest buildPermAddRequest(CreatePermModel model) {
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
	
	@ApiOperation(value="创建权限", notes="创建权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse create(@RequestBody CreatePermModel model) {
		
		permService.addPerm(this.buildPermAddRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private PermUpdateRequest buildPermUpdateRequest(UpdatePermModel model){
		PermUpdateRequest request = new PermUpdateRequest();
		request.setPermId(model.getPermId());
		request.setPermName(model.getPermName());
		request.setPermUrl(model.getPermUrl());
		request.setPermDesc(model.getPermDesc());
		return request;
	}
	
	@ApiOperation(value="修改权限", notes="修改权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse update(@RequestBody UpdatePermModel model) {
		
		permService.updatePerm(this.buildPermUpdateRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="删除权限", notes="删除权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "permId")Long permId) {
		
		permService.deletePerm(permId).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="禁用权限", notes="禁用权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/disable", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse disable(@RequestBody DisablePermModel model) {
		
		permService.disablePerm(model.getPermId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="启用权限", notes="启用权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/enable", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse enable(@RequestBody ActivePermModel model) {
		
		permService.activePerm(model.getPermId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private PermQueryRequest buildPermQueryRequest(Integer moduleType, String permName, Long parentId) {
		PermQueryRequest request = new PermQueryRequest();
		request.setModuleType(moduleType);
		request.setParentId(parentId);
		request.setPermName(permName);
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
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "moduleType")Integer moduleType, @RequestParam(value = "permName", required = false)String permName, @RequestParam(value = "parentId")Long parentId) {
		
		BaseResponse<List<PermData>> bdr = new BaseResponse<>();
		
		List<PermInfo> permList = permService.findPerm(this.buildPermQueryRequest(moduleType, permName, parentId));
		
		bdr.setData(this.buildPermDataList(permList));
		
		return bdr;
    }
	

}