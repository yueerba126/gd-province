package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.PestInformationApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.manage.annotation.DataBindPestInformation;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.PestInformation;
import com.sydata.manage.mapper.PestInformationMapper;
import com.sydata.manage.param.PestInformationPageParam;
import com.sydata.manage.service.IPestInformationService;
import com.sydata.manage.vo.PestInformationVo;
import com.sydata.report.api.param.manage.PestInformationReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.PEST_INFORMATION;
import static com.sydata.common.constant.ValidConstant.ILLEGAL_NAME;
import static jodd.util.StringPool.HASH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * <p>
 * 虫害信息表 服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@CacheConfig(cacheNames = PestInformationServiceImpl.CACHE_NAME)
@Service("pestInformationService")
public class PestInformationServiceImpl
        extends BaseDataImpl<PestInformationApiParam, PestInformationMapper, PestInformation, PestInformationReportParam>
        implements IPestInformationService {

    final static String CACHE_NAME = "manage:pestInformation";

    @Resource
    private PestInformationMapper pestInformationMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public PestInformation getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(PestInformation entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(PestInformation entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(PestInformation entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<PestInformationVo> pages(PestInformationPageParam pageParam) {
        Page<PestInformation> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getEnterpriseId()), PestInformation::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getId()), PestInformation::getId, pageParam.getId())
                .ge(isNotEmpty(pageParam.getBeginJcsj()), PestInformation::getJcsj, pageParam.getBeginJcsj())
                .le(isNotEmpty(pageParam.getEndJcsj()), PestInformation::getJcsj, pageParam.getEndJcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), PestInformation::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), PestInformation::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(PestInformation::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, PestInformationVo.class);
    }

    @DataBindFieldConvert
    @Override
    public PestInformationVo detail(String id) {
        IPestInformationService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), PestInformationVo.class);
    }

    @DataBindService(strategy = DataBindPestInformation.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, pestInformationMapper);
    }

    @Override
    public DataApiEnum api() {
        return PEST_INFORMATION;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, PestInformationApiParam param) {
        dataIssueDto.append(!ILLEGAL_NAME.contains(param.getFsbw()),
                PestInformationApiParam::getFsbw, "发生部位不能填写无，暂无，空，测试等");

        //害虫种类名称数组
        String[] hczlName = param.getHczlName().split(HASH);
        int length = param.getHczl().split(HASH).length;
        dataIssueDto.append(length == hczlName.length,
                PestInformationApiParam::getHczl, "害虫种类代码中存在非国家或重复的标准代码");
    }
}
