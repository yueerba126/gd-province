package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindStockHouseRecord;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.StockHouseRecord;
import com.sydata.reserve.layout.mapper.StockHouseRecordMapper;
import com.sydata.reserve.layout.param.StockHouseRecordPageParam;
import com.sydata.reserve.layout.param.StockHouseRecordSaveParam;
import com.sydata.reserve.layout.param.StockHouseRecordUpdateParam;
import com.sydata.reserve.layout.service.IStockHouseRecordService;
import com.sydata.reserve.layout.utils.RegionUtil;
import com.sydata.reserve.layout.vo.StockHouseRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-库区信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = StockHouseRecordServiceImpl.CACHE_NAME)
@Service("stockHouseRecordService")
public class StockHouseRecordServiceImpl
        extends ServiceImpl<StockHouseRecordMapper, StockHouseRecord>
        implements IStockHouseRecordService {

    final static String CACHE_NAME = "reserveLayout:StockHouseRecord";

    @Resource
    private StockHouseRecordMapper stockHouseRecordMapper;

    @Override
    public boolean removeById(StockHouseRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<StockHouseRecordVo> pages(StockHouseRecordPageParam pageParam) {
        pageParam.setXzqhdm(RegionUtil.regionQuery(pageParam.getXzqhdm()));
        Page<StockHouseRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getDwmc()), StockHouseRecord::getDwmc, pageParam.getDwmc())
                .like(isNotEmpty(pageParam.getKqmc()), StockHouseRecord::getKqmc, pageParam.getKqmc())
                .likeRight(isNotEmpty(pageParam.getXzqhdm()), StockHouseRecord::getXzqhdm, pageParam.getXzqhdm())
                .eq(isNotEmpty(pageParam.getStatus()), StockHouseRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockHouseRecord::getDwdm, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getId()), StockHouseRecord::getId, pageParam.getId())
                .orderByDesc(StockHouseRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, StockHouseRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public StockHouseRecordVo detail(String id) {
        IStockHouseRecordService StockHouseRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(StockHouseRecordService.getById(id), StockHouseRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(StockHouseRecordSaveParam stockHouseRecordSaveParam) {
        StockHouseRecord StockHouseRecord = BeanUtils.copyByClass(stockHouseRecordSaveParam, StockHouseRecord.class);
        return super.save(StockHouseRecord.setId(StockHouseRecord.getKqdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(StockHouseRecordUpdateParam stockHouseRecordUpdateParam) {
        StockHouseRecord stockHouseRecord = this.getById(stockHouseRecordUpdateParam.getId());
        Assert.notNull(stockHouseRecord, "储备布局库区信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(stockHouseRecordUpdateParam, StockHouseRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        StockHouseRecord stockHouseRecord = this.getById(id);
        Assert.notNull(stockHouseRecord, "储备布局库区信息备案不存在");
        return super.updateById(stockHouseRecord.setStatus(status));
    }

    @DataBindService(strategy = DataBindStockHouseRecord.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, stockHouseRecordMapper);
    }

}