package com.moredian.fishnet.web.controller.index;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.model.OperInfo;
import com.moredian.fishnet.auth.model.PermNode;
import com.moredian.fishnet.auth.model.SimpleRoleInfo;
import com.moredian.fishnet.auth.request.SimpleRoleQueryRequest;
import com.moredian.fishnet.auth.service.OperService;
import com.moredian.fishnet.auth.service.PermService;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.common.response.MyOrgData;
import com.moredian.fishnet.web.controller.index.request.MyOrgModel;
import com.moredian.fishnet.web.controller.index.request.MyPermModel;
import com.moredian.fishnet.web.controller.index.request.OrgRoleModel;
import com.moredian.fishnet.web.controller.index.request.SearchAllPermModel;
import com.moredian.fishnet.web.controller.index.response.RcTreeNode;
import com.moredian.fishnet.web.controller.index.response.SimpleRoleData;
import com.xier.sesame.common.utils.BeanUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Index API", description = "首页接口")
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	@SI
	private PermService permService;
	@SI
	private OperService operService;
	@SI
	private OrgService orgService;
	
	private RcTreeNode permNodeToRcTreeNode(PermNode permNode) {
		if(permNode == null) return null;
		RcTreeNode rcTreeNode = new RcTreeNode();
		rcTreeNode.setKey(String.valueOf(permNode.getPermId()));
		rcTreeNode.setLabel(permNode.getPermName());
		rcTreeNode.setValue(String.valueOf(permNode.getPermId()));
		rcTreeNode.setPId(String.valueOf(permNode.getParentId()));
		return rcTreeNode;
	}
	
	private List<RcTreeNode> permNodeListToRcTreeNodeList(List<PermNode> permNodeList) {
		List<RcTreeNode> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(permNodeList)) return list;
		for(PermNode permNode : permNodeList) {
			
			RcTreeNode rcTreeNode = this.permNodeToRcTreeNode(permNode);
			List<RcTreeNode> children = new ArrayList<>();
			
			if(CollectionUtils.isNotEmpty(permNode.getChildren())) {
				for(PermNode childPermNode : permNode.getChildren()) {
					children.add(this.permNodeToRcTreeNode(childPermNode));
				}
			}
			
			rcTreeNode.setChildren(children);
			list.add(rcTreeNode);
			
		}
		return list;
	}
	
	@ApiOperation(value="查询机构所有权限", notes="查询机构所有权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/allPerms", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse perms(@RequestBody SearchAllPermModel model) {
		
		BaseResponse<List<RcTreeNode>> br = new BaseResponse<>();
		List<PermNode> permNodeList = permService.findPermNode(model.getModuleType());
		br.setData(permNodeListToRcTreeNodeList(permNodeList));
		
		return br;
	}
	
	private SimpleRoleQueryRequest buildSimpleRoleQueryRequest(OrgRoleModel model) {
		SimpleRoleQueryRequest request = new SimpleRoleQueryRequest();
		request.setOrgId(model.getOrgId());
		return request;
	}
	
	private RcTreeNode simpleRoleInfoToRcTreeNode(SimpleRoleInfo simpleRoleInfo) {
		RcTreeNode rcTreeNode = new RcTreeNode();
		rcTreeNode.setKey(String.valueOf(simpleRoleInfo.getRoleId()));
		rcTreeNode.setLabel(simpleRoleInfo.getRoleName());
		rcTreeNode.setValue(String.valueOf(simpleRoleInfo.getRoleId()));
		return rcTreeNode;
	}
	
	private List<RcTreeNode> buildRcTreeNodeList(List<SimpleRoleInfo> simpleRoleInfoList) {
		List<RcTreeNode> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(simpleRoleInfoList)) return list;
		
		for(SimpleRoleInfo simpleRoleInfo : simpleRoleInfoList) {
			list.add(simpleRoleInfoToRcTreeNode(simpleRoleInfo));
		}
		return list;
	}
	
	private List<SimpleRoleData> buildSimpleRoleDataList(List<SimpleRoleInfo> simpleRoleInfoList) {
		List<SimpleRoleData> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(simpleRoleInfoList)) return list;
		return BeanUtils.copyListProperties(SimpleRoleData.class, simpleRoleInfoList);
	}
	
	@ApiOperation(value="查询机构所有角色", notes="查询机构所有角色")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/allRoles", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse perms(@RequestBody OrgRoleModel model) {
		
		//BaseResponse<List<RcTreeNode>> bdr = new BaseResponse<>();
		BaseResponse<List<SimpleRoleData>> bdr = new BaseResponse<>();
		
		List<SimpleRoleInfo> simpleRoleInfoList = operService.querySimpleRole(this.buildSimpleRoleQueryRequest(model));
		//bdr.setData(this.buildRcTreeNodeList(simpleRoleInfoList));
		bdr.setData(this.buildSimpleRoleDataList(simpleRoleInfoList));
		
		return bdr;
    }
	
	private List<MyOrgData> operInfoListToMyOrgDataList(List<OperInfo> operInfoList) {
		List<MyOrgData> list = new ArrayList<>();
		for(OperInfo operInfo : operInfoList) {
			OrgInfo orgInfo = orgService.getOrgInfo(operInfo.getOrgId());
			MyOrgData item = new MyOrgData(orgInfo.getOrgId(), orgInfo.getOrgName(), operInfo.getOperId());
			list.add(item);
		}
		return list;
	}
	
	@ApiOperation(value="查询我的机构", notes="查询我的机构")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/myOrgs", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse orgs(@RequestBody MyOrgModel model, HttpServletRequest request) {
		
		BaseResponse<List<MyOrgData>> br = new BaseResponse<>();
		
		List<OperInfo> operInfoList = operService.findEnableOper(model.getAccountId(), model.getModuleType());
		
		br.setData(operInfoListToMyOrgDataList(operInfoList));
		
		return br;
	}
	
	@ApiOperation(value="查询我的权限", notes="查询我的权限")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/myPerms", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse perms(@RequestBody MyPermModel model) {
		
		BaseResponse<List<PermNode>> br = new BaseResponse<>();
		List<PermNode> permNodeList = permService.findPermNodeByOper(model.getOperId(), model.getModuleType());
		br.setData(permNodeList);
		
		return br;
	}
	
}