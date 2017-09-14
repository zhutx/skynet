package com.moredian.skynet.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.auth.domain.Perm;

@Mapper
public interface PermMapper {
	
	int insert(Perm perm);
	
	Perm loadByPermName(@Param("permName")String permName, @Param("moduleType")Integer moduleType);
	
	Perm load(Long permId);
	
	int update(Perm perm);
	
	int deleteById(Long permId);
	
	int getChildrenCount(Long parentId);
	
	int updateStatus(@Param("permId")Long permId, @Param("status")Integer status);
	
	List<Perm> findByCondition(@Param("moduleType")Integer moduleType, @Param("parentId")Long parentId, @Param("permName")String permName);
	
	void incChildrenSize(Long permId);
	
	void decChildrenSize(Long permId);
	
	List<String> findPermUrl(@Param("permIdList")List<Long> permIdList);
	
	List<Perm> findPermByOper(@Param("operId")Long operId, @Param("moduleType")Integer moduleType);
	
	List<Perm> findPermByModule(Integer moduleType);
	
	List<Long> findPermIdByModule(Integer moduleType);
	
	List<Long> findChildrenId(Long parentId);
	

}
