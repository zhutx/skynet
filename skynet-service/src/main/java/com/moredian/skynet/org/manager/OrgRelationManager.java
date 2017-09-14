package com.moredian.skynet.org.manager;

public interface OrgRelationManager {
	
	boolean bindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	
	boolean unbindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	

}
