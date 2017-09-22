package com.moredian.skynet.auth.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.exception.CommonErrorCode;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Validator;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.auth.domain.Oper;
import com.moredian.skynet.auth.domain.OperRole;
import com.moredian.skynet.auth.enums.AuthErrorCode;
import com.moredian.skynet.auth.enums.OperStatus;
import com.moredian.skynet.auth.manager.OperManager;
import com.moredian.skynet.auth.mapper.OperMapper;
import com.moredian.skynet.auth.mapper.OperRoleMapper;
import com.moredian.skynet.auth.request.OperAddRequest;
import com.moredian.skynet.auth.request.OperQueryRequest;
import com.moredian.skynet.auth.request.OperUpdateRequest;
import com.xier.sesame.pigeon.mm.service.MMService;
import com.xier.uif.account.enums.AccountBindType;
import com.xier.uif.account.service.AccountPassportService;
import com.xier.uif.account.service.AccountProfileService;
import com.xier.uif.account.service.request.AccountRegisterRequest;
import com.xier.uif.account.service.response.SimpleAccountInfo;

@Service
public class OperManagerImpl implements OperManager {
	
	private static final Logger logger = LoggerFactory.getLogger(OperManagerImpl.class);
	
	@Autowired
	private OperMapper operMapper;
	@Autowired
	private OperRoleMapper operRoleMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@Reference
	private AccountProfileService accountProfileService;
	@Reference
	private AccountPassportService accountPassportService;
	@Reference
	private MMService mmService;
	
	private Oper operAddRequestToOper(OperAddRequest request, Long accountId) {
		
		Oper oper = new Oper();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.auth.Oper").getData();
		oper.setOperId(id);
		oper.setOrgId(request.getOrgId());
		oper.setModuleType(request.getModuleType());
		oper.setAccountId(accountId);
		oper.setAccountName(request.getAccountName());
		oper.setEmail(request.getEmail());
		oper.setMobile(request.getMobile());
		oper.setOperName(request.getOperName());
		oper.setOperDesc(request.getOperDesc());
		oper.setDefaultFlag(0);
		oper.setStatus(OperStatus.USABLE.getValue());
		return oper;
	}
	
	private void updateOperRole(Long operId, List<Long> newRoleIds){
		
		List<Long> orgiRoleIds = operRoleMapper.findRoleIdByOperId(operId);
		if(orgiRoleIds == null) orgiRoleIds = new ArrayList<>();
		if(newRoleIds == null) newRoleIds = new ArrayList<>();
		
		// 复制一份，备用
		List<Long> orgiRoleIds_clone = new ArrayList<>();
		orgiRoleIds_clone.addAll(orgiRoleIds);
		
		
		orgiRoleIds.removeAll(newRoleIds);// 定位删除的权限
		for(Long roleId : orgiRoleIds) {
			operRoleMapper.delete(operId, roleId);
		}

		newRoleIds.removeAll(orgiRoleIds_clone); //定位新增的权限
		for(Long roleId : newRoleIds) {
			OperRole operRole = new OperRole();
			Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.auth.OperRole").getData();
			operRole.setOperRoleId(id);
			operRole.setOperId(operId);
			operRole.setRoleId(roleId);
			operRoleMapper.insert(operRole);
		}
	}
	
	private com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> registerAccount(String name, String password, String accountName, AccountBindType accountBindType) {
		AccountRegisterRequest request = new AccountRegisterRequest();
		request.setName(name);
		request.setAccount(accountName);
		request.setType(accountBindType);
		request.setPassword(password);
    	request.setVerified(true);
    	request.setHeadImageUrl("");
    	logger.info(JsonUtils.toJson(request));
    	com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr = accountPassportService.register(request);
    	return sr;
	}
	
	/*@Override
	@Transactional
	public boolean addOper(OperAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getModuleType(), "moduleType must not be null");
		BizAssert.notBlank(request.getAccountName(), "accountName must not be blank");
		
		AccountBindType accountBindType = this.getAccountBindType(request.getAccountName());
		
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr = accountProfileService.getByAccountName(request.getAccountName(), accountBindType);
		Long accountId = null;
		
		//if(!sr.isSuccess()) sr.pickDataThrowException();
		if(!sr.isSuccess()) {
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, sr.getErrorContext().getMessage());
		}
		
		if(sr.isExistData()) { 
			accountId = sr.getData().getAccountId();
		} else { // 统一账号不存在，创建账号
			String password = null;
			if(this.isMobile(request.getAccountName())) {
			    password = RandomStringUtils.randomNumeric(6);
			} else {
				password = "88888888";
			}
			
			com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr_reg = this.registerAccount(request.getOperName(), password, request.getAccountName(), accountBindType);
			if(sr_reg.isSuccess()) {
				accountId = sr_reg.getData().getAccountId();
				if(this.isMobile(request.getAccountName())) {
					this.sendSmsNotify(request.getOperName(), request.getAccountName(), password); // 发送短信
				}
			} else {
				//sr_reg.pickDataThrowException();
				ExceptionUtils.throwException(CommonErrorCode.UNKONWN, sr_reg.getErrorContext().getMessage());
			}
		}
		
		Oper existOper = operMapper.loadByAccount(request.getOrgId(), accountId, request.getModuleType());
		if(existOper != null) ExceptionUtils.throwException(AuthErrorCode.OPER_EXIST, String.format(AuthErrorCode.OPER_EXIST.getMessage(), request.getAccountName()));
		
		Oper oper = this.operAddRequestToOper(request, accountId);
		operMapper.insert(oper);
		
		this.updateOperRole(oper.getOperId(), request.getRoleIds());
		
		return true;
	}*/
	
