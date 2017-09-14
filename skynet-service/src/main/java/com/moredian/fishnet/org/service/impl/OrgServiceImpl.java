package com.moredian.fishnet.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.domain.Org;
import com.moredian.fishnet.org.domain.OrgQueryCondition;
import com.moredian.fishnet.org.enums.OrgType;
import com.moredian.fishnet.org.enums.PoliceOrgLevel;
import com.moredian.fishnet.org.manager.OrgManager;
import com.moredian.fishnet.org.manager.OrgRelationManager;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.request.ModuleAdminConfigRequest;
import com.moredian.fishnet.org.request.OrgAddRequest;
import com.moredian.fishnet.org.request.OrgQueryRequest;
import com.moredian.fishnet.org.request.OrgUpdateRequest;
import com.moredian.fishnet.org.service.OrgService;

@SI
public class OrgServiceImpl implements OrgService {
	
	@Autowired
	private OrgManager orgManager;
	@Autowired
	private OrgRelationManager orgRelationManager;

	@Override
	public List<Long> findAllOrgId() {
		return orgManager.findAllOrgId();
	}

	public OrgInfo orgToOrgInfo(Org org) {
		if(org == null) return null;
		return BeanUtils.copyProperties(OrgInfo.class, org);
	}
	
	@Override
	public ServiceResponse<Long> addOrg(OrgAddRequest request) {
		Long orgId = orgManager.addOrg(request);
		return new ServiceResponse<Long>(true, null, orgId);
	}

	@Override
	public ServiceResponse<Boolean> configModuleAdmin(ModuleAdminConfigRequest request) {
		boolean result = orgManager.configModuleAdmin(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> enableBiz(Long orgId, Integer bizType) {
		boolean result = orgManager.enableBiz(orgId, bizType);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> enableBizs(Long orgId, List<Integer> bizTypes) {
		boolean result = orgManager.enableBiz(orgId, bizTypes);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> disableBiz(Long orgId, Integer bizType) {
		boolean result = orgManager.disableBiz(orgId, bizType);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> disableBizs(Long orgId, List<Integer> bizTypes) {
		boolean result = orgManager.disableBiz(orgId, bizTypes);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateOrg(OrgUpdateRequest request) {
	    boolean result = orgManager.updateOrg(request);
	    return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteOrg(Long orgId) {
        boolean result = orgManager.deleteOrg(orgId);
        return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> disableOrg(Long orgId) {
        boolean result = orgManager.disableOrg(orgId);
        return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> enableOrg(Long orgId) {
		boolean result = orgManager.enableOrg(orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public OrgInfo getOrgInfo(Long orgId) {
		Org org = orgManager.getOrgById(orgId);
		return orgToOrgInfo(org);
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
	
	public List<OrgInfo> orgListToOrgInfoList(List<Org> orgList) {
		if (orgList == null) return null;
		
		List<OrgInfo> response = new ArrayList<>();
		for(Org org : orgList) {
			response.add(orgToOrgInfo(org));
		}
		
		return response;
	}
	
	public Pagination<OrgInfo> paginationOrgToPaginationOrgInfo(PaginationDomain<Org> fromPagination) {
		Pagination<OrgInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<OrgInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(orgListToOrgInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<OrgInfo> findPaginationOrg(Pagination<OrgInfo> pagination, OrgQueryRequest request) {
		
		PaginationDomain<Org> paginationOrg = orgManager.findPaginationOrg(request, pagination);
		
		return paginationOrgToPaginationOrgInfo(paginationOrg);
	}
	
	@Override
	public ServiceResponse<Boolean> bindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType) {
		boolean result = orgRelationManager.bindOrgRelation(upstreamOrgId, downstreamOrgId, relationType);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> unbindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType) {
		boolean result = orgRelationManager.unbindOrgRelation(upstreamOrgId, downstreamOrgId, relationType);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public OrgInfo getUpstream(Long orgId, Integer relationType) {
		Org org = orgManager.getUpstream(orgId, relationType);
		return orgToOrgInfo(org);
	}

	@Override
	public List<OrgInfo> findDownstream(Long orgId, Integer relationType) {
		List<Org> orgList = orgManager.findDownstream(orgId, relationType);
		return orgListToOrgInfoList(orgList);
	}

	@Override
	public boolean isBizEnable(Long orgId, Integer bizType) {
		return orgManager.isBizEnable(orgId, bizType);
	}

	@Override
	public OrgInfo getOrgByName(String orgName) {
		Org org = orgManager.getOrgByName(orgName);
		return this.orgToOrgInfo(org);
	}

	@Override
	public OrgInfo getOneOrg(Integer orgType, Integer orgLevel, Integer areaCode) {
		Org org = orgManager.getOneOrg(orgType, orgLevel, areaCode);
		return this.orgToOrgInfo(org);
	}

	@Override
	public List<OrgInfo> findMonitorPoliceOrg(Integer townCode) {
		List<Org> orgList = new ArrayList<>();
		Org townPoliceOrg = orgManager.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.TOWN.getValue(), townCode);
		orgList.add(townPoliceOrg);
		Org districtPoliceOrg = orgManager.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.DISTRICT.getValue(), townPoliceOrg.getDistrictId());
		orgList.add(districtPoliceOrg);
		Org cityPoliceOrg = orgManager.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.CITY.getValue(), districtPoliceOrg.getCityId());
		orgList.add(cityPoliceOrg);
		Org provPoliceOrg = orgManager.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.PROV.getValue(), cityPoliceOrg.getProvinceId());
		orgList.add(provPoliceOrg);
		return this.orgListToOrgInfoList(orgList);
	}


}
