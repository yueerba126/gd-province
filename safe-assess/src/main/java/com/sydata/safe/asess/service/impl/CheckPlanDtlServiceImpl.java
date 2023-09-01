package com.sydata.safe.asess.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.CheckPlanDtl;
import com.sydata.safe.asess.mapper.CheckPlanDtlMapper;
import com.sydata.safe.asess.service.ICheckPlanDtlService;
import com.sydata.safe.asess.vo.CheckPlanDtlVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sydata.safe.asess.enums.CheckPlanStateEnum.*;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * 粮食安全考核-抽查计划明细Service业务层处理
 *
 * @author system
 * @date 2023-02-14
 */
@CacheConfig(cacheNames = CheckPlanDtlServiceImpl.CACHE_NAME)
@Service("checkPlanDtlService")
public class CheckPlanDtlServiceImpl extends ServiceImpl<CheckPlanDtlMapper, CheckPlanDtl> implements ICheckPlanDtlService {

    final static String CACHE_NAME = "safeAssess:checkPlanDtl";

    @Override
    public List<String> orgAssessIds(List<String> orgAssessIds) {
        return isEmpty(orgAssessIds) ? Collections.emptyList() : super.lambdaQuery()
                .select(CheckPlanDtl::getOrgAssessId)
                .in(CheckPlanDtl::getOrgAssessId, orgAssessIds)
                .list()
                .stream()
                .map(CheckPlanDtl::getOrgAssessId)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "'planId='+#planId")
    @DataBindFieldConvert
    @Override
    public List<CheckPlanDtlVo> listByPlanId(String planId) {
        List<CheckPlanDtl> list = super.lambdaQuery().eq(CheckPlanDtl::getPlanId, planId).list();
        return BeanUtils.copyToList(list, CheckPlanDtlVo.class);
    }

    @CacheEvict(key = "'planId='+#planId")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean setUp(String planId, List<CheckPlanDtl> list) {
        // 从参数抽查计划明细列表检查是否重复设置了单位考核ID
        List<String> orgAssessIds = StreamEx.of(list).map(CheckPlanDtl::getOrgAssessId).distinct().toList();
        Assert.state(orgAssessIds.size() == list.size(), "单位考核设置重复");

        // 删除抽查计划明细
        removeByPlanIds(Collections.singletonList(planId));

        // 检查数据库是否重复设置了单位考核ID
        Assert.state(isEmpty(this.orgAssessIds(orgAssessIds)), "已有计划设置了相同的单位考核");

        list.forEach(v -> v.setPlanId(planId));
        return super.saveBatch(list);
    }

    @Override
    public boolean removeByPlanIds(List<String> planIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, CheckPlanDtl, CheckPlanDtl>composeExecute()
                .fields(CheckPlanDtl::getPlanId)
                .params(planIds)
                .remove();
        return super.lambdaUpdate().in(CheckPlanDtl::getPlanId, planIds).remove();
    }

    @CacheEvict(key = "'planId='+#planId")
    @Override
    public Boolean push(String planId) {
        return super.lambdaUpdate()
                .eq(CheckPlanDtl::getPlanId, planId)
                .set(CheckPlanDtl::getState, CHECKING.getState())
                .eq(CheckPlanDtl::getState, SAVE.getState())
                .update();
    }

    @CacheEvict(key = "'planId='+#planId")
    @Override
    public void checkSubmit(String id, String planId) {
        boolean state = super.lambdaUpdate()
                .set(CheckPlanDtl::getState, COMPLETE.getState())
                .eq(CheckPlanDtl::getState, CHECKING.getState())
                .eq(CheckPlanDtl::getId, id)
                .update();
        Assert.state(state, "抽查计划明细提交失败,请检查是否为抽查中状态");
    }

    @CacheEvict(key = "'planId='+#planId")
    @Override
    public void checkRevoke(String id, String planId) {
        boolean state = super.lambdaUpdate()
                .set(CheckPlanDtl::getState, CHECKING.getState())
                .eq(CheckPlanDtl::getState, COMPLETE.getState())
                .eq(CheckPlanDtl::getId, id)
                .update();
        Assert.state(state, "抽查计划明细撤回失败,请检查是否为已抽查状态");
    }
}