	@Override
	@Transactional
	public boolean addOper(OperAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getModuleType(), "moduleType must not be null");
		BizAssert.notBlank(request.getAccountName(), "accountName must not be blank");
		if(StringUtils.isNotBlank(request.getMobile())) {
			if(!Validator.isMobile(request.getMobile())){
				ExceptionUtils.throwException(AuthErrorCode.MOBILE_WRONG, AuthErrorCode.MOBILE_WRONG.getMessage());
			}
		}
		
		AccountBindType accountBindType = AccountBindType.USER_NAME;
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr = accountProfileService.getByAccountName(request.getAccountName(), accountBindType);
		Long accountId = null;
		
		//if(!sr.isSuccess()) sr.pickDataThrowException();
		if(!sr.isSuccess()) {
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, sr.getErrorContext().getMessage());
		}
		
		if(sr.isExistData()) { 
			accountId = sr.getData().getAccountId();
		} else { // 统一账号不存在，创建账号
			com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr_reg = this.registerAccount(request.getOperName(), request.getPassword(), request.getAccountName(), accountBindType);
			if(sr_reg.isSuccess()) {
				accountId = sr_reg.getData().getAccountId();
			} else {
				//sr_reg.pickDataThrowException();
				ExceptionUtils.throwException(CommonErrorCode.UNKONWN, sr_reg.getErrorContext().getMessage());
			}
		}
		
		Oper existOper = operMapper.loadByAccount(accountId, request.getModuleType());
		if(existOper != null) ExceptionUtils.throwException(AuthErrorCode.OPER_EXIST, AuthErrorCode.OPER_EXIST.getMessage());
		
		if(StringUtils.isNotBlank(request.getMobile())) {
			existOper = operMapper.loadByMobile(request.getMobile(), request.getModuleType());
			if(existOper != null) ExceptionUtils.throwException(AuthErrorCode.MOBILE_EXIST, AuthErrorCode.MOBILE_EXIST.getMessage());
		}
		
		Oper oper = this.operAddRequestToOper(request, accountId);
		operMapper.insert(oper);
		
		this.updateOperRole(oper.getOperId(), request.getRoleIds());
		
		return true;
	}
	
	private Oper operUpdateRequestToOper(OperUpdateRequest request) {
		
		Oper oper = operMapper.load(request.getOperId(), request.getOrgId());
		
		oper.setOperName(request.getOperName());
		oper.setOperDesc(request.getOperDesc());
		oper.setEmail(request.getEmail());
		oper.setMobile(request.getMobile());
		
		return oper;
	}

	@Override
	public boolean updateOper(OperUpdateRequest request) {
		BizAssert.notNull(request.getOperId(), "operId must not be null");
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getModuleType(), "moduleType must not be null");
		if(StringUtils.isNotBlank(request.getMobile())) {
			if(!Validator.isMobile(request.getMobile())){
				ExceptionUtils.throwException(AuthErrorCode.MOBILE_WRONG, AuthErrorCode.MOBILE_WRONG.getMessage());
			}
		}
		
		Oper existOper = operMapper.load(request.getOperId(), request.getOrgId());
		if(StringUtils.isNotBlank(request.getMobile()) && !request.getMobile().equals(existOper.getMobile())) {
			Oper existMobileOper = operMapper.loadByMobile(request.getMobile(), request.getModuleType());
			if(existMobileOper != null) ExceptionUtils.throwException(AuthErrorCode.MOBILE_EXIST, AuthErrorCode.MOBILE_EXIST.getMessage());
		}
		
		Oper oper = this.operUpdateRequestToOper(request);
		operMapper.update(oper);
		
		this.updateOperRole(oper.getOperId(), request.getRoleIds());
		
		return true;
		
	}

	@Override
	public boolean disableOper(Long operId, Long orgId) {
		BizAssert.notNull(operId, "operId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		
		operMapper.updateStatus(orgId, operId, OperStatus.DISABLE.getValue());
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean deleteOper(Long operId, Long orgId) {
		BizAssert.notNull(operId, "operId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		
		operMapper.delete(orgId, operId);
		operRoleMapper.deleteByOper(operId);
		
		return true;
	}

	@Override
	public boolean activeOper(Long operId, Long orgId) {
		BizAssert.notNull(operId, "operId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		
		operMapper.updateStatus(orgId, operId, OperStatus.USABLE.getValue());
		
		return true;
	}

	@Override
	public List<Oper> findOper(OperQueryRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getModuleType(), "moduleType must not be null");
		
		return operMapper.findByCondition(request.getOrgId(), request.getKeywords(), request.getModuleType());
	}

	@Override
	public List<Oper> findEnableOper(Long accountId, Integer moduleType) {
		
		BizAssert.notNull(accountId, "loginName must not be null");
		BizAssert.notNull(moduleType, "moduleType must not be null");
		
		return operMapper.findEnable(accountId, moduleType, OperStatus.USABLE.getValue());
	}

	@Override
	public Oper getOperById(Long orgId, Long operId) {
		BizAssert.notNull(operId);
		return operMapper.load(operId, orgId);
	}

	@Override
	public Oper getOper(Long orgId, Long accountId, Integer moduleType) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(accountId);
		BizAssert.notNull(moduleType);
		return operMapper.loadOne(orgId, accountId, moduleType);
	}

	@Override
	public List<Oper> findOperByRoleId(Long orgId, Long roleId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(roleId);
		return operMapper.findOperByRoleId(orgId, OperStatus.USABLE.getValue(), roleId);
	}

	@Override
	public Oper getOperByMobile(Integer moduleType, String mobile) {
		BizAssert.notNull(moduleType);
		BizAssert.notNull(mobile);
		return operMapper.getOperByMobile(moduleType, mobile);
	}

}
