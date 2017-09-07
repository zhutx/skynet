package com.moredian.fishnet.web.controller.org;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.request.ModuleBindRequest;
import com.moredian.fishnet.org.request.OrgAddRequest;
import com.moredian.fishnet.org.request.OrgUpdateRequest;
import com.moredian.fishnet.org.service.AreaService;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.org.request.AddOrgModel;
import com.moredian.fishnet.web.controller.org.request.BindModuleModel;
import com.moredian.fishnet.web.controller.org.request.UpdateOrgModel;
import com.moredian.fishnet.web.controller.org.response.OrgData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Org API", description = "机构接口")
@RequestMapping(value="/org") 
public class OrgController extends BaseController {
	
	@SI
	private OrgService orgService;
	@SI
	private AreaService areaService;
	
	private OrgAddRequest buildRequest(AddOrgModel model) {
		OrgAddRequest request = BeanUtils.copyProperties(OrgAddRequest.class, model);
		return request;
	}
	
	@ApiOperation(value="创建机构", notes="创建机构")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@RequestBody AddOrgModel model) {
		
		orgService.addOrg(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private OrgUpdateRequest buildRequest(UpdateOrgModel model) {
		return BeanUtils.copyProperties(OrgUpdateRequest.class, model);
	}
	
	@ApiOperation(value="修改机构信息", notes="修改机构信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse editOrg(@RequestBody UpdateOrgModel model) {
    	
		orgService.updateOrg(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private ModuleBindRequest buildRequest(BindModuleModel model) {
		return BeanUtils.copyProperties(ModuleBindRequest.class, model);
	}
	
	@ApiOperation(value="绑定系统模块", notes="绑定系统模块,初始化管理员账号")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/bindModule", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse bindModule(@RequestBody BindModuleModel model) {
		
		orgService.bindModule(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private OrgData orgInfoToOrgData(OrgInfo orgInfo) {
		
		OrgData orgData = BeanUtils.copyProperties(OrgData.class, orgInfo);
		
		if(orgInfo.getProvinceId() != null && orgInfo.getProvinceId() != 0) {
			orgData.setProvinceStr(areaService.getAreaByCode(orgInfo.getProvinceId()).getAreaName());
		}
		if(orgInfo.getCityId() != null && orgInfo.getCityId() != 0) {
			orgData.setCityStr(areaService.getAreaByCode(orgInfo.getCityId()).getAreaName());
		}
		if(orgInfo.getDistrictId() != null && orgInfo.getDistrictId() != 0) {
			orgData.setDistrictStr(areaService.getAreaByCode(orgInfo.getDistrictId()).getAreaName());
		}
		
		return orgData;
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取机构信息", notes="获取机构信息")
	@RequestMapping(value="/info", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse info(@RequestParam(value = "orgId") Long orgId) {
		BaseResponse<OrgData> br = new BaseResponse<>();
		
		OrgInfo orgInfo = orgService.getOrgInfo(orgId);
		br.setData(this.orgInfoToOrgData(orgInfo));
		
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="是否已开通业务", notes="是否已开通业务")
	@RequestMapping(value="/isBusiOpen", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse isBusiOpen(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "bizType") int bizType) {
		BaseResponse<Boolean> br = new BaseResponse<>();
		br.setData(orgService.isBizEnable(orgId, bizType));
		return br;
    }
	

}
