package com.moredian.fishnet.member.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moredian.bee.common.consts.Sex;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.common.model.msg.ClearMemberRelationDataMsg;
import com.moredian.fishnet.common.model.msg.ConfigMemberGroupDataMsg;
import com.moredian.fishnet.common.model.msg.ResetMemberGroupDataMsg;
import com.moredian.fishnet.member.domain.DeptRelation;
import com.moredian.fishnet.member.domain.Member;
import com.moredian.fishnet.member.enums.MemberErrorCode;
import com.moredian.fishnet.member.enums.MemberStatus;
import com.moredian.fishnet.member.manager.CloudeyePersonProxy;
import com.moredian.fishnet.member.manager.DeptMemberManager;
import com.moredian.fishnet.member.manager.DingUserManager;
import com.moredian.fishnet.member.mapper.MemberMapper;
import com.moredian.fishnet.member.model.DingUserExtend;
import com.moredian.fishnet.member.request.ModifyDingUserIsAdminRequest;
import com.moredian.fishnet.member.request.ModifyDingUserUnionRequest;
import com.moredian.fishnet.member.request.UserCloseRequest;
import com.moredian.fishnet.member.request.UserDingUserIdRequest;
import com.moredian.fishnet.member.request.UserSearchForSynRequest;
import com.moredian.fishnet.member.request.UserWithOutFaceRequest;
import com.moredian.fishnet.org.domain.Dept;
import com.moredian.fishnet.org.domain.Position;
import com.moredian.fishnet.org.enums.OrgErrorCode;
import com.moredian.fishnet.org.enums.TpType;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.manager.DeptManager;
import com.moredian.fishnet.org.manager.PositionManager;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class DingUserManagerImpl implements DingUserManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DingUserManagerImpl.class);
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private DeptMemberManager deptMemberManager;
	@Autowired
	private CloudeyePersonProxy cloudeyePersonProxy;
	@Autowired
	private PositionManager positionManager;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private DeptManager deptManager;
	@Value("${rmq.switch}")
	private int rmqSwitch;
	
	private Long genPrimaryKey(String typeName) {
		return idgeneratorService.getNextIdByTypeName(typeName).getData();
	}

	@Override
	@Transactional
	public boolean closeUser(UserCloseRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if (member == null) {
			ExceptionUtils.throwException(MemberErrorCode.USER_NOT_EXIST, MemberErrorCode.USER_NOT_EXIST.getMessage());
		}
		
		memberMapper.updateStatus(request.getOrgId(), member.getMemberId(), MemberStatus.CLOSE.getValue());
		
		deptMemberManager.disableRelation(request.getOrgId(), member.getMemberId());
		
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			
			if(StringUtils.isNotBlank(member.getVerifyFaceUrl())){
				boolean cloudeyePersonExist = cloudeyePersonProxy.isPersonExist(request.getOrgId(), member.getMemberId());
				if(cloudeyePersonExist) {
					cloudeyePersonProxy.disablePerson(request.getOrgId(), member.getMemberId());
				}
	        }
			
			ClearMemberRelationDataMsg msg = new ClearMemberRelationDataMsg();
	    	msg.setOrgId(request.getOrgId());
	    	msg.setMemberId(member.getMemberId());
	    	EventBus.publish(msg);
	    	logger.info("发出MQ消息[删除员工]:"+JsonUtils.toJson(msg));
		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean modifyDingUserIsAdmin(ModifyDingUserIsAdminRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		BizAssert.notNull(request.getDingIsAdmin());
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if(member == null) {
			ExceptionUtils.throwException(MemberErrorCode.USER_NOT_EXIST, MemberErrorCode.USER_NOT_EXIST.getMessage());
		}
		
		int adminFlag = request.getDingIsAdmin() ? YesNoFlag.YES.getValue(): YesNoFlag.NO.getValue();
		memberMapper.updateAdminFlag(member.getOrgId(), member.getMemberId(), adminFlag);
		
		DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
		extend.setDing_isAdmin(adminFlag);
		
		memberMapper.updateTpExtend(member.getOrgId(), member.getMemberId(), JsonUtils.toJson(extend));
		
		return true;
	}

	@Override
	public List<String> getAllDingUserId(UserDingUserIdRequest request) {
		return memberMapper.findAllTpId(request.getOrgId(), MemberStatus.USABLE.getValue());
	}

	@Override
	public Member getSynUserForDing(UserSearchForSynRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		
		return memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		
	}

	@Override
	public boolean modifyDingUserUnionId(ModifyDingUserUnionRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notBlank(request.getDingUserId());
		BizAssert.notBlank(request.getUnionId());
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if (member == null) {
			ExceptionUtils.throwException(MemberErrorCode.USER_NOT_EXIST, MemberErrorCode.USER_NOT_EXIST.getMessage());
		}
		
		DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
		extend.setDing_unionid(request.getUnionId());
		
		memberMapper.updateTpExtend(member.getOrgId(), member.getMemberId(), JsonUtils.toJson(extend));
		
		return true;
	}
	
	private Member buildMember(UserWithOutFaceRequest request) {
		Member member = new Member();
		member.setMemberId(this.genPrimaryKey(Member.class.getName()));
		member.setOrgId(request.getOrgId());
		member.setTpType(TpType.DING.getValue());
		member.setTpId(request.getDingUserId());
		member.setAccountId(0L);
		member.setShowFaceUrl(request.getDingAvatar());
		member.setVerifyFaceUrl("");
		member.setChargeFlag(booleanToInt(request.getDingIsBoss()));
		member.setAdminFlag(booleanToInt(request.getDingIsAdmin()));
		member.setMemberName(request.getDingName());
		member.setNickName(request.getDingName());
		member.setShowVerifyFlag(YesNoFlag.YES.getValue());
		member.setSex(Sex.UNKNOWN.intValue());
		member.setEmail(request.getDingEmail());
		member.setOrgEmail(request.getDingOrgEmail());
		member.setJobNum(request.getDingJobNum());
		member.setHideFlag(booleanToInt(request.getDingIsHide()));
		member.setStatus(MemberStatus.USABLE.getValue());
		
		DingUserExtend extend = new DingUserExtend();
		extend.setDing_id(request.getDingId());
		extend.setDing_orderInDepts(request.getDingOrderInDepts());
		extend.setDing_orgEmail(request.getDingOrgEmail());
		extend.setDing_email(request.getDingEmail());
		extend.setDing_position(request.getDingPosition());
		extend.setDing_department(request.getDingDepartment());
		extend.setDing_active(booleanToInt(request.getDingActive()));
		extend.setDing_avatar(request.getDingAvatar());
		extend.setDing_isAdmin(booleanToInt(request.getDingIsAdmin()));
		extend.setDing_isHide(booleanToInt(request.getDingIsHide()));
		extend.setDing_isBoss(booleanToInt(request.getDingIsBoss()));
		extend.setDing_name(request.getDingName());
		extend.setDing_isLeaderInDepts(request.getDingIsLeaderInDepts());
		extend.setDing_extattr(JsonUtils.toJson(request.getDingExtattrMap()));
		extend.setDing_job_num(request.getDingJobNum());
		extend.setDing_userid(request.getDingUserId());
		extend.setDing_unionid(request.getDingUnionId());
		member.setTpExtend(JsonUtils.toJson(extend));
		return member;
	}
	
	private Member buildMemberForUpdate(UserWithOutFaceRequest request, Member member) {
		
		// 以下用户信息是平台专属的，不存在值才让钉信息去初始上去
		if(StringUtils.isBlank(member.getShowFaceUrl())) {
			member.setShowFaceUrl(request.getDingAvatar());
		}
		if(StringUtils.isBlank(member.getNickName())) {
			member.setNickName(request.getDingName());
		}
		
		// 以下用户信息是钉维护的，可以不用判断直接要更新
		member.setChargeFlag(booleanToInt(request.getDingIsBoss()));
		member.setAdminFlag(booleanToInt(request.getDingIsAdmin()));
		member.setMemberName(request.getDingName());
		member.setShowVerifyFlag(YesNoFlag.YES.getValue());
		member.setEmail(request.getDingEmail());
		member.setOrgEmail(request.getDingOrgEmail());
		member.setJobNum(request.getDingJobNum());
		member.setHideFlag(booleanToInt(request.getDingIsHide()));
		member.setStatus(MemberStatus.USABLE.getValue());
		
		DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
		extend.setDing_id(request.getDingId());
		extend.setDing_orderInDepts(request.getDingOrderInDepts());
		extend.setDing_orgEmail(request.getDingOrgEmail());
		extend.setDing_email(request.getDingEmail());
		extend.setDing_position(request.getDingPosition());
		extend.setDing_department(request.getDingDepartment());
		extend.setDing_active(booleanToInt(request.getDingActive()));
		extend.setDing_avatar(request.getDingAvatar());
		extend.setDing_isAdmin(booleanToInt(request.getDingIsAdmin()));
		extend.setDing_isHide(booleanToInt(request.getDingIsHide()));
		extend.setDing_isBoss(booleanToInt(request.getDingIsBoss()));
		extend.setDing_name(request.getDingName());
		extend.setDing_isLeaderInDepts(request.getDingIsLeaderInDepts());
		extend.setDing_extattr(JsonUtils.toJson(request.getDingExtattrMap()));
		extend.setDing_job_num(request.getDingJobNum());
		extend.setDing_userid(request.getDingUserId());
		extend.setDing_unionid(request.getDingUnionId());
		member.setTpExtend(JsonUtils.toJson(extend));
		
		return member;
		
	}

	@Override
	@Transactional
	public Long synDingUserWithOutFace(UserWithOutFaceRequest request) {
		
		logger.info("##########synDingUserWithOutFace#############");
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notBlank(request.getDingUserId(), "dingUserId must not be blank");
		BizAssert.notNull(request.getUserStatus(), "userStatus must not be null");
		if(MemberStatus.USABLE.getValue() != request.getUserStatus()) ExceptionUtils.throwException(MemberErrorCode.PARAM_WRONG, MemberErrorCode.PARAM_WRONG.getMessage());
		if(StringUtils.isBlank(request.getDingName())) {
			request.setDingName("钉用户");
		}
		
		Position position = positionManager.getRootPosition(request.getOrgId());
		if(position == null) {
			ExceptionUtils.throwException(OrgErrorCode.ROOT_POSITION_NOT_EXIST, OrgErrorCode.ROOT_POSITION_NOT_EXIST.getMessage());
		}
		
		Member member = memberMapper.loadByTpId(request.getOrgId(), request.getDingUserId());
		if(member == null) {
			
			member = this.buildMember(request);
			memberMapper.insert(member); // 创建成员
			this.addDeptRelation(request, member); // 创建部门关系
			
			if(YesNoFlag.YES.getValue() == rmqSwitch) {
				ConfigMemberGroupDataMsg msg = new ConfigMemberGroupDataMsg();
	            msg.setOrgId(member.getOrgId());
	            msg.setMemberId(member.getMemberId());
	            msg.setMemberName(member.getMemberName());
	            msg.setDepartments(JsonUtils.fromJson(Long[].class, request.getDingDepartment()));
	            msg.setStatus(member.getStatus());
	            EventBus.publish(msg);
	           
	            logger.info("发出MQ消息[同步通讯录成员]"+ JsonUtils.toJson(msg));
			}
            
		} else {
			
			member = this.buildMemberForUpdate(request, member);
			memberMapper.update(member); // 修改成员
			this.updateDeptRelation(request, member); // 修改部门关系
			
			if(YesNoFlag.YES.getValue() == rmqSwitch) {
				ResetMemberGroupDataMsg msg = new ResetMemberGroupDataMsg();
	            msg.setOrgId(member.getOrgId());
	            msg.setMemberId(member.getMemberId());
	            msg.setMemberName(member.getMemberName());
	            msg.setDepartments(JsonUtils.fromJson(Long[].class, request.getDingDepartment()));
	            msg.setStatus(member.getStatus());
	            EventBus.publish(msg);
	           
	            logger.info("发出MQ消息[调整通讯录成员]"+ JsonUtils.toJson(msg));
			}
			
		}
		
		return member.getMemberId();
	}
	
	private boolean addDeptRelation(UserWithOutFaceRequest request, Member member) {
        if(StringUtils.isNotBlank(request.getDingDepartment())){
            Long[] dingDepts = JsonUtils.fromJson(Long[].class, request.getDingDepartment()); // 用户关联钉部门
            Map<Long,Boolean> dingLeaderMap = JSON.parseObject(request.getDingIsLeaderInDepts(), new TypeReference<Map<Long, Boolean>>() {}); // 用户管理钉部门
            if(dingDepts.length > 0){
                for(Long dingDept : dingDepts){
                	
                	Dept dept = deptManager.getDeptByTpId(member.getOrgId(), dingDept);
                	if(dept != null) {
                		DeptRelation deptRelation = new DeptRelation();
                    	deptRelation.setDeptRelationId(this.genPrimaryKey(DeptRelation.class.getName()));
                    	deptRelation.setOrgId(member.getOrgId());
                    	deptRelation.setDeptId(dept.getDeptId());
                    	deptRelation.setTpDeptId(dingDept);
                    	deptRelation.setMemberId(member.getMemberId());
                    	
                    	if(dingLeaderMap != null && dingLeaderMap.containsKey(dingDept) && dingLeaderMap.get(dingDept)) {
                    		deptRelation.setLeaderFlag(YesNoFlag.YES.getValue());
                    	} else {
                    		deptRelation.setLeaderFlag(YesNoFlag.NO.getValue());
                    	}
                    	deptRelation.setStatus(YesNoFlag.YES.getValue());
                    	
                        deptMemberManager.addMemberToDept(deptRelation);
                	}
                	
                }
            } 
        }
        return true;
    }
	
	private boolean updateDeptRelation(UserWithOutFaceRequest request, Member member) {
		
		Map<Long,Boolean> dingLeaderMap = JSON.parseObject(request.getDingIsLeaderInDepts(), new TypeReference<Map<Long, Boolean>>() {});
		
        List<Long> existDeptIdList = deptMemberManager.findDeptId(member.getOrgId(), member.getMemberId());
        List<Long> existDingDeptIdList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(existDeptIdList)) {
        	existDingDeptIdList = deptManager.findTpIdByIds(member.getOrgId(), existDeptIdList);
        }
        Long[] dingDepts = JsonUtils.fromJson(Long[].class, request.getDingDepartment());
        List<Long> finalDingDeptIdList = Arrays.asList(dingDepts);
        
        List<Long> finalDingDeptIdList_copy = new ArrayList<>();
        finalDingDeptIdList_copy.addAll(finalDingDeptIdList);
        
        finalDingDeptIdList_copy.removeAll(existDingDeptIdList); // 定位新增
        for(Long tpDeptId : finalDingDeptIdList_copy) {
        	
        	Dept dept = deptManager.getDeptByTpId(member.getOrgId(), tpDeptId);
        	
        	DeptRelation deptRelation = new DeptRelation();
        	deptRelation.setDeptRelationId(this.genPrimaryKey(DeptRelation.class.getName()));
        	deptRelation.setOrgId(member.getOrgId());
        	deptRelation.setDeptId(dept.getDeptId());
        	deptRelation.setTpDeptId(tpDeptId);
        	deptRelation.setMemberId(member.getMemberId());
        	
        	if(dingLeaderMap != null && dingLeaderMap.containsKey(tpDeptId) && dingLeaderMap.get(tpDeptId)) {
        		deptRelation.setLeaderFlag(YesNoFlag.YES.getValue());
        	} else {
        		deptRelation.setLeaderFlag(YesNoFlag.NO.getValue());
        	}
        	deptRelation.setStatus(YesNoFlag.YES.getValue());
        	
            deptMemberManager.addMemberToDept(deptRelation);
        }
        
        existDingDeptIdList.removeAll(finalDingDeptIdList); // 定位解除
        for(Long tpDeptId : existDingDeptIdList) {
        	Dept dept = deptManager.getDeptByTpId(member.getOrgId(), tpDeptId);
        	int count = deptMemberManager.findRelationCount(member.getOrgId(), member.getMemberId(), dept.getDeptId());
        	if(count > 0){
        		deptMemberManager.disableRelation(member.getOrgId(), member.getMemberId(), dept.getDeptId());
        	}
        }
        
        return true;
    }
	
	private int booleanToInt(Boolean bool) {
		int result = YesNoFlag.NO.getValue();
		if(bool!=null && bool){
			result = YesNoFlag.YES.getValue();
		}
		return result;
	}

}
