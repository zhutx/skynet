package com.moredian.fishnet.org.manager;

public interface OrgRelationManager {
	
	boolean bindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	
	boolean unbindOrgRelation(Long upstreamOrgId, Long downstreamOrgId, Integer relationType);
	

}
