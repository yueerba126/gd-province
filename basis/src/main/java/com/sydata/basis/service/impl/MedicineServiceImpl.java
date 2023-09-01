package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Medicine;
import com.sydata.basis.mapper.MedicineMapper;
import com.sydata.basis.param.MedicinePageParam;
import com.sydata.basis.service.IMedicineService;
import com.sydata.basis.vo.MedicineVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.MedicineApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindMedicine;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.MedicineReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.MEDICINE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-药剂信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = MedicineServiceImpl.CACHE_NAME)
@Service("medicineService")
public class MedicineServiceImpl extends BaseDataImpl<MedicineApiParam, MedicineMapper, Medicine, MedicineReportParam>
        implements IMedicineService {

    final static String CACHE_NAME = "basis:medicine";

    @Resource
    private MedicineMapper medicineMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Medicine getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Medicine entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Medicine entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Medicine entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<MedicineVo> pages(MedicinePageParam pageParam) {
        Page<Medicine> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Medicine::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Medicine::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), Medicine::getStockHouseId, pageParam.getStockHouseId())
                .ge(isNotEmpty(pageParam.getBeginCgrq()), Medicine::getCgrq, pageParam.getBeginCgrq())
                .le(isNotEmpty(pageParam.getEndCgrq()), Medicine::getCgrq, pageParam.getEndCgrq())
                .likeRight(isNotEmpty(pageParam.getYjmc()), Medicine::getYjmc, pageParam.getYjmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Medicine::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Medicine::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Medicine::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, MedicineVo.class);
    }

    @DataBindFieldConvert
    @Override
    public MedicineVo detail(String id) {
        IMedicineService medicineService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(medicineService.getById(id), MedicineVo.class);
    }

    @DataBindService(strategy = DataBindMedicine.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, medicineMapper);
    }

    @Override
    public DataApiEnum api() {
        return MEDICINE;
    }
}