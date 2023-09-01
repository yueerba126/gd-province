package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.domain.CheckGroup;
import com.sydata.safe.asess.domain.CheckPlan;
import com.sydata.safe.asess.domain.CheckPlanDtl;
import com.sydata.safe.asess.mapper.CheckPlanMapper;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.ICheckGroupService;
import com.sydata.safe.asess.service.ICheckPlanDtlService;
import com.sydata.safe.asess.service.ICheckPlanService;
import com.sydata.safe.asess.service.IOrgAssessService;
import com.sydata.safe.asess.vo.CheckPlanVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.safe.asess.enums.CheckPlanStateEnum.*;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮食安全考核-抽查计划Service业务层处理
 *
 * @author system
 * @date 2023-02-14
 */
@CacheConfig(cacheNames = CheckPlanServiceImpl.CACHE_NAME)
@Service("checkPlanService")
public class CheckPlanServiceImpl extends ServiceImpl<CheckPlanMapper, CheckPlan> implements ICheckPlanService {

    final static String CACHE_NAME = "safeAssess:checkPlan";

    @Resource
    private CheckPlanMapper checkPlanMapper;

    @Resource
    private ICheckPlanDtlService checkPlanDtlService;

    @Resource
    private ICheckGroupService checkGroupService;

    @Resource
    private IOrgAssessService orgAssessService;

