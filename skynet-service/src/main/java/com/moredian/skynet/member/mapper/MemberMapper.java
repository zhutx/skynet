package com.moredian.skynet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.member.domain.Member;
import com.moredian.skynet.member.domain.MemberQueryCondition;

@Mapper
public interface MemberMapper {
	
	int insert(Member member);
	
	int updateShowFaceUrl(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("showFaceUrl")String showFaceUrl);
	
	int updateVerifyFaceUrl(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("verifyFaceUrl")String verifyFaceUrl);
	
	int updateSignature(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("signature")String signature);
	
	List<Member> findByIds(@Param("orgId")Long orgId, @Param("memberIdList")List<Long> memberIdList, @Param("keywords")String keywords, @Param("status")Integer status);
	
	int getTotalCountByCondition(MemberQueryCondition condition);
	
	List<Object> findPaginationByCondition(MemberQueryCondition condition);
	
	Member load(@Param("orgId")Long orgId, @Param("memberId")Long memberId);
	
	Member loadByTpId(@Param("orgId")Long orgId, @Param("tpId")String tpId);
	
	List<String> findMemberNamesByIds(@Param("orgId")Long orgId, @Param("memberIdList")List<Long> memberIdList);
	
	List<Long> findIdsWithoutVerifyUrl(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	Member loadByCertNo(@Param("orgId")Long orgId, @Param("certNo")String certNo);
	
	Member loadByMobile(@Param("orgId")Long orgId, @Param("mobile")String mobile);
	
	Member loadByNameAndMobile(@Param("orgId")Long orgId, @Param("memberName")String memberName, @Param("mobile")String mobile);
	
	int update(Member member);
	
	int count(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	List<Long> findIdsByOrgId(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	int countNoVerifyPic(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	List<String> findFromIds(@Param("orgId")Long orgId, @Param("hasVerifyPic")boolean hasVerifyPic, @Param("status")Integer status);
	
	List<Long> findMemberId(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	String loadTpUserId(@Param("orgId")Long orgId, @Param("memberId")Long memberId);
	
	List<String> findTpId(@Param("orgId")Long orgId, @Param("memberIdList")List<Long> memberIdList);
	
	int updateStatus(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("status")Integer status);
	
	int updateAdminFlag(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("adminFlag")Integer adminFlag);
	
	int updateChargeFlag(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("chargeFlag")Integer chargeFlag);
	
	List<String> findAllTpId(@Param("orgId")Long orgId, @Param("status")Integer status);
	
	int updateTpExtend(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("tpExtend")String tpExtend);
	
	List<String> findAdminTpIds(@Param("orgId")Long orgId, @Param("adminFlag")Integer adminFlag, @Param("status")Integer status);
	
}
