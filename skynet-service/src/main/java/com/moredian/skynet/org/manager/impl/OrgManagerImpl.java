package com.moredian.skynet.org.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.exception.BizException;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.CloudeyeErrorCode;
import com.moredian.cloudeye.core.api.conf.huber.OHuberConfigProvider;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.auth.domain.Role;
import com.moredian.skynet.auth.manager.OperManager;
import com.moredian.skynet.auth.manager.PermManager;
import com.moredian.skynet.auth.manager.RoleManager;
import com.moredian.skynet.auth.request.OperAddRequest;
import com.moredian.skynet.auth.request.RoleAddRequest;
import com.moredian.skynet.auth.service.OperService;
import com.moredian.skynet.org.domain.Dept;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.domain.Org;
import com.moredian.skynet.org.domain.OrgBiz;
import com.moredian.skynet.org.domain.OrgQueryCondition;
import com.moredian.skynet.org.domain.Position;
import com.moredian.skynet.org.enums.BizType;
import com.moredian.skynet.org.enums.OrgBizStatus;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.enums.OrgStatus;
import com.moredian.skynet.org.enums.TpType;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.DeptManager;
import com.moredian.skynet.org.manager.OrgManager;
import com.moredian.skynet.org.manager.PositionManager;
import com.moredian.skynet.org.mapper.GroupMapper;
import com.moredian.skynet.org.mapper.OrgBizMapper;
import com.moredian.skynet.org.mapper.OrgMapper;
import com.moredian.skynet.org.mapper.OrgRelationMapper;
import com.moredian.skynet.org.model.OrgInfo;
import com.moredian.skynet.org.request.ModuleAdminConfigRequest;
import com.moredian.skynet.org.request.OrgAddRequest;
import com.moredian.skynet.org.request.OrgQueryRequest;
import com.moredian.skynet.org.request.OrgUpdateRequest;

@Service
public class OrgManagerImpl implements OrgManager {
	
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private PositionManager positionManager;
	@Autowired
	private OrgRelationMapper orgRelationMapper;
	@Autowired
	private OrgBizMapper orgBizMapper;
	@SI
	private OHuberConfigProvider oHuberConfigProvider;
	@Autowired
	private GroupMapper groupMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@SI
	private OperService operService;
	@Autowired
	private DeptManager deptManager;
	@Value("${rmq.switch}")
	private int rmqSwitch;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private OperManager operManager;
	@Autowired
	private PermManager permManager;
	
	private Long genPrimaryKey(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}

	@Override
	public List<Long> findAllOrgId() {
		return orgMapper.findAllId();
	}

	private Org requestToDomain(OrgAddRequest request) {
		Org org = BeanUtils.copyProperties(Org.class, request);
		
		Long orgId = this.genPrimaryKey(Org.class.getName());
		org.setOrgId(orgId);
		org.setTpType(TpType.SELF.getValue());
		org.setTpId(String.valueOf(orgId));
		org.setStatus(OrgStatus.USABLE.getValue());
		
		return org;
	}
	
