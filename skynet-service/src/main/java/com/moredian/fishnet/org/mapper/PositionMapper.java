package com.moredian.fishnet.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.fishnet.org.domain.Position;

@Mapper
public interface PositionMapper {
	
    Position load(@Param("orgId")Long orgId, @Param("positionId")Long positionId);
    
    Position getRoot(@Param("orgId")Long orgId, @Param("parentId")Long parentId, @Param("positionModel")Integer positionModel);
    
    int insert(Position position);
    
    int updatePositionName(@Param("positionId")Long positionId, @Param("orgId")Long orgId, @Param("positionName")String positionName, @Param("fullName")String fullName);
    
    List<Position> findChildren(@Param("orgId")Long orgId, @Param("positionModel")Integer positionModel, @Param("positionCodePrefix")String positionCodePrefix, @Param("level")Integer level);
    
    int updateFullName(@Param("orgId")Long orgId, @Param("positionId")Long positionId, @Param("fullName")String fullName);
    
    int delete(@Param("orgId")Long orgId, @Param("positionId")Long positionId);
    
    List<Position> findPlatPositions(@Param("orgId")Long orgId, @Param("positionType")Integer positionType, @Param("positionModel")Integer positionModel);
    
    List<Position> findDirectChildrens(@Param("orgId")Long orgId, @Param("parentId")Long parentId, @Param("positionModel")Integer positionModel);
    
    List<Long> findDirectChildrenIds(@Param("orgId")Long orgId, @Param("parentId")Long parentId, @Param("positionModel")Integer positionModel);
    
    List<Long> findAllChildrenIds(@Param("orgId")Long orgId, @Param("positionModel")Integer positionModel, @Param("positionCodePrefix")String positionCodePrefix, @Param("level")Integer level);
    
    List<Position> findByNameAndType(@Param("orgId")Long orgId, @Param("positionType")Integer positionType, @Param("positionName")String positionName);
 
}
