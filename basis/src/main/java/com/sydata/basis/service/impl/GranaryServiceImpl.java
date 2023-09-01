package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Granary;
import com.sydata.basis.dto.GranaryQualityCheckDto;
import com.sydata.basis.mapper.GranaryMapper;
import com.sydata.basis.param.GranaryPageParam;
import com.sydata.basis.service.IGranaryService;
import com.sydata.basis.vo.GranaryVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.GranaryApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindGranary;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.report.api.param.basis.GranaryReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.sydata.collect.api.enums.DataApiEnum.GRANARY;
import static com.sydata.common.constant.ValidConstant.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-廒间信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = GranaryServiceImpl.CACHE_NAME)
@Service("granaryService")
public class GranaryServiceImpl extends BaseDataImpl<GranaryApiParam, GranaryMapper, Granary, GranaryReportParam>
        implements IGranaryService {

    final static String CACHE_NAME = "basis:granary";

    @Resource
    private GranaryMapper granaryMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Granary getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Granary entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Granary entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Granary entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<GranaryVo> pages(GranaryPageParam pageParam) {
        Page<Granary> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Granary::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Granary::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getCfdm()), Granary::getCfbh, pageParam.getCfdm())
                .likeRight(isNotEmpty(pageParam.getAjdm()), Granary::getId, pageParam.getAjdm())
                .likeRight(isNotEmpty(pageParam.getAjmc()), Granary::getAjmc, pageParam.getAjmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Granary::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Granary::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Granary::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GranaryVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GranaryVo detail(String id) {
        IGranaryService granaryService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(granaryService.getById(id), GranaryVo.class);
    }

    @DataBindService(strategy = DataBindGranary.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, granaryMapper);
    }

    @Override
    public DataApiEnum api() {
        return GRANARY;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, GranaryApiParam param) {
        // 仓房类型代码不能为空就校验
        boolean isNotCheckZero = false;
        if (StringUtils.isNotBlank(param.getCflxdm())) {
            if (param.getCflxdm().startsWith(QYC) || param.getCflxdm().startsWith(TC)) {

                dataIssueDto.append(param.getAjcd().compareTo(ZERO) == 0, GranaryApiParam::getAjcd,
                        "所属仓房类型为浅圆仓或筒仓时廒间长度只能为0");

                dataIssueDto.append(param.getAjkd().compareTo(ZERO) == 0, GranaryApiParam::getAjkd,
                        "所属仓房类型为浅圆仓或筒仓时廒间宽度只能为0");
                isNotCheckZero = true;
            } else if (param.getCflxdm().startsWith(PFC)) {
                if (nonNull(param.getCfcnc())) {
                    boolean state = param.getAjcd().compareTo(param.getCfcnc()) <= 0;
                    dataIssueDto.append(state, GranaryApiParam::getAjcd, "所属仓房类型为平房仓时廒间长度不能大于所属仓房长度");
                }

                if (nonNull(param.getCfcnk())) {
                    boolean state = param.getAjkd().compareTo(param.getCfcnk()) <= 0;
                    dataIssueDto.append(state, GranaryApiParam::getAjkd, "所属仓房类型为平房仓时廒间宽度不能大于所属仓房宽度");

                }
            }
        }

        boolean ajcd = isNotCheckZero || param.getAjcd().compareTo(ZERO) > 0;
        boolean ajkd = isNotCheckZero || param.getAjkd().compareTo(ZERO) > 0;
        dataIssueDto.append(ajcd, GranaryApiParam::getAjcd, "廒间长度不能小于等于0");
        dataIssueDto.append(ajkd, GranaryApiParam::getAjkd, "廒间宽度不能小于等于0");
    }

    @Override
    public void dataQualityCustomCheck(DataQualityProcessContext context) {
        // 数据质量校验：是否上传了货位信息
        List<GranaryQualityCheckDto> checks = granaryMapper.qualityCheck(context.getDataIds());

        checks.forEach(check -> {
            DataIssueDto dataIssue = context.issueRecord(check.getId());
            dataIssue.append(check.getHws() > 0, GranaryApiParam::getAjmc, "该廒间下未上传货位信息");
        });
    }
}
