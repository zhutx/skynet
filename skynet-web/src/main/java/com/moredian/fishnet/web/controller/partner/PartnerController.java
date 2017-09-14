package com.moredian.fishnet.web.controller.partner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.request.ModuleAdminConfigRequest;
import com.moredian.fishnet.org.request.OrgAddRequest;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.partner.request.CreateAdminModel;
import com.moredian.fishnet.web.controller.partner.request.CreateOrgModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Partner API", description = "客户接口")
@RequestMapping(value="/partner") 
public class PartnerController extends BaseController {
	
	@SI
	private OrgService orgService;
	
	private OrgAddRequest buildRequest(CreateOrgModel model) {
		OrgAddRequest request = BeanUtils.copyProperties(OrgAddRequest.class, model);
		return request;
	}
	
	@ApiOperation(value="创建机构", notes="创建机构")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/create", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestBody CreateOrgModel model) {
		
		orgService.addOrg(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private ModuleAdminConfigRequest buildRequest(CreateAdminModel model) {
		return BeanUtils.copyProperties(ModuleAdminConfigRequest.class, model);
	}
	
	@ApiOperation(value="配置机构业务系统管理员", notes="配置机构业务系统管理员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/configOrgAdmin", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse configOrgAdmin(@RequestBody CreateAdminModel model) {
		
		orgService.configModuleAdmin(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }

}
