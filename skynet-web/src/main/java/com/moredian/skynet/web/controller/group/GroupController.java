package com.moredian.skynet.web.controller.group;

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
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.enums.YesNoFlag;
import com.moredian.skynet.member.model.MemberInfo;
import com.moredian.skynet.member.request.GroupMemberQueryRequest;
import com.moredian.skynet.member.service.GroupRangeService;
import com.moredian.skynet.member.service.MemberService;
import com.moredian.skynet.org.model.GroupInfo;
import com.moredian.skynet.org.service.DeptService;
import com.moredian.skynet.org.service.GroupService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.group.request.AllMemberFlagModel;
import com.moredian.skynet.web.controller.group.request.CreateGroupModel;
import com.moredian.skynet.web.controller.group.request.GroupRangeConfigModel;
import com.moredian.skynet.web.controller.group.request.SearchGroupMemberModel;
import com.moredian.skynet.web.controller.group.request.UpdateGroupNameModel;
import com.moredian.skynet.web.controller.group.response.GroupData;
import com.moredian.skynet.web.controller.group.response.GroupMemberData;
import com.moredian.skynet.web.controller.group.response.PaginationGroupMemberData;
import com.moredian.skynet.web.controller.group.response.RelationDeptAndMemberData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Group API", description = "群组接口")
@RequestMapping(value="/group") 
public class GroupController extends BaseController {
	
	@SI
	private GroupService groupService;
	@SI
	private MemberService memberService;
	@Autowired
	private ImageFileManager imageFileManager;
	@SI
	private DeptService deptService;
	@SI
	private GroupRangeService groupRangeService;
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="创建群组", notes="创建群组")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse create(@RequestBody CreateGroupModel model) {
		groupService.addSimpleGroup(model.getOrgId(), model.getGroupName(), model.getAllMemberFlag()).pickDataThrowException();
		return new BaseResponse();
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改群组名", notes="修改群组名")
	@RequestMapping(value="/updateName", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse updateName(@RequestBody UpdateGroupNameModel model) {
		BaseResponse br = new BaseResponse();
		groupService.updateGroupName(model.getOrgId(), model.getGroupId(), model.getGroupName()).pickDataThrowException();
		return br;
    }
	
	@ApiOperation(value="删除群组", notes="删除群组")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "groupId") Long groupId) {
		groupService.deleteGroup(orgId, groupId).pickDataThrowException();
		return new BaseResponse();
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取群组数", notes="获取群组数")
	@RequestMapping(value="/count", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse count(@RequestParam(value = "orgId") Long orgId) {
		BaseResponse<Integer> br = new BaseResponse<>();
		int count = groupService.countGroup(orgId);
		br.setData(count);
		return br;
    }
	
	private List<GroupData> groupInfoListToGroupDataList(List<GroupInfo> groupInfoList) {
		return BeanUtils.copyListProperties(GroupData.class, groupInfoList);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="查询群组", notes="查询群组")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId") Long orgId) {
		BaseResponse<List<GroupData>> br = new BaseResponse<>();
		List<GroupInfo> groupInfoList = groupService.findGroup(orgId);
		br.setData(this.groupInfoListToGroupDataList(groupInfoList));
		return br;
    }
	
	private GroupMemberQueryRequest buildRequest(SearchGroupMemberModel model) {
		return BeanUtils.copyProperties(GroupMemberQueryRequest.class, model);
	}
	
	private GroupMemberData memberInfoToGroupMemberData(MemberInfo memberInfo) {
		GroupMemberData data = BeanUtils.copyProperties(GroupMemberData.class, memberInfo);
		data.setShowFaceUrl(imageFileManager.getImageUrl(memberInfo.getShowFaceUrl()));
		return data;
	}
	
	private List<GroupMemberData> memberInfoListToGroupMemberDataList(List<MemberInfo> memberInfoList) {
		
		List<GroupMemberData> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(memberInfoList)) return list;
		
		for(MemberInfo memberInfo : memberInfoList) {
			list.add(memberInfoToGroupMemberData(memberInfo));
		}
		
		return list;
	}
	
	private Pagination<MemberInfo> buildPagination(SearchGroupMemberModel model) {
		Pagination<MemberInfo> pagination = new Pagination<>();
		pagination.setPageNo(model.getPageNo());
		pagination.setPageSize(model.getPageSize());
		return pagination;
	}
	
	private PaginationGroupMemberData paginationMemberInfoToPaginationGroupMember(Pagination<MemberInfo> pagination) {
		PaginationGroupMemberData data = new PaginationGroupMemberData();
		data.setPageNo(pagination.getPageNo());
		data.setTotalCount(pagination.getTotalCount());
		data.setMembers(this.memberInfoListToGroupMemberDataList(pagination.getData()));
		return data;
	}
	
	@ApiOperation(value="查询群组成员", notes="查询群组成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/members", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse groupMembers(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
		
		SearchGroupMemberModel model = new SearchGroupMemberModel();
		model.setGroupId(groupId);
		model.setKeywords(keywords);
		model.setOrgId(orgId);
		model.setPageNo(pageNo);
		model.setPageSize(pageSize);
		
		BaseResponse<PaginationGroupMemberData> br = new BaseResponse<>();
		
		Pagination<MemberInfo> paginationMemberInfo = memberService.findPaginationMemberByGroup(this.buildPagination(model), this.buildRequest(model));
		
		br.setData(paginationMemberInfoToPaginationGroupMember(paginationMemberInfo));
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="配置群组范围", notes="配置群组范围")
	@RequestMapping(value="/range", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse configRange(@RequestBody GroupRangeConfigModel model) {
		groupService.justUpdateAllMemberFlag(model.getOrgId(), model.getGroupId(), YesNoFlag.YES.getValue()).pickDataThrowException();
		groupRangeService.resetGroupRange(model.getOrgId(), model.getGroupId(), model.getDeptIds(), model.getMemberIds()).pickDataThrowException();
		return new BaseResponse();
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取群组范围数据", notes="获取群组范围数据")
	@RequestMapping(value="/range", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse getRange(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "groupId") Long groupId) {
		BaseResponse<RelationDeptAndMemberData> br = new BaseResponse<>();
		
		List<Long> deptIds = groupRangeService.findDeptId(orgId, groupId);// 获取群组部门
		List<Long> memberIds = groupRangeService.findMemberId(orgId, groupId);// 获取群组人员
		
		RelationDeptAndMemberData data = new RelationDeptAndMemberData();
		data.setDeptIds(deptIds);
		data.setMemberIds(memberIds);
		br.setData(data);
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="切换群组全员使用标识", notes="切换群组全员使用标识")
	@RequestMapping(value="/allMemberFlag", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse allMemberFlag(@RequestBody AllMemberFlagModel model) {
		groupService.updateAllMemberFlag(model.getOrgId(), model.getGroupId(), model.getAllMemberFlag()).pickDataThrowException();
		return new BaseResponse();
    }
	
}
