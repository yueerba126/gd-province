package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.RegionGis;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * <p>
 * 行政区划地图数据Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */
@DataPermissionExclude
public interface RegionGisMapper extends BaseMapper<RegionGis> {

}
