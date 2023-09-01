package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.ContractApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindContract;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.ContractReportParam;
import com.sydata.trade.domain.Contract;
import com.sydata.trade.mapper.ContractMapper;
import com.sydata.trade.param.ContractPageParam;
import com.sydata.trade.service.IContractService;
import com.sydata.trade.util.BigDecimalUtil;
import com.sydata.trade.vo.ContractVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import static com.sydata.common.constant.ValidConstant.PRICE_DIFFERENCE;
import static com.sydata.framework.util.StringUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-合同Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@Service("contractService")
@CacheConfig(cacheNames = ContractServiceImpl.CACHE_NAME)
public class ContractServiceImpl extends BaseDataImpl<ContractApiParam, ContractMapper, Contract, ContractReportParam>
        implements IContractService {
    final static String CACHE_NAME = "trade:contract";

    @Resource
    private ContractMapper contractMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Contract getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Contract entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Contract entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Contract entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<ContractVo> pages(ContractPageParam pageParam) {
        Page<Contract> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Contract::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Contract::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getHtmc()), Contract::getHtmc, pageParam.getHtmc())
                .eq(isNotEmpty(pageParam.getHth()), Contract::getHth, pageParam.getHth())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Contract::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Contract::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Contract::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ContractVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ContractVo detail(String id) {
        IContractService contractService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(contractService.getById(id), ContractVo.class);
    }

    @DataBindService(strategy = DataBindContract.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, contractMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.CONTRACT;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, ContractApiParam param) {
        // 合同单价校验：合同总金额/(约定购销粮食数量/1000)和合同单价比较其误差不允许超过0.01
        BigDecimal ydgxlssl = param.getYdgxlssl().movePointLeft(3);
        boolean htdj = BigDecimalUtil.priceCheck(param.getHtzje(), ydgxlssl, param.getHtdj(), PRICE_DIFFERENCE);
        dataIssueDto.append(htdj, ContractApiParam::getHtdj,
                "合同总金额/(约定购销数量/1000)与合同单价的误差结果大于" + PRICE_DIFFERENCE);

        // 结算价格校验：结算总金额/(履约数量/1000)和结算价格比较其误差不允许超过0.01
        if (param.getJszje() != null) {
            BigDecimal lysl = param.getLysl().movePointLeft(3);
            boolean jsjg = BigDecimalUtil.priceCheck(param.getJszje(), lysl, param.getJsjg(), PRICE_DIFFERENCE);
            dataIssueDto.append(jsjg, ContractApiParam::getJsjg,
                    "结算总金额/(履约数量/1000)与结算价格的误差结果大于" + PRICE_DIFFERENCE);
        }

        // 客户名称校验
        boolean isCustomerName = isEmpty(param.getCustomerName()) || param.getCustomerName().equals(param.getKhmc());
        dataIssueDto.append(isCustomerName, ContractApiParam::getKhmc,
                "填写的客户名称与实际客户名称不符,实际客户名称为:" + param.getCustomerName());

    }
}
