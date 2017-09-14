package com.moredian.skynet.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.auth.domain.Oper;
import com.moredian.skynet.auth.domain.Role;
import com.moredian.skynet.auth.manager.OperManager;
import com.moredian.skynet.auth.manager.OperRoleManager;
import com.moredian.skynet.auth.manager.PermManager;
import com.moredian.skynet.auth.manager.RoleManager;
import com.moredian.skynet.auth.model.OperInfo;
import com.moredian.skynet.auth.model.SimpleRoleInfo;
import com.moredian.skynet.auth.request.AdminInitRequest;
import com.moredian.skynet.auth.request.OperAddRequest;
import com.moredian.skynet.auth.request.OperQueryRequest;
import com.moredian.skynet.auth.request.OperUpdateRequest;
import com.moredian.skynet.auth.request.RoleAddRequest;
import com.moredian.skynet.auth.request.SimpleRoleQueryRequest;
import com.moredian.skynet.auth.service.OperService;
import com.xier.uif.account.service.AccountPassportService;
import com.xier.uif.account.service.AccountProfileService;

@SI
public class OperServiceImpl implements OperService {
	
	@Autowired
	private OperManager operManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private OperRoleManager operRoleManager;
	@Autowired
	private PermManager permManager;
	@Reference
	private AccountPassportService accountPassportService;
	@Reference
	private AccountProfileService accountProfileService;

