package com.sydata.trade.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.InStockSettlementApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindInStockSettlement;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.InStockSettlementReportParam;
import com.sydata.trade.domain.InStockSettlement;
import com.sydata.trade.mapper.InStockSettlementMapper;
import com.sydata.trade.param.InStockSettlementPageParam;
import com.sydata.trade.service.IInStockSettlementService;
import com.sydata.trade.util.BigDecimalUtil;
import com.sydata.trade.vo.InStockSettlementVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.common.constant.ValidConstant.*;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-入库结算信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = InStockSettlementServiceImpl.CACHE_NAME)
@Service("inStockSettlementService")
public class InStockSettlementServiceImpl extends BaseDataImpl<InStockSettlementApiParam, InStockSettlementMapper,
        InStockSettlement, InStockSettlementReportParam> implements IInStockSettlementService {

    final static String CACHE_NAME = "trade:inStockSettlement";

    @Resource
    private InStockSettlementMapper inStockSettlementMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public InStockSettlement getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(InStockSettlement entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(InStockSettlement entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(InStockSettlement entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<InStockSettlementVo> pages(InStockSettlementPageParam pageParam) {
        Page<InStockSettlement> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), InStockSettlement::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), InStockSettlement::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getRkjsdh()), InStockSettlement::getRkjsdh, pageParam.getRkjsdh())
                .eq(isNotEmpty(pageParam.getHwdm()), InStockSettlement::getHwdm, pageParam.getHwdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), InStockSettlement::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), InStockSettlement::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(InStockSettlement::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, InStockSettlementVo.class);
    }

    @DataBindFieldConvert
    @Override
    public InStockSettlementVo detail(String id) {
        IInStockSettlementService stockSettlementService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(stockSettlementService.getById(id), InStockSettlementVo.class);
    }

    @DataBindService(strategy = DataBindInStockSettlement.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, inStockSettlementMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.IN_STOCK_SETTLEMENT;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, InStockSettlementApiParam param) {
        //获取入库结算单号格式
        String rkjsdh = String.format("%s%s", param.getRkjsdh().substring(0, 21),
                LocalDateTimeUtil.format(param.getJssj(), YYYY_MM_DD));
        dataIssueDto.append(param.getRkjsdh().startsWith(rkjsdh),
                InStockSettlementApiParam::getRkjsdh, "入库结算单号(由库点代码+结算日期(yyyyMMdd)+4 位顺序号组成)");


        // 结算单价校验：结算金额/结算数量和结算单价比较其误差不允许超过0.01
        boolean jsdj = BigDecimalUtil.priceCheck(param.getJsje(), param.getJssl(), param.getJsdj(), PRICE_DIFFERENCE);
        dataIssueDto.append(jsdj, InStockSettlementApiParam::getJsdj,
                "结算金额/结算数量和结算单价的误差结果大于" + PRICE_DIFFERENCE);

        //校验合同业务类型
        dataIssueDto.append(!XS_HT.equals(param.getHtywlx()),
                InStockSettlementApiParam::getHth, "入库结算不能关联销售合同");
    }

}
