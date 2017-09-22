package com.moredian.skynet.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.enums.PersonType;
import com.moredian.skynet.member.manager.GroupPersonManager;
import com.moredian.skynet.member.manager.MemberManager;
import com.moredian.skynet.member.model.DeptMemberInfo;
import com.moredian.skynet.member.model.MemberInfo;
import com.moredian.skynet.member.request.GroupMemberQueryRequest;
import com.moredian.skynet.member.request.ImportMemberModel;
import com.moredian.skynet.member.request.MemberAddRequest;
import com.moredian.skynet.member.request.MemberQueryRequest;
import com.moredian.skynet.member.request.MemberUpdateRequest;
import com.moredian.skynet.member.service.MemberService;
import com.moredian.skynet.org.manager.DeptManager;

@SI
public class MemberServiceImpl implements MemberService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private GroupPersonManager groupPersonManager;
	@Autowired
	private DeptManager deptManager;
	
	@Override
	public ServiceResponse<Long> addMember(MemberAddRequest request) {
		Member member = memberManager.addMember(request);
		return new ServiceResponse<Long>(true, null, member.getMemberId());
	}

	@Override
	public MemberInfo getMemberInfo(Long orgId, Long memberId) {
		Member member = memberManager.getMember(orgId, memberId);
		return this.memberToMemberInfo(member);
	}

	@Override
	public MemberInfo getMemberByTpId(Long orgId, String tpUserId) {
		Member member = memberManager.getMemberByTpId(orgId, tpUserId);
		return this.memberToMemberInfo(member);
	}

	@Override
	public String getTpUserId(Long orgId, Long memberId) {
		return memberManager.getTpUserId(orgId, memberId);
	}

	@Override
	public ServiceResponse<Boolean> modifyShowImage(Long orgId, Long memberId, String headUrl) {
		boolean result = memberManager.modifyShowImage(orgId, memberId, headUrl);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> modifyVerifyImage(Long orgId, Long memberId, String verifyFaceUrl) {
		boolean result = memberManager.modifyVerifyImage(orgId, memberId, verifyFaceUrl);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateSignature(Long orgId, Long memberId, String signature) {
		boolean result = memberManager.updateSignature(orgId, memberId, signature);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	private MemberInfo memberToMemberInfo(Member member) {
		if(member == null) return null;
		return BeanUtils.copyProperties(MemberInfo.class, member);
	}
	
	private List<MemberInfo> memberListToMemberInfoList(List<Member> members) {
		List<MemberInfo> memberInfoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(members)) return memberInfoList;
		
		for(Member member : members) {
			memberInfoList.add(this.memberToMemberInfo(member));
		}
		return memberInfoList;
	}
	
	private Pagination<MemberInfo> paginationMemberToPaginationMemberInfo(PaginationDomain<Member> fromPagination) {
		Pagination<MemberInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<MemberInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(memberListToMemberInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<MemberInfo> findPaginationMemberByGroup(Pagination<MemberInfo> pagination, GroupMemberQueryRequest request) {
		List<Long> memberIds = groupPersonManager.findPersonIdByGroup(request.getOrgId(), request.getGroupId(), PersonType.MEMBER.getValue());
		if(CollectionUtils.isEmpty(memberIds)) return new Pagination<>();
		
		MemberQueryRequest req = new MemberQueryRequest();
		req.setOrgId(request.getOrgId());
		req.setMemberIdList(memberIds);
		req.setKeywords(request.getKeywords());
		
		PaginationDomain<Member> paginationMember = memberManager.findPaginationMember(pagination, req);
		
		return paginationMemberToPaginationMemberInfo(paginationMember);
	}
	
	private DeptMemberInfo memberToDeptMemberInfo(Member member){
		DeptMemberInfo deptMemberInfo = new DeptMemberInfo();		
		deptMemberInfo.setMemberId(member.getMemberId());
		deptMemberInfo.setVerifyFaceUrl(member.getVerifyFaceUrl());
		deptMemberInfo.setShowFaceUrl(member.getShowFaceUrl());
		deptMemberInfo.setMemberName(member.getMemberName());
		deptMemberInfo.setNickName(member.getNickName());
		deptMemberInfo.setPost(member.getPost());
		deptMemberInfo.setAdminFlag(member.getAdminFlag());
		deptMemberInfo.setChargeFlag(member.getChargeFlag());
		return deptMemberInfo;
	}
	
	public List<DeptMemberInfo> memberListToDeptMemberInfoList(List<Member> memberList) {
		if (memberList == null) return null;
		
		List<DeptMemberInfo> deptMemberInfoList = new ArrayList<>();
		for(Member member : memberList) {
			deptMemberInfoList.add(this.memberToDeptMemberInfo(member));
		}
		
		return deptMemberInfoList;
	}
	
	@Override
	public List<DeptMemberInfo> findMemberInDept(Long orgId, Long deptId) {
		List<Member> memberList = memberManager.findMemberInDept(orgId, deptId);
		return this.memberListToDeptMemberInfoList(memberList);
	}

	@Override
	public List<String> findMemberNamesByIds(Long orgId, List<Long> memberIds) {
		return memberManager.findMemberNamesByIds(orgId, memberIds);
	}

	@Override
	public ServiceResponse<Boolean> toggleAdminFlag(Long orgId, Long memberId, Integer adminFlag) {
		boolean result = memberManager.toggleAdminFlag(orgId, memberId, adminFlag);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> toggleChargeFlag(Long orgId, Long memberId, Integer chargeFlag) {
		boolean result = memberManager.toggleChargeFlag(orgId, memberId, chargeFlag);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public Pagination<MemberInfo> findPaginationMember(Pagination<MemberInfo> pagination, MemberQueryRequest request) {
		
		if(request.getKeywords() != null) { // 搜索关键字可能会是部门
			List<Long> deptIdList = deptManager.findDeptIdByDeptName(request.getOrgId(), request.getKeywords());
			if(CollectionUtils.isNotEmpty(deptIdList)) {
				request.setDeptIdList(deptIdList);
				request.setKeywords(null);
			} 
		}
		
		PaginationDomain<Member> paginationMember = memberManager.findPaginationMember(pagination, request);
		
		return paginationMemberToPaginationMemberInfo(paginationMember);
	}

	@Override
	public ServiceResponse<Boolean> updateMember(MemberUpdateRequest request) {
		boolean result = memberManager.updateMember(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateDeptRelation(Long orgId, Long memberId, List<Long> deptIdList) {
		boolean result = memberManager.updateDeptRelation(orgId, memberId, deptIdList);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public int countMember(Long orgId) {
		return memberManager.countMember(orgId);
	}

	@Override
	public List<MemberInfo> findMemberByIds(Long orgId, List<Long> memberIds) {
		List<Member> memberList = memberManager.findMemberByIds(orgId, memberIds);
		return this.memberListToMemberInfoList(memberList);
	}

	@Override
	public int countNoVerifyPicMember(Long orgId) {
		return memberManager.countNoVerifyPicMember(orgId);
	}

	@Override
	public List<String> findFromIds(Long orgId, boolean hasVerifyPic) {
		return memberManager.findFromIds(orgId, hasVerifyPic);
	}

	@Override
	public boolean isFacePic(String verifyFaceUrl) {
		return memberManager.isFacePic(verifyFaceUrl);
	}

	@Override
	public String cutFaceAndReturnUrl(String verifyFaceUrl) {
		return memberManager.cutFaceAndReturnUrl(verifyFaceUrl);
	}

	@Override
	public ServiceResponse<Boolean> addLoginLog(Long orgId, Long memberId, Integer moduleType) {
		memberManager.addLoginLog(orgId, memberId, moduleType);
		return new ServiceResponse<Boolean>(true, null, true);
	}

	@Override
	public boolean isFirstLogin(Long orgId, Long memberId, Integer moduleType) {
		return memberManager.isFirstLogin(orgId, memberId, moduleType);
	}

	@Override
	public List<Long> findAllMemberId(Long orgId) {
		return memberManager.findAllMemberId(orgId);
	}

	@Override
	public ServiceResponse<Boolean> removeMember(Long orgId, Long memberId) {
		memberManager.removeMember(orgId, memberId);
		return new ServiceResponse<Boolean>(true, null, true);
	}

	@Override
	public ServiceResponse<Boolean> updateStatus(Long orgId, Long memberId, Integer status) {
		memberManager.updateStatus(orgId, memberId, status);
		return new ServiceResponse<Boolean>(true, null, true);
	}

	@Override
	public MemberInfo getMemberByNameAndMobile(Long orgId, String memberName, String mobile) {
		Member member = memberManager.getMemberByNameAndMobile(orgId, memberName, mobile);
		return this.memberToMemberInfo(member);
	}

	@Override
	public ServiceResponse<List<Long>> findReceptionist(Long orgId) {
		logger.info("##########findReceptionist#############");
		List<Long> memberIdList = memberManager.findReceptionist(orgId);
		return new ServiceResponse<List<Long>>(true, null, memberIdList);
	}

	@Override
	public ServiceResponse<Boolean> deleteReceptionist(Long orgId, Long memberId) {
		logger.info("##########deleteReceptionist#############");
		boolean result = memberManager.deleteReceptionist(orgId, memberId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> addReception(Long orgId, Long memberId) {
		logger.info("##########addReception#############");
		boolean result = memberManager.addReception(orgId, memberId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> importMemberInfo(Long orgId, List<ImportMemberModel> importList) {
		boolean result = memberManager.importMemberInfo(orgId, importList);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public List<String> findAdminTpIds(Long orgId) {
		return memberManager.findAdminTpIds(orgId);
	}

}
