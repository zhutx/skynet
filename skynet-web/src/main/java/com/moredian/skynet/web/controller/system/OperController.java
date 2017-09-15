package com.moredian.skynet.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.auth.model.OperInfo;
import com.moredian.skynet.auth.model.SimpleRoleInfo;
import com.moredian.skynet.auth.request.OperAddRequest;
import com.moredian.skynet.auth.request.OperQueryRequest;
import com.moredian.skynet.auth.request.OperUpdateRequest;
import com.moredian.skynet.auth.service.OperService;
import com.moredian.skynet.auth.service.RoleService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.system.req.ActiveOperModel;
import com.moredian.skynet.web.controller.system.req.CreateOperModel;
import com.moredian.skynet.web.controller.system.req.DisableOperModel;
import com.moredian.skynet.web.controller.system.req.UpdateOperModel;
import com.moredian.skynet.web.controller.system.resp.OperData;
import com.moredian.skynet.web.controller.system.resp.OperDetailData;
import com.moredian.skynet.web.controller.system.resp.SimpleRoleData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Oper API", description = "账号接口")
@RequestMapping("/oper")
public class OperController extends BaseController {
	
	@SI
	private OperService operService;
	@SI
	private RoleService roleService;
	
	private OperAddRequest buildOperAddRequest(CreateOperModel model) {
		OperAddRequest request = new OperAddRequest();
		request.setModuleType(model.getModuleType());
		request.setAccountName(model.getAccountName());
		request.setPassword(model.getPassword());
		request.setOperName(model.getOperName());
		request.setOperDesc(model.getOperDesc());
		request.setOrgId(model.getOrgId());
		request.setEmail(model.getEmail());
		request.setMobile(model.getMobile());
		
		List<Long> roleIds = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getRoleIds())) {
			for(String str : model.getRoleIds().split(",")) {
				roleIds.add(Long.parseLong(str));
			}
		}
		request.setRoleIds(roleIds);
		return request;
	}
	
	@ApiOperation(value="添加账号", notes="添加账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse create(@RequestBody CreateOperModel model) {
		
		operService.addOper(this.buildOperAddRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private OperUpdateRequest buildOperUpdateRequest(UpdateOperModel model) {
		OperUpdateRequest request = new OperUpdateRequest();
		request.setModuleType(model.getModuleType());
		request.setOperId(model.getOperId());
		request.setOperName(model.getOperName());
		request.setEmail(model.getEmail());
		request.setMobile(model.getMobile());
		request.setOperDesc(model.getOperDesc());
		request.setOrgId(model.getOrgId());
		
		List<Long> roleIds = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getRoleIds())) {
			for(String str : model.getRoleIds().split(",")) {
				roleIds.add(Long.parseLong(str));
			}
		}
		request.setRoleIds(roleIds);
		return request;
	}
	
	@ApiOperation(value="修改账号", notes="修改账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse edit(@RequestBody UpdateOperModel model) {
		
		operService.updateOper(this.buildOperUpdateRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="禁用账号", notes="禁用账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/disable", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse disable(@RequestBody DisableOperModel model) {
		
		operService.disableOper(model.getOperId(), model.getOrgId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="启用账号", notes="启用账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/enable", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse enable(@RequestBody ActiveOperModel model) {
		
		operService.activeOper(model.getOperId(), model.getOrgId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="删除账号", notes="删除账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "operId")Long operId) {
		
		operService.deleteOper(operId, orgId).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private SimpleRoleData simpleRoleInfoToSimpleRoleData(SimpleRoleInfo simpleRoleInfo) {
		if(simpleRoleInfo == null) return null;
		return BeanUtils.copyProperties(SimpleRoleData.class, simpleRoleInfo);
	}
	
	private List<SimpleRoleData> simpleRoleInfoToSimpleRoleData(List<SimpleRoleInfo> roleInfoList) {
		List<SimpleRoleData> list = new ArrayList<>();
		if(roleInfoList.isEmpty()) return list;
		for(SimpleRoleInfo roleInfo : roleInfoList) {
			list.add(simpleRoleInfoToSimpleRoleData(roleInfo));
		}
		return list;
	}
	
	private OperData operInfoToOperData(OperInfo operInfo) {
		if(operInfo == null) return null;
		OperData operData = new OperData();
		operData.setOperId(operInfo.getOperId());
		operData.setOperName(operInfo.getOperName());
		operData.setAccountName(operInfo.getAccountName());
		operData.setMobile(operInfo.getMobile());
		operData.setStatus(operInfo.getStatus());
		operData.setGmtCreate(operInfo.getGmtCreate());
		operData.setRoles(this.simpleRoleInfoToSimpleRoleData(operInfo.getRoles()));
		return operData;
	}
	
	private List<OperData> buildOperDataList(List<OperInfo> operInfoList) {
		List<OperData> operDataList = new ArrayList<>();
		if(operInfoList == null) return operDataList;
		for(OperInfo operInfo : operInfoList) {
			operDataList.add(operInfoToOperData(operInfo));
		}
		return operDataList;
	}
	
	private OperQueryRequest buildOperQueryRequest(Long orgId, Integer moduleType, String accountName, String keywords) {
		OperQueryRequest request = new OperQueryRequest();
		request.setOrgId(orgId);
		request.setModuleType(moduleType);
		request.setAccountName(accountName);
		request.setKeywords(keywords);
		return request;
	}
	
	@ApiOperation(value="查询账号", notes="查询账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "moduleType")Integer moduleType, @RequestParam(value = "accountName")String accountName, @RequestParam(value = "keywords")String keywords) {
		
		BaseResponse<List<OperData>> bdr = new BaseResponse<>();
		
		List<OperInfo> operInfoList = operService.findOper(this.buildOperQueryRequest(orgId, moduleType, accountName, keywords));
		
		bdr.setData(this.buildOperDataList(operInfoList));
		
		return bdr;
    }
	
	private OperDetailData buildOperDetailData(OperInfo oper) {
		OperDetailData data = BeanUtils.copyProperties(OperDetailData.class, oper);
		data.setRoleIdList(roleService.findRoleIdByOper(oper.getOperId()));
		return data;
	}
	
	@ApiOperation(value="账号详情", notes="账号详情")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse detail(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "operId") Long operId) {
		
		BaseResponse<OperDetailData> br = new BaseResponse<>();
		
		OperInfo oper = operService.getOperById(orgId, operId);
		br.setData(this.buildOperDetailData(oper));
		
		return br;
    }
	
	
}