    @Cacheable(key = "'id='+#id")
    @Override
    public CheckPlan getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean removeById(Serializable id) {
        CheckPlan checkPlan = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(SAVE.getState().equals(checkPlan.getPlanState()), "非保存状态不可删除");

        // 删除抽查计划明细
        checkPlanDtlService.removeByPlanIds(Collections.singletonList(checkPlan.getId()));

        // 删除抽查计划
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, CheckPlan, CheckPlan>composeExecute()
                .fields(CheckPlan::getId)
                .params((Collection<String>) list)
                .remove();
        checkPlanDtlService.removeByPlanIds((List<String>) list);
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<CheckPlanVo> pages(CheckPlanPageParam pageParam) {
        Page<CheckPlan> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getPlanName()), CheckPlan::getPlanName, pageParam.getPlanName())
                .eq(isNotEmpty(pageParam.getTemplateId()), CheckPlan::getTemplateId, pageParam.getTemplateId())
                .eq(isNotEmpty(pageParam.getPlanState()), CheckPlan::getPlanState, pageParam.getPlanState())
                .eq(isNotEmpty(pageParam.getPlanYear()), CheckPlan::getPlanYear, pageParam.getPlanYear())
                .eq(isNotEmpty(pageParam.getOrganizeId()), CheckPlan::getOrganizeId, pageParam.getOrganizeId())
                .orderByDesc(CheckPlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CheckPlanVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(CheckPlanSaveParam saveParam) {
        String id = IdWorker.getIdStr();

        // 设置抽查计划明细
        List<CheckPlanDtl> list = saveParam.getList();
        checkPlanDtlService.setUp(id, list);

        // 新增抽查计划
        String checkRegionIds = StreamEx.of(list).map(CheckPlanDtl::getRegionId).joining(COMMA);
        CheckPlan checkPlan = BeanUtils.copyByClass(saveParam, CheckPlan.class)
                .setId(id)
                .setCheckRegionIds(checkRegionIds)
                .setRegionCount(list.size())
                .setRegionCheckedCount(ZERO.intValue())
                .setPlanState(SAVE.getState());
        return super.save(checkPlan);
    }

    @CacheEvict(key = "'id='+#updateParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(CheckPlanUpdateParam updateParam) {
        // 设置抽查计划明细
        List<CheckPlanDtl> list = updateParam.getList();
        checkPlanDtlService.setUp(updateParam.getId(), list);

        // 修改抽查计划
        String checkRegionIds = StreamEx.of(list).map(CheckPlanDtl::getRegionId).joining(COMMA);
        CheckPlan checkPlan = BeanUtils.copyByClass(updateParam, CheckPlan.class)
                .setCheckRegionIds(checkRegionIds)
                .setRegionCount(list.size())
                .setRegionCheckedCount(ZERO.intValue())
                .setPlanState(SAVE.getState());
        return super.updateById(checkPlan);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean push(String id) {
        CheckPlan checkPlan = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(checkPlan.getRegionCount() > 0, "没有设置抽查地区不可发布");
        Assert.state(SAVE.getState().equals(checkPlan.getPlanState()), "非保存状态不可发布");

        boolean state = super.lambdaUpdate()
                .set(CheckPlan::getPlanState, CHECKING.getState())
                .eq(CheckPlan::getId, id)
                .eq(CheckPlan::getPlanState, SAVE.getState())
                .update();
        Assert.state(state, "非保存状态不可发布");

        return checkPlanDtlService.push(id);
    }

    @Override
    public Boolean checkAuth(String id) {
        CheckPlan checkPlan = SpringUtil.getBean(this.getClass()).getById(id);
        CheckGroup checkGroup = checkGroupService.getById(checkPlan.getGroupId());
        Assert.notNull(checkGroup, "计划关联的小组不存在");

        // 必须为：小组组长或联络员才能抽查
        Set<String> userIds = SetUtils.hashSet(checkGroup.getLeaderId(), checkGroup.getLiaisonManId());
        return userIds.contains(UserSecurity.loginUser().getId());
    }

    @CacheEvict(key = "'id='+#checkParam.planId")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean check(CheckPlanCheckParam checkParam) {
        // 校验是否有抽查权限
        Boolean auth = checkAuth(checkParam.getPlanId());
        Assert.state(auth, "无权抽查该计划");

        // 校验抽查计划状态
        CheckPlan checkPlan = SpringUtil.getBean(this.getClass()).getById(checkParam.getPlanId());
        Assert.state(CHECKING.getState().equals(checkPlan.getPlanState()), "非抽查中的计划状态不可抽查");

        // 校验抽查计划明细和计划关联性，和状态是否为抽查中
        CheckPlanDtl checkPlanDtl = StreamEx.of(checkPlanDtlService.listByPlanId(checkPlan.getId()))
                .toMap(CheckPlanDtl::getId, Function.identity())
                .get(checkParam.getDtlId());
        boolean relationship = checkPlanDtl != null && checkPlanDtl.getPlanId().equals(checkPlan.getId());
        Assert.state(relationship, "抽查计划明细与该抽查计划无关");
        Assert.state(CHECKING.getState().equals(checkPlanDtl.getState()), "非抽查中的计划明细状态不可抽查");


        // 更新抽查计划明细状态、抽查计划统计明细提交数量
        boolean isSubmit = COMPLETE.getState().equals(checkParam.getState());
        if (isSubmit) {
            checkPlanDtlService.checkSubmit(checkPlanDtl.getId(), checkPlan.getId());
            checkPlanMapper.checkSubmit(checkPlan.getId(), 1);
        }

        // 单位考核设置抽查结果
        return orgAssessService.check(checkPlanDtl.getOrgAssessId(), checkPlan.getTemplateId(), checkParam.getList());
    }

    @CacheEvict(key = "'id='+#planId")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean revoke(String planId, String dtlId) {
        // 校验是否有抽查权限
        Assert.state(checkAuth(planId), "无权撤销该计划");

        checkPlanDtlService.checkRevoke(dtlId, planId);
        return checkPlanMapper.checkSubmit(planId, -1);
    }

    @Override
    public Boolean uploadCheckFiles(CheckPlanUploadFileParam uploadFileParam) {
        // 校验是否有抽查权限
        Boolean auth = checkAuth(uploadFileParam.getPlanId());
        Assert.state(auth, "无权上传该计划的抽查文件");

        return orgAssessService.uploadCheckFiles(uploadFileParam);
    }

    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(CheckPlan::getId)
                .eq(CheckPlan::getTemplateId, templateId)
                .list()
                .stream()
                .map(CheckPlan::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Override
    public Boolean operationAuth(String id) {
        CheckPlan checkPlan = SpringUtil.getBean(this.getClass()).getById(id);
        return UserSecurity.organizeId().equals(checkPlan.getOrganizeId());
    }
}
