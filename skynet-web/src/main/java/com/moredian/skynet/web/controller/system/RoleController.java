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
import com.moredian.skynet.auth.model.RoleInfo;
import com.moredian.skynet.auth.model.SimplePermInfo;
import com.moredian.skynet.auth.request.RoleAddRequest;
import com.moredian.skynet.auth.request.RoleQueryRequest;
import com.moredian.skynet.auth.request.RoleUpdateRequest;
import com.moredian.skynet.auth.request.SimplePermQueryRequest;
import com.moredian.skynet.auth.service.PermService;
import com.moredian.skynet.auth.service.RoleService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.system.req.CreateRoleModel;
import com.moredian.skynet.web.controller.system.req.UpdateRoleModel;
import com.moredian.skynet.web.controller.system.resp.RoleData;
import com.moredian.skynet.web.controller.system.resp.RoleDetailData;
import com.moredian.skynet.web.controller.system.resp.SimplePermData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Role API", description = "角色接口")
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@SI
	private RoleService roleService;
	@SI
	private PermService permService;
	
	private RoleAddRequest buildRoleAddRequest(CreateRoleModel model) {
		return BeanUtils.copyProperties(RoleAddRequest.class, model);
	}
	
	@ApiOperation(value="创建角色", notes="创建角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse create(@RequestBody CreateRoleModel model) {
		
		roleService.addRole(this.buildRoleAddRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private RoleUpdateRequest buildRoleUpdateRequest(UpdateRoleModel model) {
		return BeanUtils.copyProperties(RoleUpdateRequest.class, model);
	}
	
	@ApiOperation(value="修改角色", notes="修改角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse update(@RequestBody UpdateRoleModel model) {
		
		roleService.updateRole(this.buildRoleUpdateRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private RoleDetailData buildRoleDetailData(RoleInfo role) {
		RoleDetailData data = BeanUtils.copyProperties(RoleDetailData.class, role);
		List<Long> permIdList = permService.findPermByRole(role.getRoleId());
		data.setPermIds(permIdList);
		return data;
	}
	
	@ApiOperation(value="角色详情", notes="角色详情")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse detail(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "roleId") Long roleId) {
		
		BaseResponse<RoleDetailData> br = new BaseResponse<>();
		
		RoleInfo role = roleService.getRoleById(roleId, orgId);
		br.setData(this.buildRoleDetailData(role));
		
		return br;
    }
	
	@ApiOperation(value="删除角色", notes="删除角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse delete(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "roleId") Long roleId) {
		
		roleService.deleteRole(roleId, orgId).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private RoleData roleInfoToRoleData(RoleInfo roleInfo) {
		return BeanUtils.copyProperties(RoleData.class, roleInfo);
	}
	
	private List<RoleData> buildRoleDataList(List<RoleInfo> roleInfoList) {
		List<RoleData> list = new ArrayList<>();
		if(roleInfoList == null) return list;
		for(RoleInfo roleInfo : roleInfoList) {
			list.add(roleInfoToRoleData(roleInfo));
		}
		return list;
	}
	
	private RoleQueryRequest buildRoleQueryRequest(Long orgId, String roleName) {
		RoleQueryRequest request = new RoleQueryRequest();
		request.setOrgId(orgId);
		request.setRoleName(roleName);
		return request;
	}
	
	@ApiOperation(value="查询角色", notes="查询角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId")Long orgId, @RequestParam(value = "roleName", required = false) String roleName) {
		
		BaseResponse<List<RoleData>> bdr = new BaseResponse<>();
		
		List<RoleInfo> roleList = roleService.findRole(this.buildRoleQueryRequest(orgId, roleName));
		
		bdr.setData(this.buildRoleDataList(roleList));
		
		return bdr;
    }
	
	private SimplePermQueryRequest buildSimplePermQueryRequest(Integer moduleType, Long roleId, Long parentPermId) {
		SimplePermQueryRequest request = new SimplePermQueryRequest();
		request.setModuleType(moduleType);
		request.setParentPermId(parentPermId);
		request.setRoleId(roleId);
		return request;
	}
	
	private SimplePermData simplePermInfoToSimplePermData(SimplePermInfo simplePermInfo){
		if(simplePermInfo == null) return null;
		SimplePermData simplePermData = new SimplePermData();
		simplePermData.setPermId(simplePermInfo.getPermId());
		simplePermData.setPermName(simplePermInfo.getPermName());
		simplePermData.setHasChild(simplePermInfo.isHasChild());
		simplePermData.setChecked(simplePermInfo.isChecked());
		return simplePermData;
	}
	
	private List<SimplePermData> buildSimplePermDataList(List<SimplePermInfo> permInfoList) {
		List<SimplePermData> list = new ArrayList<>();
		if(permInfoList == null) return list;
		
		for(SimplePermInfo simplePermInfo : permInfoList) {
			list.add(this.simplePermInfoToSimplePermData(simplePermInfo));
		}
		return list;
	}
	
	@ApiOperation(value="查询角色权限", notes="查询角色权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/perms", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse perms(@RequestParam(value = "moduleType")Integer moduleType, @RequestParam(value = "roleId")Long roleId, @RequestParam(value = "parentPermId")Long parentPermId) {
		
		BaseResponse<List<SimplePermData>> bdr = new BaseResponse<>();
		
		List<SimplePermInfo> permInfoList = roleService.querySimplePerm(this.buildSimplePermQueryRequest(moduleType, roleId, parentPermId));
		List<SimplePermData> data = buildSimplePermDataList(permInfoList);
		bdr.setData(data);
		
		return bdr;
    }

}
