package com.moredian.skynet.member.manager.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.consts.Sex;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.NetworkFacePicture;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.common.utils.Picture;
import com.moredian.bee.common.utils.RandomUtil;
import com.moredian.bee.common.utils.Rect;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.filemanager.enums.FilePathType;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.lib.LibraryQueryService;
import com.moredian.cloudeye.core.api.rs.DetectFace;
import com.moredian.cloudeye.core.api.rs.LibraryDetectService;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.common.model.msg.ClearMemberRelationDataMsg;
import com.moredian.skynet.member.domain.DeptMember;
import com.moredian.skynet.member.domain.GroupPerson;
import com.moredian.skynet.member.domain.LoginLog;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.domain.MemberQueryCondition;
import com.moredian.skynet.member.domain.UserRelation;
import com.moredian.skynet.member.enums.CertType;
import com.moredian.skynet.member.enums.MemberErrorCode;
import com.moredian.skynet.member.enums.MemberStatus;
import com.moredian.skynet.member.enums.UserRelationType;
import com.moredian.skynet.member.manager.CloudeyePersonProxy;
import com.moredian.skynet.member.manager.DeptMemberManager;
import com.moredian.skynet.member.manager.GroupPersonManager;
import com.moredian.skynet.member.manager.GroupPersonSizeManager;
import com.moredian.skynet.member.manager.GroupRangeManager;
import com.moredian.skynet.member.manager.MemberManager;
import com.moredian.skynet.member.mapper.GroupPersonMapper;
import com.moredian.skynet.member.mapper.LoginLogMapper;
import com.moredian.skynet.member.mapper.MemberMapper;
import com.moredian.skynet.member.mapper.UserRelationMapper;
import com.moredian.skynet.member.model.DingUserExtend;
import com.moredian.skynet.member.model.MemberInfo;
import com.moredian.skynet.member.request.ImportMemberModel;
import com.moredian.skynet.member.request.MemberAddRequest;
import com.moredian.skynet.member.request.MemberQueryRequest;
import com.moredian.skynet.member.request.MemberUpdateRequest;
import com.moredian.skynet.member.service.adapter.request.ModifyUserRelationRequest;
import com.moredian.skynet.member.service.adapter.request.SelectUserRelationRequest;
import com.moredian.skynet.member.service.adapter.response.MemberIdMapping;
import com.moredian.skynet.member.service.adapter.response.ProsceniumUserResponse;
import com.moredian.skynet.member.utils.IDValidator;
import com.moredian.skynet.org.domain.Dept;
import com.moredian.skynet.org.domain.Position;
import com.moredian.skynet.org.enums.GroupRangeType;
import com.moredian.skynet.org.enums.PersonType;
import com.moredian.skynet.org.enums.TpType;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.DeptManager;
import com.moredian.skynet.org.manager.GroupManager;
import com.moredian.skynet.org.manager.PositionManager;


@Service
public class MemberManagerImpl implements MemberManager {

	private static final Logger logger = LoggerFactory.getLogger(MemberManagerImpl.class);
	
	@Autowired
	private ImageFileManager imageFileManager;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private DeptMemberManager deptMemberManager;
	@Autowired
	private CloudeyePersonProxy cloudeyePersonProxy;
	@SI
	private LibraryDetectService libraryDetectService;
	@Autowired
	private LoginLogMapper loginLogMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@SI
	private LibraryQueryService libraryQueryService;
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private GroupPersonManager groupPersonManager;
	@Autowired
	private GroupRangeManager groupRangeManager;
	@Autowired
	private GroupPersonMapper groupPersonMapper;
	@Autowired
	private GroupPersonSizeManager groupPersonSizeManager;
	@Autowired
	private DeptManager deptManager;
	@Autowired
	private UserRelationMapper userRelationMapper;
	@Autowired
	private PositionManager positionManager;
	@Value("${rmq.switch}")
	private int rmqSwitch;
	
	private Long genPrimaryKey(String typeName) {
		return idgeneratorService.getNextIdByTypeName(typeName).getData();
	}
	
	private Long genLoginLogId() {
		return idgeneratorService.getNextIdByTypeName("com.moredian.skynet.member.LoginLog").getData();
	}
	
	private Long genPrimaryId(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}
	
	/**
	 * 存储图片
	 * @param image
	 * @param filePathType
	 * @return
	 */
	private String storeImage(byte[] image, FilePathType filePathType) {
		
		// 存储图片
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
		
		return (String)map.get("url");
	}
	
	private void busiCheck(MemberAddRequest request){
		
		// 证件号重复校验
		/*if(StringUtils.isNotBlank(request.getCertNo())) {
			
			if(CertType.IDENTITY.getValue() == request.getCertType() && !IDValidator.isValid(request.getCertNo())) {
				ExceptionUtils.throwException(MemberErrorCode.IDENTITY_CARD_WRONG, String.format(MemberErrorCode.IDENTITY_CARD_WRONG.getMessage(), request.getMobile()));
			}
			
			Member member = memberMapper.loadByCertNo(request.getOrgId(), request.getCertNo());
			if(member != null) {
				ExceptionUtils.throwException(MemberErrorCode.MEMBER_EXIST, String.format(MemberErrorCode.MEMBER_EXIST.getMessage(), request.getCertNo()));
			}
			
		}*/
		
		// 手机号重复校验
		if(StringUtils.isNotBlank(request.getMobile())) {
			Member member = memberMapper.loadByMobile(request.getOrgId(), request.getMobile());
			if(member != null) {
				ExceptionUtils.throwException(MemberErrorCode.MEMBER_EXIST, String.format(MemberErrorCode.MEMBER_EXIST.getMessage(), request.getMobile()));
			}
		}
		
	}
	
