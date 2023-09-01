package com.sydata.reserve.manage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.plan.PlanManageApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.manage.domain.PlanManageAudit;
import com.sydata.reserve.manage.domain.PlanManageAuditDtl;
import com.sydata.reserve.manage.enums.ManageBillStatusEnum;
import com.sydata.reserve.manage.mapper.PlanManageAuditMapper;
import com.sydata.reserve.manage.param.PlanManageAuditPageParam;
import com.sydata.reserve.manage.param.PlanManageAuditParam;
import com.sydata.reserve.manage.service.IPlanManageAuditDtlService;
import com.sydata.reserve.manage.service.IPlanManageAuditService;
import com.sydata.reserve.manage.vo.PlanManageAuditDetailVo;
import com.sydata.reserve.manage.vo.PlanManageAuditVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sydata.collect.api.enums.DataApiEnum.PLAN_MANAGE;

/**
 * 轮换计划审核Service业务层处理
 *
 * @author fuql
 * @date 2023-05-31
 */
@CacheConfig(cacheNames = PlanManageAuditServiceImpl.CACHE_NAME)
@Service("planManageAuditService")
public class PlanManageAuditServiceImpl extends BaseDataImpl<PlanManageApiParam, PlanManageAuditMapper, PlanManageAudit, Object>
        implements IPlanManageAuditService {

    final static String CACHE_NAME = "record:manage";

    @Resource
    private PlanManageAuditMapper planManageAuditMapper;

    @Resource
    private IPlanManageAuditDtlService planManageAuditDtlService;


    @DataBindFieldConvert
    @Override
    public Page<PlanManageAuditVo> pageData(PlanManageAuditPageParam param) {
        Page<PlanManageAuditVo> planVoPage = planManageAuditMapper
                .pageData(new Page<>(param.getPageNum(), param.getPageSize()), param);
        if (CollectionUtils.isEmpty(planVoPage.getRecords())) {
            return planVoPage;
        }
        List<PlanManageAuditVo> records = planVoPage.getRecords();
        List<String> mainIds = StreamEx.of(records)
                .map(PlanManageAuditVo::getId)
                .collect(Collectors.toList());
        List<PlanManageAuditDtl> planDtls = planManageAuditDtlService.lambdaQuery()
                .in(PlanManageAuditDtl::getMainId, mainIds)
                .list();
        Map<String, List<PlanManageAuditDtl>> detailMap = StreamEx.of(planDtls)
                .groupingBy(PlanManageAuditDtl::getMainId);
        records.forEach(main -> {
            boolean detailStatus = Objects.nonNull(detailMap) && !CollectionUtils.isEmpty(detailMap.get(main.getId()));
            if (detailStatus) {
                main.setDetailVos(BeanUtils.copyToList(detailMap.get(main.getId()), PlanManageAuditDetailVo.class));
            }
        });
        return planVoPage.setRecords(records);
    }

    @Override
    public Boolean audit(PlanManageAuditParam param) {
        PlanManageAudit audit = super.getById(param.getLhjhdh());
        Assert.state(ManageBillStatusEnum.AUDIT.getStatusCode().equals(audit.getBillStatus()), "审核时：修改单据状态失败(请检查单据是否为待审状态)");
        audit.setBillStatus(param.getBillStatus());
        if (ManageBillStatusEnum.AUDIT_PASS.getStatusCode().equals(param.getBillStatus())) {
            audit.setBillStatusName(ManageBillStatusEnum.AUDIT_PASS.getStatusName());
        }
        if (ManageBillStatusEnum.AUDIT_FAILED.getStatusCode().equals(param.getBillStatus())) {
            audit.setBillStatusName(ManageBillStatusEnum.AUDIT_FAILED.getStatusName());
        }
        super.updateById(audit);
        //将审核结果下发到市平台
        return Boolean.TRUE;
    }


    @Override
    public DataApiEnum api() {
        return PLAN_MANAGE;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(PlanManageAudit entity) {
        return super.removeById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(PlanManageAudit entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(PlanManageAudit entity) {
        return super.save(entity);
    }

    @Cacheable(key = "'id='+#id")
    @Override
    public PlanManageAudit getById(Serializable id) {
        return super.getById(id);
    }

    /**
     * 新增详情
     *
     * @param param param
     * @param planManageAudit planManageAudit
     */
    @Override
    protected void collectAfter(PlanManageApiParam param, PlanManageAudit planManageAudit) {
        List<PlanManageApiParam.PlanManageApiDetailParam> detailVos = param.getDetail();
        planManageAuditDtlService.lambdaUpdate()
                .eq(PlanManageAuditDtl::getMainId, planManageAudit.getId()).remove();
        List<PlanManageAuditDtl> auditDtls = StreamEx.of(detailVos)
                .map(v -> BeanUtils.copyByClass(v, PlanManageAuditDtl.class)
                        .setId(planManageAudit.getLhjhdh() + v.getDetailId())
                        .setMainId(planManageAudit.getId())
                        .setLhjhdh(planManageAudit.getLhjhdh())
                        .setDetailId(v.getDetailId())
                        .setPlanMainId(v.getMainId()))
                .toList();
        planManageAuditDtlService.saveBatch(auditDtls);
    }

}
