package com.sydata.safe.asess.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.Region;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRegionService;
import com.sydata.safe.asess.domain.*;
import com.sydata.safe.asess.mapper.OrgAssessMapper;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.*;
import com.sydata.safe.asess.vo.OrgAssessExportDtlVo;
import com.sydata.safe.asess.vo.OrgAssessExportVo;
import com.sydata.safe.asess.vo.OrgAssessVo;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;
import static com.sydata.safe.asess.enums.SafeAssessStateEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮食安全考核-单位考核Service业务层处理
 *
 * @author system
 * @date 2023-02-16
 */
@CacheConfig(cacheNames = OrgAssessServiceImpl.CACHE_NAME)
@Service("orgAssessService")
public class OrgAssessServiceImpl extends ServiceImpl<OrgAssessMapper, OrgAssess> implements IOrgAssessService {

    final static String CACHE_NAME = "safeAssess:orgAssess";

    @Resource
    private OrgAssessMapper orgAssessMapper;

    @Resource
    private IOrgAssessIndexService orgAssessIndexService;

    @Resource
    private IOrgAssessDeptService orgAssessDeptService;

    @Resource
    private IOrgAssessReviewService orgAssessReviewService;

    @Resource
    private ITemplateService templateService;

    @Resource
    private ITemplateIndexService templateIndexService;

    @Resource
    private IReviewService reviewService;

    @Resource
    private IRegionService regionService;