	private Member memberAddRequestToMember(MemberAddRequest request){
		Member member = new Member();
		member.setOrgId(request.getOrgId());
		member.setMemberId(this.genPrimaryId(Member.class.getName()));
		member.setTpType(TpType.SELF.getValue());
		member.setTpId(String.valueOf(member.getMemberId()));
		if(StringUtils.isNotBlank(request.getVerifyFaceUrl())) {
			this.checkFace(request.getVerifyFaceUrl());
			member.setVerifyFaceUrl(imageFileManager.getRelativePath(request.getVerifyFaceUrl()));
		} 
		
		if(StringUtils.isNotBlank(request.getShowFaceUrl())) {
			member.setShowFaceUrl(imageFileManager.getRelativePath(request.getShowFaceUrl()));
		} 
		member.setShowVerifyFlag(YesNoFlag.YES.getValue());
		member.setChargeFlag(YesNoFlag.NO.getValue());
		member.setAdminFlag(YesNoFlag.NO.getValue());
		member.setMemberName(request.getMemberName());
		member.setNickName(request.getNickName());
		member.setSignature(request.getSignature());
		if(request.getSex() != null){
			member.setSex(request.getSex());
		} else {
			member.setSex(Sex.UNKNOWN.intValue());
		}
		member.setMobile(request.getMobile());
		member.setEmail(request.getEmail());
		member.setBirthday(request.getBirthday());
		member.setJobNum(request.getJobNum());
		member.setEnterTime(request.getEnterTime());
		member.setPost(request.getPost());
		member.setMemo(request.getMemo());
		member.setHideFlag(YesNoFlag.NO.getValue());
		member.setStatus(MemberStatus.USABLE.getValue());
		return member;
	}
	
	private Member doAddMember(MemberAddRequest request) {
		Member member = memberAddRequestToMember(request);
		memberMapper.insert(member);
		
		// 添加人员部门关系
		List<Long> relationDepts = new ArrayList<>();
		if(CollectionUtils.isEmpty(request.getRelationDepts())) {
			Dept dept = deptManager.getRootDept(request.getOrgId());
			relationDepts.add(dept.getDeptId()); // 根部门
		} else {
			relationDepts.addAll(request.getRelationDepts());
		}
		for(Long deptId : relationDepts) {
			DeptMember deptMember = new DeptMember();
			deptMember.setDeptMemberId(this.genPrimaryId(DeptMember.class.getName()));
			deptMember.setOrgId(request.getOrgId());
			deptMember.setDeptId(deptId);
			deptMember.setTpDeptId(String.valueOf(deptId));
			deptMember.setMemberId(member.getMemberId());
			deptMember.setLeaderFlag(YesNoFlag.NO.getValue());
			deptMember.setStatus(YesNoFlag.YES.getValue());
			deptMemberManager.addMemberToDept(deptMember);
		}
		
		// 调整群组关系
		this.buildGroupRelation(request.getOrgId(), member.getMemberId(), relationDepts, request.getRelationGroupIds());
		
		// 同步云眼
		if(StringUtils.isNotBlank(member.getVerifyFaceUrl())) {
			cloudeyePersonProxy.addPerson(member);
			if(StringUtils.isNotBlank(request.getSignature())) {
				Map<String, Object> extend = new HashMap<>();
				extend.put("signature", request.getSignature());
				cloudeyePersonProxy.putPersonExtend(request.getOrgId(), member.getMemberId(), extend);
			}
		}
		
		return member;
	}
	
	@Override
	@Transactional
	public Member addMember(MemberAddRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		
		this.busiCheck(request);
		
		return this.doAddMember(request);
	}
	
	@Override
	@Transactional
	public void batchAddMember(List<MemberAddRequest> memberList) {
		
		for(MemberAddRequest request : memberList) {
			// TODO 过滤重复的，过滤数据库已存在的
		}
		
		for(MemberAddRequest request : memberList) {
			this.doAddMember(request);
		}
		
	}

	private void buildGroupRelation(Long orgId, Long memberId, List<Long> relationDepts, List<Long> relationGroups) {
		Set<Long> groupIdSet = new HashSet<>();
		
		groupIdSet.addAll(relationGroups);
		
		// 查询全员使用的群组
		List<Long> groupIdList1 = groupManager.findAllMemberUseGroupIds(orgId); 
		
		// 查询范围包含部门的群组
		List<Long> groupIdList2 = groupRangeManager.findGroupIdByRanges(orgId, GroupRangeType.RANGE_DEPT.getValue(), relationDepts);
		
		groupIdSet.addAll(groupIdList1);
		groupIdSet.addAll(groupIdList2);
		
		for(Long groupId : groupIdSet) {
			
			GroupPerson groupPerson = new GroupPerson();
			groupPerson.setGroupPersonId(this.genPrimaryId(GroupPerson.class.getName()));
			groupPerson.setOrgId(orgId);
			groupPerson.setGroupId(groupId);
			groupPerson.setPersonId(memberId);
			groupPerson.setPersonType(PersonType.MEMBER.getValue());
			groupPersonMapper.insert(groupPerson);
			
		}
		
		// 重置群组人员数
		List<Long> groupIdList = new ArrayList<>();
		groupIdList.addAll(groupIdSet);
		groupPersonSizeManager.batchResetPersonSize(orgId, groupIdList, PersonType.MEMBER.getValue());
	}
	
