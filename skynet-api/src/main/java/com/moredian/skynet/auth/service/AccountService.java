package com.moredian.skynet.auth.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.auth.model.AccountInfo;

/**
 * 账号服务
 * @author zhutx
 *
 */
public interface AccountService {
	
	AccountInfo getAccountForLogin(String accountName, String password);
	
	AccountInfo getByAccountName(String accountName);
	
	ServiceResponse<Boolean> resetPassword(Long accountId, String password);

}
