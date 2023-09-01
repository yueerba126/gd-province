package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Finance;
import com.sydata.basis.enums.FinanceTypeEnum;
import com.sydata.basis.mapper.FinanceMapper;
import com.sydata.basis.param.FinancePageParam;
import com.sydata.basis.param.FinanceReportDetailParam;
import com.sydata.basis.service.IFinanceService;
import com.sydata.basis.vo.FinanceVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.FinanceApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindFinance;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.FinanceReportParam;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sydata.collect.api.enums.DataApiEnum.FINANCE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 财务报信息Service业务层处理
 *
 * @author system
 * @date 2022-12-07
 */
@CacheConfig(cacheNames = FinanceServiceImpl.CACHE_NAME)
@Service("financeService")
public class FinanceServiceImpl extends BaseDataImpl<FinanceApiParam, FinanceMapper, Finance, FinanceReportParam>
        implements IFinanceService {

    final static String CACHE_NAME = "basis:finance";

    @Resource
    private FinanceMapper financeMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Finance getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Finance entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Finance entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Finance entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<FinanceVo> pages(FinancePageParam pageParam) {
        Page<Finance> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Finance::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Finance::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getBbm()), Finance::getBbm, pageParam.getBbm())
                .eq(isNotEmpty(pageParam.getZbmc()), Finance::getZbmc, pageParam.getZbmc())
                .eq(isNotEmpty(pageParam.getType()), Finance::getType, pageParam.getType())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Finance::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Finance::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Finance::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, FinanceVo.class);
    }

    @DataBindFieldConvert
    @Override
    public FinanceVo detail(String id) {
        IFinanceService financeService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(financeService.getById(id), FinanceVo.class);
    }

    @DataBindFieldConvert
    @Override
    public Page<FinanceVo> reportPages(FinancePageParam pageParam) {
        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        Page<FinanceVo> pageVo = financeMapper.reportPages(page, pageParam);
        return BeanUtils.copyToPage(pageVo, FinanceVo.class);
    }

    @Override
    public Map<Integer, Finance> reportDetail(FinanceReportDetailParam param) {
        return super.lambdaQuery()
                .select(Finance::getZbxh, Finance::getZbmc, Finance::getZbz1, Finance::getZbz2)
                .eq(Finance::getDwdm, param.getDwdm())
                .eq(Finance::getBbsj, param.getBbsj())
                .eq(Finance::getBbm, param.getBbm())
                .list()
                .stream()
                .collect(Collectors.toMap(Finance::getZbxh, Function.identity()));
    }

    @DataBindService(strategy = DataBindFinance.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, financeMapper);
    }

    @Override
    public DataApiEnum api() {
        return FINANCE;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, FinanceApiParam param) {
        // 报表时间校验
        FinanceTypeEnum typeEnum = FinanceTypeEnum.getByLength(param.getBbsj().length());
        dataIssueDto.append(isNotEmpty(typeEnum), FinanceApiParam::getBbsj, "报表时间长度只能是<4,6,8>位");
        dataIssueDto.append(typeEnum.checkDate(param.getBbsj()), FinanceApiParam::getBbsj, "报表时间格式错误");

        // 校验序号名称与指标名称是否一致
        boolean nameState = StreamEx.of(param.getZcfzbzbmc())
                .append(param.getXjllbzbmc())
                .append(param.getLrbzbmc())
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("指标名称不存在"))
                .equals(param.getZbmc());
        dataIssueDto.append(nameState, FinanceApiParam::getZbmc, "指标序号对应的指标名称与传参指标名称不对应");
    }

    @Override
    public Finance toEntity(FinanceApiParam param) {
        // 设置报表类型
        return super.toEntity(param)
                .setType(FinanceTypeEnum.getByLength(param.getBbsj().length()).getType());
    }
}