package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.model.MemberInfo;
import com.moredian.skynet.member.request.ImportMemberModel;
import com.moredian.skynet.member.request.MemberAddRequest;
import com.moredian.skynet.member.request.MemberQueryRequest;
import com.moredian.skynet.member.request.MemberUpdateRequest;

public interface MemberManager {
	
	Member addMember(MemberAddRequest request);
	
	boolean modifyShowImage(Long orgId, Long memberId, String headUrl);
	
	boolean modifyVerifyImage(Long orgId, Long memberId, String verifyFaceUrl);
	
	boolean updateSignature(Long orgId, Long memberId, String signature);
	
	PaginationDomain<Member> findPaginationMember(Pagination<MemberInfo> pagination, MemberQueryRequest request);
	
	Member getMember(Long orgId, Long memberId);
	
	Member getMemberByTpId(Long orgId, String tpUserId);
	
	List<Member> findMemberInDept(Long orgId, Long deptId);
	
	List<String> findMemberNamesByIds(Long orgId, List<Long> memberIds);
	
	List<Long> findNoVerifyUrlIds(Long orgId);
	
	boolean updateMember(MemberUpdateRequest request);
	
	boolean updateDeptRelation(Long orgId, Long memberId, List<Long> deptIdList);
	
	int countMember(Long orgId);
	
	List<Member> findMemberByIds(Long orgId, List<Long> memberIds);
	
	List<Long> findMemberId(Long orgId);
	
	int countNoVerifyPicMember(Long orgId);
	
	List<String> findFromIds(Long orgId, boolean hasVerifyPic);
	
	boolean isFacePic(String verifyFaceUrl);
	
	String cutFaceAndReturnUrl(String verifyFaceUrl);
	
	void addLoginLog(Long orgId, Long memberId, Integer moduleType);
	
	boolean isFirstLogin(Long orgId, Long memberId, Integer moduleType);
	
	List<Long> findAllMemberId(Long orgId);
	
	String getTpUserId(Long orgId, Long memberId);
	
	List<String> findTpId(Long orgId, List<Long> memberIds);
	
	boolean activeMember(Long orgId, Long memberId);

	Member getMemberByNameAndMobile(Long orgId, String memberName, String mobile);

	boolean removeMember(Long orgId,Long memberId);
	
	boolean updateStatus(Long orgId, Long memberId, Integer status);
	
	int updateTpExtend(Long orgId, Long memberId, String tpExtend);
	
	List<Long> findReceptionist(Long orgId);
	
	boolean deleteReceptionist(Long orgId, Long memberId);
	
	boolean addReception(Long orgId, Long memberId);
	
	boolean importMemberInfo(Long orgId, List<ImportMemberModel> importList);
	
	List<String> findAdminTpIds(Long orgId);
	
	boolean toggleAdminFlag(Long orgId, Long memberId, Integer adminFlag);
	
	boolean toggleChargeFlag(Long orgId, Long memberId, Integer chargeFlag);
	
	int getCount(Long orgId);

}
