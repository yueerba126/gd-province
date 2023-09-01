package com.sydata.manage.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.SafetyCheckApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.manage.annotation.DataBindSafetyCheck;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.SafetyCheck;
import com.sydata.manage.mapper.SafetyCheckMapper;
import com.sydata.manage.param.SafetyCheckPageParam;
import com.sydata.manage.service.ISafetyCheckService;
import com.sydata.manage.vo.SafetyCheckVo;
import com.sydata.report.api.param.manage.SafetyCheckReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.SAFETY_CHECK;
import static com.sydata.common.constant.ValidConstant.YYYY_MM_DD;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 安全管理接口实现类
 *
 * @author lzq
 * @date 2022/8/19 11:26
 */
@CacheConfig(cacheNames = SafetyCheckServiceImpl.CACHE_NAME)
@Service("safetyCheckService")
public class SafetyCheckServiceImpl
        extends BaseDataImpl<SafetyCheckApiParam, SafetyCheckMapper, SafetyCheck, SafetyCheckReportParam>
        implements ISafetyCheckService {

    final static String CACHE_NAME = "manage:safetyCheck";

    @Resource
    private SafetyCheckMapper safetyCheckMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public SafetyCheck getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(SafetyCheck entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(SafetyCheck entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(SafetyCheck entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<SafetyCheckVo> pages(SafetyCheckPageParam pageParam) {
        Page<SafetyCheck> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), SafetyCheck::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), SafetyCheck::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), SafetyCheck::getStockHouseId, pageParam.getStockHouseId())
                .ge(isNotEmpty(pageParam.getBeginZgsx()), SafetyCheck::getZgsx, pageParam.getBeginZgsx())
                .le(isNotEmpty(pageParam.getEndZgsx()), SafetyCheck::getZgsx, pageParam.getEndZgsx())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), SafetyCheck::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), SafetyCheck::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(SafetyCheck::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, SafetyCheckVo.class);
    }

    @DataBindFieldConvert
    @Override
    public SafetyCheckVo detail(String id) {
        ISafetyCheckService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), SafetyCheckVo.class);
    }

    @DataBindService(strategy = DataBindSafetyCheck.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, safetyCheckMapper);
    }

    @Override
    public DataApiEnum api() {
        return SAFETY_CHECK;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, SafetyCheckApiParam param) {
        //风险识别日期不能超过当前日期
        LocalDate fxsbrq = LocalDateTimeUtil.parseDate(param.getFxdbm().substring(6, 14), YYYY_MM_DD);
        dataIssueDto.append(LocalDate.now().compareTo(fxsbrq) > -1,
                SafetyCheckApiParam::getFxdbm, " 风险点编码中的风险识别日期不能大于当前日期");
    }
}
