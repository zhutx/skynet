package com.moredian.skynet.auth.manager;

import com.moredian.skynet.auth.domain.Account;

public interface AccountManager {
	
	Account getAccountForLogin(String accountName, String password);
	
	Account getByAccountName(String accountName);
	
	Account getAccountById(Long accountId);
	
	boolean resetPassword(Long accountId, String password);

}
