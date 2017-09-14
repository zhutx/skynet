package com.moredian.skynet.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.auth.domain.Oper;

@Mapper
public interface OperMapper {
	
	List<Oper> findByCondition(@Param("orgId")Long orgId, @Param("accountName")String accountName, @Param("operName")String operName, @Param("keywords")String keywords, @Param("moduleType")Integer moduleType);
	
	List<Oper> findEnable(@Param("accountId")Long accountId, @Param("moduleType")Integer moduleType, @Param("status")Integer status);
	
	Oper loadByAccount(@Param("accountId")Long accountId, @Param("moduleType")Integer moduleType);
	
	Oper loadByMobile(@Param("mobile")String mobile, @Param("moduleType")Integer moduleType);
	
	int insert(Oper oper);
	
	Oper load(@Param("operId")Long operId, @Param("orgId")Long orgId);
	
	int update(Oper oper);
	
	int updateStatus(@Param("orgId")Long orgId, @Param("operId")Long operId, @Param("status")Integer status);
	
	Oper loadOne(@Param("orgId")Long orgId, @Param("accountId")Long accountId, @Param("moduleType")Integer moduleType);
	
	List<Oper> findOperByRoleId(@Param("orgId")Long orgId, @Param("status")Integer status, @Param("roleId")Long roleId);
	
	int delete(@Param("orgId")Long orgId, @Param("operId")Long operId);
	
	Oper getOperByMobile(@Param("moduleType")Integer moduleType, @Param("mobile")String mobile);

}
