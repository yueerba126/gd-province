package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.service.ICargoService;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.VentilationApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.manage.annotation.DataBindVentilation;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.Ventilation;
import com.sydata.manage.mapper.VentilationMapper;
import com.sydata.manage.param.VentilationPageParam;
import com.sydata.manage.service.IVentilationService;
import com.sydata.manage.vo.VentilationVo;
import com.sydata.report.api.param.manage.VentilationReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static cn.hutool.core.date.TemporalAccessorUtil.format;
import static com.sydata.collect.api.enums.DataApiEnum.VENTILATION;
import static com.sydata.common.constant.ValidConstant.YYYY_MM_DD;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮库管理-通风作业Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = VentilationServiceImpl.CACHE_NAME)
@Service("ventilationService")
public class VentilationServiceImpl
        extends BaseDataImpl<VentilationApiParam, VentilationMapper, Ventilation, VentilationReportParam>
        implements IVentilationService {
    final static String CACHE_NAME = "manage:ventilation";

    @Resource
    private VentilationMapper ventilationMapper;

    @Resource
    private ICargoService cargoService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Ventilation getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Ventilation entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Ventilation entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Ventilation entity) {
        return super.removeById(entity);
    }


    @DataBindFieldConvert
    @Override
    public Page<VentilationVo> pages(VentilationPageParam pageParam) {
        Page<Ventilation> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Ventilation::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Ventilation::getEnterpriseId, pageParam.getEnterpriseId())
                .ge(isNotEmpty(pageParam.getBeginTfrq()), Ventilation::getTfrq, pageParam.getBeginTfrq())
                .le(isNotEmpty(pageParam.getEndTfrq()), Ventilation::getTfrq, pageParam.getEndTfrq())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Ventilation::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Ventilation::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Ventilation::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, VentilationVo.class);
    }

    @DataBindFieldConvert
    @Override
    public VentilationVo detail(String id) {
        IVentilationService ventilationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(ventilationService.getById(id), VentilationVo.class);
    }

    @DataBindService(strategy = DataBindVentilation.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, ventilationMapper);
    }

    @Override
    public DataApiEnum api() {
        return VENTILATION;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, VentilationApiParam param) {
        dataIssueDto.append(param.getTfzydh().substring(30, 38).equals(format(param.getTfrq(), YYYY_MM_DD)),
                VentilationApiParam::getTfzydh, "通风作业日期与通风作业单号中的日期不一致");
    }
}