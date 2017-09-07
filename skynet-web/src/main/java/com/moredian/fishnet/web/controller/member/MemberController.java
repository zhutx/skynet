package com.moredian.fishnet.web.controller.member;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.NetworkImage;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.utils.Picture;
import com.moredian.bee.common.utils.RandomUtil;
import com.moredian.bee.common.utils.Validator;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.filemanager.enums.FilePathType;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.model.DeviceInfo;
import com.moredian.fishnet.device.service.DeviceGroupRelationService;
import com.moredian.fishnet.device.service.DeviceService;
import com.moredian.fishnet.member.enums.MemberErrorCode;
import com.moredian.fishnet.member.enums.Sex;
import com.moredian.fishnet.member.model.MemberInfo;
import com.moredian.fishnet.member.request.ImportMemberModel;
import com.moredian.fishnet.member.request.MemberAddRequest;
import com.moredian.fishnet.member.request.MemberQueryRequest;
import com.moredian.fishnet.member.request.MemberUpdateRequest;
import com.moredian.fishnet.member.service.DeptMemberRelationService;
import com.moredian.fishnet.member.service.GroupMemberRelationService;
import com.moredian.fishnet.member.service.MemberService;
import com.moredian.fishnet.org.model.DeptInfo;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.DeptService;
import com.moredian.fishnet.org.service.GroupService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.dept.response.DeptData;
import com.moredian.fishnet.web.controller.member.request.AddMemberModel;
import com.moredian.fishnet.web.controller.member.request.AddSimpleMemberModel;
import com.moredian.fishnet.web.controller.member.request.ConfigDeptModel;
import com.moredian.fishnet.web.controller.member.request.ConfigGroupsModel;
import com.moredian.fishnet.web.controller.member.request.GetUserInfoModel;
import com.moredian.fishnet.web.controller.member.request.SearchMemberModel;
import com.moredian.fishnet.web.controller.member.request.SimpleUpdateMemberModel;
import com.moredian.fishnet.web.controller.member.request.ToggleShowImgModel;
import com.moredian.fishnet.web.controller.member.request.UpdateHeadModel;
import com.moredian.fishnet.web.controller.member.request.UpdateMemberModel;
import com.moredian.fishnet.web.controller.member.request.UpdateSignatureModel;
import com.moredian.fishnet.web.controller.member.request.UpdateVerifyPicModel;
import com.moredian.fishnet.web.controller.member.request.UploadVerifyImgModel;
import com.moredian.fishnet.web.controller.member.response.GroupData;
import com.moredian.fishnet.web.controller.member.response.MemberData;
import com.moredian.fishnet.web.controller.member.response.MemberDetailData;
import com.moredian.fishnet.web.controller.member.response.PaginationMemberData;
import com.moredian.fishnet.web.controller.member.response.UserPassDeviceModel;
import com.moredian.fishnet.web.controller.utils.ExcelImportUtil;
import com.moredian.fishnet.web.controller.utils.RotateImage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Member API", description = "成员接口")
@RequestMapping(value="/member") 
public class MemberController extends BaseController {
	
	@SI
	private MemberService memberService;
	@SI
	private GroupMemberRelationService groupMemberRelationService;
	@SI
	private GroupService groupService;
	@SI
	private DeviceGroupRelationService deviceGroupRelationService;
	@SI
	private DeviceService deviceService;
	@Autowired
	private ImageFileManager imageFileManager;
	@SI
	private DeptMemberRelationService deptMemberRelationService;
	@SI
	private DeptService deptService;
	
	@ApiOperation(value="修改个性头像", notes="修改个性头像")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/updatePersonalityHead", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse updatePersonalityHead(@RequestBody UpdateHeadModel model) throws Exception {
		
