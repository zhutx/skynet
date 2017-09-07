package com.moredian.fishnet.auth.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.auth.model.AccountInfo;

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