	@Override
	@Transactional
	public boolean modifyShowImage(Long orgId, Long memberId, String headUrl) {
		
		Member existMember = memberMapper.load(orgId, memberId);
		if(existMember == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, String.format(MemberErrorCode.MEMBER_NOT_EXIST.getMessage(), memberId));
		
		memberMapper.updateShowFaceUrl(orgId, memberId, imageFileManager.getRelativePath(headUrl));
		cloudeyePersonProxy.changeMemberHead(orgId, memberId, existMember.getMemberName(), imageFileManager.getRelativePath(headUrl));
		
		return true;
	}

	@Override
	@Transactional
	public boolean modifyVerifyImage(Long orgId, Long memberId, String verifyFaceUrl) {
		this.checkFace(verifyFaceUrl);
		
		memberMapper.updateVerifyFaceUrl(orgId, memberId, imageFileManager.getRelativePath(verifyFaceUrl));
		
		boolean cloudeyePersonExist = cloudeyePersonProxy.isPersonExist(orgId, memberId);
		
		if(cloudeyePersonExist) {
			cloudeyePersonProxy.replaceVerifyImage(orgId, memberId, verifyFaceUrl);
		} else {
			Member member = memberMapper.load(orgId, memberId);
			cloudeyePersonProxy.addPerson(member);
		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateSignature(Long orgId, Long memberId, String signature) {
		memberMapper.updateSignature(orgId, memberId, signature);
		Map<String, Object> extend = new HashMap<>();
		extend.put("signature", signature);
		cloudeyePersonProxy.putPersonExtend(orgId, memberId, extend);
		return true;
	}
	
	public MemberQueryCondition memberQueryRequestToMemberQueryCondition(MemberQueryRequest request) {
		MemberQueryCondition condition = new MemberQueryCondition();
		
		condition.setOrgId(request.getOrgId());
		condition.setMemberType(request.getMemberType());
		condition.setMemberName(request.getMemberName());
		condition.setMobile(request.getMobile());
		condition.setCertNo(request.getCertNo());
		
		if(request.getPositionId() != null) {
//			List<Long> positionList = new ArrayList<>();
//			positionList.add(request.getPositionId());
//			
//			List<Long> childrenIdList = positionService.findAllChildrenIds(request.getOrgId(), request.getPositionId());
//			if(CollectionUtils.isNotEmpty(childrenIdList)) {
//				positionList.addAll(childrenIdList);
//			}
//			
//			condition.setPositionIdList(positionList);
		}
		
		condition.setDeptId(request.getDeptId());
		condition.setDeptIdList(request.getDeptIdList());
		
		List<Integer> statusList = new ArrayList<>();
		if(request.getStatus() != null) {
			statusList.add(request.getStatus());
		} else {
			statusList.add(MemberStatus.USABLE.getValue());
		}
		condition.setStatusList(statusList);
		condition.setMemberIdList(request.getMemberIdList());
		condition.setKeywords(request.getKeywords());
		
		return condition;
	}
	
	public static Member memberInfoToMember(MemberInfo memberInfo) {
		if (memberInfo == null) return null;
		Member member = new Member();
		member.setMemberId(memberInfo.getMemberId());
		member.setMemberType(memberInfo.getMemberType());
		member.setMemberName(memberInfo.getMemberName());
		member.setSex(memberInfo.getSex());
		member.setAge(memberInfo.getAge());
		member.setMobile(memberInfo.getMobile());
		member.setEmail(memberInfo.getEmail());
		member.setCertType(memberInfo.getCertType());
		member.setCertNo(memberInfo.getCertNo());
		member.setJobNum(memberInfo.getJobNum());
		member.setEnterTime(memberInfo.getEnterTime());
		member.setPost(memberInfo.getPost());
		member.setMemo(memberInfo.getMemo());
		//member.setAuthStartTime(memberInfo.getAuthStartTime());
		//member.setAuthEndTime(memberInfo.getAuthEndTime());
		member.setTpExtend(JsonUtils.toJson(memberInfo.getExtend()));
		member.setStatus(memberInfo.getStatus());
		return member;
	}
	
	public static List<Member> memberInfoListToMemberList(List<MemberInfo> memberInfoList) {
		if (memberInfoList == null) return null;
		
		List<Member> memberList = new ArrayList<>();
		for(MemberInfo memberInfo : memberInfoList) {
			memberList.add(memberInfoToMember(memberInfo));
		}
		
		return memberList;
	}
	
	public PaginationDomain<Member> paginationMemberInfoToPaginationMember(Pagination<MemberInfo> fromPagination) {
		PaginationDomain<Member> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Member>());
		if (toPagination == null)
			return null;
		toPagination.setData(memberInfoListToMemberList(fromPagination.getData()));
		return toPagination;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Member> findPaginationMember(Pagination<MemberInfo> paginationMemberInfo, MemberQueryRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		MemberQueryCondition queryCondition = memberQueryRequestToMemberQueryCondition(request);
		PaginationDomain<Member> paginationMember = paginationMemberInfoToPaginationMember(paginationMemberInfo);
		
		return this.getPagination(paginationMember, queryCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PaginationDomain getPagination(PaginationDomain pagination, MemberQueryCondition queryCondition) {
        int totalCount = (Integer) memberMapper.getTotalCountByCondition(queryCondition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	queryCondition.setStartRow(pagination.getStartRow());
        	queryCondition.setPageSize(pagination.getPageSize());
        	pagination.setData(memberMapper.findPaginationByCondition(queryCondition));
        }
        return pagination;
    }

	@Override
	public Member getMember(Long orgId, Long memberId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(memberId, "memberId must not be null");
		
		Member member = memberMapper.load(orgId, memberId);
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, String.format(MemberErrorCode.MEMBER_NOT_EXIST.getMessage(), memberId));
		return member;
	}
	
	@Override
	public Member getMemberByTpId(Long orgId, String tpUserId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(tpUserId);
		
		return memberMapper.loadByTpId(orgId, tpUserId);
		
	}

	@Override
	public List<Member> findMemberInDept(Long orgId, Long deptId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deptId, "deptId must not be null");
		
		List<Long> memberIdList = deptMemberManager.findMemberIdByDeptId(orgId, deptId);
		if(CollectionUtils.isEmpty(memberIdList)) return new ArrayList<>();
		return memberMapper.findByIds(orgId, memberIdList, null, MemberStatus.USABLE.getValue());
	}

	@Override
	public Member getMemberByNameAndMobile(Long orgId, String memberName, String mobile) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(memberName, "memberName must not be blank");
		BizAssert.notBlank(mobile, "mobile must not be blank");
		return memberMapper.loadByNameAndMobile(orgId, memberName, mobile);
	}

	@Override
	public List<String> findMemberNamesByIds(Long orgId, List<Long> memberIds) {
		
		BizAssert.notNull(orgId);
		
		if(CollectionUtils.isEmpty(memberIds)) return new ArrayList<>();
		
		return memberMapper.findMemberNamesByIds(orgId, memberIds);
	}

	@Override
	public List<Long> findNoVerifyUrlIds(Long orgId) {
		BizAssert.notNull(orgId);
		return memberMapper.findIdsWithoutVerifyUrl(orgId, MemberStatus.USABLE.getValue());
	}
	
	private void busiCheck(MemberUpdateRequest request, Member existMember){
		
		// 证件号重复校验
		if(StringUtils.isNotBlank(request.getCertNo())) {
			
			if(CertType.IDENTITY.getValue() == request.getCertType() && !IDValidator.isValid(request.getCertNo())) {
				ExceptionUtils.throwException(MemberErrorCode.IDENTITY_CARD_WRONG, String.format(MemberErrorCode.IDENTITY_CARD_WRONG.getMessage(), request.getMobile()));
			}
			
			Member member = memberMapper.loadByCertNo(request.getOrgId(), request.getCertNo());
			if(member != null) {
				if(StringUtils.isNotBlank(existMember.getCertNo()) && !(existMember.getCertNo().equals(request.getCertNo()))) {
					ExceptionUtils.throwException(MemberErrorCode.MEMBER_EXIST, String.format(MemberErrorCode.MEMBER_EXIST.getMessage(), request.getCertNo()));
				}
			}
			
		}
		
		// 手机号重复校验
		if(StringUtils.isNotBlank(request.getMobile())) {
			Member member = memberMapper.loadByMobile(request.getOrgId(), request.getMobile());
			if(member != null) {
				if(StringUtils.isNotBlank(existMember.getMobile()) && !(existMember.getMobile().equals(request.getMobile()))) {
					ExceptionUtils.throwException(MemberErrorCode.MOBILE_NUMBER_EXIST,MemberErrorCode.MOBILE_NUMBER_EXIST.getMessage());
				}
			}
		}
		
		// 邮箱重复校验
		/*if(StringUtils.isNotBlank(request.getEmail())) {
			Member member = memberMapper.getByEmail(request.getEmail(), request.getOrgId());
			if(member != null) {
				if(StringUtils.isNotBlank(existMember.getEmail()) && !(existMember.getEmail().equals(request.getEmail()))) {
					ExceptionUtils.throwException(MemberErrorCode.MEMBER_EXIST, String.format(MemberErrorCode.MEMBER_EXIST.getMessage(), request.getEmail()));
				}
			}
		}*/
	}
	
	private Rect checkFace(String url) {
		Picture picture = new NetworkFacePicture(url);
		ServiceResponse<List<DetectFace>> sr = libraryDetectService.detectFace(picture);
		if(!sr.isSuccess()) sr.pickDataThrowException();
		if(!sr.isExistData() || CollectionUtils.isEmpty(sr.getData())) ExceptionUtils.throwException(MemberErrorCode.NO_FACE, MemberErrorCode.NO_FACE.getMessage());
		if(sr.getData().size() > 1) ExceptionUtils.throwException(MemberErrorCode.MORE_THEN_ONE_FACE, MemberErrorCode.MORE_THEN_ONE_FACE.getMessage());
		return sr.getData().get(0).getRect();
	}
	
	private Member memberUpdateRequestToMember(MemberUpdateRequest request, Member existMember){
		Member member = new Member();		
		member.setMemberId(request.getMemberId());
		member.setOrgId(request.getOrgId());
		
		if(this.isVerifyFaceEdit(request.getVerifyFaceUrl(), existMember.getVerifyFaceUrl())) {
			this.checkFace(request.getVerifyFaceUrl());
			member.setVerifyFaceUrl(imageFileManager.getRelativePath(request.getVerifyFaceUrl()));
		} 
		
		if(this.isShowImageEdit(request.getShowFaceUrl(), existMember.getShowFaceUrl())) {
			member.setShowFaceUrl(imageFileManager.getRelativePath(request.getShowFaceUrl()));
		} 
		
		member.setMemberName(request.getMemberName());
		member.setNickName(request.getNickName());
		member.setSex(request.getSex());
		member.setAge(request.getAge());
		member.setMobile(request.getMobile());
		member.setEmail(request.getEmail());
		member.setOrgEmail(request.getOrgEmail());
		member.setCertType(request.getCertType());
		member.setCertNo(request.getCertNo());
		member.setJobNum(request.getJobNum());
		member.setEnterTime(request.getEnterTime());
		member.setPost(request.getPost());
		member.setSignature(request.getSignature());
		member.setBirthday(request.getBirthday());
		member.setMemo(request.getMemo());
		return member;
	}
	
	/**
	 * 判定是否修改了个性头像
	 * @param paramUrl
	 * @param existShowFaceUrl
	 * @return
	 */
	private boolean isShowImageEdit(String paramUrl, String existShowFaceUrl) {
		if(StringUtils.isBlank(paramUrl)) return false;
		if(imageFileManager.getRelativePath(paramUrl).equals(existShowFaceUrl)) return false;
		return true;
	}

	/**
	 *  * 判定是否修改了名称
	 * @param nickName
	 * @param existNickName
	 * @return
	 */

	private boolean isNiceNameEdit(String nickName, String existNickName) {
		if(StringUtils.isNotBlank(nickName)) {
			if(nickName.equals(existNickName)) {
				return false;
			} else {
				return true;
			}
		} else {
			if(StringUtils.isBlank(existNickName)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/**
	 * 判定是否修改了识别照片
	 * @param paramUrl
	 * @param existVerifyFaceUrl
	 * @return
	 */
	private boolean isVerifyFaceEdit(String paramUrl, String existVerifyFaceUrl) {
		if(StringUtils.isBlank(paramUrl)) return false;
		if(imageFileManager.getRelativePath(paramUrl).equals(existVerifyFaceUrl)) return false;
		return true;
	}
	
	/**
	 * 判定是否修改了个性签名
	 * @param signature
	 * @param existSignature
	 * @return
	 */
	private boolean isSignatureEdit(String signature, String existSignature) {
		if(StringUtils.isBlank(signature)) return false;
		if(signature.equals(existSignature)) return false;
		return true;
	}
	
	private void adjustDeptRelation(Long orgId, Long memberId, Set<Long> finalDeptIds) {
		List<Long> existDeptIds = deptMemberManager.findDeptId(orgId, memberId);
		List<Long> existDeptIds_clone = new ArrayList<>();
		existDeptIds_clone.addAll(existDeptIds);
		
		existDeptIds.removeAll(finalDeptIds); // 定位解除的关系
		for(Long deptId : existDeptIds) {
			deptMemberManager.disableRelation(orgId, memberId, deptId);
		}
		
		finalDeptIds.removeAll(existDeptIds_clone); // 定位新增的关系
		for(Long deptId : finalDeptIds) {
			DeptMember deptRelation = new DeptMember();
        	deptRelation.setDeptMemberId(this.genPrimaryKey(DeptMember.class.getName()));
        	
        	Dept dept = deptManager.getDept(orgId, deptId);
        	
        	deptRelation.setOrgId(orgId);
        	deptRelation.setDeptId(deptId);
        	deptRelation.setTpDeptId(dept.getTpId());
        	deptRelation.setMemberId(memberId);
    		deptRelation.setLeaderFlag(YesNoFlag.NO.getValue());
        	deptRelation.setStatus(YesNoFlag.YES.getValue());
			deptMemberManager.addMemberToDept(deptRelation);
		}
		
	}
	
	private void resetGroupRelation(Long orgId, Long memberId, Set<Long> deptIds) {
		
		// 先清理
		
		// 1) 清理掉成员的群组关系数据
		List<Long> myGroupIdList = groupPersonMapper.findGroupIdByMember(orgId, memberId, PersonType.MEMBER.getValue());
		groupPersonMapper.deleteByPerson(orgId, memberId, PersonType.MEMBER.getValue());
		// 2) 清理成员的群组范围数据
		groupRangeManager.deleteByRangeId(orgId, memberId, GroupRangeType.RANGE_MEMBER.getValue());
		// 3) 重置群组人员数
		groupPersonSizeManager.batchResetPersonSize(orgId, myGroupIdList, PersonType.MEMBER.getValue());
		
		// 再重设
		List<Long> deptIdList = new ArrayList<>();
		deptIdList.addAll(deptIds);
		
		Set<Long> groupIdSet = new HashSet<>();
		// 查询全员使用的群组
		List<Long> groupIdList1 = groupManager.findAllMemberUseGroupIds(orgId); 
		// 查询范围包含部门的群组
		List<Long> groupIdList2 = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(deptIdList)) {
			groupIdList2 = groupRangeManager.findGroupIdByRanges(orgId, GroupRangeType.RANGE_DEPT.getValue(), deptIdList);
		}
		groupIdSet.addAll(groupIdList1);
		groupIdSet.addAll(groupIdList2);
		
		List<Long> groupIdList = new ArrayList<>();
		groupIdList.addAll(groupIdSet);
		
		groupPersonManager.resetGroupRelation(orgId, memberId, groupIdList, PersonType.MEMBER.getValue());
	}

	@Override
	@Transactional
	public boolean updateMember(MemberUpdateRequest request) {
		
		BizAssert.notNull(request.getMemberId());
		BizAssert.notNull(request.getOrgId());
		
		Member existMember = memberMapper.load(request.getOrgId(), request.getMemberId());
		if(existMember == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, String.format(MemberErrorCode.MEMBER_NOT_EXIST.getMessage(), request.getMemberId()));
		
		this.busiCheck(request, existMember);
		
		Member member = memberUpdateRequestToMember(request, existMember);
		memberMapper.update(member);
		
		if(this.isVerifyFaceEdit(request.getVerifyFaceUrl(), existMember.getVerifyFaceUrl())) {
			cloudeyePersonProxy.addSimplePerson(request.getOrgId(), request.getMemberId(), 97, member.getMemberName());
		}
		
		if(this.isVerifyFaceEdit(request.getVerifyFaceUrl(), existMember.getVerifyFaceUrl())) {
			cloudeyePersonProxy.replaceVerifyImage(request.getOrgId(), request.getMemberId(), request.getVerifyFaceUrl());
		}
		
		boolean needChangeCEPersonInfo = false;
		if(this.isShowImageEdit(request.getShowFaceUrl(), existMember.getShowFaceUrl())) {
			needChangeCEPersonInfo = true;
		}
		
		String nickName = request.getNickName();
		if(this.isNiceNameEdit(request.getNickName(), existMember.getNickName())) {
			//needChangeCEPersonInfo = true;
		}
		
		if(needChangeCEPersonInfo) {
			nickName = StringUtils.isBlank(nickName) ? member.getMemberName() : nickName;
			cloudeyePersonProxy.changeMemberHead(request.getOrgId(), request.getMemberId(), nickName, imageFileManager.getRelativePath(request.getShowFaceUrl()));
		}
		
		if(this.isSignatureEdit(request.getSignature(), existMember.getSignature())) {
			Map<String, Object> extend = new HashMap<>();
			extend.put("signature", request.getSignature());
			cloudeyePersonProxy.putPersonExtend(request.getOrgId(), request.getMemberId(), extend);
		}
		
		if(CollectionUtils.isNotEmpty(request.getRelationDepts())) {
			Set<Long> deptSet = new HashSet<>();
			deptSet.addAll(request.getRelationDepts());
			deptSet.add(deptManager.getRootDept(request.getOrgId()).getDeptId());
			
			// 修改人员部门关系
			this.adjustDeptRelation(request.getOrgId(), request.getMemberId(), deptSet);
			// 重置人员群组关系
			this.resetGroupRelation(request.getOrgId(), request.getMemberId(), deptSet);
		}
		
		return true;
		
	}

	@Override
	@Transactional
	public boolean updateDeptRelation(Long orgId, Long memberId, List<Long> deptIdList) {
		
		Set<Long> deptSet = new HashSet<>();
		deptSet.add(deptManager.getRootDept(orgId).getDeptId());
		
		if(CollectionUtils.isNotEmpty(deptIdList)) {
			deptSet.addAll(deptIdList);
		} 
		
		// 修改人员部门关系
		this.adjustDeptRelation(orgId, memberId, deptSet);
		// 重置人员群组关系
		this.resetGroupRelation(orgId, memberId, deptSet);
		
		return true;
	}
	
	@Override
	public int countMember(Long orgId) {
		
		BizAssert.notNull(orgId);
		
		return memberMapper.count(orgId, MemberStatus.USABLE.getValue());
	}

	@Override
	public List<Member> findMemberByIds(Long orgId, List<Long> memberIds) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(memberIds)) return new ArrayList<>();
		return memberMapper.findByIds(orgId, memberIds, null, MemberStatus.USABLE.getValue());
	}

	@Override
	public List<Long> findMemberId(Long orgId) {
		BizAssert.notNull(orgId);
		return memberMapper.findIdsByOrgId(orgId, MemberStatus.USABLE.getValue());
	}

	@Override
	public int countNoVerifyPicMember(Long orgId) {
		BizAssert.notNull(orgId);
		return memberMapper.countNoVerifyPic(orgId, MemberStatus.USABLE.getValue());
	}

	@Override
	public List<String> findFromIds(Long orgId, boolean hasVerifyPic) {
		BizAssert.notNull(orgId);
		return memberMapper.findFromIds(orgId, hasVerifyPic, MemberStatus.USABLE.getValue());
	}

	@Override
	public boolean isFacePic(String verifyFaceUrl) {
		BizAssert.notBlank(verifyFaceUrl);
		Picture picture = new NetworkFacePicture(verifyFaceUrl);
		ServiceResponse<List<DetectFace>> sr = libraryDetectService.detectFace(picture);
		if(!sr.isSuccess()) sr.pickDataThrowException();
		
		if(!sr.isExistData()) ExceptionUtils.throwException(MemberErrorCode.NO_FACE, MemberErrorCode.NO_FACE.getMessage());
		
		if(sr.getData().size() == 0) ExceptionUtils.throwException(MemberErrorCode.NO_FACE, MemberErrorCode.NO_FACE.getMessage());
		
		if(sr.getData().size() > 1) ExceptionUtils.throwException(MemberErrorCode.MORE_THEN_ONE_FACE, MemberErrorCode.MORE_THEN_ONE_FACE.getMessage());
		
		return true;
	}
	
	public static String bytesToHexString(byte[] src) {  
        StringBuilder stringBuilder = new StringBuilder();  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }
	
	public static byte[] cutImage(String url,int x,int y,int w,int h) {
		InputStream is = null;
        ImageInputStream iis = null;
        ByteArrayOutputStream out = null;
        
		try {
			Picture picture = new NetworkFacePicture(url);
			is = new ByteArrayInputStream(picture.getImageData());
			iis = ImageIO.createImageInputStream(is);
			
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);   
			ImageReader reader = iterator.next();
			
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w,h);
			param.setSourceRegion(rect);
			BufferedImage outputImage = reader.read(0,param);
			
			out = new ByteArrayOutputStream();
			ImageIO.write(outputImage,"jpg",out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {  
                if (is != null) {  
                    is.close();  
                }  
                if (iis != null) {  
                    iis.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            } 
		}
      
        return out.toByteArray();
        
 }
	
	@Override
	public String cutFaceAndReturnUrl(String verifyFaceUrl) {
		BizAssert.notBlank(verifyFaceUrl);
		Picture picture = new NetworkFacePicture(verifyFaceUrl);
		ServiceResponse<List<DetectFace>> sr = libraryDetectService.detectFace(picture);
		if(!sr.isSuccess()) sr.pickDataThrowException();
		
		if(!sr.isExistData()) ExceptionUtils.throwException(MemberErrorCode.NO_FACE, MemberErrorCode.NO_FACE.getMessage());
		
		if(sr.getData().size() == 0) ExceptionUtils.throwException(MemberErrorCode.NO_FACE, MemberErrorCode.NO_FACE.getMessage());
		
		if(sr.getData().size() > 1) ExceptionUtils.throwException(MemberErrorCode.MORE_THEN_ONE_FACE, MemberErrorCode.MORE_THEN_ONE_FACE.getMessage());
		
		Rect rect = sr.getData().get(0).getRect();
		
		byte[] imageData = null;
		try {
			imageData = cutImage(verifyFaceUrl, rect.getLeft(), rect.getTop(), rect.getWidth(), rect.getHeight());
		} catch (Exception e) {
			ExceptionUtils.throwException(MemberErrorCode.FACE_CUT_ERROR, MemberErrorCode.FACE_CUT_ERROR.getMessage());
		}
		
		String url = this.storeImage(imageData, FilePathType.TYPE_MEMBERFACEIMAGE);
		return imageFileManager.getImageUrl(url);
	}

	@Override
	public void addLoginLog(Long orgId, Long memberId, Integer moduleType) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notNull(moduleType);
		
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginLogId(this.genLoginLogId());
		loginLog.setOrgId(orgId);
		loginLog.setMemberId(memberId);
		loginLog.setModuleType(moduleType);
		loginLog.setLoginTime(new Date());
		loginLogMapper.insert(loginLog);
		
	}