	@Override
	@Transactional
	public Long addOrg(OrgAddRequest request) {
		
		BizAssert.notNull(request.getOrgName(), "orgName must not be null");
		BizAssert.notNull(request.getOrgType(), "orgType must not be null");
		
		Org existOrg = orgMapper.loadExist(request.getOrgType(), request.getOrgName());
		if(existOrg != null) ExceptionUtils.throwException(OrgErrorCode.ORG_EXIST, OrgErrorCode.ORG_EXIST.getMessage());
		
		Org org = this.requestToDomain(request);
		orgMapper.insert(org);
		
		positionManager.addRootPosition(org.getOrgId(), org.getOrgName());
		
		// 创建根部门
		deptManager.addDept(org.getOrgId(), org.getOrgName(), 0L);
		
		// 初始化全员组和访客组
		Group group = new Group();
		group.setGroupId(this.genPrimaryKey(Group.class.getName()));
		group.setOrgId(org.getOrgId());
		group.setGroupName("全员组");
		group.setSystemDefault(YesNoFlag.YES.getValue());
		group.setAllMemberFlag(YesNoFlag.YES.getValue());
		group.setBlackFlag(YesNoFlag.NO.getValue());
		group.setPersonSize(0);
		groupMapper.insert(group);
		
		/*ConfigGroupAtcDataMsg msg = null;
		msg = new ConfigGroupAtcDataMsg();
		msg.setOrgId(group.getOrgId());
		msg.setGroupId(group.getGroupId());
		msg.setGroupType(group.getGroupType());
		msg.setGroupCode(group.getGroupCode());
		msg.setGroupName(group.getGroupName());
		EventBus.publish(msg);*/
		
		group = new Group();
		group.setGroupId(this.genPrimaryKey(Group.class.getName()));
		group.setOrgId(org.getOrgId());
		group.setGroupName("访客组");
		group.setSystemDefault(YesNoFlag.YES.getValue());
		group.setAllMemberFlag(YesNoFlag.NO.getValue());
		group.setBlackFlag(YesNoFlag.NO.getValue());
		group.setPersonSize(0);
		groupMapper.insert(group);
		
		/*msg = new ConfigGroupAtcDataMsg();
		msg.setOrgId(group.getOrgId());
		msg.setGroupId(group.getGroupId());
		msg.setGroupType(group.getGroupType());
		msg.setGroupCode(group.getGroupCode());
		msg.setGroupName(group.getGroupName());
		EventBus.publish(msg);*/
			
		return org.getOrgId();
	}
	
	private void initOneAdmin(ModuleAdminConfigRequest request) {
		Long roleId = null;
		// 2) 创建或获取管理员角色
		Role role = roleManager.getRoleByName(request.getOrgId(), "系统管理员");
		if(role == null){
			RoleAddRequest roleAddRequest = new RoleAddRequest();
			roleAddRequest.setOrgId(request.getOrgId());
			roleAddRequest.setRoleName("系统管理员");
			roleAddRequest.setRoleDesc("系统管理员");
			List<Long> permIds = permManager.findPermIdByModuleType(request.getModuleType()); // 赋予模块所有权限
			roleAddRequest.setPermIds(permIds);
			roleId = roleManager.addRole(roleAddRequest);
		} else {
			roleId = role.getRoleId();
		}
		
		// 3) 创建账号
		OperAddRequest operAddRequest = new OperAddRequest();
		operAddRequest.setOrgId(request.getOrgId());
		operAddRequest.setModuleType(request.getModuleType());
		operAddRequest.setOperName(request.getOperName());
		operAddRequest.setAccountName(request.getAccountName());
		operAddRequest.setPassword(request.getPassword());
		operAddRequest.setMobile(request.getMobile());
		List<Long> roleIds = new ArrayList<>();
		roleIds.add(roleId);
		operAddRequest.setRoleIds(roleIds); // 赋予管理员角色
		operManager.addOper(operAddRequest);
	}
	
	@Override
	public boolean configModuleAdmin(ModuleAdminConfigRequest request) {
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getModuleType());
		BizAssert.notBlank(request.getAccountName());
		BizAssert.notBlank(request.getOperName());
		
		this.initOneAdmin(request);
		
