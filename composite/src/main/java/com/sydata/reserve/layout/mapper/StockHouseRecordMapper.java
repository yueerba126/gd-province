package com.sydata.reserve.layout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.reserve.layout.domain.StockHouseRecord;

/**
 * 储备布局地理信息-库区信息备案Mapper接口
 *
 * @author lzq
 * @date 2022-07-08
 */
@DataPermissionExclude
public interface StockHouseRecordMapper extends BaseMapper<StockHouseRecord> {

}