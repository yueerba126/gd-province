package com.sydata.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.domain.RotationPlanDtl;
import com.sydata.admin.mapper.RotationPlanDtlMapper;
import com.sydata.admin.param.RotationPlanDtlPageParam;
import com.sydata.admin.service.IRotationPlanDtlService;
import com.sydata.admin.vo.RotationPlanDtlVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.admin.RotationPlanDtlApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.admin.annotation.DataBindRotationPlanDtl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.report.api.param.admin.RotationPlanDtlReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * <p>
 * 储备计划明细表 服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@CacheConfig(cacheNames = RotationPlanDtlServiceImpl.CACHE_NAME)
@Service("rotationPlanDtlService")
public class RotationPlanDtlServiceImpl
        extends BaseDataImpl<RotationPlanDtlApiParam, RotationPlanDtlMapper, RotationPlanDtl, RotationPlanDtlReportParam>
        implements IRotationPlanDtlService {

    final static String CACHE_NAME = "admin:rotationPlanDtl";


    @Resource
    private RotationPlanDtlMapper rotationPlanDtlMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public RotationPlanDtl getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(RotationPlanDtl entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(RotationPlanDtl entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(RotationPlanDtl entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<RotationPlanDtlVo> pages(RotationPlanDtlPageParam pageParam) {
        Page<RotationPlanDtl> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), RotationPlanDtl::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getLhjhdh()), RotationPlanDtl::getLhjhdh, pageParam.getLhjhdh())
                .likeRight(isNotEmpty(pageParam.getJhmxdh()), RotationPlanDtl::getJhmxdh, pageParam.getJhmxdh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), RotationPlanDtl::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), RotationPlanDtl::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(RotationPlanDtl::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, RotationPlanDtlVo.class);
    }

    @DataBindFieldConvert
    @Override
    public RotationPlanDtlVo detail(String id) {
        IRotationPlanDtlService rotationPlanDtlService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(rotationPlanDtlService.getById(id), RotationPlanDtlVo.class);
    }

    @Override
    public String generate(String lhjhdh, String hwdm) {
        // 生成自增编码
        String key = new StringJoiner(COLON).add(CACHE_NAME).add(lhjhdh).add(hwdm.substring(0, 21)).toString();
        String jhmxdh = GenerateNoUtil.generate(key, 4);

        // 去掉拼接的属性
        return jhmxdh.substring(CACHE_NAME.length()).replace(COLON, EMPTY);
    }

    @DataBindService(strategy = DataBindRotationPlanDtl.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, rotationPlanDtlMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.ROTATION_PLAN_DTL;
    }
}
