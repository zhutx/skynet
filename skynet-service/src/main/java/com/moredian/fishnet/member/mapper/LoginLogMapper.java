package com.moredian.fishnet.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.member.domain.LoginLog;

@Mapper
public interface LoginLogMapper {
	
	int insert(LoginLog loginLog);
	
	LoginLog loadLast(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("moduleType")Integer moduleType);
	
	int count(@Param("orgId")Long orgId, @Param("memberId")Long memberId, @Param("moduleType")Integer moduleType);

}