		return true;
	}

	private void addOrEnableBiz(Long orgId, Integer bizType) {
		OrgBiz orgBiz = orgBizMapper.loadByBizType(orgId, bizType);
		if(orgBiz == null) {
			orgBiz = new OrgBiz();
			Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.org.OrgBiz").getData();
			orgBiz.setOrgBizId(id);
			orgBiz.setOrgId(orgId);
			orgBiz.setBizType(bizType);
			orgBiz.setStatus(OrgBizStatus.ENABLE.getValue());
			orgBizMapper.insert(orgBiz);
			
			if(BizType.RECOGNIZE.getValue() == bizType) {
				try {
					oHuberConfigProvider.initOHuberConfig(orgId, true);
				} catch (BizException e) {
					if(!e.getErrorContext().equalsErrorCode(CloudeyeErrorCode.OHC_REPEAT_INIT)) {
						ExceptionUtils.throwException(e.getErrorContext(), e.getMessage());
					}
				}
			}
			
		} else {
			if(orgBiz.getStatus() != OrgBizStatus.ENABLE.getValue()) {
				orgBizMapper.updateStatus(orgId, bizType, OrgBizStatus.ENABLE.getValue());
			}
		}
		
	}
	
	@Transactional
	@Override
	public boolean enableBiz(Long orgId, Integer bizType) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(bizType, "bizType must not be null");
		
		this.addOrEnableBiz(orgId, bizType);
		
		return true;
	}

	@Transactional
	@Override
	public boolean enableBiz(Long orgId, List<Integer> bizTypes) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notEmpty(bizTypes);
		
		for(int bizType : bizTypes) {
			this.enableBiz(orgId, bizType);
		}
		
		return true;
	}

	@Override
	public boolean disableBiz(Long orgId, Integer bizType) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(bizType, "bizType must not be null");
		
		if(BizType.RECOGNIZE.getValue() == bizType) return true;
		
		int result = orgBizMapper.updateStatus(orgId, bizType, OrgBizStatus.DISABLE.getValue());
		
		return result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean disableBiz(Long orgId, List<Integer> bizTypes) {
		BizAssert.notEmpty(bizTypes);
		for(int bizType : bizTypes) {
			this.disableBiz(orgId, bizType);
		}
		return true;
	}

	private Org requestToDomain(OrgUpdateRequest request) {
		return BeanUtils.copyProperties(Org.class, request);
	}

	@Override
	@Transactional
	public boolean updateOrg(OrgUpdateRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		Org org = requestToDomain(request);
		orgMapper.update(org);
		
		Position rootPosition = positionManager.getRootPosition(request.getOrgId());
		
		if(request.getOrgName() != null) { // 修改机构名同步修改位置里的相关描述
			Dept rootDept = deptManager.getRootDept(request.getOrgId());
			deptManager.updateDeptName(request.getOrgId(), rootDept.getDeptId(), request.getOrgName());
			positionManager.updatePosition(rootPosition.getOrgId(), rootPosition.getPositionId(), org.getOrgName());
		}
		
		return true;
	}
	
	private boolean isAllBizStop(Long orgId) {
		int count = orgBizMapper.countEnable(orgId, OrgBizStatus.ENABLE.getValue());
		if(count > 0) return false;
		return true;
	}

	@Override
	public boolean deleteOrg(Long orgId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		
		Org org = orgMapper.load(orgId);
		if(OrgStatus.UNBIND.getValue() != org.getStatus()) ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_STOP, OrgErrorCode.ORG_NOT_STOP.getMessage());
		
		if(!this.isAllBizStop(orgId)) ExceptionUtils.throwException(OrgErrorCode.SOME_BIZ_NOT_STOP, OrgErrorCode.SOME_BIZ_NOT_STOP.getMessage());
		
		int result = orgMapper.updateStatus(orgId, OrgStatus.DELETE.getValue());
		return result > 0 ? true : false;
	}

	@Override
	public boolean disableOrg(Long orgId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		int result = orgMapper.updateStatus(orgId, OrgStatus.UNBIND.getValue());
		return result > 0 ? true : false;
	}

	@Override
	public boolean enableOrg(Long orgId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		int result = orgMapper.updateStatus(orgId, OrgStatus.USABLE.getValue());
		return result > 0 ? true : false;
	}

	@Override
	public Org getOrgById(Long orgId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		return orgMapper.load(orgId);
	}
	
	public static OrgQueryCondition orgQueryRequestToOrgQueryCondition(OrgQueryRequest request) {
		OrgQueryCondition condition = new OrgQueryCondition();
		
		condition.setOrgType(request.getOrgType());
		condition.setOrgName(request.getOrgName());
		condition.setProvinceId(request.getProvinceId());
		condition.setCityId(request.getCityId());
		condition.setDistrictId(request.getDistrictId());
		condition.setStatus(request.getStatus());
		
		return condition;
	}
	
	public static Org orgInfoToOrg(OrgInfo orgInfo) {
		if (orgInfo == null) return null;
		Org org = new Org();
		org.setOrgId(orgInfo.getOrgId());
		org.setOrgName(orgInfo.getOrgName());
		org.setProvinceId(orgInfo.getProvinceId());
		org.setCityId(orgInfo.getCityId());
		org.setDistrictId(orgInfo.getDistrictId());
		org.setOrgType(orgInfo.getOrgType());
		org.setContact(orgInfo.getContact());
		org.setPhone(orgInfo.getPhone());
		org.setAddress(orgInfo.getAddress());
		org.setMemo(orgInfo.getMemo());
		org.setLon(orgInfo.getLon());
		org.setLat(orgInfo.getLat());
		org.setStatus(orgInfo.getStatus());
		org.setGmtCreate(orgInfo.getGmtCreate());
		org.setGmtModify(orgInfo.getGmtModify());
		return org;
	}
	
	public static List<Org> orgInfoListToOrgList(List<OrgInfo> orgInfoList) {
		if (orgInfoList == null) return null;
		
		List<Org> orgList = new ArrayList<>();
		for(OrgInfo orgInfo : orgInfoList) {
			orgList.add(orgInfoToOrg(orgInfo));
		}
		
		return orgList;
	}
	
	public static PaginationDomain<Org> paginationOrgInfoToPaginationOrg(Pagination<OrgInfo> fromPagination) {
		PaginationDomain<Org> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Org>());
		if (toPagination == null)
			return null;
		toPagination.setData(orgInfoListToOrgList(fromPagination.getData()));
		return toPagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Org> findPaginationOrg(OrgQueryRequest request, Pagination<OrgInfo> pagination) {
		
		OrgQueryCondition queryCondition = orgQueryRequestToOrgQueryCondition(request);
		PaginationDomain<Org> orgPagination = paginationOrgInfoToPaginationOrg(pagination);
		
		return this.getPagination(orgPagination, queryCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, OrgQueryCondition condition) {
        int totalCount = (Integer) orgMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(orgMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

	@Override
	public List<Org> findDownstream(Long orgId, Integer relationType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(relationType, "relationType must not be null");
		
		List<Long> orgIdList = orgRelationMapper.findChildrenIds(orgId, relationType);
		if(CollectionUtils.isEmpty(orgIdList)) return new ArrayList<>();
		return orgMapper.findChildren(orgIdList);
	}

	@Override
	public Org getUpstream(Long orgId, Integer relationType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(relationType, "relationType must not be null");
		
		Long parentOrgId = orgRelationMapper.getParentId(orgId, relationType);
		if(parentOrgId == null) return null;
		
		return orgMapper.load(parentOrgId);
	}

	@Override
	public boolean isBizEnable(Long orgId, Integer bizType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(bizType, "bizType must not be null");
		OrgBiz orgBiz = orgBizMapper.loadByBizType(orgId, bizType);
		if(orgBiz != null && orgBiz.getStatus() == OrgBizStatus.ENABLE.getValue()) return true;
		return false;
	}

	@Override
	public Org getOrgByName(String orgName) {
		BizAssert.notBlank(orgName);
		return orgMapper.loadByName(orgName);
	}

	@Override
	public Org getOneOrg(Integer orgType, Integer orgLevel, Integer areaCode) {
		BizAssert.notNull(orgType);
		BizAssert.notNull(orgLevel);
		BizAssert.notNull(areaCode);
		return orgMapper.getOneOrg(orgType, orgLevel, areaCode);
	}

}
