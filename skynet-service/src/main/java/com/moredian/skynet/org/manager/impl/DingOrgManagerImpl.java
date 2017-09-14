package com.moredian.skynet.org.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.common.model.msg.ConfigGroupAtcDataMsg;
import com.moredian.skynet.org.domain.Area;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.domain.Org;
import com.moredian.skynet.org.enums.GroupType;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.enums.OrgStatus;
import com.moredian.skynet.org.enums.OrgType;
import com.moredian.skynet.org.enums.TpType;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.DingOrgManager;
import com.moredian.skynet.org.manager.PositionCodeManager;
import com.moredian.skynet.org.manager.PositionManager;
import com.moredian.skynet.org.mapper.AreaMapper;
import com.moredian.skynet.org.mapper.GroupMapper;
import com.moredian.skynet.org.mapper.OrgMapper;
import com.moredian.skynet.org.request.OrgEnterpriseBind;
import com.moredian.skynet.org.request.OrgEnterpriseNotBindRequest;
import com.moredian.skynet.org.utils.PinyinUtil;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class DingOrgManagerImpl implements DingOrgManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DingOrgManagerImpl.class);
	
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private PositionManager positionManager;
	@Autowired
	private PositionCodeManager positionCodeManager;
	@SI
	private IdgeneratorService idgeneratorService;
	@Value("${rmq.switch}")
	private int rmqSwitch;
	@Autowired
	private GroupMapper groupMapper;
	
	private Long genPrimaryKey(String typeName) {
		return idgeneratorService.getNextIdByTypeName(typeName).getData();
	}
	
	private void validateArea(OrgEnterpriseNotBindRequest request) {
		Area province = null;
		if(request.getProvinceId() != null){
			province = areaMapper.loadByCode(request.getProvinceId());
			if(province == null) BizAssert.notNull(province, "不存在省份数据");
		}
		
		if(province == null) return;
		
		Area city = null;
		if(request.getCityId() != null) {
			city = areaMapper.loadByCode(request.getCityId());
			if(city == null) BizAssert.notNull(city, "不存在市数据");
			if(city.getParentCode().intValue() != request.getProvinceId().intValue()) ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, OrgErrorCode.AREA_WRONG.getMessage());
		}
		
		if(city == null) return;
		
		Area district = null;
		if(request.getDistrictId() != null) {
			district = areaMapper.loadByCode(request.getDistrictId());
			if(district == null) BizAssert.notNull(district, "不存在区数据");
			if(district.getParentCode().intValue() != request.getCityId().intValue()) ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, OrgErrorCode.AREA_WRONG.getMessage());
		}
		
		if(district == null) return;
		
		BizAssert.notBlank(request.getDetailedAddress(), "详细地址不为空");
	}
	
	private Org buildOrg(OrgEnterpriseNotBindRequest request) {
		Org org = BeanUtils.copyProperties(Org.class, request);
		org.setOrgId(this.genPrimaryKey(Org.class.getName()));
		org.setTpType(TpType.DING.getValue());
		org.setTpId("0");
		org.setOrgType(OrgType.ENTERPRISE.getValue());
		org.setStatus(OrgStatus.UNBIND.getValue());
		org.setAddress(request.getDetailedAddress());
		
		org.setOrgCode(positionCodeManager.genOrgCode(org));
		org.setMemo(org.getOrgName());
		
		return org;
	}

	@Override
	@Transactional
	public Long addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request) {
		
		BizAssert.notBlank(request.getOrgName());
		this.validateArea(request);
		
		// 1) 创建机构
		Org org = buildOrg(request);
		orgMapper.insert(org);
		
		// 2) 初始化根位置
		positionManager.addRootPosition(org.getOrgId(), org.getOrgName());
		
		// 3) 初始化群组
		this.initGroup(org.getOrgId());
		
		return org.getOrgId();
	}
	
	public void initGroup(Long orgId) {
		Group group = new Group();
		group.setGroupId(this.genPrimaryKey(Group.class.getName()));
		group.setGroupCode(String.valueOf(group.getGroupId()));
		group.setGroupType(GroupType.ALLMEMBER.getValue());
		group.setGroupName(GroupType.ALLMEMBER.getDesc());
		group.setAllMemberFlag(YesNoFlag.YES.getValue());
		group.setOrgId(orgId);
		group.setSystemDefault(YesNoFlag.YES.getValue());
		group.setMemberSize(0);
		groupMapper.insert(group);
		
		ConfigGroupAtcDataMsg noticeGroupMsg = null;
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			noticeGroupMsg = BeanUtils.copyProperties(ConfigGroupAtcDataMsg.class, group);
	        EventBus.publish(noticeGroupMsg); // MQ消息
	        logger.info("发出MQ消息[初始化机构全员组]: "+JsonUtils.toJson(noticeGroupMsg));
		}
        
		
		group = new Group();
		group.setGroupId(this.genPrimaryKey(Group.class.getName()));
		group.setGroupCode(String.valueOf(group.getGroupId()));
		group.setGroupType(GroupType.VISITOR.getValue());
		group.setGroupName(GroupType.VISITOR.getDesc());
		group.setAllMemberFlag(YesNoFlag.NO.getValue());
		group.setOrgId(orgId);
		group.setSystemDefault(YesNoFlag.YES.getValue());
		group.setMemberSize(0);
		groupMapper.insert(group);
		
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			noticeGroupMsg = BeanUtils.copyProperties(ConfigGroupAtcDataMsg.class, group);
			EventBus.publish(noticeGroupMsg); // MQ消息
			logger.info("发出MQ消息[初始化机构访客组]: "+JsonUtils.toJson(noticeGroupMsg));
		}
	}

	@Override
	public boolean bingOrgEnterpriseNotBind(OrgEnterpriseBind request) {
		
		BizAssert.notNull(request.getOrgId());
		
		orgMapper.updateStatus(request.getOrgId(), OrgStatus.USABLE.getValue());
		
		return true;
	}

}
