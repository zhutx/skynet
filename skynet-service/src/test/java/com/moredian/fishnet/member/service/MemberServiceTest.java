package com.moredian.fishnet.member.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.moredian.bee.common.exception.BizException;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.FishnetCoreApplication;
import com.moredian.fishnet.member.enums.MemberErrorCode;
import com.moredian.fishnet.member.model.DeptMemberInfo;
import com.moredian.fishnet.member.model.MemberInfo;
import com.moredian.fishnet.member.request.GroupMemberQueryRequest;
import com.moredian.fishnet.member.request.MemberQueryRequest;
import com.moredian.fishnet.member.request.MemberUpdateRequest;
import com.moredian.fishnet.org.enums.ModuleType;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.GroupService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FishnetCoreApplication.class)
@Transactional
public class MemberServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(MemberServiceTest.class);
	
	@SI
	private GroupService groupService;
	@Autowired
	private MemberService memberService;
	
	private Long orgId = 1569091801116049408L;
	private Long adminMemberId = 1569092153303367685L;
	private Integer moduleType = ModuleType.GATE_H5.getValue();
	
	@Test
	public void testFindPaginationMemberByGroup() {
		
		Pagination<MemberInfo> pagination = new Pagination<>();
		pagination.setPageNo(1);
		pagination.setPageSize(20);
		GroupMemberQueryRequest request = new GroupMemberQueryRequest();
		request.setOrgId(orgId);
		request.setGroupId(this.fetchQYGroup().getGroupId());
		
		Pagination<MemberInfo> paginationMemberInfo = memberService.findPaginationMemberByGroup(pagination, request);
		Assert.notEmpty(paginationMemberInfo.getData());
		logger.info(JsonUtils.toJson(paginationMemberInfo.getData()));
		
	}
	
	@Test
	public void testFindMemberNamesByIds() {
		
		List<Long> memberIds = this.fetchMemberIds();
		List<String> memberNameList = memberService.findMemberNamesByIds(orgId, memberIds);
		Assert.notEmpty(memberNameList);
		logger.info(JsonUtils.toJson(memberNameList));
		
	}
	
	@Test
	public void testFindMemberInDept() {
		
		Long deptId = 1L;
		List<DeptMemberInfo> deptMemberInfoList = memberService.findMemberInDept(orgId, deptId);
		Assert.notEmpty(deptMemberInfoList);
		logger.info(JsonUtils.toJson(deptMemberInfoList));
	}
	
	@Test
	@Rollback(false)
	public void testModifyShowImage() {
		
		Long memberId = adminMemberId;
		String headUrl = "http://img.dev.moredian.com:8000/userHeadImage/2017/6/3/14/cc21617f694149c78ca00d409c6abc61.jpg";
		ServiceResponse<Boolean> sr = memberService.modifyShowImage(orgId, memberId, headUrl);
		Assert.isTrue(sr.isSuccess());
		
	}
	
	@Test
	@Rollback(false)
	public void testUpdateSignature() {
		
		Long memberId = adminMemberId;
		String signature = "全力推进一带一路,实现我们中华民族的伟大复兴";
		ServiceResponse<Boolean> sr = memberService.updateSignature(orgId, memberId, signature);
		Assert.isTrue(sr.isSuccess());
		
	}
	
	@Test
	public void testFindPaginationMember() {
		
		Pagination<MemberInfo> pagination = new Pagination<>();
		pagination.setPageNo(1);
		pagination.setPageSize(20);
		MemberQueryRequest request = new MemberQueryRequest();
		request.setOrgId(orgId);
		
		Pagination<MemberInfo> paginationMemberInfo = memberService.findPaginationMember(pagination, request);
		Assert.notEmpty(paginationMemberInfo.getData());
		
	}
	
	@Test
	public void testGetMemberInfo() {
		
		Long memberId = adminMemberId;
		MemberInfo memberInfo = memberService.getMemberInfo(orgId, memberId);
		Assert.notNull(memberInfo);
		logger.info(JsonUtils.toJson(memberInfo));
		
	}
	
	@Test
	@Rollback(false)
	public void testUpdateMember() {
		
		MemberUpdateRequest request = new MemberUpdateRequest();
		
		MemberInfo member = this.fetchOneMember();
		request.setAge(member.getAge());
		request.setBirthday(member.getBirthday());
		request.setCertNo(member.getCertNo());
		request.setCertType(member.getCertType());
		request.setEmail(member.getEmail());
		request.setEnterTime(member.getEnterTime());
		request.setJobNum(member.getJobNum());
		request.setMemberId(member.getMemberId());
		request.setMemberName(member.getMemberName());
		request.setMemo(member.getMemo());
		request.setMobile(member.getMobile());
		request.setNickName(member.getNickName());
		request.setOrgEmail(member.getOrgEmail());
		request.setOrgId(member.getOrgId());
		request.setPost(member.getPost());
		request.setSex(member.getSex());
		request.setShowFaceUrl("http://img.dev.moredian.com:8000/userHeadImage/2017/6/3/14/cc21617f694149c78ca00d409c6abc61.jpg");
		request.setSignature(member.getSignature());
		request.setVerifyFaceUrl("http://img.dev.moredian.com:8000/memberFaceImage/2017/3/27/18/82cb854e0a5a46ba8da4a0705ec64075.jpg");
		ServiceResponse<Boolean> sr = memberService.updateMember(request);
		
		Assert.isTrue(sr.isSuccess());
		
	}
	
	@Test
	@Rollback(false)
	public void testModifyVerifyImage() {
		
		Long memberId = adminMemberId;
		String verifyFaceUrl = "http://img.dev.moredian.com:8000/memberFaceImage/2017/3/27/18/82cb854e0a5a46ba8da4a0705ec64075.jpg";
		ServiceResponse<Boolean> sr = memberService.modifyVerifyImage(orgId, memberId, verifyFaceUrl);
		Assert.isTrue(sr.isSuccess());
		
	}
	
	@Test
	public void testGetMemberByTpId() {
		
		String tpUserId = this.fetchOneTpUserId();
		MemberInfo memberInfo = memberService.getMemberByTpId(orgId, tpUserId);
		Assert.notNull(memberInfo);
		logger.info(JsonUtils.toJson(memberInfo));
		
	}
	
	@Test
	public void testCountNoVerifyPicMember() {
		
		int count = memberService.countNoVerifyPicMember(orgId);
		logger.info(String.valueOf(count));
		
	}
	
	@Test
	public void testGetFindFromIds() {
		
		boolean hasVerifyPic = false;
		List<String> tpIdList = memberService.findFromIds(orgId, hasVerifyPic);
		logger.info(JsonUtils.toJson(tpIdList));
		
	}
	
	@Test
	public void testIsFacePic() {
		
		String verifyFaceUrl = "http://img.dev.moredian.com:8000/memberFaceImage/2017/3/27/18/82cb854e0a5a46ba8da4a0705ec64075.jpg";
		boolean flag = memberService.isFacePic(verifyFaceUrl);
		Assert.isTrue(flag);
		
	}
	
	@Test
	public void testIsNotFacePic() {
		
		String verifyFaceUrl = "http://static.dingtalk.com/media/lADO04ro5s0CFs0C6g_746_534.jpg";
		boolean flag = false;
		try {
			flag = memberService.isFacePic(verifyFaceUrl);
		} catch (BizException e) {
			Assert.isTrue(e.getErrorContext().equalsErrorCode(MemberErrorCode.NO_FACE));
		}
		Assert.isTrue(!flag);
	}
	
	@Test
	public void testAddLoginLog() {
		
		Long memberId = adminMemberId;
		ServiceResponse<Boolean> sr = memberService.addLoginLog(orgId, memberId, moduleType);
		Assert.isTrue(sr.isSuccess());
		
	}
	
	@Test
	public void testIsFirstLogin() {
		
		Long memberId = adminMemberId;
		boolean flag = memberService.isFirstLogin(orgId, memberId, moduleType);
		Assert.isTrue(!flag);
		
	}
	
	/**
	 * ----------------------------Private Helper Method-----------------------------------
	 */
	private GroupInfo fetchQYGroup() {
		return groupService.getQYGroup(orgId);
	}
	
	private List<Long> fetchMemberIds() {
		return memberService.findAllMemberId(orgId);
	}
	
	private MemberInfo fetchOneMember() {
		return memberService.getMemberInfo(orgId, adminMemberId);
	}
	
	private String fetchOneTpUserId() {
		return memberService.getTpUserId(orgId, adminMemberId);
	}
	
	
}
