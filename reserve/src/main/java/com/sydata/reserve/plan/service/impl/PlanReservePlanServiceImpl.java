package com.sydata.reserve.plan.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.YesNo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.reserve.http.service.PlanReserveFactoryService;
import com.sydata.reserve.http.vo.DistributionResultVo;
import com.sydata.reserve.plan.domain.PlanReservePlan;
import com.sydata.reserve.plan.domain.PlanReservePlanDtl;
import com.sydata.reserve.plan.domain.PlanReservePlanLog;
import com.sydata.reserve.plan.domain.PlanReservePlanSendLog;
import com.sydata.reserve.plan.enums.BillStatusEnum;
import com.sydata.reserve.plan.enums.PlanReturnStatusEnum;
import com.sydata.reserve.plan.mapper.PlanReservePlanMapper;
import com.sydata.reserve.plan.param.*;
import com.sydata.reserve.plan.service.IPlanReservePlanDtlService;
import com.sydata.reserve.plan.service.IPlanReservePlanLogService;
import com.sydata.reserve.plan.service.IPlanReservePlanSendLogService;
import com.sydata.reserve.plan.service.IPlanReservePlanService;
import com.sydata.reserve.plan.vo.PlanReservePlanDtlVo;
import com.sydata.reserve.plan.vo.PlanReservePlanLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanSendLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * 储备计划管理-储备计划Service业务层处理
 *
 * @author fuql
 * @date 2023-05-19
 */
@Service("planReservePlanService")
public class PlanReservePlanServiceImpl extends ServiceImpl<PlanReservePlanMapper, PlanReservePlan> implements IPlanReservePlanService {

    @Resource
    private PlanReservePlanMapper planReservePlanMapper;

    @Resource
    private IPlanReservePlanDtlService planReservePlanDtlService;

    @Resource
    private IPlanReservePlanLogService planReservePlanLogService;

    @Resource
    private PlanReserveFactoryService planReserveFactoryService;

    @Resource
    private IPlanReservePlanSendLogService planReservePlanSendLogService;

    final static String CACHE_NAME = "plan:reserve";


