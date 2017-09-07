package com.moredian.fishnet.web.controller.group;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.enums.YesNoFlag;
import com.moredian.fishnet.member.model.MemberInfo;
import com.moredian.fishnet.member.request.GroupMemberQueryRequest;
import com.moredian.fishnet.member.service.GroupRangeService;
import com.moredian.fishnet.member.service.MemberService;
import com.moredian.fishnet.org.model.DeptInfo;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.DeptService;
import com.moredian.fishnet.org.service.GroupService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.group.request.EditNameModel;
import com.moredian.fishnet.web.controller.group.request.GroupMemberConfigModel;
import com.moredian.fishnet.web.controller.group.request.SearchGroupMemberModel;
import com.moredian.fishnet.web.controller.group.request.ToggleAllMemberUseFlagModel;
import com.moredian.fishnet.web.controller.group.response.GroupData;
import com.moredian.fishnet.web.controller.group.response.GroupMemberData;
import com.moredian.fishnet.web.controller.group.response.GroupScopeData;
import com.moredian.fishnet.web.controller.group.response.PaginationGroupMemberData;
import com.moredian.fishnet.web.controller.group.response.RelationDeptAndMemberData;
import com.moredian.fishnet.web.controller.group.response.RelationDeptAndMemberPCData;
import com.moredian.fishnet.web.controller.group.response.SimpleDeptData;
import com.moredian.fishnet.web.controller.group.response.SimpleMemberData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Group API", description = "群组接口")
@RequestMapping(value="/group") 
public class GroupController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	
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
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改群组名", notes="修改群组名")
	@RequestMapping(value="/editName", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse editName(@RequestBody EditNameModel model) {
		BaseResponse br = new BaseResponse();
		groupService.editGroup(model.getOrgId(), model.getGroupId(), model.getGroupName()).pickDataThrowException();
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="切换全员使用标识", notes="切换全员使用标识")
	@RequestMapping(value="/toggleAllMemberUseFlag", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse toggleAllMemberUseFlag(@RequestBody ToggleAllMemberUseFlagModel model) {
		BaseResponse br = new BaseResponse();
		if(model.getAllMemberFlag() == YesNoFlag.NO.getValue()) return br;
		groupService.updateAllMemberFlag(model.getOrgId(), model.getGroupId(), model.getAllMemberFlag()).pickDataThrowException();
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="修改群组范围配置", notes="修改群组范围配置")
	@RequestMapping(value="/range", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse configRange(@RequestBody GroupMemberConfigModel model) {
		
		logger.info("修改群组范围: "+ JsonUtils.toJson(model));
		BaseResponse br = new BaseResponse();
		
		List<Long> deptIdList = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getDeptIds())) {
			String[] deptIdArr = model.getDeptIds().split(",");
			for(String deptIdStr : deptIdArr) {
				deptIdList.add(Long.parseLong(deptIdStr));
			}
		}
		
		List<Long> memberIdList = new ArrayList<>();
		if(StringUtils.isNotBlank(model.getMemberIds())) {
			String[] memberIdArr = model.getMemberIds().split(",");
			for(String memberIdStr : memberIdArr) {
				memberIdList.add(Long.parseLong(memberIdStr));
			}
		}
		
		groupService.justUpdateAllMemberFlag(model.getOrgId(), model.getGroupId(), model.getAllMemberFlag()).pickDataThrowException();
		groupRangeService.resetGroupRange(model.getOrgId(), model.getGroupId(), deptIdList, memberIdList).pickDataThrowException();
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
	
	private List<String> getDeptNames(Long orgId, List<Long> deptIds) {
		return deptService.findDeptNamesByIds(orgId, deptIds);
	}
	
	private List<String> getMemberNames(Long orgId, List<Long> memberIds) {
		return memberService.findMemberNamesByIds(orgId, memberIds);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取群组范围描述", notes="获取群组范围描述")
	@RequestMapping(value="/rangeDesc", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse rangeDesc(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "groupId") Long groupId) {
		BaseResponse<GroupScopeData> br = new BaseResponse<>();
		
		GroupInfo groupInfo = groupService.getGroupInfo(orgId, groupId);
		
		List<Long> deptIds = groupRangeService.findDeptId(orgId, groupId);
		// 获取群组部门
		List<String> deptNames = getDeptNames(orgId, deptIds);
		
		// 获取群组人员
		List<String> memberNames = new ArrayList<>();
		if(deptNames.size() < 6) { // 部门不足6个时，需继续提取成员
			List<Long> memberIds = groupRangeService.findMemberId(orgId, groupId);
			memberNames = getMemberNames(orgId, memberIds);
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<deptNames.size();i++) {
			if(sb.length() > 0) {
				sb.append(","+deptNames.get(i));
			} else {
				sb.append(deptNames.get(i));
			}
			if(i == 5) break;
		}
		for(int i=0;i<memberNames.size();i++) {
			if(sb.length() > 0) {
				sb.append(","+memberNames.get(i));
			} else {
				sb.append(memberNames.get(i));
			}
			if(i == 5) break;
		}
		
		GroupScopeData data = new GroupScopeData();
		data.setScopeDesc(sb.toString());
		data.setMemberSize(groupInfo.getMemberSize());
		br.setData(data);
		
		return br;
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
	
	private List<SimpleDeptData> deptInfoListToSimpleDeptDataList(List<DeptInfo> deptList) {
		List<SimpleDeptData> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(deptList)) return list;
		
		for(DeptInfo dept : deptList) {
			SimpleDeptData item = BeanUtils.copyProperties(SimpleDeptData.class, dept);
			//item.setDeptId(dept.getFromId());
			list.add(item);
		}
		return list;
	}
	
	private List<SimpleMemberData> memberInfoListToSimpleMemberDataList(List<MemberInfo> memberList) {
		if(CollectionUtils.isEmpty(memberList)) return null;
		return BeanUtils.copyListProperties(SimpleMemberData.class, memberList);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取群组范围数据", notes="获取群组范围数据")
	@RequestMapping(value="/rangeForPC", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse getPCRange(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "groupId") Long groupId) {
		BaseResponse<RelationDeptAndMemberPCData> br = new BaseResponse<>();
		
		List<Long> deptIds = groupRangeService.findDeptId(orgId, groupId);// 获取群组部门
		List<DeptInfo> deptList = deptService.findDeptByIds(orgId, deptIds);
		
		List<Long> memberIds = groupRangeService.findMemberId(orgId, groupId);// 获取群组人员
		List<MemberInfo> memberList = memberService.findMemberByIds(orgId, memberIds);
		
		RelationDeptAndMemberPCData data = new RelationDeptAndMemberPCData();
		data.setDepts(deptInfoListToSimpleDeptDataList(deptList));
		data.setMembers(memberInfoListToSimpleMemberDataList(memberList));
		br.setData(data);
		return br;
    }
	
}
