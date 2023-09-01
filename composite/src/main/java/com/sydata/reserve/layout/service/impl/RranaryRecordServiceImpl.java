package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindRranaryRecord;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.RranaryRecord;
import com.sydata.reserve.layout.mapper.RranaryRecordMapper;
import com.sydata.reserve.layout.param.RranaryRecordPageParam;
import com.sydata.reserve.layout.param.RranaryRecordSaveParam;
import com.sydata.reserve.layout.param.RranaryRecordUpdateParam;
import com.sydata.reserve.layout.service.IRranaryRecordService;
import com.sydata.reserve.layout.vo.RranaryRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-廒间信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = RranaryRecordServiceImpl.CACHE_NAME)
@Service("rranaryRecordService")
public class RranaryRecordServiceImpl
        extends ServiceImpl<RranaryRecordMapper, RranaryRecord>
        implements IRranaryRecordService {

    final static String CACHE_NAME = "reserveLayout:RranaryRecord";

    @Resource
    private RranaryRecordMapper rranaryRecordMapper;

    @Override
    public boolean removeById(RranaryRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<RranaryRecordVo> pages(RranaryRecordPageParam pageParam) {
        Page<RranaryRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getAjmc()), RranaryRecord::getAjmc, pageParam.getAjmc())
                .eq(isNotEmpty(pageParam.getStatus()), RranaryRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getDwdm()), RranaryRecord::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getId()), RranaryRecord::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getKqdm()), RranaryRecord::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getCfdm()), RranaryRecord::getCfhygdm, pageParam.getCfdm())
                .orderByDesc(RranaryRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, RranaryRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public RranaryRecordVo detail(String id) {
        IRranaryRecordService RranaryRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(RranaryRecordService.getById(id), RranaryRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(RranaryRecordSaveParam RranaryRecordSaveParam) {
        RranaryRecord RranaryRecord = BeanUtils.copyByClass(RranaryRecordSaveParam, RranaryRecord.class);
        return super.save(RranaryRecord.setId(RranaryRecord.getAjdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(RranaryRecordUpdateParam RranaryRecordUpdateParam) {
        RranaryRecord rranaryRecord = this.getById(RranaryRecordUpdateParam.getId());
        Assert.notNull(rranaryRecord, "储备布局地理信息-廒间信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(RranaryRecordUpdateParam, RranaryRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        RranaryRecord rranaryRecord = this.getById(id);
        Assert.notNull(rranaryRecord, "储备布局地理信息-廒间信息备案不存在");
        return super.updateById(rranaryRecord.setStatus(status));
    }

    @DataBindService(strategy = DataBindRranaryRecord.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, rranaryRecordMapper);
    }


}