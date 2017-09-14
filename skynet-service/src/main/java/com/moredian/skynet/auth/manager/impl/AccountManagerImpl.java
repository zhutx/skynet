package com.moredian.skynet.auth.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.skynet.auth.domain.Account;
import com.moredian.skynet.auth.manager.AccountManager;
import com.moredian.skynet.auth.mapper.AccountMapper;

@Service
public class AccountManagerImpl implements AccountManager {
	
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public Account getAccountForLogin(String accountName, String password) {
		return accountMapper.loadForLogin(accountName, password);
	}

	@Override
	public Account getByAccountName(String accountName) {
		return accountMapper.loadByAccountName(accountName);
	}

	@Override
	public Account getAccountById(Long accountId) {
		return accountMapper.load(accountId);
	}

	@Override
	public boolean resetPassword(Long accountId, String password) {
		int result = accountMapper.updatePassword(accountId, password);
		return result > 0 ? true : false;
	}

}