    @DataBindFieldConvert
    @Override
    public Page<PlanReservePlanVo> pageData(PlanReservePlanPageParam param) {
        Page<PlanReservePlanVo> planVoPage = planReservePlanMapper
                .pageData(new Page<>(param.getPageNum(), param.getPageSize()), param);
        if (CollectionUtils.isEmpty(planVoPage.getRecords())) {
            return planVoPage;
        }
        List<PlanReservePlanVo> records = planVoPage.getRecords();
        List<String> mainIds = StreamEx.of(records)
                .map(PlanReservePlanVo::getId)
                .collect(Collectors.toList());
        List<PlanReservePlanDtl> planDtls = planReservePlanDtlService.lambdaQuery()
                .ne(PlanReservePlanDtl::getBillStatus , BillStatusEnum.ABOLISH.getStatusCode())
                .in(PlanReservePlanDtl::getMainId, mainIds)
                .list();
        List<PlanReservePlanLog> planLogs = planReservePlanLogService.lambdaQuery()
                .in(PlanReservePlanLog::getMainId, mainIds)
                .list();
        Map<String, List<PlanReservePlanDtl>> detailMap = StreamEx.of(planDtls)
                .groupingBy(PlanReservePlanDtl::getMainId);
        Map<String, List<PlanReservePlanLog>> logMap = StreamEx.of(planLogs)
                .groupingBy(PlanReservePlanLog::getMainId);
        records.forEach(main->{
            boolean logStatus = Objects.nonNull(logMap) && !CollectionUtils.isEmpty(logMap.get(main.getId()));
            if (logStatus) {
                main.setLogVos(BeanUtils.copyToList(logMap.get(main.getId()), PlanReservePlanLogVo.class));
            }
            boolean detailStatus = Objects.nonNull(detailMap) && !CollectionUtils.isEmpty(detailMap.get(main.getId()));
            if (detailStatus) {
                main.setDetailVos(BeanUtils.copyToList(detailMap.get(main.getId()), PlanReservePlanDtlVo.class));
            }
        });
        return planVoPage.setRecords(records);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(PlanReservePlanSaveParam param) {
        PlanReservePlan planReservePlan = BeanUtils.copyByClass(param.getMainBill(), PlanReservePlan.class);
        planReservePlan.setBillStatus(BillStatusEnum.SAVE.getStatusCode());
        super.save(planReservePlan);
        List<PlanReservePlanDtl> details = param.getDetails();
        if (!CollectionUtils.isEmpty(details)) {
            details.forEach(sc-> sc.setMainId(planReservePlan.getId()).setBillStatus(BillStatusEnum.SAVE.getStatusCode()));
            planReservePlanDtlService.saveBatch(details);
        }
        //初始划调整记录
        List<PlanReservePlanLog> planLogs = new ArrayList<>();
        details.forEach(sc->{
            PlanReservePlanLog log = new PlanReservePlanLog();
            planLogs.add(log.setAmount(sc.getAmount())
                    .setDetailId(sc.getId())
                    .setMainId(sc.getMainId())
                    .setLsdjdm(sc.getLsdjdm())
                    .setRemark(sc.getRemark())
                    .setRemarks(sc.getRemarks())
                    .setYlpz(sc.getYlpz()));
        });
        //新增调整记录
        planReservePlanLogService.saveBatch(planLogs);
        return planReservePlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(PlanReservePlanUpdateParam param) {
        PlanReservePlan mainBill = param.getMainBill();
        Assert.notNull(mainBill, "需修改的主表信息不能为空！");
        String mainId = mainBill.getId();
        // 校验是否是拟稿状态
        List<PlanReservePlan> statusList = super.lambdaQuery()
                .eq(PlanReservePlan::getId, mainId)
                .eq(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .list();
        Assert.state(!CollectionUtils.isEmpty(statusList), "修改时：修改单据失败(请检查单据是否为拟稿状态)");
        super.updateById(mainBill);
        //删除明细
        if (CollectionUtils.isNotEmpty(param.getDeleteDetailIds())) {
            planReservePlanDtlService.lambdaUpdate()
                    .in(PlanReservePlanDtl::getId ,param.getDeleteDetailIds() )
                    .set(PlanReservePlanDtl::getBillStatus , BillStatusEnum.ABOLISH.getStatusCode())
                    .update();
        }
        //修改明细
        if (CollectionUtils.isNotEmpty(param.getUpdateDetailParams())) {
            planReservePlanDtlService.updateBatchById(param.getUpdateDetailParams());
        }
        //新增明细
        if (CollectionUtils.isNotEmpty(param.getAddDetailParams())) {
            param.getAddDetailParams().forEach(sc-> sc.setMainId(mainBill.getId()).setBillStatus(BillStatusEnum.SAVE.getStatusCode()));
            planReservePlanDtlService.saveBatch(param.getAddDetailParams());
        }
        //初始划调整记录,先删后加
        List<PlanReservePlanDtl> details = planReservePlanDtlService.lambdaQuery()
                .eq(PlanReservePlanDtl::getMainId, param.getMainBill().getId())
                .eq(PlanReservePlanDtl::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .list();
        List<PlanReservePlanLog> planLogs = new ArrayList<>();
        details.forEach(sc->{
            PlanReservePlanLog log = new PlanReservePlanLog();
            planLogs.add(log.setAmount(sc.getAmount())
                    .setDetailId(sc.getId())
                    .setMainId(sc.getMainId())
                    .setLsdjdm(sc.getLsdjdm())
                    .setRemark(sc.getRemark())
                    .setRemarks(sc.getRemarks())
                    .setYlpz(sc.getYlpz()));
        });
        planReservePlanLogService.lambdaUpdate()
                .eq(PlanReservePlanLog::getMainId , param.getMainBill().getId())
                .remove();
        if (CollectionUtils.isNotEmpty(planLogs)) {
            //新增调整记录
            planReservePlanLogService.saveBatch(planLogs);
        }
        return mainBill.getId();
    }

    @Override
    public String generateBillMainCode() {
        String date = LocalDate.now().toString().replace(DASH, EMPTY);
        String prefix = CACHE_NAME + ":number:";
        String number = GenerateNoUtil.generate(prefix + date);
        return number.substring(prefix.length());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean batchAbolish(List<String> mainIds) {
        List<PlanReservePlan> statusList = super.lambdaQuery()
                .in(PlanReservePlan::getId, mainIds)
                .ne(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .list();
        Assert.state(CollectionUtils.isEmpty(statusList), "删除单据时：修改单据状态失败(请检查单据是否为拟稿状态)");
        super.lambdaUpdate()
                .set(PlanReservePlan::getBillStatus, BillStatusEnum.ABOLISH.getStatusCode())
                .in(PlanReservePlan::getId, mainIds)
                .eq(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .update();
        planReservePlanDtlService.lambdaUpdate()
                .set(PlanReservePlanDtl::getBillStatus, BillStatusEnum.ABOLISH.getStatusCode())
                .in(PlanReservePlanDtl::getMainId, mainIds)
                .eq(PlanReservePlanDtl::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .update();
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean submit(List<String> mainIds) {
        List<PlanReservePlan> statusList = super.lambdaQuery()
                .in(PlanReservePlan::getId, mainIds)
                .ne(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .list();
        Assert.state(CollectionUtils.isEmpty(statusList), "提交单据时：修改单据状态失败(请检查单据是否为拟稿状态)");
        super.lambdaUpdate()
                .set(PlanReservePlan::getBillStatus, BillStatusEnum.TO_BE_ISSUED.getStatusCode())
                .in(PlanReservePlan::getId, mainIds)
                .eq(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .update();
        planReservePlanDtlService.lambdaUpdate()
                .set(PlanReservePlanDtl::getBillStatus, BillStatusEnum.TO_BE_ISSUED.getStatusCode())
                .in(PlanReservePlanDtl::getMainId, mainIds)
                .eq(PlanReservePlanDtl::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .update();
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean unSubmit(List<String> mainIds) {
        List<PlanReservePlan> statusList = super.lambdaQuery()
                .in(PlanReservePlan::getId, mainIds)
                .ne(PlanReservePlan::getBillStatus, BillStatusEnum.TO_BE_ISSUED.getStatusCode())
                .list();
        Assert.state(CollectionUtils.isEmpty(statusList), "反提交单据时：修改单据状态失败(请检查单据是否为待下发状态！)");
        super.lambdaUpdate()
                .set(PlanReservePlan::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .in(PlanReservePlan::getId, mainIds)
                .eq(PlanReservePlan::getBillStatus, BillStatusEnum.TO_BE_ISSUED.getStatusCode())
                .update();
        planReservePlanDtlService.lambdaUpdate()
                .set(PlanReservePlanDtl::getBillStatus, BillStatusEnum.SAVE.getStatusCode())
                .in(PlanReservePlanDtl::getMainId, mainIds)
                .eq(PlanReservePlanDtl::getBillStatus, BillStatusEnum.TO_BE_ISSUED.getStatusCode())
                .update();
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean distribution(String mainId) {
        PlanReserveDistributionParam saveParam = new PlanReserveDistributionParam();
        LocalDateTime now = LocalDateTime.now();
        PlanReservePlan main = super.getById(mainId);
        Assert.notNull(main, "未查询到储备计划!");
        List<PlanReservePlanDtl> dtls = planReservePlanDtlService.lambdaQuery()
                .eq(PlanReservePlanDtl::getMainId, mainId)
                .ne(PlanReservePlanDtl::getBillStatus, BillStatusEnum.ABOLISH.getStatusCode())
                .list();
        Assert.state(CollectionUtils.isNotEmpty(dtls), "未查询到储备计划详情！");
        List<PlanReservePlanLog> planLogs = planReservePlanLogService.lambdaQuery()
                .eq(PlanReservePlanLog::getMainId, mainId)
                .list();
        saveParam.setMainBill(BeanUtils.copyByClass(main, PlanReserveDistributionMainParam.class))
                .setDetails(BeanUtils.copyToList(dtls , PlanReserveDistributionDetailParam.class));
        if (CollectionUtils.isNotEmpty(planLogs)) {
            saveParam.setDetailLogs(BeanUtils.copyToList(planLogs , PlanReserveDistributionLogParam.class));
        }
        DistributionResultVo data = planReserveFactoryService.receivingInformation(saveParam).getData();
        List<PlanReservePlanSendLog> sendLogs = new ArrayList<>();
        dtls.forEach(sc->{
            PlanReservePlanSendLog log = new PlanReservePlanSendLog();
            log.setMainId(mainId)
                    .setDetailId(sc.getId())
                    .setSendTime(now);
            if (Objects.nonNull(data)) {
                log.setSendStatus(data.getSendStatus())
                        .setSendLog(data.getSendLog());
            } else {
                log.setSendStatus(PlanReturnStatusEnum.TWO.getStatusCode())
                        .setSendLog(PlanReturnStatusEnum.TWO.getStatusName());
            }
            sendLogs.add(log);
        });
        planReservePlanSendLogService.saveBatch(sendLogs);
        main.setJhxdsj(LocalDateTime.now().toString());
        //下发返回值状态为1修改计划状态为已下发，否则修改为下发失败！
        if (Objects.nonNull(data) && PlanReturnStatusEnum.ONE.getStatusCode().equals(data.getSendStatus())) {
            main.setBillStatus(BillStatusEnum.DISTRIBUTION.getStatusCode());
            dtls.forEach(sc-> sc.setBillStatus(BillStatusEnum.DISTRIBUTION.getStatusCode()));
        } else {
            main.setBillStatus(BillStatusEnum.DISTRIBUTION_FAILED.getStatusCode());
            dtls.forEach(sc-> sc.setBillStatus(BillStatusEnum.DISTRIBUTION_FAILED.getStatusCode()));
        }
        super.updateById(main);
        planReservePlanDtlService.updateBatchById(dtls);
        if (Objects.isNull(data) || PlanReturnStatusEnum.TWO.getStatusCode().equals(data.getSendStatus())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String adjust(PlanReservePlanAdjustParam param) {
        PlanReservePlan plan = super.lambdaQuery()
                .eq(PlanReservePlan::getId, param.getMainBill().getId())
                .one();
        Assert.notNull(plan, "未查询到储备计划信息，下发失败!");
        boolean status = BillStatusEnum.DISTRIBUTION.getStatusCode().equals(plan.getBillStatus()) || BillStatusEnum.ADJUST_BE_ISSUED.getStatusCode().equals(plan.getBillStatus());
        Assert.state(status, "调整单据时：修改失败(请检查单据是否为已下发或者调整待下发状态)!");
        plan.setBillStatus(BillStatusEnum.ADJUST_BE_ISSUED.getStatusCode());
        plan.setUpdateTime(LocalDateTime.now());
        plan.setIsChange(YesNo.YES.getCode());
        if (CollectionUtils.isNotEmpty(param.getUpdateDetailParams())) {
            BigDecimal amount = StreamEx.of(param.getUpdateDetailParams())
                    .map(PlanReservePlanDtl::getAmount).reduce(ZERO, BigDecimal::add);
            plan.setAmount(amount);
        }
        super.updateById(plan);
        List<PlanReservePlanLog> planLogs = new ArrayList<>();
        param.getUpdateDetailParams().forEach(sc-> {
            sc.setBillStatus(BillStatusEnum.ADJUST_BE_ISSUED.getStatusCode());
            PlanReservePlanLog log = new PlanReservePlanLog();
            planLogs.add(log.setAmount(sc.getAmount())
                    .setDetailId(sc.getId())
                    .setLsdjdm(sc.getLsdjdm())
                    .setMainId(sc.getMainId())
                    .setRemark(sc.getRemark())
                    .setRemarks(sc.getRemarks())
                    .setYlpz(sc.getYlpz()));
        });
        planReservePlanDtlService.updateBatchById(param.getUpdateDetailParams());
        //新增调整记录
        planReservePlanLogService.saveBatch(planLogs);
        return param.getMainBill().getId();
    }

    @DataBindFieldConvert
    @Override
    public List<PlanReservePlanSendLogVo> distributionLog(String mainId) {
        List<PlanReservePlanSendLog> list = planReservePlanSendLogService.lambdaQuery()
                .eq(PlanReservePlanSendLog::getMainId, mainId)
                .orderByDesc(PlanReservePlanSendLog::getCreateTime)
                .list();
        return BeanUtils.copyToList(list, PlanReservePlanSendLogVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<PlanReservePlanLogVo> adjustLog(String mainId) {
        List<PlanReservePlanLog> list = planReservePlanLogService.lambdaQuery()
                .eq(PlanReservePlanLog::getMainId, mainId)
                .orderByDesc(PlanReservePlanLog::getCreateTime)
                .list();
        return BeanUtils.copyToList(list, PlanReservePlanLogVo.class);
    }
}
