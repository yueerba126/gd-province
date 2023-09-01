package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.RegionGis;
import com.sydata.organize.vo.RegionGisVo;

/**
 * <p>
 * 组织架构-行政区划地图数据Service接口
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */
public interface RegionGisService extends IService<RegionGis> {

    /**
     * 地图json
     *
     * @param id 区域ID
     * @return 分页列表
     */
    RegionGisVo geoJson(String id);
}
