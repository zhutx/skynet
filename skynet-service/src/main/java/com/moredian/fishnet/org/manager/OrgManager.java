package com.moredian.fishnet.org.manager;

import java.util.List;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.org.domain.Org;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.request.ModuleBindRequest;
import com.moredian.fishnet.org.request.OrgAddRequest;
import com.moredian.fishnet.org.request.OrgQueryRequest;
import com.moredian.fishnet.org.request.OrgUpdateRequest;

public interface OrgManager {
	
	List<Long> findAllOrgId();
	
	Long addOrg(OrgAddRequest request);
	
	boolean bindModule(ModuleBindRequest request);
	
	boolean enableBiz(Long orgId, Integer bizType);
	
	boolean enableBiz(Long orgId, List<Integer> bizTypes);
	
	boolean disableBiz(Long orgId, Integer bizType);
	
	boolean disableBiz(Long orgId, List<Integer> bizTypes);
	
	boolean enableOrg(Long orgId);
	
	boolean updateOrg(OrgUpdateRequest request);
	
	boolean deleteOrg(Long orgId);
	
	boolean disableOrg(Long orgId);
	
	Org getOrgById(Long orgId);
	
	PaginationDomain<Org> findPaginationOrg(OrgQueryRequest request, Pagination<OrgInfo> pagination);
	
	Org getUpstream(Long orgId, Integer relationType);
	
	List<Org> findDownstream(Long orgId, Integer relationType);
	
	boolean isBizEnable(Long orgId, Integer bizType);
	
	Org getOrgByName(String orgName);
	
	Org getOneOrg(Integer orgType, Integer orgLevel, Integer areaCode);
	
}
