package com.moredian.skynet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.member.model.DeptMemberInfo;
import com.moredian.skynet.member.model.MemberInfo;
import com.moredian.skynet.member.request.GroupMemberQueryRequest;
import com.moredian.skynet.member.request.ImportMemberModel;
import com.moredian.skynet.member.request.MemberAddRequest;
import com.moredian.skynet.member.request.MemberQueryRequest;
import com.moredian.skynet.member.request.MemberUpdateRequest;

public interface MemberService {
	
	ServiceResponse<Long> addMember(MemberAddRequest request);
	
	ServiceResponse<Boolean> batchAddMember(List<MemberAddRequest> memberList);
	
	ServiceResponse<Boolean> modifyShowImage(Long orgId, Long memberId, String headUrl);
	
	ServiceResponse<Boolean> modifyVerifyImage(Long orgId, Long memberId, String verifyFaceUrl);
	
	ServiceResponse<Boolean> updateSignature(Long orgId, Long memberId, String signature);
	
	Pagination<MemberInfo> findPaginationMemberByGroup(Pagination<MemberInfo> pagination, GroupMemberQueryRequest request);
	
	MemberInfo getMemberInfo(Long orgId, Long memberId);
	
	MemberInfo getMemberByTpId(Long orgId, String tpUserId);
	
	String getTpUserId(Long orgId, Long memberId);
	
	List<DeptMemberInfo> findMemberInDept(Long orgId, Long deptId);
	
	List<String> findMemberNamesByIds(Long orgId, List<Long> memberIds);
	
	Pagination<MemberInfo> findPaginationMember(Pagination<MemberInfo> pagination, MemberQueryRequest request);
	
	ServiceResponse<Boolean> updateMember(MemberUpdateRequest request);
	
	ServiceResponse<Boolean> updateDeptRelation(Long orgId, Long memberId, List<Long> deptIdList);
	
	int countMember(Long orgId);
	
	List<MemberInfo> findMemberByIds(Long orgId, List<Long> memberIds);
	
	int countNoVerifyPicMember(Long orgId);
	
	List<String> findFromIds(Long orgId, boolean hasVerifyPic);
	
	boolean isFacePic(String verifyFaceUrl);
	
	String cutFaceAndReturnUrl(String verifyFaceUrl);
	
	ServiceResponse<Boolean> addLoginLog(Long orgId, Long memberId, Integer moduleType);
	
    boolean isFirstLogin(Long orgId, Long memberId, Integer moduleType);
    
    List<Long> findAllMemberId(Long orgId);
    
	ServiceResponse<Boolean>  removeMember(Long orgId,Long memberId);
	
	ServiceResponse<Boolean>  updateStatus(Long orgId,Long memberId,Integer status);
	
	MemberInfo getMemberByNameAndMobile(Long orgId, String memberName, String mobile);
	
	ServiceResponse<Boolean> importMemberInfo(Long orgId, List<ImportMemberModel> importList);
	
	ServiceResponse<Boolean> toggleAdminFlag(Long orgId,Long memberId, Integer adminFlag);
	
	ServiceResponse<Boolean> toggleChargeFlag(Long orgId,Long memberId, Integer adminFlag);
    
    /**
	 * 查询前台人员id
	 * @param orgId
	 * @return
	 */
	ServiceResponse<List<Long>> findReceptionist(Long orgId);
	
	/**
	 * 删除前台人员
	 * @param orgId
	 * @param memberId
	 * @return
	 */
	ServiceResponse<Boolean> deleteReceptionist(Long orgId, Long memberId);
	
	/**
	 * 添加前台人员
	 * @param orgId
	 * @param memberId
	 * @return
	 */
	ServiceResponse<Boolean> addReception(Long orgId, Long memberId);
	
	/**
	 * 查询管理员的第三方id
	 * @param orgId
	 * @return
	 */
	List<String> findAdminTpIds(Long orgId);
    
}
