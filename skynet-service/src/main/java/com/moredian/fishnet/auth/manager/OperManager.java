package com.moredian.fishnet.auth.manager;

import java.util.List;

import com.moredian.fishnet.auth.domain.Oper;
import com.moredian.fishnet.auth.request.OperAddRequest;
import com.moredian.fishnet.auth.request.OperQueryRequest;
import com.moredian.fishnet.auth.request.OperUpdateRequest;

public interface OperManager {
	
	boolean addOper(OperAddRequest request);
	
	boolean updateOper(OperUpdateRequest request);
	
	boolean disableOper(Long operId, Long orgId);
	
	boolean deleteOper(Long operId, Long orgId);
	
	boolean activeOper(Long operId, Long orgId);
	
	List<Oper> findOper(OperQueryRequest request);
	
	List<Oper> findEnableOper(Long accountId, Integer moduleType);
	
	Oper getOperById(Long orgId, Long operId);
	
	Oper getOper(Long orgId, Long accountId, Integer moduleType);
	
	List<Oper> findOperByRoleId(Long orgId, Long roleId);
	
	Oper getOperByMobile(Integer moduleType, String mobile);

}
