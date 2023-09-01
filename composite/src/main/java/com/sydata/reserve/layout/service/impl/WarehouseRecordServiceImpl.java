package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindWarehouseRecord;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.WarehouseRecord;
import com.sydata.reserve.layout.mapper.WarehouseRecordMapper;
import com.sydata.reserve.layout.param.WarehouseRecordPageParam;
import com.sydata.reserve.layout.param.WarehouseRecordSaveParam;
import com.sydata.reserve.layout.param.WarehouseRecordUpdateParam;
import com.sydata.reserve.layout.service.IWarehouseRecordService;
import com.sydata.reserve.layout.vo.WarehouseRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-仓房信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = WarehouseRecordServiceImpl.CACHE_NAME)
@Service("warehouseRecordService")
public class WarehouseRecordServiceImpl
        extends ServiceImpl<WarehouseRecordMapper, WarehouseRecord>
        implements IWarehouseRecordService {

    final static String CACHE_NAME = "reserveLayout:WarehouseRecord";

    @Resource
    private WarehouseRecordMapper warehouseRecordMapper;

    @Override
    public boolean removeById(WarehouseRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<WarehouseRecordVo> pages(WarehouseRecordPageParam pageParam) {
        Page<WarehouseRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getCfmc()), WarehouseRecord::getCfmc, pageParam.getCfmc())
                .eq(isNotEmpty(pageParam.getStatus()), WarehouseRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getDwdm()), WarehouseRecord::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getId()), WarehouseRecord::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getKqdm()), WarehouseRecord::getKqdm, pageParam.getKqdm())
                .orderByDesc(WarehouseRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, WarehouseRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public WarehouseRecordVo detail(String id) {
        IWarehouseRecordService WarehouseRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(WarehouseRecordService.getById(id), WarehouseRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(WarehouseRecordSaveParam WarehouseRecordSaveParam) {
        WarehouseRecord WarehouseRecord = BeanUtils.copyByClass(WarehouseRecordSaveParam, WarehouseRecord.class);
        return super.save(WarehouseRecord.setId(WarehouseRecord.getCfdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(WarehouseRecordUpdateParam WarehouseRecordUpdateParam) {
        WarehouseRecord warehouseRecord = this.getById(WarehouseRecordUpdateParam.getId());
        Assert.notNull(warehouseRecord, "储备布局仓房信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(WarehouseRecordUpdateParam, WarehouseRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        WarehouseRecord warehouseRecord = this.getById(id);
        Assert.notNull(warehouseRecord, "储备布局仓房信息备案不存在");
        return super.updateById(warehouseRecord.setStatus(status));
    }


    @DataBindService(strategy = DataBindWarehouseRecord.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehouseRecordMapper);
    }
}