	@Override
	public ServiceResponse<Boolean> addOper(OperAddRequest request) {
		boolean result = operManager.addOper(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateOper(OperUpdateRequest request) {
		boolean result = operManager.updateOper(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> disableOper(Long operId, Long orgId) {
		boolean result = operManager.disableOper(operId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	@Override
	public ServiceResponse<Boolean> deleteOper(Long operId, Long orgId) {
		boolean result = operManager.deleteOper(operId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> activeOper(Long operId, Long orgId) {
		boolean result = operManager.activeOper(operId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}
	
	private OperInfo operToOperInfo(Oper oper) {
		
		if(oper == null) return null;
		
		OperInfo operInfo = BeanUtils.copyProperties(OperInfo.class, oper);
		
		List<SimpleRoleInfo> roles = new ArrayList<>();
		
		List<Long> myRoleIdList = operRoleManager.findRoleIdByOperId(oper.getOperId());
		for(Long roleId : myRoleIdList) {
			Role role = roleManager.getRoleById(roleId, oper.getOrgId());
			SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
			simpleRoleInfo.setRoleId(role.getRoleId());
			simpleRoleInfo.setRoleName(role.getRoleName());
			roles.add(simpleRoleInfo);
		}
		
		operInfo.setRoles(roles);
		return operInfo;
	}
	
	private List<OperInfo> operListToOperInfoList(List<Oper> operList) {
		List<OperInfo> operInfoList = new ArrayList<>();
		if(operList == null) return operInfoList;
		
		for(Oper oper : operList) {
			operInfoList.add(operToOperInfo(oper));
		}
		
		return operInfoList;
	}

	@Override
	public List<OperInfo> findOper(OperQueryRequest request) {
		List<Oper> operList = operManager.findOper(request);
		return operListToOperInfoList(operList);
	}
	
	private SimpleRoleInfo roleToSimpleRoleInfo(Role role) {
		SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
		simpleRoleInfo.setRoleId(role.getRoleId());
		simpleRoleInfo.setRoleName(role.getRoleName());
		return simpleRoleInfo;
	}

	@Override
	public List<OperInfo> findEnableOper(Long accountId, Integer moduleType) {
		List<Oper> operList = operManager.findEnableOper(accountId, moduleType);
		return operListToOperInfoList(operList);
	}
	
	private List<SimpleRoleInfo> buildSimpleRoleInfoList(List<Role> allRoleList) {
		List<SimpleRoleInfo> simpleRoleInfoList = new ArrayList<>();
		
		for(Role role : allRoleList) {
			simpleRoleInfoList.add(roleToSimpleRoleInfo(role));
		}
		return simpleRoleInfoList;
	}
	
	@Override
	public List<SimpleRoleInfo> querySimpleRole(SimpleRoleQueryRequest request) {
		List<Role> allRoleList = roleManager.findAllRole(request.getOrgId());
		return buildSimpleRoleInfoList(allRoleList);
	}

	@Override
	public OperInfo getOperById(Long orgId, Long operId) {
		Oper oper = operManager.getOperById(orgId, operId);
		return this.operToOperInfo(oper);
	}
	
	/*private AccountBindType getAccountBindType(String accountName) {
		Pattern p = Pattern.compile("\\d{11}");
		Matcher m = p.matcher(accountName);
		if(m.matches()) {
			return AccountBindType.MOBILE;
		} else {
			return AccountBindType.USER_NAME;
		}
	}*/

	@Override
	public ServiceResponse<Boolean> initAdmin(Long orgId, Integer moduleType, String accountName, String realName) {
		
		/*Long accountId = null;
		AccountBindType accountBindType = this.getAccountBindType(accountName);
		// 1) 创建或获取通行证账号
		ServiceResponse<SimpleAccountInfo> sr_account = accountProfileService.getByAccountName(accountName, accountBindType);
		if(sr_account.isSuccess() && sr_account.isExistData()) {
			accountId = sr_account.getData().getAccountId();
		} else {
			AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest();
			accountRegisterRequest.setAccount(accountName);
			accountRegisterRequest.setName(realName);
			if(AccountBindType.USER_NAME.intValue() == accountBindType.intValue()) {
				accountRegisterRequest.setPassword("moreding123");
			}
			if(AccountBindType.MOBILE.intValue() == accountBindType.intValue()) {
				accountRegisterRequest.setPassword("moreding123"); // TODO
			}
			accountRegisterRequest.setType(accountBindType);
			accountRegisterRequest.setVerified(true);
			sr_account = accountPassportService.register(accountRegisterRequest);
			
			if(!sr_account.isSuccess()) sr_account.pickDataThrowException();
			accountId = sr_account.getData().getAccountId();
		}*/
		
		Long roleId = null;
		// 2) 创建或获取管理员角色
		Role role = roleManager.getRoleByName(orgId, "系统管理员");
		if(role == null){
			RoleAddRequest roleAddRequest = new RoleAddRequest();
			roleAddRequest.setOrgId(orgId);
			roleAddRequest.setRoleName("系统管理员");
			roleAddRequest.setRoleDesc("系统管理员");
			List<Long> permIds = permManager.findPermIdByModuleType(moduleType); // 赋予模块所有权限
			roleAddRequest.setPermIds(permIds);
			roleId = roleManager.addRole(roleAddRequest);
		} else {
			roleId = role.getRoleId();
		}
		
		// 3) 创建账号
		OperAddRequest operAddRequest = new OperAddRequest();
		operAddRequest.setOrgId(orgId);
		operAddRequest.setModuleType(moduleType);
		operAddRequest.setOperName(realName);
		operAddRequest.setOperDesc("系统管理员");
		operAddRequest.setAccountName(accountName);
		List<Long> roleIds = new ArrayList<>();
		roleIds.add(roleId);
		operAddRequest.setRoleIds(roleIds); // 赋予管理员角色
		operManager.addOper(operAddRequest);
		
		return new ServiceResponse<Boolean>(true, null, true);
	}

	@Override
	public List<OperInfo> findOperByRoleId(Long orgId, Long roleId) {
		List<Oper> operList = operManager.findOperByRoleId(orgId, roleId);
		return operListToOperInfoList(operList);
	}

	@Override
	public ServiceResponse<Boolean> initOneAdmin(AdminInitRequest request) {
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
		operAddRequest.setOperDesc(request.getOperDesc());
		operAddRequest.setAccountName(request.getAccountName());
		operAddRequest.setPassword(request.getPassword());
		operAddRequest.setMobile(request.getMobile());
		List<Long> roleIds = new ArrayList<>();
		roleIds.add(roleId);
		operAddRequest.setRoleIds(roleIds); // 赋予管理员角色
		operManager.addOper(operAddRequest);
		
		return new ServiceResponse<Boolean>(true, null, true);
	}

	@Override
	public OperInfo getOperByMobile(Integer moduleType, String mobile) {
		Oper oper = operManager.getOperByMobile(moduleType, mobile);
		return this.operToOperInfo(oper);
	}

}