	@Override
	public boolean isFirstLogin(Long orgId, Long memberId, Integer moduleType) {
		
		boolean result = false;
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notNull(moduleType);
		
		int count = loginLogMapper.count(orgId, memberId, moduleType);
		result = (count == 0)? true : false;
		
		return result;
	}

	@Override
	public List<Long> findAllMemberId(Long orgId) {
		BizAssert.notNull(orgId);
		return memberMapper.findMemberId(orgId, MemberStatus.USABLE.getValue());
	}

	@Override
	public String getTpUserId(Long orgId, Long memberId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		return memberMapper.loadTpUserId(orgId, memberId);
	}

	@Override
	public List<String> findTpId(Long orgId, List<Long> memberIds) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(memberIds)) return new ArrayList<>();
		return memberMapper.findTpId(orgId, memberIds);
	}

	@Override
	public boolean activeMember(Long orgId, Long memberId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		memberMapper.updateStatus(orgId, memberId, MemberStatus.USABLE.getValue());
		return true;
	}

	@Override
	public boolean removeMember(Long orgId, Long memberId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		Member member = memberMapper.load(orgId, memberId);
		Integer removeStatus=MemberStatus.CLOSE.getValue();
		memberMapper.updateStatus(orgId, memberId, removeStatus);
		
		deptMemberManager.disableRelation(orgId, memberId);
		
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			
			if(StringUtils.isNotBlank(member.getVerifyFaceUrl())){
				boolean cloudeyePersonExist = cloudeyePersonProxy.isPersonExist(orgId, memberId);
				if(cloudeyePersonExist) {
					cloudeyePersonProxy.disablePerson(orgId, memberId);
				}
	        }
			
			ClearMemberRelationDataMsg msg = new ClearMemberRelationDataMsg();
	    	msg.setOrgId(orgId);
	    	msg.setMemberId(member.getMemberId());
	    	EventBus.publish(msg);
	    	logger.info("发出MQ消息[删除员工]:"+JsonUtils.toJson(msg));
		}
		
		return true;
	}

	@Override
	public boolean updateStatus(Long orgId, Long memberId, Integer status) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notNull(status);
		// TODO 删除禁用启用，分别操纵云眼
		return false;
	}

	@Override
	public boolean judgeProscenium(SelectUserRelationRequest request) {
		Member member = memberMapper.load(request.getOrgId(), request.getUserId());
		if(member == null || MemberStatus.USABLE.getValue() != member.getStatus()) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		Position position = positionManager.getRootPosition(request.getOrgId());
		UserRelation userRelation = userRelationMapper.loadOne(request.getOrgId(), request.getUserId(), position.getPositionId());
		if(userRelation == null || userRelation.getRelationStatus() == 1) return false;
		return (UserRelationType.FRONT.getValue() == userRelation.getRelation())?true:false;
	}
	
	private List<ProsceniumUserResponse> buildResponse(List<Member> memberList) {
		List<ProsceniumUserResponse> list = new ArrayList<>();
		if(CollectionUtils.isEmpty(memberList)) return list;
		for(Member member : memberList) {
			
			DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
			
			ProsceniumUserResponse item = new ProsceniumUserResponse();
			item.setDingUserId(extend.getDing_userid());
			item.setOrgId(member.getOrgId());
			item.setDingAvatar(extend.getDing_avatar());
			item.setDingName(extend.getDing_name());
			item.setUserId(member.getMemberId());
			list.add(item);
		}
		return list;
	}

	@Override
	public List<ProsceniumUserResponse> selectProscenium(SelectUserRelationRequest request) {
		BizAssert.notNull(request.getOrgId());
		
		Position position = positionManager.getRootPosition(request.getOrgId());
		List<Long> userIdList = userRelationMapper.findUserId(request.getOrgId(), position.getPositionId(), UserRelationType.FRONT.getValue());
		
		if(CollectionUtils.isEmpty(userIdList)) return new ArrayList<>();
		
		List<Member> memberList = memberMapper.findByIds(request.getOrgId(), userIdList, null, MemberStatus.USABLE.getValue());
		
		return this.buildResponse(memberList);
	}

	@Override
	public boolean addProscenium(ModifyUserRelationRequest request) {
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		
		Position position = positionManager.getRootPosition(request.getOrgId());
		
		/*UserRelation userRelation = userRelationMapper.loadOne(request.getOrgId(), member.getMemberId(), position.getPositionId());
		if(userRelation == null) ExceptionUtils.throwException(MemberErrorCode.USER_RELATION_NOT_EXIST, MemberErrorCode.USER_RELATION_NOT_EXIST.getMessage());
		
		userRelationMapper.updateRelation(userRelation.getOrgId(), userRelation.getUserRelationId(), UserRelationType.FRONT.getValue());*/
		
		UserRelation userRelation = new UserRelation();
		userRelation.setUserRelationId(this.genPrimaryId(UserRelation.class.getName()));
		userRelation.setOrgId(request.getOrgId());
		userRelation.setUserId(member.getMemberId());
		userRelation.setRelation(UserRelationType.FRONT.getValue());
		userRelation.setRelationStatus(0); // 这个遗留下来的状态跟其他地方正好相反，无法使用YesNoFlag枚举，纠结，先写死
		userRelation.setSubOrgId(position.getPositionId());
		userRelationMapper.insertOrUpdate(userRelation);
		
		return true;
	}

	@Override
	public boolean deleteProscenium(ModifyUserRelationRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		
		Position position = positionManager.getRootPosition(request.getOrgId());
		
		UserRelation userRelation = userRelationMapper.loadOne(request.getOrgId(), member.getMemberId(), position.getPositionId());
		if(userRelation == null) ExceptionUtils.throwException(MemberErrorCode.USER_RELATION_NOT_EXIST, MemberErrorCode.USER_RELATION_NOT_EXIST.getMessage());
		
		userRelationMapper.disableStatus(userRelation.getOrgId(), userRelation.getUserRelationId());
		
		return true;
	}

	@Override
	public int updateTpExtend(Long orgId, Long memberId, String tpExtend) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notBlank(tpExtend);
		
		return memberMapper.updateTpExtend(orgId, memberId, tpExtend);
	}

	@Override
	public List<MemberIdMapping> findIdMappingByIds(Long orgId, List<Long> memberIdList) {
		BizAssert.notNull(orgId);
		BizAssert.notEmpty(memberIdList);
		return memberMapper.findIdMappingByIds(orgId, memberIdList);
	}

	@Override
	public List<MemberIdMapping> findIdMappingByTpIds(Long orgId, List<String> tpIdList) {
		BizAssert.notNull(orgId);
		BizAssert.notEmpty(tpIdList);
		return memberMapper.findIdMappingByTpIds(orgId, tpIdList);
	}

	@Override
	public List<Long> findReceptionist(Long orgId) {
		BizAssert.notNull(orgId);
		return userRelationMapper.findReceptionist(orgId, UserRelationType.FRONT.getValue());
	}

	@Override
	public boolean deleteReceptionist(Long orgId, Long memberId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		userRelationMapper.deleteReceptionist(orgId, memberId);
		return true;
	}

	@Override
	public boolean addReception(Long orgId, Long memberId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		
		Member member = memberMapper.load(orgId, memberId);
		if(member == null) ExceptionUtils.throwException(MemberErrorCode.MEMBER_NOT_EXIST, MemberErrorCode.MEMBER_NOT_EXIST.getMessage());
		
		UserRelation userRelation = new UserRelation();
		userRelation.setOrgId(orgId);
		userRelation.setRelation(UserRelationType.FRONT.getValue());
		userRelation.setRelationStatus(0);
		Position position = positionManager.getRootPosition(orgId);
		userRelation.setSubOrgId(position.getPositionId());
		userRelation.setUserId(memberId);
		userRelation.setUserRelationId(this.genPrimaryId(UserRelation.class.getName()));
		userRelationMapper.insertOrUpdate(userRelation);
		return true;
	}

	@Override
	@Transactional
	public boolean importMemberInfo(Long orgId, List<ImportMemberModel> importList) {
		
		for(ImportMemberModel model : importList) {
			MemberAddRequest request = new MemberAddRequest();
			request.setOrgId(orgId);
			request.setMemberName(model.getMemberName());
			request.setMobile(model.getMobile());
			request.setNickName(model.getNickName());
			request.setSex(model.getSex());
			request.setBirthday(model.getBirthday());
			request.setEmail(model.getEmail());
			request.setPost(model.getPost());
			
			if(model.getDeptId() != null) {
				List<Long> relationDepts = new ArrayList<>();
				relationDepts.add(model.getDeptId());
				request.setRelationDepts(relationDepts);
			}
			
			this.addMember(request);
		}
		
		return true;
	}

	@Override
	public List<String> findAdminTpIds(Long orgId) {
		return memberMapper.findAdminTpIds(orgId, YesNoFlag.YES.getValue(), MemberStatus.USABLE.getValue());
	}

	@Override
	public boolean toggleAdminFlag(Long orgId, Long memberId, Integer adminFlag) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notNull(adminFlag);
		memberMapper.updateAdminFlag(orgId, memberId, adminFlag);
		return true;
	}

	@Override
	public boolean toggleChargeFlag(Long orgId, Long memberId, Integer chargeFlag) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notNull(chargeFlag);
		memberMapper.updateChargeFlag(orgId, memberId, chargeFlag);
		return true;
	}

	@Override
	public int getCount(Long orgId) {
		BizAssert.notNull(orgId);
		return memberMapper.count(orgId, MemberStatus.USABLE.getValue());
	}

}
