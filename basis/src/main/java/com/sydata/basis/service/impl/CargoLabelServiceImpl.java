package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.CargoLabel;
import com.sydata.basis.mapper.CargoLabelMapper;
import com.sydata.basis.param.CargoLabelPageParam;
import com.sydata.basis.service.ICargoLabelService;
import com.sydata.basis.vo.CargoLabelVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.CargoLabelApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindCargoLabel;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.CargoLabelReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.CARGO_LABEL;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 库区图仓房点位标注Service业务层处理
 *
 * @author lzq
 * @date 2022-10-11
 */
@CacheConfig(cacheNames = CargoLabelServiceImpl.CACHE_NAME)
@Service("cargoLabelService")
public class CargoLabelServiceImpl
        extends BaseDataImpl<CargoLabelApiParam, CargoLabelMapper, CargoLabel, CargoLabelReportParam>
        implements ICargoLabelService {

    final static String CACHE_NAME = "basis:cargoLabel";

    @Resource
    private CargoLabelMapper cargoLabelMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public CargoLabel getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(CargoLabel entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(CargoLabel entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(CargoLabel entity) {
        return super.removeById(entity);
    }

    @Override
    public Page<CargoLabelVo> pages(CargoLabelPageParam pageParam) {
        Page<CargoLabel> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), CargoLabel::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), CargoLabel::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), CargoLabel::getStockHouseId, pageParam.getStockHouseId())
                .eq(isNotEmpty(pageParam.getHwdm()), CargoLabel::getHwdm, pageParam.getHwdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), CargoLabel::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), CargoLabel::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(CargoLabel::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CargoLabelVo.class);
    }

    @Override
    public CargoLabelVo detail(String id) {
        ICargoLabelService cargoLabelService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(cargoLabelService.getById(id), CargoLabelVo.class);
    }

    @DataBindService(strategy = DataBindCargoLabel.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, cargoLabelMapper);
    }

    @Override
    public DataApiEnum api() {
        return CARGO_LABEL;
    }
}