    @Cacheable(key = "'id='+#id")
    @Override
    public OrgAssess getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<OrgAssess> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, OrgAssess, OrgAssess>composeExecute()
                .fields(OrgAssess::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(OrgAssess::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssess, OrgAssess>composeExecute()
                .fields(OrgAssess::getId)
                .params((Collection<String>) list)
                .remove();
        orgAssessIndexService.removeByOrgAssessIds((List<String>) list);
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<OrgAssessVo> page(OrgAssessPageParam pageParam) {
        // 获取当前登录人所属行政区划和下级行政区划的区域集合 进行数据权限过滤
        String regionId = UserSecurity.loginUser().getRegionId();
        List<String> regionIds = ListUtil.toList(regionId);
        StreamEx.of(regionService.listByParentId(regionId)).map(Region::getId).toListAndThen(regionIds::addAll);

        Page<OrgAssess> page = super.lambdaQuery()
                .in(isNotEmpty(regionIds), OrgAssess::getRegionId, regionIds)
                .likeRight(isNotEmpty(pageParam.getName()), OrgAssess::getTemplateName, pageParam.getName())
                .likeRight(isNotEmpty(pageParam.getNumber()), OrgAssess::getTemplateNumber, pageParam.getNumber())
                .eq(isNotEmpty(pageParam.getTemplateId()), OrgAssess::getTemplateId, pageParam.getTemplateId())
                .eq(isNotEmpty(pageParam.getRegionId()), OrgAssess::getRegionId, pageParam.getRegionId())
                .eq(isNotEmpty(pageParam.getState()), OrgAssess::getState, pageParam.getState())
                .ge(isNotEmpty(pageParam.getPushDateBegin()), OrgAssess::getPushDate, pageParam.getPushDateBegin())
                .le(isNotEmpty(pageParam.getPushDateEnd()), OrgAssess::getPushDate, pageParam.getPushDateEnd())
                .orderByDesc(OrgAssess::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OrgAssessVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean push(Template template, List<TemplateIndex> list, List<Region> regions, LocalDate pushDate, LocalDate lastSubmitDate) {
        List<OrgAssess> assesses = new ArrayList<>();
        List<OrgAssessIndex> orgAssessIndexList = new ArrayList<>();

        regions.forEach(region -> {
            // 构建单位考核
            OrgAssess orgAssess = buildOrgAssess(template, region, pushDate, lastSubmitDate);

            // 构建单位考核指标
            List<OrgAssessIndex> assessIndices = StreamEx.of(list)
                    .map(templateIndex -> buildOrgAssessIndex(orgAssess, templateIndex, region.getId()))
                    .toList();

            // 单位考核统计数量
            List<OrgAssessIndex> topics = StreamEx.of(assessIndices)
                    .filter(OrgAssessIndex::getTopic)
                    .filter(OrgAssessIndex::getEnable)
                    .toList();

            orgAssess.setOrgCount(topics.size())
                    .setOrgAwaitCount(topics.size())
                    .setCheckAwaitCount(topics.size())
                    .setTemplateScore(StreamEx.of(topics).map(OrgAssessIndex::getScore).reduce(ZERO, BigDecimal::add))
                    .setState(AWAIT_ALLOT.getState());

            // 组装数据
            assesses.add(orgAssess);
            orgAssessIndexList.addAll(assessIndices);
        });

        // 考核评审绑定各个地市对单位考核进行统计
        reviewService.regionBind(template.getId(), assesses, orgAssessIndexList);

        super.saveBatch(assesses);
        return orgAssessIndexService.saveBatch(orgAssessIndexList);
    }

    @DataBindFieldConvert
    @Override
    public OrgAssessVo details(String id) {
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(id);
        return BeanUtils.copyByClass(orgAssess, OrgAssessVo.class);
    }


    @CacheEvict(key = "'id='+#allotParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(OrgAssessAllotParam allotParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(allotParam.getId()), "非该区划考核人员不可操作");

        // 校验状态
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(allotParam.getId());
        Assert.state(AWAIT_ALLOT.getState().equals(orgAssess.getState()), "非待分配状态无法分配");

        // 校验时间
        LocalDate cooperateDeptEndDate = allotParam.getCooperateDeptEndDate();
        boolean isAfter = cooperateDeptEndDate == null || orgAssess.getLastSubmitDate().isAfter(cooperateDeptEndDate);
        Assert.state(isAfter, "配合部门填报截止日期必须在最晚提交日期之前");

        List<OrgAssessIndex> indexList = orgAssessIndexService.listByOrgAssessId(orgAssess.getId());
        Map<String, OrgAssessIndex> indexMap = StreamEx.of(indexList).toMap(OrgAssessIndex::getId, identity());
        List<OrgAssessIndexDistributionParam> list = allotParam.getList();

        // 校验是否已提交所有考核指标
        Set<String> topicIds = StreamEx.of(indexList)
                .filter(OrgAssessIndex::getTopic)
                .filter(OrgAssessIndex::getEnable)
                .map(OrgAssessIndex::getId)
                .toSet();
        StreamEx.of(list).map(OrgAssessIndexDistributionParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(CollectionUtils.isEmpty(topicIds), "并未提交所有考核指标,无法进行分配");

        Integer distributionCount = StreamEx.of(allotParam.getList())
                .filter(v -> {
                    OrgAssessIndex orgAssessIndex = indexMap.get(v.getId());
                    return orgAssessIndex.getTopic() && orgAssessIndex.getEnable();
                })
                .map(OrgAssessIndexDistributionParam::getLeadDeptId)
                .nonNull()
                .toList()
                .size();

        // 如果是分配提交：则进入校验项
        boolean isSubmit = AWAIT_SELF_ASSESSMENT.getState().equals(allotParam.getState());
        if (isSubmit) {
            // 校验配合部门填报截止日期必填
            Assert.notNull(cooperateDeptEndDate, "配合部门填报截止日期必填");

            // 校验：单位考核指标必须全部分配完
            int count = orgAssess.getOrgCount() - distributionCount;
            Assert.state(count == 0, String.format("单位考核指标剩余%s个未分配牵头部门", count));
        }

        // 修改单位考核
        boolean update = super.lambdaUpdate()
                .set(OrgAssess::getState, allotParam.getState())
                .set(OrgAssess::getCooperateDeptEndDate, cooperateDeptEndDate)
                .set(OrgAssess::getDistributionCount, distributionCount)
                .eq(OrgAssess::getId, allotParam.getId())
                .eq(OrgAssess::getState, AWAIT_ALLOT.getState())
                .update();
        Assert.state(!isSubmit || update, "非待分配状态无法分配");

        // 修改单位考核指标
        Boolean distribution = orgAssessIndexService.allot(orgAssess.getId(), allotParam.getList());

        // 如果是分配提交：将单位考核发布给各部门
        if (isSubmit) {
            List<OrgAssessIndex> indices = orgAssessIndexService.listByOrgAssessId(orgAssess.getId());
            List<OrgAssessDeptIndex> deptIndexList = orgAssessDeptService.allot(orgAssess, indices, cooperateDeptEndDate);

            Map<Boolean, List<OrgAssessDeptIndex>> typeMap = StreamEx.of(deptIndexList)
                    .filter(OrgAssessDeptIndex::getTopic)
                    .groupingBy(OrgAssessDeptIndex::getType);
            int leadCount = typeMap.getOrDefault(TRUE, Collections.emptyList()).size();
            int cooperateCount = typeMap.getOrDefault(FALSE, Collections.emptyList()).size();

            // 单位考核统计：部门考核总数/牵头指标数/配合指标数
            int assessDeptCount = StreamEx.of(deptIndexList).map(OrgAssessDeptIndex::getOrgAssessDeptId).toSet().size();
            boolean statistics = super.lambdaUpdate()
                    .set(OrgAssess::getAssessDeptCount, assessDeptCount)
                    .set(OrgAssess::getLeadCount, leadCount)
                    .set(OrgAssess::getLeadAwaitCount, leadCount)
                    .set(OrgAssess::getCooperateCount, cooperateCount)
                    .set(OrgAssess::getCooperateAwaitCount, cooperateCount)
                    .eq(OrgAssess::getId, allotParam.getId())
                    .update();
            Assert.state(statistics, "单位考核统计失败");
        }

        return distribution;
    }

    @CacheEvict(key = "'id='+#uploadFileParam.id")
    @Override
    public Boolean uploadFiles(OrgAssessUploadFileParam uploadFileParam) {
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(uploadFileParam.getId());
        Set<String> assessSet = SetUtils.hashSet(AWAIT_ASSESS.getState(), ASSESS.getState());
        Assert.state(!assessSet.contains(orgAssess.getState()), "已进入考核阶段不允许上传附件");

        return super.lambdaUpdate()
                .set(OrgAssess::getFileIds, uploadFileParam.getFileIds())
                .set(OrgAssess::getFileNames, uploadFileParam.getFileNames())
                .eq(OrgAssess::getId, uploadFileParam.getId())
                .notIn(OrgAssess::getState, assessSet)
                .update();
    }

    @CacheEvict(key = "'id='+#orgAssessDept.orgAssessId")
    @Override
    public Boolean deptAssessSubmit(OrgAssessDept orgAssessDept, BigDecimal leadAlreadyScore) {
        Boolean state = orgAssessMapper.deptAssessSubmit(orgAssessDept, leadAlreadyScore);
        Assert.state(state, "单位考核统计部门考核提交自评结果失败");
        return TRUE;
    }


    @CacheEvict(key = "'id='+#orgAssessDept.orgAssessId")
    @Override
    public Boolean deptAssessReset(OrgAssessDept orgAssessDept) {
        // 操作权限判断
        Assert.state(this.operationAuth(orgAssessDept.getOrgAssessId()), "非该区划考核人员不可操作");

        // 校验状态-进入考核阶段不允许退回
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(orgAssessDept.getOrgAssessId());
        Set<String> states = SetUtils.hashSet(AWAIT_ASSESS.getState(), ASSESS.getState());
        Assert.state(!states.contains(orgAssess.getState()), "单位考核已进入考核阶段不允许退回");

        Boolean state = orgAssessMapper.deptAssessReset(orgAssessDept);
        Assert.state(state, "单位考核已进入考核阶段不允许退回");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean submit(String id) {
        // 操作权限判断
        Assert.state(this.operationAuth(id), "非该区划考核人员不可操作");

        // 校验状态
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(id);
        Set<String> states = SetUtils.hashSet(SELF_ASSESSMENT.getState(), RESET.getState());
        Assert.state(states.contains(orgAssess.getState()), "非已自评/已退回状态无法提交");

        // 校验提交是否超时,如果超时校验必须上传附件
        LocalDateTime submitTime = LocalDateTime.now();
        LocalDate lastSubmitDate = orgAssess.getLastSubmitDate();
        boolean submitOvertime = submitTime.toLocalDate().isAfter(lastSubmitDate);
        Assert.state(!submitOvertime || isNotEmpty(orgAssess.getFileIds()), "超时提交必须上传附件说明");

        // 修改单位考核
        boolean update = super.lambdaUpdate()
                .set(OrgAssess::getSubmitOvertime, submitOvertime)
                .set(OrgAssess::getSubmitTime, submitTime)
                .set(OrgAssess::getState, AWAIT_ASSESS.getState())
                .in(OrgAssess::getState, states)
                .eq(OrgAssess::getId, id)
                .update();
        Assert.state(update, "非已自评/已退回状态无法提交");

        // 考核评审提交
        reviewService.regionSubmit(id);

        // 考核模板累加待自评数量
        return templateService.operationRegionSubmitCount(orgAssess.getTemplateId(), 1);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean reset(String id) {
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(AWAIT_ASSESS.getState().equals(orgAssess.getState()), "非待考核状态无法退回");

        boolean update = super.lambdaUpdate()
                .set(OrgAssess::getState, RESET.getState())
                .set(OrgAssess::getDeptAssessSubmitCount, 0)
                .set(OrgAssess::getAssessScore, ZERO)
                .set(OrgAssess::getScore, ZERO)
                .set(OrgAssess::getSubmitOvertime, null)
                .set(OrgAssess::getSubmitTime, null)
                .setSql("dept_lead_await_count = dept_lead_count")
                .setSql("dept_cooperate_await_count = dept_cooperate_count")
                .eq(OrgAssess::getState, AWAIT_ASSESS.getState())
                .eq(OrgAssess::getId, id)
                .update();
        Assert.state(update, "非待考核状态无法退回");

        // 考核评审重置
        reviewService.regionReset(id);

        // 考核模板累减地市已提交数量
        Assert.state(templateService.operationAuth(orgAssess.getTemplateId()), "非考核模板组织人员不可操作");
        return templateService.operationRegionSubmitCount(orgAssess.getTemplateId(), -1);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean revoke(String id) {
        // 操作权限判断
        Assert.state(this.operationAuth(id), "非该区划考核人员不可操作");

        // 校验状态-待分配状态和进入考核阶段不允许撤回
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(!AWAIT_ALLOT.getState().equals(orgAssess.getState()), "单位考核已是待分配状态无需撤回");

        Set<String> states = SetUtils.hashSet(AWAIT_ASSESS.getState(), ASSESS.getState(), AWAIT_ALLOT.getState());
        Assert.state(!states.contains(orgAssess.getState()), "单位考核已进入考核阶段不允许撤回");

        boolean update = super.lambdaUpdate()
                .set(OrgAssess::getState, AWAIT_ALLOT.getState())
                .set(OrgAssess::getAssessDeptCount, 0)
                .set(OrgAssess::getAssessDeptSubmitCount, 0)
                .set(OrgAssess::getOrgScore, ZERO)
                .set(OrgAssess::getLeadCount, 0)
                .set(OrgAssess::getLeadAwaitCount, 0)
                .set(OrgAssess::getCooperateCount, 0)
                .set(OrgAssess::getCooperateAwaitCount, 0)
                .eq(OrgAssess::getId, id)
                .notIn(OrgAssess::getState, states)
                .update();
        Assert.state(update, "单位考核已进入考核阶段不允许撤回");

        orgAssessDeptService.orgAssessRevoke(id);
        orgAssessReviewService.orgAssessRevoke(id);

        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean assess(String id, String templateId, List<ReviewIndexAssessParam> realLeads, int cooperatesSize) {
        // 单位考核统计
        BigDecimal assessScore = realLeads.stream().map(ReviewIndexAssessParam::getAssessScore)
                .reduce(ZERO, BigDecimal::add);
        Boolean state = orgAssessMapper.assess(id, realLeads.size(), cooperatesSize, assessScore);
        Assert.state(state, "单位考核统计考核评审考核结果失败");
        orgAssessIndexService.assess(id, realLeads);

        // 考核模板统计
        if (ASSESS.getState().equals(this.getById(id).getState())) {
            templateService.operationRegionAssessCount(templateId, 1);
        }
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean assessReset(String id, String templateId, List<ReviewIndex> realLeads, int cooperatesSize) {
        // 单位考核统计
        BigDecimal assessScore = realLeads.stream().map(ReviewIndex::getAssessScore).reduce(ZERO, BigDecimal::add);
        Boolean state = orgAssessMapper.assessReset(id, realLeads.size(), cooperatesSize, assessScore);
        Assert.state(state, "单位考核统计考核评审退回结果失败");

        // 修改单位考核状态成功累减考核模板统计地市已考核数量-1
        boolean update = super.lambdaUpdate()
                .set(OrgAssess::getState, AWAIT_ASSESS.getState())
                .eq(OrgAssess::getId, id)
                .eq(OrgAssess::getState, ASSESS.getState())
                .update();
        if (update) {
            templateService.operationRegionAssessCount(templateId, -1);
        }
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean check(String id, String templateId, List<CheckPlanIndexCheckParam> list) {
        // 校验是否已提交所有考核指标
        List<OrgAssessIndex> indexList = orgAssessIndexService.listByOrgAssessId(id);
        Set<String> topicIds = StreamEx.of(indexList)
                .filter(OrgAssessIndex::getTopic)
                .filter(OrgAssessIndex::getEnable)
                .map(OrgAssessIndex::getId)
                .toSet();
        Set<String> ids = StreamEx.of(list).map(CheckPlanIndexCheckParam::getId).toSet();
        boolean state = topicIds.size() == ids.size() && ids.removeAll(topicIds) && isEmpty(ids);
        Assert.state(state, "并未提交所有考核指标,无法进行检查");

        // 更新单位考核指标检查分
        orgAssessIndexService.check(id, list);

        // 计算抽查总分,更新单位考核
        List<BigDecimal> checks = StreamEx.of(list).map(CheckPlanIndexCheckParam::getCheckScore).nonNull().toList();
        BigDecimal checkScore = checks.stream().reduce(ZERO, BigDecimal::add);

        Template template = templateService.getById(templateId);
        return orgAssessMapper.check(id, list.size() - checks.size(), checkScore,
                template.getAssessScoreProportion(), template.getCheckScoreProportion());
    }

    @CacheEvict(key = "'id='+#uploadFileParam.orgAssessId")
    @Override
    public Boolean uploadCheckFiles(CheckPlanUploadFileParam uploadFileParam) {
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(uploadFileParam.getOrgAssessId());
        Assert.state(ASSESS.getState().equals(orgAssess.getState()), "已考核才能上传抽查附件");

        return super.lambdaUpdate()
                .set(OrgAssess::getCheckFileIds, uploadFileParam.getCheckFileIds())
                .set(OrgAssess::getCheckFileNames, uploadFileParam.getCheckFileNames())
                .eq(OrgAssess::getId, uploadFileParam.getOrgAssessId())
                .eq(OrgAssess::getState, ASSESS.getState())
                .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssess::getId)
                .eq(OrgAssess::getTemplateId, templateId)
                .list()
                .stream()
                .map(OrgAssess::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Override
    public void export(OrgAssessExportParam exportParam) {
        // 获取考核模板指标列表并转换为单位考核导出明细VO模板
        List<TemplateIndex> indexList = templateIndexService.listByTemplateId(exportParam.getTemplateId());
        Map<String, TemplateIndex> indexIdMap = StreamEx.of(indexList).toMap(TemplateIndex::getId, identity());
        List<OrgAssessExportDtlVo> templateDtls = StreamEx.of(indexList)
                .filter(TemplateIndex::getTopic)
                .map(topic -> {
                    // 寻找所有指标父名称拼接起来
                    StringJoiner parentNames = new StringJoiner(SLASH);
                    TemplateIndex parent = indexIdMap.get(topic.getParentId());
                    while (parent != null) {
                        parentNames.add(parent.getName());
                        parent = indexIdMap.get(parent.getParentId());
                    }

                    return new OrgAssessExportDtlVo()
                            .setTemplateIndexId(topic.getId())
                            .setParentNames(parentNames.toString())
                            .setName(topic.getName())
                            .setScore(topic.getScore());
                })
                .toList();

        // 组装数据
        List<String> ids = exportParam.getIds();
        Map<String, OrgAssessVo> idMaps = this.listByIds(ids).stream()
                .map(orgAssess -> BeanUtils.copyByClass(orgAssess, OrgAssessVo.class))
                .collect(Collectors.toMap(OrgAssess::getId, identity()));
        DataBindHandleBootstrap.dataHandConvert(idMaps);

        Map<String, List<OrgAssessIndex>> orgAssessIdMap = StreamEx.of(orgAssessIndexService.listByOrgAssessIds(ids))
                .groupingBy(OrgAssessIndex::getOrgAssessId);

        List<OrgAssessExportVo> vos = new ArrayList<>();
        ids.forEach(id -> {
            List<OrgAssessIndex> list = orgAssessIdMap.getOrDefault(id, Collections.emptyList());
            Map<String, OrgAssessIndex> idMap = StreamEx.of(list).toMap(OrgAssessIndex::getTemplateIndexId, identity());

            // 构建导出明细
            List<OrgAssessExportDtlVo> dtlVos = StreamEx.of(templateDtls)
                    .map(dtl -> BeanUtils.copyByClass(dtl, OrgAssessExportDtlVo.class))
                    .peek(dtl -> {
                        OrgAssessIndex orgAssessIndex = idMap.get(dtl.getTemplateIndexId());
                        dtl.setEnable(orgAssessIndex.getEnable() ? "否" : "是")
                                .setOrgIllustrate(orgAssessIndex.getOrgIllustrate())
                                .setAssessScore(orgAssessIndex.getAssessScore())
                                .setAssessMinusCause(orgAssessIndex.getAssessMinusCause())
                                .setCheckScore(orgAssessIndex.getCheckScore())
                                .setCheckRemark(orgAssessIndex.getCheckRemark());
                    }).toList();

            // 构建导出主表
            OrgAssessVo orgAssess = idMaps.get(id);
            OrgAssessExportVo vo = new OrgAssessExportVo()
                    .setRegionName(orgAssess.getRegionName())
                    .setTemplateScore(orgAssess.getTemplateScore())
                    .setAssessScore(orgAssess.getAssessScore())
                    .setCheckScore(orgAssess.getCheckScore())
                    .setScore(orgAssess.getScore())
                    .setState(orgAssess.getState())
                    .setDtlVos(dtlVos);
            vos.add(vo);
        });


        // 导出数据
        Template template = templateService.getById(exportParam.getTemplateId());
        String title = template.getName() + DASH + template.getYear();
        ZtExcelBuildUtil.buildExcelExport(OrgAssessExportVo.class, title).setData(vos).doWebExport();
    }

    @Override
    public Boolean operationAuth(String id) {
        OrgAssess orgAssess = SpringUtil.getBean(this.getClass()).getById(id);
        return Optional.ofNullable(UserSecurity.loginUser())
                .map(LoginUser::getRegionId)
                .map(regionId -> regionId.equals(orgAssess.getRegionId()))
                .orElse(FALSE);
    }

    /**
     * 构建单位考核
     *
     * @param template       考核模板
     * @param region         行政区域
     * @param pushDate       发布日期
     * @param lastSubmitDate 最晚提交日期
     * @return 单位考核
     */
    private OrgAssess buildOrgAssess(Template template, Region region,
                                     LocalDate pushDate, LocalDate lastSubmitDate) {
        return new OrgAssess()
                .setId(IdWorker.getIdStr())
                .setTemplateId(template.getId())
                .setTemplateNumber(template.getNumber())
                .setTemplateName(template.getName())
                .setYear(template.getYear())
                .setPushDate(pushDate)
                .setOrgScore(ZERO)
                .setAssessScore(ZERO)
                .setCheckScore(ZERO)
                .setScore(ZERO)
                .setLastSubmitDate(lastSubmitDate)
                .setState(AWAIT_ALLOT.getState())
                .setRegionId(region.getId())
                .setCountryId(region.getCountryId())
                .setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());
    }

    /**
     * 构建单位考核指标
     *
     * @param orgAssess     单位考核
     * @param templateIndex 考核模板指标
     * @param regionId      区域ID
     * @return 单位考核指标
     */
    private OrgAssessIndex buildOrgAssessIndex(OrgAssess orgAssess, TemplateIndex templateIndex, String regionId) {
        // 是否属于缺项地市
        boolean enable = TRUE;
        if (isNotEmpty(templateIndex.getLackRegionIds())) {
            enable = !StreamEx.of(templateIndex.getLackRegionIds().split(COMMA)).toSet().contains(regionId);
        }

        return new OrgAssessIndex()
                .setId(IdWorker.getIdStr())
                .setOrgAssessId(orgAssess.getId())
                .setTemplateIndexId(templateIndex.getId())
                .setTemplateIndexParentId(templateIndex.getParentId())
                .setName(templateIndex.getName())
                .setTopic(templateIndex.getTopic())
                .setScore(templateIndex.getScore())
                .setStandard(templateIndex.getStandard())
                .setIllustrate(templateIndex.getIllustrate())
                .setEnable(enable)
                .setTemplateLeadDeptId(templateIndex.getLeadDeptId())
                .setTemplateCooperateDeptIds(templateIndex.getCooperateDeptIds());
    }
}