package com.moredian.skynet.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.auth.domain.Account;

@Mapper
public interface AccountMapper {
	
	Account load(Long accountId);
	
	Account loadByAccountName(String accountName);
	
	int insert(Account account);
	
	Account loadForLogin(@Param("accountName")String accountName, @Param("password")String password);
	
	int updatePassword(@Param("accountId")Long accountId, @Param("password")String password);

}
