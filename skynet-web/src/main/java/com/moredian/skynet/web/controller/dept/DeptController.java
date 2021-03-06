package com.moredian.skynet.web.controller.dept;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.model.DeptMemberInfo;
import com.moredian.skynet.member.service.MemberService;
import com.moredian.skynet.org.model.DeptInfo;
import com.moredian.skynet.org.request.UpdateDeptRequest;
import com.moredian.skynet.org.service.DeptService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.dept.request.CreateDeptModel;
import com.moredian.skynet.web.controller.dept.request.UpdateDeptModel;
import com.moredian.skynet.web.controller.dept.response.DeptData;
import com.moredian.skynet.web.controller.dept.response.DeptMemberData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Dept API", description = "部门接口")
@RequestMapping(value="/dept") 
public class DeptController extends BaseController {
	
	@SI
	private DeptService deptService;
	@SI
	private MemberService memberService;
	@Autowired
	private ImageFileManager imageFileManager;
	
	@ApiOperation(value="创建部门", notes="创建部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse create(@RequestBody CreateDeptModel model) {

		deptService.addDept(model.getOrgId(), model.getDeptName(), model.getParentId()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private UpdateDeptRequest buildRequest(UpdateDeptModel model) {
		return BeanUtils.copyProperties(UpdateDeptRequest.class, model);
	}
	
	@ApiOperation(value="修改部门", notes="修改部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse update(@RequestBody UpdateDeptModel model) {

		deptService.updateDept(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="删除部门", notes="删除部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deptId") Long deptId) {
		deptService.removeByDeptId(orgId,deptId).pickDataThrowException();
		return new BaseResponse();
	}
	
	private DeptData deptInfoToDept(DeptInfo deptInfo) {
		return BeanUtils.copyProperties(DeptData.class, deptInfo);
	}
	
	@ApiOperation(value="获取根部门", notes="获取根部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/root", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse root(@RequestParam(value = "orgId") Long orgId) {
		BaseResponse<DeptData> br = new BaseResponse<>();
		DeptInfo deptInfo = deptService.getRootDept(orgId);
		br.setData(deptInfoToDept(deptInfo));
		return br;
    }
	
	private List<DeptData> deptInfoListToDeptDataList(List<DeptInfo> deptInfoList) {
		List<DeptData> deptDataList = new ArrayList<>();
		if(CollectionUtils.isEmpty(deptInfoList)) return deptDataList;
		
		for(DeptInfo deptInfo : deptInfoList) {
			deptDataList.add(deptInfoToDept(deptInfo));
		}
		return deptDataList;
	}
	
	@ApiOperation(value="查询下级部门", notes="查询下级部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/children", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse depts(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "parentDeptId", required = false) Long parentDeptId) {
		BaseResponse<List<DeptData>> br = new BaseResponse<>();
		List<DeptInfo> deptInfoList = deptService.findDepts(orgId, parentDeptId);
		br.setData(deptInfoListToDeptDataList(deptInfoList));
		return br;
    }
	
	private DeptMemberData deptMemberInfoToDeptMemberData(DeptMemberInfo deptMemberInfo) {
		DeptMemberData data = BeanUtils.copyProperties(DeptMemberData.class, deptMemberInfo);
		
		data.setShowFaceUrl(imageFileManager.getImageUrl(deptMemberInfo.getShowFaceUrl()));
		data.setVerifyFaceUrl(imageFileManager.getImageUrl(deptMemberInfo.getVerifyFaceUrl()));
		
		return data;
	}
	
	private List<DeptMemberData> deptMemberInfoListToDeptMemberDataList(List<DeptMemberInfo> deptMemberInfoList) {
		List<DeptMemberData> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(deptMemberInfoList)) return list;
		for(DeptMemberInfo info : deptMemberInfoList) {
			list.add(deptMemberInfoToDeptMemberData(info));
		}
		return list;
	}


	@ApiOperation(value="查询部门人员", notes="查询部门人员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/members", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse deptMembers(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deptId") Long deptId) {
		BaseResponse<List<DeptMemberData>> br = new BaseResponse<>();
		List<DeptMemberInfo> deptMemberInfoList = memberService.findMemberInDept(orgId, deptId);
		br.setData(this.deptMemberInfoListToDeptMemberDataList(deptMemberInfoList));
		return br;
    }

}
