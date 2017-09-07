package com.moredian.fishnet.web.controller.dept;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.model.DeptMemberInfo;
import com.moredian.fishnet.member.service.MemberService;
import com.moredian.fishnet.org.model.DeptInfo;
import com.moredian.fishnet.org.request.UpdateDeptRequest;
import com.moredian.fishnet.org.service.DeptService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.dept.request.AddDeptModel;
import com.moredian.fishnet.web.controller.dept.response.DeptData;
import com.moredian.fishnet.web.controller.dept.response.DeptMemberData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
	
	private DeptData deptInfoToDept(DeptInfo deptInfo) {
		DeptData deptData = BeanUtils.copyProperties(DeptData.class, deptInfo);
		deptData.setCommonName(deptInfo.getDeptName());
		deptData.setCommonId(deptData.getDeptId()+""+deptData.getDataFlag());
		return deptData;
	}
	
	private List<DeptData> deptInfoListToDeptDataList(List<DeptInfo> deptInfoList) {
		List<DeptData> deptDataList = new ArrayList<>();
		if(CollectionUtils.isEmpty(deptInfoList)) return deptDataList;
		
		for(DeptInfo deptInfo : deptInfoList) {
			deptDataList.add(deptInfoToDept(deptInfo));
		}
		return deptDataList;
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
	
	@ApiOperation(value="查询部门", notes="查询部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/depts", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse depts(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "parentDeptId", required = false) Long parentDeptId) {
		
		if(parentDeptId == 1L) { // 临时适配
			DeptInfo deptInfo = deptService.getRootDept(orgId);
			parentDeptId = deptInfo.getDeptId();
		}
		
		BaseResponse<List<DeptData>> br = new BaseResponse<>();
		List<DeptInfo> deptInfoList = deptService.findDepts(orgId, parentDeptId);
		br.setData(deptInfoListToDeptDataList(deptInfoList));
		return br;
    }
	
	private DeptMemberData deptMemberInfoToDeptMemberData(DeptMemberInfo deptMemberInfo) {
		DeptMemberData data = BeanUtils.copyProperties(DeptMemberData.class, deptMemberInfo);
		
		if(StringUtils.isBlank(deptMemberInfo.getShowFaceUrl())) {
			data.setShowFaceUrl(deptMemberInfo.getDingAvatar());
		} else {
			data.setShowFaceUrl(imageFileManager.getImageUrl(deptMemberInfo.getShowFaceUrl()));
		}
		
		data.setVerifyFaceUrl(imageFileManager.getImageUrl(deptMemberInfo.getVerifyFaceUrl()));
		
		data.setCommonName(deptMemberInfo.getMemberName());
		
		data.setCommonId(data.getMemberId()+""+data.getDataFlag());
		
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

	@ApiOperation(value="修改部门", notes="修改部门信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/put", method=RequestMethod.PUT)
	@ResponseBody
	public BaseResponse changeDeptInfo(@RequestParam(value = "orgId",required=true) Long orgId,
									   @RequestParam(value = "deptId",required=true) Long deptId,
									   @RequestParam(value = "deptName",required=true) String deptName,
									   @RequestParam(value = "parentId",required=false) Long parentId,
									   @RequestParam(value = "tpExtend",required=false) String tpExtend
	) {

		UpdateDeptRequest request=new UpdateDeptRequest();
		request.setOrgId(orgId);
		request.setDeptId(deptId);
		request.setDeptName(deptName);
		request.setParentId(parentId);
		request.setTpExtend(tpExtend);

		BaseResponse<Boolean> br = new BaseResponse<>();
		ServiceResponse<Boolean> updateResult = deptService.updateDept(request);
		br.setData(updateResult.getData());
		return br;
	}


	@ApiOperation(value="删除部门", notes="删除部门信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteDept(@RequestParam(value = "orgId") Long orgId,
									   @RequestParam(value = "deptId") Long deptId
	) {

		BaseResponse  br = new BaseResponse();
		br.setData(deptService.removeByDeptId(orgId,deptId).pickDataThrowException());
		return br;
	}





	@ApiOperation(value="查询部门成员", notes="查询部门成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/members", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse deptMembers(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "deptId") Long deptId) {
		
		if(deptId == 1L) { // 临时适配
			DeptInfo deptInfo = deptService.getRootDept(orgId);
			deptId = deptInfo.getDeptId();
		}
		
		BaseResponse<List<DeptMemberData>> br = new BaseResponse<>();
		
		List<DeptMemberInfo> deptMemberInfoList = memberService.findMemberInDept(orgId, deptId);
		
		br.setData(this.deptMemberInfoListToDeptMemberDataList(deptMemberInfoList));
		return br;
    }
	
	@ApiOperation(value="添加部门", notes="添加部门")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse add(@RequestBody AddDeptModel model) {

		BizAssert.isTrue(model.getParentId()!=0);
		
		deptService.addDept(model.getOrgId(), model.getDeptName(), model.getParentId()).pickDataThrowException();
		
		return new BaseResponse();
    }

}
