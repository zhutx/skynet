package com.moredian.skynet.auth.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.auth.model.OperInfo;
import com.moredian.skynet.auth.model.SimpleRoleInfo;
import com.moredian.skynet.auth.request.AdminInitRequest;
import com.moredian.skynet.auth.request.OperAddRequest;
import com.moredian.skynet.auth.request.OperQueryRequest;
import com.moredian.skynet.auth.request.OperUpdateRequest;
import com.moredian.skynet.auth.request.SimpleRoleQueryRequest;

/**
 * 操作员服务
 * @author zhutx
 *
 */
public interface OperService {
	
	ServiceResponse<Boolean> addOper(OperAddRequest request);
	
	ServiceResponse<Boolean> updateOper(OperUpdateRequest request);
	
	ServiceResponse<Boolean> disableOper(Long operId, Long orgId);
	
	ServiceResponse<Boolean> activeOper(Long operId, Long orgId);
	
	ServiceResponse<Boolean> deleteOper(Long operId, Long orgId);
	
	List<SimpleRoleInfo> querySimpleRole(SimpleRoleQueryRequest request);
	
	List<OperInfo> findOper(OperQueryRequest request);
	
	List<OperInfo> findOperByRoleId(Long orgId, Long roleId);
	
	List<OperInfo> findEnableOper(Long accountId, Integer moduleType);
	
	OperInfo getOperById(Long orgId, Long operId);
	
	ServiceResponse<Boolean> initAdmin(Long orgId, Integer moduleType, String accountName, String realName);
	
	ServiceResponse<Boolean> initOneAdmin(AdminInitRequest request);
	
	OperInfo getOperByMobile(Integer moduleType, String mobile);
	
}