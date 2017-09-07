package com.moredian.fishnet.auth.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.auth.model.PermInfo;
import com.moredian.fishnet.auth.model.PermNode;
import com.moredian.fishnet.auth.model.PermZNode;
import com.moredian.fishnet.auth.request.PermAddRequest;
import com.moredian.fishnet.auth.request.PermQueryRequest;
import com.moredian.fishnet.auth.request.PermUpdateRequest;

/**
 * 权限服务
 * @author zhutx
 *
 */
public interface PermService {
	
	ServiceResponse<Boolean> addPerm(PermAddRequest request);
	
	ServiceResponse<Boolean> updatePerm(PermUpdateRequest request);
	
	ServiceResponse<Boolean> deletePerm(Long permId);
	
	ServiceResponse<Boolean> disablePerm(Long permId);
	
	ServiceResponse<Boolean> activePerm(Long permId);
	
	List<PermInfo> findPerm(PermQueryRequest request);
	
	List<String> findPermUrlByOper(Long operId);
	
	List<PermNode> findPermNodeByOper(Long operId, Integer moduleType);
	
	List<PermNode> findPermNode(Integer moduleType);
	
	List<PermZNode> findPermZNode(Integer moduleType, Long roleId);
	
	List<Long> findPermByRole(Long roleId);
	
	PermInfo getPerm(Long permId);

}