		if(model.isTransform()) {
			model.setHeadUrl(this.changeStoreImg(model.getHeadUrl(), FilePathType.TYPE_MEMBERHEADIMAGE));
		}
    	memberService.modifyShowImage(model.getOrgId(), model.getMemberId(), model.getHeadUrl()).pickDataThrowException();
		return new BaseResponse();
    	
    }
	
	@ApiOperation(value="修改个性签名", notes="修改个性签名")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/updatePersonalitySignature", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse updatePersonalitySignature(@RequestBody UpdateSignatureModel model) {
    	memberService.updateSignature(model.getOrgId(), model.getMemberId(), model.getSignature()).pickDataThrowException();
		return new BaseResponse();
    	
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="获取人员通行信息", notes="获取人员通行信息")
	@RequestMapping(value="/passInfo", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse passInfo(GetUserInfoModel model) {
		BaseResponse<UserPassDeviceModel> br = new BaseResponse<>();
		UserPassDeviceModel data = new UserPassDeviceModel();
		
		List<String> groupNames = new ArrayList<>();
		List<Long> groupIdList = groupMemberRelationService.findGroupIdByMember(model.getOrgId(), model.getUserId());
		if(CollectionUtils.isNotEmpty(groupIdList)) {
			for(Long groupId : groupIdList) {
				GroupInfo group = groupService.getGroupInfo(model.getOrgId(), groupId);
				groupNames.add(group.getGroupName());
			}
		}
		
		List<String> deviceNames = new ArrayList<>();
		List<Long> deviceIdList = deviceGroupRelationService.findDeviceIdByGroupIds(model.getOrgId(), groupIdList);
		if(CollectionUtils.isNotEmpty(deviceIdList)) {
			for(Long deviceId : deviceIdList) {
				DeviceInfo device = deviceService.getDeviceById(model.getOrgId(), deviceId);
				deviceNames.add(device.getDeviceName());
			}
		}
		
		data.setGroupName(groupNames);
		data.setDeviceAliasName(deviceNames);
		
		br.setData(data);
		return br;
    }
	
	private Pagination<MemberInfo> buildPagination(SearchMemberModel model) {
		Pagination<MemberInfo> pagination = new Pagination<>();
		pagination.setPageNo(model.getPageNo());
		pagination.setPageSize(model.getPageSize());
		return pagination;
	}
	
	private MemberQueryRequest buildRequest(SearchMemberModel model) {
		return BeanUtils.copyProperties(MemberQueryRequest.class, model);
	}
	
	private MemberData memberInfoToMemberData(MemberInfo memberInfo, boolean mergeDeptName) {
		MemberData data = BeanUtils.copyProperties(MemberData.class, memberInfo);
		
		if(StringUtils.isBlank(memberInfo.getShowFaceUrl())) {
			data.setShowFaceUrl(memberInfo.getDingAvatar());
		} else {
			if(!memberInfo.getShowFaceUrl().startsWith("http")) {
				data.setShowFaceUrl(imageFileManager.getImageUrl(memberInfo.getShowFaceUrl()));
			}
		}
		
		data.setVerifyFaceUrl(imageFileManager.getImageUrl(memberInfo.getVerifyFaceUrl()));
		
		if(mergeDeptName) {
			data.setDepts(deptMemberRelationService.findDeptName(memberInfo.getOrgId(), memberInfo.getMemberId()));
		}
		
		return data;
	}
	
	private List<MemberData> memberInfoListToMemberDataList(List<MemberInfo> memberInfoList, boolean mergeDeptName) {
		
		List<MemberData> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(memberInfoList)) return list;
		
		for(MemberInfo memberInfo : memberInfoList) {
			list.add(memberInfoToMemberData(memberInfo, mergeDeptName));
		}
		
		return list;
	}
	
	private PaginationMemberData paginationMemberInfoToPaginationMember(Pagination<MemberInfo> pagination, boolean mergeDeptName) {
		PaginationMemberData data = new PaginationMemberData();
		data.setPageNo(pagination.getPageNo());
		data.setTotalCount(pagination.getTotalCount());
		data.setMembers(this.memberInfoListToMemberDataList(pagination.getData(), mergeDeptName));
		
		return data;
	}
	
	@ApiOperation(value="查询成员", notes="查询成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse list(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
		
		SearchMemberModel model = new SearchMemberModel();
		model.setKeywords(keywords);
		model.setOrgId(orgId);
		model.setPageNo(pageNo);
		model.setPageSize(pageSize);
		
		BaseResponse<PaginationMemberData> br = new BaseResponse<>();
		
		Pagination<MemberInfo> paginationMemberInfo = memberService.findPaginationMember(this.buildPagination(model), this.buildRequest(model));
		
		br.setData(paginationMemberInfoToPaginationMember(paginationMemberInfo, true));
		return br;
    }
	
	private List<GroupData> groupInfoListToGroupDataList(List<GroupInfo> groupInfoList) {
		return BeanUtils.copyListProperties(GroupData.class, groupInfoList);
	}
	
	@ApiOperation(value="查询成员通行群组", notes="查询成员通行群组")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/groups", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse groups(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "memberId") Long memberId) {
		
		BaseResponse<List<GroupData>> br = new BaseResponse<>();
		
		List<Long> groupIdList = groupMemberRelationService.findGroupIdByMember(orgId, memberId);
		List<GroupInfo> groupInfoList = groupService.findGroupByIds(orgId, groupIdList);
		
		br.setData(groupInfoListToGroupDataList(groupInfoList));
		
		return br;
    }
	
	private MemberDetailData memberInfoToMemberDetailData(MemberInfo memberInfo, List<DeptInfo> deptInfoList) {
		MemberDetailData data = BeanUtils.copyProperties(MemberDetailData.class, memberInfo);
		if(StringUtils.isBlank(memberInfo.getShowFaceUrl())) {
			data.setShowFaceUrl(memberInfo.getDingAvatar());
		} else {
			if(!memberInfo.getShowFaceUrl().startsWith("http")) {
				data.setShowFaceUrl(imageFileManager.getImageUrl(memberInfo.getShowFaceUrl()));
			}
		}
		data.setVerifyFaceUrl(imageFileManager.getImageUrl(memberInfo.getVerifyFaceUrl()));
		data.setDepts(BeanUtils.copyListProperties(DeptData.class, deptInfoList));
		return data;
	}
	
	@ApiOperation(value="查看人员详情", notes="查看人员详情")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/info", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse memberInfo(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "memberId") Long memberId) {
		
		BaseResponse<MemberDetailData> br = new BaseResponse<>();
		
		MemberInfo memberInfo = memberService.getMemberInfo(orgId, memberId);
		
		List<Long> deptIdList = deptMemberRelationService.findDeptId(orgId, memberId);
		
		List<DeptInfo> deptInfoList = deptService.findDeptByIds(orgId, deptIdList);
		
		br.setData(memberInfoToMemberDetailData(memberInfo, deptInfoList));
		
		return br;
    }
	
	private String storeImage(byte[] image, FilePathType filePathType) {
		
		Map<String, Object> map = null;
		String fileName = RandomUtil.getUUID() + ".jpg";
		try {
			map = imageFileManager.saveImage(image, filePathType, fileName);
		} catch (Exception e) {
			ExceptionUtils.throwException(MemberErrorCode.SAVE_IMAGE_ERROR, MemberErrorCode.SAVE_IMAGE_ERROR.getMessage());
		}
		if(!"0".equals(map.get("result"))){
			ExceptionUtils.throwException(MemberErrorCode.SAVE_IMAGE_WRONG, MemberErrorCode.SAVE_IMAGE_WRONG.getMessage());
		}
		
		String relativeUrl = (String)map.get("url");
		File file = new File(imageFileManager.getImageLocalPath(relativeUrl));
        RotateImage.metadataExtractor(file);
		
		return relativeUrl;
	}
	
	/**
	 * 临时图片转存目标图片目录
	 * @param sourceUrl
	 * @param filePathType
	 * @return
	 */
	private String changeStoreImg(String sourceUrl, FilePathType filePathType) {
		Picture picture = new NetworkImage(sourceUrl);
		byte[] imageData = picture.getImageData();
		return imageFileManager.getImageUrl(this.storeImage(imageData, filePathType));
	}
	
	private MemberUpdateRequest buildRequest(UpdateMemberModel model) {
		if(!StringUtils.isBlank(model.getShowFaceUrl())) {
			model.setShowFaceUrl(this.changeStoreImg(model.getShowFaceUrl(), FilePathType.TYPE_MEMBERHEADIMAGE));
		}
		
		if(!StringUtils.isBlank(model.getVerifyFaceUrl())) {
			model.setVerifyFaceUrl(this.changeStoreImg(model.getVerifyFaceUrl(), FilePathType.TYPE_MEMBERFACEIMAGE));
		}
		
		return BeanUtils.copyProperties(MemberUpdateRequest.class, model);
	}
	
	private MemberAddRequest buildRequest(AddMemberModel model) {
		if(!StringUtils.isBlank(model.getShowFaceUrl())) {
			model.setShowFaceUrl(this.changeStoreImg(model.getShowFaceUrl(), FilePathType.TYPE_MEMBERHEADIMAGE));
		}
		
		if(!StringUtils.isBlank(model.getVerifyFaceUrl())) {
			model.setVerifyFaceUrl(this.changeStoreImg(model.getVerifyFaceUrl(), FilePathType.TYPE_MEMBERFACEIMAGE));
		}
		
		return BeanUtils.copyProperties(MemberAddRequest.class, model);
	}
	
	@ApiOperation(value="添加成员", notes="添加成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse addMember(@RequestBody AddMemberModel model) {
    	
		memberService.addMember(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private MemberAddRequest buildRequest(AddSimpleMemberModel model) {
		if(!StringUtils.isBlank(model.getShowFaceUrl())) {
			model.setShowFaceUrl(this.changeStoreImg(model.getShowFaceUrl(), FilePathType.TYPE_MEMBERHEADIMAGE));
		}
		
		if(!StringUtils.isBlank(model.getVerifyFaceUrl())) {
			model.setVerifyFaceUrl(this.changeStoreImg(model.getVerifyFaceUrl(), FilePathType.TYPE_MEMBERFACEIMAGE));
		}
		
		return BeanUtils.copyProperties(MemberAddRequest.class, model);
	}
	
	@ApiOperation(value="添加成员", notes="添加成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/simpleAdd", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse simpleAdd(@RequestBody AddSimpleMemberModel model) {
    	
		memberService.addMember(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="导入成员信息", notes="导入成员信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/import", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse importMember(@RequestParam(value="orgId") Long orgId, @RequestParam(value="file") MultipartFile file) throws Exception {
    	
		List<ImportMemberModel> importList = new ArrayList<>();
		
		if(file.getOriginalFilename().indexOf(".xls") == -1 && file.getOriginalFilename().indexOf(".xlsx") == -1) {
			return new BaseResponse("1", "文件格式不正确");
		}
		
		int i = 1;
		List<String[]> infoList = ExcelImportUtil.read(file.getInputStream(), file.getOriginalFilename(), 8, false);
		
		Set<Integer> wrongRowSet = new HashSet<>(); // 提供的数据有误
		Set<Integer> repeatRowSet = new HashSet<>(); // 数据自我重复
		Set<Integer> deptWrongRowSet = new HashSet<>(); // 部门数据有误
		Set<Integer> dbRepeatRowSet = new HashSet<>(); // 库中已存在
		Set<String> uniqueSet = new HashSet<>();
		for(String[] str : infoList){
			i++;
			
			String memberName = StringUtils.isBlank(str[0]) ? null : str[0].trim();
			String mobile = StringUtils.isBlank(str[1]) ? null : str[1].trim();
			String nickName = StringUtils.isBlank(str[2]) ? null : str[2].trim();
			String sexText = StringUtils.isBlank(str[3]) ? null : str[3].trim();
			String birthdayText = StringUtils.isBlank(str[4]) ? null : str[4].trim();
			String email = StringUtils.isBlank(str[5]) ? null : str[5].trim();
			String deptName = StringUtils.isBlank(str[6]) ? null : str[6].trim();
			String post = StringUtils.isBlank(str[7]) ? null : str[7].trim();
			
			Integer sex = Sex.getValue(sexText);
			Date birthday = null;
			Long deptId = null;
			
			if(memberName == null) {
				wrongRowSet.add(i);
				continue;
			}
			
			if(mobile == null) {
				wrongRowSet.add(i);
				continue;
			}
			
			if(!Validator.isMobile(mobile)) {
				wrongRowSet.add(i);
				continue;
			}
			
			if(email != null && !Validator.isEmail(email)) {
				wrongRowSet.add(i);
				continue;
			}
			
			if(birthdayText != null) {
				boolean transOk = false; // TODO 生日转换为Date
				if(transOk) {
					wrongRowSet.add(i);
					continue;
				}
			}
			
			if(uniqueSet.contains(memberName + "-" + mobile)) {
				repeatRowSet.add(i);
				continue;
			}
			
			if(deptName != null) {
				DeptInfo dept = deptService.getDeptByName(orgId, deptName);
				if(dept == null) {
					deptWrongRowSet.add(i);
					continue;
				} 
				deptId = dept.getDeptId();
				
			} else {
				deptId = deptService.getRootDept(orgId).getDeptId();
			}
			
			MemberInfo memberInfo = memberService.getMemberByNameAndMobile(orgId, memberName, mobile);
			if(memberInfo != null) {
				dbRepeatRowSet.add(i);
				continue;
			}
			
			uniqueSet.add(memberName + "-" + mobile);
			
			importList.add(new ImportMemberModel(memberName, mobile, nickName, sex, birthday, email, deptId, post));
			
		}
		
		StringBuffer sb = new StringBuffer();
		if(CollectionUtils.isNotEmpty(wrongRowSet)) {
			sb.append("录入的数据有误, 行号: " + wrongRowSet.toString());
		}
		
		if(CollectionUtils.isNotEmpty(repeatRowSet)) {
			sb.append("重复录入, 行号: " + repeatRowSet.toString());
		}

		if(CollectionUtils.isNotEmpty(deptWrongRowSet)) {
			sb.append("部门填写错误, 行号: " + deptWrongRowSet.toString());
		}
		
		if(CollectionUtils.isNotEmpty(dbRepeatRowSet)) {
			sb.append("系统中已存在, 行号: " + dbRepeatRowSet.toString());
		}
		
		if(CollectionUtils.isNotEmpty(importList)) {
			memberService.importMemberInfo(orgId, importList).pickDataThrowException();
		}
		
		BaseResponse<String> br = new BaseResponse<>();
		br.setData(sb.toString());
		
		return br;
    }
	
	public static byte[] multipartFile2byte(MultipartFile imgFile)  
            throws IOException {  
		InputStream inStream = imgFile.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
	
	@ApiOperation(value="上传识别照片", notes="上传识别照片")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/uploadVerifyImg", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse uploadVerifyImg(@RequestBody UploadVerifyImgModel model) throws Exception {
		
		if(StringUtils.isBlank(model.getFilename()) || StringUtils.isBlank(model.getBase64Image())) return new BaseResponse("1", "缺少必要参数");
		
		String filename = model.getFilename();
		filename = filename.substring(0, filename.indexOf(".")); // "某某某_1388888888"
		
		if(filename.indexOf("_")==-1) {
			return new BaseResponse("1", "文件名格式错误");
		}
		
		String memberName = filename.substring(0, filename.length()-12);
		String mobile = filename.substring(filename.length()-11);
		
		MemberInfo memberInfo = memberService.getMemberByNameAndMobile(model.getOrgId(), memberName, mobile);
		if(memberInfo == null) {
			return new BaseResponse("1", "人员不存在");
		}
		
		byte[] imageData = Base64.decodeBase64(model.getBase64Image());
		
		String url = this.storeImage(imageData, FilePathType.TYPE_MEMBERFACEIMAGE);
		
		memberService.modifyVerifyImage(model.getOrgId(), memberInfo.getMemberId(), imageFileManager.getImageUrl(url)).pickDataThrowException();
		
		BaseResponse<String> br = new BaseResponse<>();
		br.setData(imageFileManager.getImageUrl(url));
		
		return br;
    }
	
	@ApiOperation(value="修改成员信息", notes="修改成员信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse editMember(@RequestBody UpdateMemberModel model) {
    	
		memberService.updateMember(this.buildRequest(model)).pickDataThrowException();
		
		if(CollectionUtils.isNotEmpty(model.getGroupIds())) {
			groupMemberRelationService.resetGroupRelation(model.getOrgId(), model.getMemberId(), model.getGroupIds()).pickDataThrowException();
		}
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="配置人员群组关系", notes="配置人员群组关系")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/groups", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse configGroups(@RequestBody ConfigGroupsModel model) {
    	
		groupMemberRelationService.resetGroupRelation(model.getOrgId(), model.getMemberId(), model.getGroupIds()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	private MemberUpdateRequest buildRequest(SimpleUpdateMemberModel model) {
		if(!StringUtils.isBlank(model.getShowFaceUrl())) {
			model.setShowFaceUrl(this.changeStoreImg(model.getShowFaceUrl(), FilePathType.TYPE_MEMBERHEADIMAGE));
		}
		
		if(!StringUtils.isBlank(model.getVerifyFaceUrl())) {
			model.setVerifyFaceUrl(this.changeStoreImg(model.getVerifyFaceUrl(), FilePathType.TYPE_MEMBERFACEIMAGE));
		}
		
		return BeanUtils.copyProperties(MemberUpdateRequest.class, model);
	}
	
	@ApiOperation(value="修改成员信息", notes="修改成员信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/simpleEdit", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse simpleEdit(@RequestBody SimpleUpdateMemberModel model) {
    	
		memberService.updateMember(this.buildRequest(model)).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	
	@ApiOperation(value="配置人员部门关系", notes="配置人员部门关系")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/depts", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse configDepts(@RequestBody ConfigDeptModel model) {
    	
		memberService.updateDeptRelation(model.getOrgId(), model.getMemberId(), model.getRelationDepts()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="获取成员总数", notes="获取成员总数")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/count", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse count(@RequestParam(value = "orgId") Long orgId) {
		
		BaseResponse<Integer> br = new BaseResponse<>();
		
		br.setData(memberService.countMember(orgId));
		
		return br;
    }
	
	@ApiOperation(value="获取未上传识别照片成员数", notes="获取未上传识别照片成员数")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/countNoVerifyPic", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse countNoVerifyPic(@RequestParam(value = "orgId") Long orgId) {
		
		BaseResponse<Integer> br = new BaseResponse<>();
		
		br.setData(memberService.countNoVerifyPicMember(orgId));
		
		return br;
    }
	
	@ApiOperation(value="获取所有人员第三方id", notes="获取所有人员第三方id")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/tpIds", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse tpIds(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "hasFaceUrl") boolean hasFaceUrl) {
		
		BaseResponse<List<String>> br = new BaseResponse<>();
		
		br.setData(memberService.findFromIds(orgId, hasFaceUrl));
		
		return br;
    }
	
	@ApiOperation(value="修改识别头像", notes="修改识别头像")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/modifyVerifyPic", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse modifyVerifyPic(@RequestBody UpdateVerifyPicModel model) {
		
		//model.setVerifyFaceUrl(memberService.cutFaceAndReturnUrl(model.getVerifyFaceUrl()));
		if(model.isTransform()) {
			model.setVerifyFaceUrl(this.changeStoreImg(model.getVerifyFaceUrl(), FilePathType.TYPE_MEMBERFACEIMAGE));
		}
    	
		memberService.modifyVerifyImage(model.getOrgId(), model.getMemberId(), model.getVerifyFaceUrl()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
	@ApiOperation(value="获取用户信息(按第三方id)", notes="获取用户信息(按第三方id)")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/simpleInfoByTpId", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse simpleInfoByTpId(@RequestParam(value = "orgId", required=false) Long orgId, @RequestParam(value = "tpUserId", required=false) String tpUserId) {
		
		BaseResponse<MemberData> br = new BaseResponse<>();
		
		MemberInfo memberInfo = memberService.getMemberByTpId(orgId, tpUserId);
		br.setData(memberInfoToMemberData(memberInfo, false));
		
		return br;
    }


	@ApiOperation(value="删除用户", notes="删除用户")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteMemberById(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "memberId") Long memberId) {

		BaseResponse<Boolean> br = new BaseResponse<>();
		ServiceResponse<Boolean> removeResult= memberService.removeMember(orgId, memberId);
		br.setData(removeResult.getData());
		return br;
	}
	
	@ApiOperation(value="获取用户信息", notes="获取用户信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/simpleInfo", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse infoByTpId(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "memberId") Long memberId) {
		
		BaseResponse<MemberData> br = new BaseResponse<>();
		
		MemberInfo memberInfo = memberService.getMemberInfo(orgId, memberId);
		br.setData(memberInfoToMemberData(memberInfo, false));
		
		return br;
    }
	
	@ApiOperation(value="快速检索成员", notes="快速检索成员")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/quickSearch", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse quickSearch(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
		
		SearchMemberModel model = new SearchMemberModel();
		model.setKeywords(keywords);
		model.setOrgId(orgId);
		model.setPageNo(pageNo);
		model.setPageSize(pageSize);
		
		BaseResponse<PaginationMemberData> br = new BaseResponse<>();
		
		Pagination<MemberInfo> paginationMemberInfo = memberService.findPaginationMember(this.buildPagination(model), this.buildRequest(model));
		
		br.setData(paginationMemberInfoToPaginationMember(paginationMemberInfo, false));
		return br;
    }
	
	@ApiOperation(value="判断是否需要完善个人信息", notes="判断是否需要完善个人信息")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/needFillPersonalInfo", method=RequestMethod.GET)
    @ResponseBody
    public BaseResponse needFillPersonalInfo(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "memberId") Long memberId, @RequestParam(value = "moduleType", required = false) Integer moduleType, @RequestParam(value = "admin") boolean admin) {
		
		BaseResponse<Boolean> br = new BaseResponse<>();
		
		if(orgId == null) {
			br.setData(true);
			return br;
		}
		
		if(moduleType == null) {
			br.setData(false);
			return br;
		}
		
		MemberInfo memberInfo = memberService.getMemberInfo(orgId, memberId);
		
		boolean hadVerifyFace = false;
		if(StringUtils.isNotBlank(memberInfo.getVerifyFaceUrl())) {
			hadVerifyFace = true;
		}
		
		boolean hadSignature = false;
		if(StringUtils.isNotBlank(memberInfo.getSignature())) {
			hadSignature = true;
		}
		
		boolean hadNickName = false;
		if(StringUtils.isNotBlank(memberInfo.getNickName())) {
			hadNickName = true;
		}
		
		if(!hadVerifyFace) { // 没有识别照片，无论管理版还是员工版，要需要完善信息
			br.setData(true);
			return br;
		}
		
		if(!admin) { // 员工版
			boolean firstLogin = memberService.isFirstLogin(orgId, memberId, moduleType);
			if(firstLogin) { // 首次登录
				if(!hadSignature || !hadNickName) { // 没有个性签名或昵称，也要引导完善
					br.setData(true);
					return br;
				}
			}
		} 
		
		br.setData(false);
    	
		return br;
    	
    }
	
	@ApiOperation(value="切换默认显示照片", notes="切换默认显示识别照片/个性照片")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/toggleShowImg", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse toggleShowImg(@RequestBody ToggleShowImgModel model) {
    	
		memberService.toggleShowImg(model.getOrgId(), model.getMemberId(), model.getShowVerifyFlag()).pickDataThrowException();
		
		return new BaseResponse();
    }
	
}
