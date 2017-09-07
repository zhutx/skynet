package com.moredian.fishnet.auth.manager;

import java.util.List;

import com.moredian.fishnet.auth.domain.Perm;
import com.moredian.fishnet.auth.request.PermAddRequest;
import com.moredian.fishnet.auth.request.PermQueryRequest;
import com.moredian.fishnet.auth.request.PermUpdateRequest;

public interface PermManager {
	
	boolean addPerm(PermAddRequest request);
	
	boolean updatePerm(PermUpdateRequest request);
	
	boolean deletePerm(Long permId);
	
	boolean disablePerm(Long permId);
	
	boolean activePerm(Long permId);
	
	List<Perm> findPerm(PermQueryRequest request);
	
	List<Perm> findPerm(Integer moduleType, Long parentId);
	
	List<String> findPermUrlByOper(Long operId);
	
	List<Perm> findPermByOper(Long operId, Integer moduleType);
	
	List<Perm> findPermByModuleType(Integer moduleType);
	
	Perm getPerm(Long permId);
	
	List<Long> findPermIdByModuleType(Integer moduleType);
	
}
