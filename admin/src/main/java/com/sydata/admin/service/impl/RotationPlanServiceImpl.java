package com.sydata.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.domain.RotationPlan;
import com.sydata.admin.mapper.RotationPlanMapper;
import com.sydata.admin.param.RotationPlanPageParam;
import com.sydata.admin.service.IRotationPlanService;
import com.sydata.admin.vo.RotationPlanVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.admin.RotationPlanApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.admin.annotation.DataBindRotationPlan;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.report.api.param.admin.RotationPlanReportParam;
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
 * 轮换计划主表 服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@CacheConfig(cacheNames = RotationPlanServiceImpl.CACHE_NAME)
@Service("rotationPlanService")
public class RotationPlanServiceImpl
        extends BaseDataImpl<RotationPlanApiParam, RotationPlanMapper, RotationPlan, RotationPlanReportParam>
        implements IRotationPlanService {

    final static String CACHE_NAME = "admin:rotationPlan";

    @Resource
    private RotationPlanMapper rotationPlanMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public RotationPlan getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(RotationPlan entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(RotationPlan entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(RotationPlan entity) {
        return super.removeById(entity);
    }


    @Override
    @DataBindFieldConvert
    public Page<RotationPlanVo> pages(RotationPlanPageParam pageParam) {
        Page<RotationPlan> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), RotationPlan::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getJhxddw()), RotationPlan::getJhxddw, pageParam.getJhxddw())
                .likeRight(isNotEmpty(pageParam.getLhjhdh()), RotationPlan::getLhjhdh, pageParam.getLhjhdh())
                .likeRight(isNotEmpty(pageParam.getJhwh()), RotationPlan::getJhwh, pageParam.getJhwh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), RotationPlan::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), RotationPlan::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(RotationPlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, RotationPlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public RotationPlanVo detail(String id) {
        IRotationPlanService rotationPlanService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(rotationPlanService.getById(id), RotationPlanVo.class);
    }

    @Override
    public String generate(String jhxddw, String jhnd) {
        // 生成自增编码
        String key = new StringJoiner(COLON).add(CACHE_NAME).add(jhxddw).add(jhnd).toString();
        String lhjhdh = GenerateNoUtil.generate(key, 3);

        // 去掉拼接的属性
        return lhjhdh.substring(CACHE_NAME.length()).replace(COLON, EMPTY);
    }

    @DataBindService(strategy = DataBindRotationPlan.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, rotationPlanMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.ROTATION_PLAN;
    }
}
