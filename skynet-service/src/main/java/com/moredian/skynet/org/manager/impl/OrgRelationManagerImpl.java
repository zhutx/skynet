package com.moredian.skynet.org.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Org;
import com.moredian.skynet.org.domain.OrgRelation;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.manager.OrgRelationManager;
import com.moredian.skynet.org.mapper.OrgMapper;
import com.moredian.skynet.org.mapper.OrgRelationMapper;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class OrgRelationManagerImpl implements OrgRelationManager {
	
	@Autowired
	private OrgRelationMapper orgRelationMapper;
	@Autowired
	private OrgMapper orgMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	
	private OrgRelation orgRelationBindRequestToOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType) {
		OrgRelation orgRelation = new OrgRelation();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.org.OrgRelation").getData();
		orgRelation.setOrgRelationId(id);
		orgRelation.setOrgId(upstreamOrgId);
		orgRelation.setRelationOrgId(downstreamOrgId);
		orgRelation.setRelationType(relationType);
		return orgRelation;
	}
	
	@Override
	public boolean bindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType) {
		BizAssert.notNull(upstreamOrgId, "orgId must not be null");
		BizAssert.notNull(downstreamOrgId, "relationOrgId must not be null");
		BizAssert.notNull(relationType, "relationType must not be null");
		
		Org org = orgMapper.load(upstreamOrgId);
		if(org == null) ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());
		Org relationOrg = orgMapper.load(downstreamOrgId);
		if(relationOrg == null) ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());
		
		int result = orgRelationMapper.insert(this.orgRelationBindRequestToOrgRelation(upstreamOrgId, downstreamOrgId, relationType));
		
		return result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean unbindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType) {
		BizAssert.notNull(upstreamOrgId, "upstreamOrgId must not be null");
		BizAssert.notNull(downstreamOrgId, "downstreamOrgId must not be null");
		
		Org org = orgMapper.load(upstreamOrgId);
		if(org == null) ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());
		Org relationOrg = orgMapper.load(downstreamOrgId);
		if(relationOrg == null) ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());
		
		int result = orgRelationMapper.delete(upstreamOrgId, downstreamOrgId, relationType);
		
		return result > 0 ? true : false;
		
	}


}
