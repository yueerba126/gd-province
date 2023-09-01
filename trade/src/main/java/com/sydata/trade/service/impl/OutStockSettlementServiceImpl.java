package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.OutStockSettlementApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindOutStockSettlement;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.OutStockSettlementReportParam;
import com.sydata.trade.domain.OutStockSettlement;
import com.sydata.trade.mapper.OutStockSettlementMapper;
import com.sydata.trade.param.OutStockSettlementPageParam;
import com.sydata.trade.service.IOutStockSettlementService;
import com.sydata.trade.util.BigDecimalUtil;
import com.sydata.trade.vo.OutStockSettlementVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static cn.hutool.core.date.LocalDateTimeUtil.format;
import static com.sydata.common.constant.ValidConstant.*;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-出库结算信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = OutStockSettlementServiceImpl.CACHE_NAME)
@Service("outStockSettlementService")
public class OutStockSettlementServiceImpl extends BaseDataImpl<OutStockSettlementApiParam, OutStockSettlementMapper,
        OutStockSettlement, OutStockSettlementReportParam> implements IOutStockSettlementService {

    final static String CACHE_NAME = "trade:outStockSettlement";

    @Resource
    private OutStockSettlementMapper outStockSettlementMapper;

    @Resource
    private IStockHouseService stockHouseService;

    @Cacheable(key = "'id='+#id")
    @Override
    public OutStockSettlement getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(OutStockSettlement entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(OutStockSettlement entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(OutStockSettlement entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<OutStockSettlementVo> pages(OutStockSettlementPageParam pageParam) {
        Page<OutStockSettlement> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), OutStockSettlement::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), OutStockSettlement::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getCkjsdh()), OutStockSettlement::getCkjsdh, pageParam.getCkjsdh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), OutStockSettlement::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), OutStockSettlement::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(OutStockSettlement::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OutStockSettlementVo.class);
    }


    @DataBindFieldConvert
    @Override
    public OutStockSettlementVo detail(String id) {
        IOutStockSettlementService outStockSettlementService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(outStockSettlementService.getById(id), OutStockSettlementVo.class);
    }

    @DataBindService(strategy = DataBindOutStockSettlement.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, outStockSettlementMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.OUT_STOCK_SETTLEMENT;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, OutStockSettlementApiParam param) {
        dataIssueDto.append(param.getCkjsdh().substring(21, 29).equals(format(param.getJssj(), YYYY_MM_DD)),
                OutStockSettlementApiParam::getCkjsdh, "出库结算单号(由库点代码+结算日期(yyyyMMdd)+4 位顺序号组成)");

        // 结算单价校验：结算金额/结算数量和结算单价比较其误差不允许超过0.01
        boolean jsdj = BigDecimalUtil.priceCheck(param.getJsje(), param.getJssl(), param.getJsdj(), PRICE_DIFFERENCE);
        dataIssueDto.append(jsdj, OutStockSettlementApiParam::getJsdj,
                "结算金额/结算数量和结算单价的误差结果大于" + PRICE_DIFFERENCE);

        //校验合同业务类型
        dataIssueDto.append(!CG_HT.equals(param.getHtywlx()), OutStockSettlementApiParam::getHth,
                "出库结算不能关联采购合同");
    }

}
