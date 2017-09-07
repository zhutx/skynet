package com.moredian.fishnet.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.domain.Account;
import com.moredian.fishnet.auth.manager.AccountManager;
import com.moredian.fishnet.auth.model.AccountInfo;
import com.moredian.fishnet.auth.service.AccountService;

@SI
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountManager accountManager;
	
	private AccountInfo accountToAccountInfo(Account account) {
		if(account == null) return null;
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountId(account.getAccountId());
		accountInfo.setAccountType(account.getAccountType());
		accountInfo.setAccountName(account.getAccountName());
		accountInfo.setPassword(account.getPassword());
		accountInfo.setStatus(account.getStatus());
		return accountInfo;
	}

	@Override
	public AccountInfo getAccountForLogin(String accountName, String password) {
		Account account = accountManager.getAccountForLogin(accountName, password);
		return accountToAccountInfo(account);
	}

	@Override
	public AccountInfo getByAccountName(String accountName) {
		Account account = accountManager.getByAccountName(accountName);
		return accountToAccountInfo(account);
	}

	@Override
	public ServiceResponse<Boolean> resetPassword(Long accountId, String password) {
		boolean result = accountManager.resetPassword(accountId, password);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
