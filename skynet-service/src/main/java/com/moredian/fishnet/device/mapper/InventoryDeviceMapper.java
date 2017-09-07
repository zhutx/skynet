package com.moredian.fishnet.device.mapper;

import com.moredian.fishnet.device.domain.WhiteDevice;
import com.moredian.fishnet.device.domain.WhiteDeviceQueryCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryDeviceMapper  extends BaseMapper{

    int getTotalCountByCondition(WhiteDeviceQueryCondition condition);

    List<WhiteDevice> findPaginationByCondition(WhiteDeviceQueryCondition condition);

    int insert(WhiteDevice whiteDevice);

}
