package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.TankRecord;
import com.sydata.reserve.layout.mapper.TankRecordMapper;
import com.sydata.reserve.layout.param.TankRecordPageParam;
import com.sydata.reserve.layout.param.TankRecordSaveParam;
import com.sydata.reserve.layout.param.TankRecordUpdateParam;
import com.sydata.reserve.layout.service.ITankRecordService;
import com.sydata.reserve.layout.vo.TankRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-油罐信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = TankRecordServiceImpl.CACHE_NAME)
@Service("tankRecordService")
public class TankRecordServiceImpl
        extends ServiceImpl<TankRecordMapper, TankRecord>
        implements ITankRecordService {

    final static String CACHE_NAME = "reserveLayout:TankRecord";


    @Override
    public boolean removeById(TankRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<TankRecordVo> pages(TankRecordPageParam pageParam) {
        Page<TankRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getYgmc()), TankRecord::getYgmc, pageParam.getYgmc())
                .eq(isNotEmpty(pageParam.getStatus()), TankRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getDwdm()), TankRecord::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getId()), TankRecord::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getKqdm()), TankRecord::getKqdm, pageParam.getKqdm())
                .orderByDesc(TankRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TankRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public TankRecordVo detail(String id) {
        ITankRecordService TankRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(TankRecordService.getById(id), TankRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(TankRecordSaveParam TankRecordSaveParam) {
        TankRecord TankRecord = BeanUtils.copyByClass(TankRecordSaveParam, TankRecord.class);
        return super.save(TankRecord.setId(TankRecord.getYgdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(TankRecordUpdateParam TankRecordUpdateParam) {
        TankRecord tankRecord = this.getById(TankRecordUpdateParam.getId());
        Assert.notNull(tankRecord, "储备布局地理信息-油罐信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(TankRecordUpdateParam, TankRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        TankRecord tankRecord = this.getById(id);
        Assert.notNull(tankRecord, "储备布局地理信息-油罐信息备案不存在");
        return super.updateById(tankRecord.setStatus(status));
    }


}