package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * @author lzq
 * @description app对接应用关联库区Mapper
 * @date 2023/5/30 10:57
 */
@DataPermissionExclude
public interface AppApiStockHouseMapper extends BaseMapper<AppApiStockHouse> {
}
