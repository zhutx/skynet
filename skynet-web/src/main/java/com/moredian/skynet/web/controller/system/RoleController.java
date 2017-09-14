package com.moredian.skynet.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.moredian.skynet.auth.model.RoleInfo;
import com.moredian.skynet.auth.model.SimplePermInfo;
import com.moredian.skynet.auth.request.RoleAddRequest;
import com.moredian.skynet.auth.request.RoleQueryRequest;
import com.moredian.skynet.auth.request.RoleUpdateRequest;
import com.moredian.skynet.auth.request.SimplePermQueryRequest;
import com.moredian.skynet.auth.service.PermService;
import com.moredian.skynet.auth.service.RoleService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.system.req.AddRoleModel;
import com.moredian.skynet.web.controller.system.req.DeleteRoleModel;
import com.moredian.skynet.web.controller.system.req.EditRoleModel;
import com.moredian.skynet.web.controller.system.req.ListPermForRoleModel;
import com.moredian.skynet.web.controller.system.req.ListRoleModel;
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
	
	private RoleAddRequest buildRoleAddRequest(AddRoleModel model) {
		RoleAddRequest request = new RoleAddRequest();
		request.setOrgId(model.getOrgId());
		request.setRoleName(model.getRoleName());
		request.setRoleDesc(model.getRoleDesc());
		List<Long> permIds = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getPermIds())) {
			for(String str : model.getPermIds().split(",")) {
				permIds.add(Long.parseLong(str));
			}
		}
		request.setPermIds(permIds);
		return request;
	}
	
	@ApiOperation(value="添加角色", notes="添加角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse add(@RequestBody AddRoleModel model) {
		
		roleService.addRole(this.buildRoleAddRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private RoleUpdateRequest buildRoleUpdateRequest(EditRoleModel model) {
		RoleUpdateRequest request = new RoleUpdateRequest();
		request.setRoleId(model.getRoleId());
		request.setOrgId(model.getOrgId());
		request.setRoleName(model.getRoleName());
		request.setRoleDesc(model.getRoleDesc());
		List<Long> permIds = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getPermIds())) {
			for(String str : model.getPermIds().split(",")) {
				permIds.add(Long.parseLong(str));
			}
		}
		request.setPermIds(permIds);
		return request;
	}
	
	@ApiOperation(value="编辑角色", notes="编辑角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse edit(@RequestBody EditRoleModel model) {
		
		roleService.updateRole(this.buildRoleUpdateRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private RoleDetailData buildRoleDetailData(RoleInfo role) {
		RoleDetailData data = BeanUtils.copyProperties(RoleDetailData.class, role);
		List<Long> permIdList = permService.findPermByRole(role.getRoleId());
		data.setPermIdList(permIdList);
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
    public BaseResponse delete(@RequestBody DeleteRoleModel model) {
		
		roleService.deleteRole(model.getRoleId(), model.getOrgId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private String listToString(List<Long> permIdList){ 
		if(CollectionUtils.isEmpty(permIdList)) return null;
		StringBuffer sb = new StringBuffer();
		for(Long permId : permIdList) {
			if(sb.length() == 0) {
				sb.append(String.valueOf(permId));
			} else {
				sb.append(","+String.valueOf(permId));
			}
		}
		return sb.toString();
	}
	
	private RoleData roleInfoToRoleData(RoleInfo roleInfo) {
		RoleData roleData = new RoleData();
		roleData.setRoleId(roleInfo.getRoleId());
		roleData.setOrgId(roleInfo.getOrgId());
		roleData.setRoleName(roleInfo.getRoleName());
		roleData.setRoleDesc(roleInfo.getRoleDesc());
		roleData.setGmtCreate(roleInfo.getGmtCreate());
		roleData.setPermIds(this.listToString(roleInfo.getPermIds()));
		return roleData;
	}
	
	private List<RoleData> buildRoleDataList(List<RoleInfo> roleInfoList) {
		List<RoleData> list = new ArrayList<>();
		if(roleInfoList == null) return list;
		for(RoleInfo roleInfo : roleInfoList) {
			list.add(roleInfoToRoleData(roleInfo));
		}
		return list;
	}
	
	private RoleQueryRequest buildRoleQueryRequest(ListRoleModel model) {
		return BeanUtils.copyProperties(RoleQueryRequest.class, model);
	}
	
	@ApiOperation(value="查询角色", notes="查询角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse list(@RequestBody ListRoleModel model) {
		
		BaseResponse<List<RoleData>> bdr = new BaseResponse<>();
		
		List<RoleInfo> roleList = roleService.findRole(this.buildRoleQueryRequest(model));
		
		bdr.setData(this.buildRoleDataList(roleList));
		
		return bdr;
    }
	
	private SimplePermQueryRequest buildSimplePermQueryRequest(ListPermForRoleModel model) {
		SimplePermQueryRequest request = new SimplePermQueryRequest();
		request.setModuleType(model.getModuleType());
		request.setParentPermId(model.getParentPermId());
		request.setRoleId(model.getRoleId());
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
	@RequestMapping(value="/perms", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse perms(@RequestBody ListPermForRoleModel model) {
		
		BaseResponse<List<SimplePermData>> bdr = new BaseResponse<>();
		
		List<SimplePermInfo> permInfoList = roleService.querySimplePerm(this.buildSimplePermQueryRequest(model));
		List<SimplePermData> data = buildSimplePermDataList(permInfoList);
		bdr.setData(data);
		
		return bdr;
    }

}
