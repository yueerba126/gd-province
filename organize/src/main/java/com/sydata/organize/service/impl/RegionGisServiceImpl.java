package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.RegionGis;
import com.sydata.organize.mapper.RegionGisMapper;
import com.sydata.organize.mapper.RegionMapper;
import com.sydata.organize.service.RegionGisService;
import com.sydata.organize.vo.RegionGisVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * <p>
 * 组织架构-行政区划地图数据Service业务层处理
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */
@Service("regionGisService")
@CacheConfig(cacheNames = RegionGisServiceImpl.CACHE_NAME)
public class RegionGisServiceImpl extends ServiceImpl<RegionGisMapper, RegionGis> implements RegionGisService {

    final static String CACHE_NAME = "organize:regionGis";

    @Resource
    private RegionMapper regionMapper;

    @Resource
    private RegionGisMapper regionGisMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public RegionGis getById(Serializable id) {
        return super.getById(id);
    }

    @DataBindFieldConvert
    @Override
    public RegionGisVo geoJson(String id) {
        RegionGisServiceImpl regionGisService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(regionGisService.getById(id), RegionGisVo.class);
    }
}
