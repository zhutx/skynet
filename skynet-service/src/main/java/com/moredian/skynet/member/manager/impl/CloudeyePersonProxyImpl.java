package com.moredian.skynet.member.manager.impl;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.ExtendModel;
import com.moredian.bee.common.utils.FacePicture;
import com.moredian.bee.common.utils.NetworkFacePicture;
import com.moredian.bee.common.utils.StringUtil;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.CloudeyeErrorCode;
import com.moredian.cloudeye.core.api.lib.LibraryEnterService;
import com.moredian.cloudeye.core.api.lib.LibraryQueryService;
import com.moredian.cloudeye.core.api.lib.PersonFullInfo;
import com.moredian.cloudeye.core.api.lib.PersonGroup;
import com.moredian.cloudeye.core.api.lib.request.*;
import com.moredian.cloudeye.core.api.lib.response.EnterPersonPicResponse;
import com.moredian.cloudeye.core.api.lib.response.EnterPersonResponse;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.enums.PersonType;
import com.moredian.skynet.member.manager.CloudeyePersonProxy;
import com.moredian.skynet.member.manager.GroupPersonManager;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.enums.BizType;
import com.moredian.skynet.org.manager.GroupManager;
import com.moredian.skynet.org.manager.OrgManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CloudeyePersonProxyImpl implements CloudeyePersonProxy {
	
	@SI
	private LibraryEnterService libraryEnterService;
	@SI
	private LibraryQueryService libraryQueryService;
	@Autowired
	private OrgManager orgManager;
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private GroupPersonManager groupPersonManager;
	@Autowired
	private ImageFileManager imageFileManager;
	
	@Override
	public void addMembersToGroup(Long orgId, Long groupId, List<Long> members) {
		
		EnterGroupPersonRequest request = new EnterGroupPersonRequest();
		request.setRelationOrgId(orgId);
		PersonGroup group = new PersonGroup();
		group.setGroupCode(String.valueOf(groupId));
		request.setGroup(group);
		request.setRelationUserIds(this.filterRepeat(members));
		
		ServiceResponse<Boolean> sr = libraryEnterService.enterGroupPerson(request);
		if(!sr.isSuccess()) {
//			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
//				sr.pickDataThrowException();
//			}
		}
		
	}

	@Override
	public void removeMembersToGroup(Long orgId, Long groupId, List<Long> members) {
		
		RemoveGroupPersonRequest request = new RemoveGroupPersonRequest();
		request.setRelationOrgId(orgId);
		PersonGroup group = new PersonGroup();
		group.setGroupCode(String.valueOf(groupId));
		request.setGroup(group);
		request.setRelationUserIds(members);
		
		ServiceResponse<Boolean> sr = libraryEnterService.removeGroupPerson(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
		
	}
	
	@Override
	public boolean isPersonExist(Long orgId, Long memberId) {
		PersonFullInfo person = libraryQueryService.getPersonByRelationUserId(orgId, memberId);
		return person != null ? true : false;
	}
	
	@Override
	public PersonFullInfo getPerson(Long orgId, Long memberId) {
		return libraryQueryService.getPersonByRelationUserId(orgId, memberId);
	}

	@Override
	public void addSimplePerson(Long orgId, Long memberId, Integer userType, String userName) {
		EnterPersonRequest request = new EnterPersonRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		request.setRelationUserType(userType);
		if(StringUtils.isBlank(userName)) {
			userName = "钉钉用户";
		}
		request.setUserName(userName);
		List<PersonGroup> personGroups = new ArrayList<>();
		request.setGroups(personGroups);
		ServiceResponse<EnterPersonResponse> sr = libraryEnterService.enterPerson(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.USER_IS_ENTERED)) {
				sr.pickDataThrowException();
			}
		}
	}
	
	private String getShowUrl(Member member) {
		if(StringUtils.isNotBlank(member.getShowFaceUrl())) return member.getShowFaceUrl();
		return null;
	}
	
	@Override
	public void addPerson(Member member) {
		
		List<Long> groupIdList = groupPersonManager.findGroupIdByMember(member.getOrgId(), member.getMemberId(), PersonType.MEMBER.getValue());
		
		EnterPersonRequest request = new EnterPersonRequest();
		request.setRelationOrgId(member.getOrgId());
		request.setRelationUserId(member.getMemberId());
		request.setRelationUserType(97);
		//fix bug MD-400
		if(StringUtil.isBlank(member.getNickName())){
			request.setUserName(member.getMemberName());
		}else{
			request.setUserName(member.getNickName());
		}

		
		if(StringUtils.isNotBlank(member.getVerifyFaceUrl())) {
			List<FacePicture> facePictures = new ArrayList<>();
			FacePicture facePicture = new NetworkFacePicture(imageFileManager.getImageUrl(member.getVerifyFaceUrl()));
			facePictures.add(facePicture);
			request.setFacePictures(facePictures);
		}
		
		String showUrl = this.getShowUrl(member);
		if(StringUtils.isNotBlank(showUrl)) {
			request.setUserHeadImg(showUrl);
		}
		
		if(StringUtils.isNotBlank(member.getSignature())) {
			ExtendModel extend = new ExtendModel();
			extend.put("signature", member.getSignature());
			request.setExtend(extend);
		}
		List<PersonGroup> personGroups = this.buildPersonGroupList(member.getOrgId(), groupIdList);
		request.setGroups(personGroups);
		ServiceResponse<EnterPersonResponse> sr = libraryEnterService.enterPerson(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.USER_IS_ENTERED)) {
				sr.pickDataThrowException();
			}
		}
	}

	private List<Long> filterRepeat(List<Long> members) {
		List<Long> list = new ArrayList<>();
		Set<Long> set = new HashSet<>();
		set.addAll(members);
		
		list.addAll(set);
		return list;
	}

	private List<PersonGroup> buildPersonGroupList(Long orgId, List<Long> groupIdList) {
		if(CollectionUtils.isEmpty(groupIdList)) return new ArrayList<>();
		List<PersonGroup> groups = new ArrayList<>();
		for(Long groupId : groupIdList) {
			Group group = groupManager.getGroupById(orgId, groupId);
			PersonGroup personGroup = new PersonGroup(PersonGroup.GROUP_TYPE_COMMON, group.getGroupId().toString(), group.getGroupName());
			groups.add(personGroup);
		}
		return groups;
	}
	
	@Override
	public void changeMemberGroupRelation(Member member, List<Long> groupIdList) {
		
		
		if(!orgManager.isBizEnable(member.getOrgId(), BizType.RECOGNIZE.getValue())) return; // 机构未开启人脸识别业务
		
		ChangePersonGroupRequest request = new ChangePersonGroupRequest();
		request.setRelationOrgId(member.getOrgId());
		request.setRelationUserId(member.getMemberId());
		request.setGroups(this.buildPersonGroupList(member.getOrgId(), groupIdList));
		
		ServiceResponse<Boolean> sr = libraryEnterService.addPersonGroupRelate(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.PERSON_GROUP_NOTEXIST)) { // TODO 云眼ErrorCode定义反了
				sr.pickDataThrowException();
			}
		}
		
	}

	@Override
	public void putPersonExtend(Long orgId, Long memberId, Map<String, Object> extend) {
		PersonExtendRequest request= new PersonExtendRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		request.setExtend(extend);
		ServiceResponse<Boolean> sr = libraryEnterService.putPersonExtend(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
	}

	@Override
	public void changeMemberHead(Long orgId, Long memberId, String memberName, String headUrl) {
		ChangePersonBaseRequest request = new ChangePersonBaseRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		if(StringUtils.isBlank(memberName)) {
			memberName = "钉钉用户";
		}
		request.setUserName(memberName);
		request.setUserHeadImg(headUrl);
		ServiceResponse<Boolean> sr = libraryEnterService.changePersonBase(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
	}

	@Override
	public void replaceVerifyImage(Long orgId, Long memberId, String verifyUrl) {
		DisablePersonPicRequest request = new DisablePersonPicRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		ServiceResponse<Boolean> sr = libraryEnterService.disablePersonPic(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
		
		EnterPersonPicRequest picRequest = new EnterPersonPicRequest();
		picRequest.setRelationOrgId(orgId);
		picRequest.setRelationUserId(memberId);
		FacePicture facePic = new NetworkFacePicture(verifyUrl);
		picRequest.setFacePic(facePic);
		ServiceResponse<EnterPersonPicResponse> sr2 = libraryEnterService.enterPersonPic(picRequest);
		if(!sr2.isSuccess()) {
			if(!sr2.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr2.pickDataThrowException();
			}
		}
	}

//	@Override
//	public void deletePerson(Long orgId, Long memberId) {
//		DeletePersonRequest request = new DeletePersonRequest();
//		request.setRelationOrgId(orgId);
//		request.setRelationUserId(memberId);
//		ServiceResponse<Boolean> sr = libraryEnterService.deletePerson(request);
//		if(!sr.isSuccess()) {
//			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
//				sr.pickDataThrowException();
//			}
//		}
//	}
	
	@Override
	public void disablePerson(Long orgId, Long memberId) {
		DisablePersonRequest request = new DisablePersonRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		ServiceResponse<Boolean> sr = libraryEnterService.disablePerson(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
	}
	
	@Override
	public void activePerson(Long orgId, Long memberId) {
		DisablePersonRequest request = new DisablePersonRequest();
		request.setRelationOrgId(orgId);
		request.setRelationUserId(memberId);
		ServiceResponse<Boolean> sr = libraryEnterService.activePerson(request);
		if(!sr.isSuccess()) {
			if(!sr.getErrorContext().equalsErrorCode(CloudeyeErrorCode.NOT_EXIST_PERSON)) {
				sr.pickDataThrowException();
			}
		}
	}

}
