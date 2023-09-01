package com.sydata.safe.asess.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.domain.OrgAssess;
import com.sydata.safe.asess.domain.OrgAssessDept;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.mapper.OrgAssessDeptMapper;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.*;
import com.sydata.safe.asess.vo.OrgAssessDeptVo;
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

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.safe.asess.enums.SafeAssessStateEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮食安全考核-部门考核Service业务层处理
 *
 * @author system
 * @date 2023-02-18
 */
@CacheConfig(cacheNames = OrgAssessDeptServiceImpl.CACHE_NAME)
@Service("orgAssessDeptService")
public class OrgAssessDeptServiceImpl extends ServiceImpl<OrgAssessDeptMapper, OrgAssessDept>
        implements IOrgAssessDeptService {

    final static String CACHE_NAME = "safeAssess:orgAssessDept";


    @Resource
    private OrgAssessDeptMapper orgAssessDeptMapper;

    @Resource
    private IOrgAssessDeptIndexService orgAssessDeptIndexService;

    @Resource
    private IOrgAssessReviewService orgAssessReviewService;

    @Resource
    private IOrgAssessService orgAssessService;

    @Resource
    private IOrgAssessIndexService orgAssessIndexService;

    @Cacheable(key = "'id='+#id")
    @Override
    public OrgAssessDept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssessDept, OrgAssessDept>composeExecute()
                .fields(OrgAssessDept::getId)
                .params((Collection<String>) list)
                .remove();
        orgAssessDeptIndexService.removeByOrgAssessDeptIds((List<String>) list);
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<OrgAssessDeptVo> page(OrgAssessDeptPageParam pageParam) {
        Page<OrgAssessDept> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getName()), OrgAssessDept::getTemplateName, pageParam.getName())
                .likeRight(isNotEmpty(pageParam.getNumber()), OrgAssessDept::getTemplateNumber, pageParam.getNumber())
                .eq(isNotEmpty(pageParam.getOrgAssessId()), OrgAssessDept::getOrgAssessId, pageParam.getOrgAssessId())
                .eq(isEmpty(pageParam.getOrgAssessId()), OrgAssessDept::getOrganizeId, UserSecurity.organizeId())
                .eq(isNotEmpty(pageParam.getOrganizeId()), OrgAssessDept::getOrganizeId, pageParam.getOrganizeId())
                .eq(isNotEmpty(pageParam.getDeptId()), OrgAssessDept::getDeptId, pageParam.getDeptId())
                .ge(isNotEmpty(pageParam.getCooperateDeptEndDateBegin()), OrgAssessDept::getCooperateDeptEndDate, pageParam.getCooperateDeptEndDateBegin())
                .le(isNotEmpty(pageParam.getCooperateDeptEndDateEnd()), OrgAssessDept::getCooperateDeptEndDate, pageParam.getCooperateDeptEndDateEnd())
                .orderByDesc(OrgAssessDept::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OrgAssessDeptVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public List<OrgAssessDeptIndex> allot(OrgAssess orgAssess, List<OrgAssessIndex> indices, LocalDate cooperateDeptEndDate) {
        List<OrgAssessIndex> topicList = StreamEx.of(indices)
                .filter(OrgAssessIndex::getTopic)
                .filter(OrgAssessIndex::getEnable)
                .toList();
        Map<Long, List<OrgAssessDeptIndex>> deptTopicsMap = MapUtil.newHashMap();

        topicList.forEach(index -> {
            // 牵头部门
            Long leadDeptId = index.getLeadDeptId();
            OrgAssessDeptIndex orgAssessDeptIndex = buildOrgAssessDeptIndex(index, TRUE);
            deptTopicsMap.computeIfAbsent(leadDeptId, (k) -> new ArrayList<>()).add(orgAssessDeptIndex);

            // 配合部门
            String cooperateDeptIdStr = index.getCooperateDeptIds();
            if (isNotEmpty(cooperateDeptIdStr)) {
                Set<String> cooperateDeptIds = StreamEx.of(cooperateDeptIdStr.split(COMMA)).toSet();
                cooperateDeptIds.forEach(cooperateDeptId -> {
                    OrgAssessDeptIndex deptIndex = buildOrgAssessDeptIndex(index, FALSE);
                    long deptId = Long.parseLong(cooperateDeptId);
                    deptTopicsMap.computeIfAbsent(deptId, (k) -> new ArrayList<>()).add(deptIndex);
                });
            }
        });
        Map<String, OrgAssessIndex> indexIdMap = StreamEx.of(indices).toMap(OrgAssessIndex::getTemplateIndexId, identity());

        List<OrgAssessDept> orgAssessDeptList = new ArrayList<>();
        deptTopicsMap.forEach((deptId, topics) -> {
            // 构建部门考核
            OrgAssessDept orgAssessDept = buildOrgAssessDept(orgAssess, deptId, topics)
                    .setCooperateDeptEndDate(cooperateDeptEndDate);

            Set<String> parentIds = new HashSet<>();
            topics.forEach(topic -> {
                // 设置部门考核ID
                topic.setOrgAssessDeptId(orgAssessDept.getId());

                // 寻找每道题目的所有父节点ID (题目的模板标题)
                OrgAssessIndex parent = indexIdMap.get(topic.getTemplateIndexParentId());
                while (parent != null) {
                    parentIds.add(parent.getTemplateIndexId());
                    parent = indexIdMap.get(parent.getTemplateIndexParentId());
                }
            });

            // 部门模板指标 追加模板标题
            parentIds.forEach(parentId -> {
                OrgAssessIndex index = indexIdMap.get(parentId);
                OrgAssessDeptIndex title = buildOrgAssessDeptIndex(index, null);
                topics.add(title.setOrgAssessDeptId(orgAssessDept.getId()));
            });

            orgAssessDeptList.add(orgAssessDept);
        });

        List<OrgAssessDeptIndex> indexList = StreamEx.of(deptTopicsMap.values()).flatMap(Collection::stream).toList();
        super.saveBatch(orgAssessDeptList);
        orgAssessDeptIndexService.saveBatch(indexList);
        return indexList;
    }

    @CacheEvict(key = "'id='+#allotParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(OrgAssessDeptAllotParam allotParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(allotParam.getId()), "非部门考核部门人员不可操作");

        // 校验状态
        OrgAssessDept orgAssessDept = SpringUtil.getBean(this.getClass()).getById(allotParam.getId());
        Assert.state(AWAIT_ALLOT.getState().equals(orgAssessDept.getState()), "非待分配状态无法分配");

        List<OrgAssessDeptIndex> indexList = orgAssessDeptIndexService.listByOrgAssessDeptId(orgAssessDept.getId());
        List<OrgAssessDeptIndexDistributionParam> list = allotParam.getList();

        // 校验是否已提交所有考核指标
        Set<String> topicIds = StreamEx.of(indexList)
                .filter(OrgAssessDeptIndex::getTopic)
                .map(OrgAssessDeptIndex::getId)
                .toSet();
        StreamEx.of(list).map(OrgAssessDeptIndexDistributionParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(CollectionUtils.isEmpty(topicIds), "并未提交所有考核指标,无法进行分配");

        // 如果是分配提交,校验所有指标的自评部门ID必填
        boolean isSubmit = ALLOT.getState().equals(allotParam.getState());
        if (isSubmit) {
            Assert.state(list.stream().noneMatch(v -> v.getDeptId() == null), "存在未分配的自评部门,无法提交");
        }

        // 修改部门考核
        int deptCount = StreamEx.of(list).map(OrgAssessDeptIndexDistributionParam::getDeptId).nonNull().toSet().size();
        boolean update = super.lambdaUpdate()
                .set(OrgAssessDept::getState, allotParam.getState())
                .set(OrgAssessDept::getDeptCount, deptCount)
                .eq(OrgAssessDept::getId, orgAssessDept.getId())
                .eq(OrgAssessDept::getState, AWAIT_ALLOT.getState())
                .update();
        Assert.state(!isSubmit || update, "非待分配状态无法分配");

        // 批量修改部门考核指标
        Boolean distribution = orgAssessDeptIndexService.distribution(orgAssessDept.getId(), list);

        // 如果是分配提交,将部门考核发布给各部门自评
        if (isSubmit) {
            List<OrgAssessDeptIndex> index = orgAssessDeptIndexService.listByOrgAssessDeptId(orgAssessDept.getId());
            orgAssessReviewService.allot(orgAssessDept, index);
        }
        return distribution;
    }

    @CacheEvict(key = "'id='+#submitParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean submit(OrgAssessDeptSubmitParam submitParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(submitParam.getId()), "非部门考核部门人员不可操作");

        // 校验状态
        OrgAssessDept orgAssessDept = SpringUtil.getBean(this.getClass()).getById(submitParam.getId());
        Set<String> states = SetUtils.hashSet(AWAIT_SELF_ASSESSMENT.getState(), RESET.getState());
        Assert.state(states.contains(orgAssessDept.getState()), "非待自评或已退回状态无法进行自评");

        // 查询指标列表
        List<OrgAssessDeptIndex> indexList = orgAssessDeptIndexService.listByOrgAssessDeptId(orgAssessDept.getId());
        Map<String, OrgAssessDeptIndex> indexMap = StreamEx.of(indexList).toMap(OrgAssessDeptIndex::getId, identity());

        List<OrgAssessDeptIndexSubmitParam> list = submitParam.getList();

        // 校验是否已提交所有考核指标
        Set<String> topicIds = StreamEx.of(indexList)
                .filter(OrgAssessDeptIndex::getTopic)
                .map(OrgAssessDeptIndex::getId)
                .toSet();
        StreamEx.of(list).map(OrgAssessDeptIndexSubmitParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(CollectionUtils.isEmpty(topicIds), "并未提交所有考核指标,无法进行自评");

        // 如果是提交:所有指标自评完才能提交
        List<OrgAssessDeptIndexSubmitParam> submits = StreamEx.of(list)
                .filter(v -> isNotEmpty(v.getOrgIllustrate())).toList();
        boolean isSubmit = SELF_ASSESSMENT.getState().equals(submitParam.getState());
        int count = orgAssessDept.getLeadCount() + orgAssessDept.getCooperateCount();
        Assert.state(!isSubmit || count == submits.size(), "所有指标自评完才能提交");

        submits.forEach(submit -> {
            OrgAssessDeptIndex index = indexMap.get(submit.getId());
            submit.setOrgAssessIndexId(index.getOrgAssessIndexId());
        });

        Map<Boolean, List<OrgAssessDeptIndexSubmitParam>> typeMap = StreamEx.of(submits)
                .groupingBy(v -> indexMap.get(v.getId()).getType());

        List<OrgAssessDeptIndexSubmitParam> leadList = typeMap.getOrDefault(TRUE, emptyList());
        List<OrgAssessDeptIndexSubmitParam> cooperateList = typeMap.getOrDefault(FALSE, emptyList());

        int leadAwaitCount = orgAssessDept.getLeadCount() - leadList.size();
        int cooperateAwaitCount = orgAssessDept.getCooperateCount() - cooperateList.size();

        BigDecimal leadAlreadyScore = StreamEx.of(leadList).map(OrgAssessDeptIndexSubmitParam::getOrgScore)
                .nonNull().reduce(ZERO, BigDecimal::add);

        BigDecimal cooperateAlreadyScore = StreamEx.of(cooperateList).map(OrgAssessDeptIndexSubmitParam::getOrgScore)
                .nonNull().reduce(ZERO, BigDecimal::add);

        LocalDate cooperateDeptEndDate = orgAssessDept.getCooperateDeptEndDate();
        LocalDateTime submitTime = LocalDateTime.now();
        boolean submitOvertime = submitTime.toLocalDate().isAfter(cooperateDeptEndDate);

        boolean update = super.lambdaUpdate()
                .set(OrgAssessDept::getState, submitParam.getState())
                .set(OrgAssessDept::getLeadAwaitCount, leadAwaitCount)
                .set(OrgAssessDept::getLeadAlreadyScore, leadAlreadyScore)
                .set(OrgAssessDept::getCooperateAwaitCount, cooperateAwaitCount)
                .set(OrgAssessDept::getCooperateAlreadyScore, cooperateAlreadyScore)
                .set(isSubmit, OrgAssessDept::getSubmitOvertime, submitOvertime)
                .set(isSubmit, OrgAssessDept::getSubmitTime, submitTime)
                .eq(OrgAssessDept::getId, orgAssessDept.getId())
                .in(OrgAssessDept::getState, AWAIT_SELF_ASSESSMENT.getState(), RESET.getState())
                .update();
        Assert.state(!isSubmit || update, "非待自评或已退回状态无法进行自评");
        orgAssessDeptIndexService.submit(orgAssessDept.getId(), submits);

        // 统计单位考核：部门考核数,将牵头指标自评结果汇集到单位考核指标
        if (isSubmit) {
            orgAssessService.deptAssessSubmit(orgAssessDept, leadAlreadyScore);
            orgAssessIndexService.deptAssessSubmit(orgAssessDept.getOrgAssessId(), leadList);
        }

        return update;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean operationDeptSubmitCount(String id, int count) {
        Set<String> states = SetUtils.hashSet(ALLOT.getState(), AWAIT_SELF_ASSESSMENT.getState(), RESET.getState());
        String state = SpringUtil.getBean(this.getClass()).getById(id).getState();
        Assert.state(states.contains(state), String.format("部门考核状态为%s不允许操作", state));

        Boolean update = orgAssessDeptMapper.operationDeptSubmitCount(id, count);
        Assert.state(update, "部门考核统计部门自评提交数失败");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean reset(String id) {
        // 校验状态
        OrgAssessDept orgAssessDept = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(SELF_ASSESSMENT.getState().equals(orgAssessDept.getState()), "非已自评状态无法退回");

        // 部门考核退回联动单位考核的统计信息需要扣除
        orgAssessService.deptAssessReset(orgAssessDept);

        // 修改部门考核状态为已退回
        boolean update = super.lambdaUpdate()
                .set(OrgAssessDept::getState, RESET.getState())
                .setSql("lead_await_count = lead_count")
                .set(OrgAssessDept::getLeadAlreadyScore, ZERO)
                .setSql("cooperate_await_count = cooperate_count")
                .set(OrgAssessDept::getCooperateAlreadyScore, ZERO)
                .set(OrgAssessDept::getSubmitOvertime, null)
                .set(OrgAssessDept::getSubmitTime, null)
                .eq(OrgAssessDept::getId, id)
                .eq(OrgAssessDept::getState, SELF_ASSESSMENT.getState())
                .update();
        Assert.state(update, "非已自评状态无法退回");

        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean revoke(String id) {
        // 操作权限判断
        Assert.state(this.operationAuth(id), "非部门考核部门人员不可操作");

        // 校验状态
        OrgAssessDept orgAssessDept = SpringUtil.getBean(this.getClass()).getById(id);
        Set<String> states = SetUtils.hashSet(ALLOT.getState(), AWAIT_SELF_ASSESSMENT.getState(), RESET.getState());
        Assert.state(states.contains(orgAssessDept.getState()), "待分配或已自评状态不允许撤回");

        // 修改部门考核状态为待分配
        boolean update = super.lambdaUpdate()
                .set(OrgAssessDept::getState, AWAIT_ALLOT.getState())
                .set(OrgAssessDept::getDeptCount, ZERO.intValue())
                .set(OrgAssessDept::getDeptSubmitCount, ZERO.intValue())
                .eq(OrgAssessDept::getId, id)
                .in(OrgAssessDept::getState, states)
                .update();
        Assert.state(update, "待分配或已自评状态不允许撤回");
        orgAssessReviewService.orgAssessDeptRevoke(id);

        return TRUE;
    }

    @Override
    public Map<Long, Map<String, OrgAssessDeptIndex>> indexMap(String orgAssessId) {
        // 查询部门考核列表
        Map<String, Long> idMap = super.lambdaQuery()
                .select(OrgAssessDept::getId, OrgAssessDept::getDeptId)
                .eq(OrgAssessDept::getOrgAssessId, orgAssessId)
                .list()
                .stream()
                .collect(toMap(OrgAssessDept::getId, OrgAssessDept::getDeptId));

        Function<List<OrgAssessDeptIndex>, Map<String, OrgAssessDeptIndex>> mapFunction =
                list -> list.stream().collect(toMap(OrgAssessDeptIndex::getTemplateIndexId, identity()));

        return StreamEx.of(orgAssessDeptIndexService.listByOrgAssessDeptIds(new ArrayList<>(idMap.keySet())))
                .groupingBy(i -> idMap.get(i.getOrgAssessDeptId()), collectingAndThen(toList(), mapFunction));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssessDept::getId)
                .eq(OrgAssessDept::getTemplateId, templateId)
                .list()
                .stream()
                .map(OrgAssessDept::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void orgAssessRevoke(String orgAssessId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssessDept::getId)
                .eq(OrgAssessDept::getOrgAssessId, orgAssessId)
                .list()
                .stream()
                .map(OrgAssessDept::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Override
    public Boolean operationAuth(String id) {
        OrgAssessDept orgAssessDept = SpringUtil.getBean(this.getClass()).getById(id);
        return UserSecurity.organizeId().equals(orgAssessDept.getDeptId().toString());
    }

    /**
     * 构建部门考核
     *
     * @param orgAssess 单位考核
     * @param deptId    部门ID
     * @param topics    部门考核指标列表
     * @return 部门考核
     */
    public OrgAssessDept buildOrgAssessDept(OrgAssess orgAssess, Long deptId, List<OrgAssessDeptIndex> topics) {
        // 将考核指标拆分为牵头/配合部门列表,便于计算
        Map<Boolean, List<OrgAssessDeptIndex>> typeMap = StreamEx.of(topics).groupingBy(OrgAssessDeptIndex::getType);
        List<OrgAssessDeptIndex> leads = typeMap.getOrDefault(TRUE, emptyList());
        List<OrgAssessDeptIndex> cooperates = typeMap.getOrDefault(FALSE, emptyList());

        int leadCount = leads.size();
        BigDecimal leadScore = StreamEx.of(leads).map(OrgAssessDeptIndex::getScore).reduce(ZERO, BigDecimal::add);

        int cooperateCount = cooperates.size();
        BigDecimal cooperateScore = StreamEx.of(cooperates)
                .map(OrgAssessDeptIndex::getScore)
                .reduce(ZERO, BigDecimal::add);

        return new OrgAssessDept()
                .setId(IdWorker.getIdStr())
                .setOrgAssessId(orgAssess.getId())
                .setTemplateId(orgAssess.getTemplateId())
                .setTemplateNumber(orgAssess.getTemplateNumber())
                .setTemplateName(orgAssess.getTemplateName())
                .setYear(orgAssess.getYear())
                .setLastSubmitDate(orgAssess.getLastSubmitDate())
                .setCooperateDeptEndDate(orgAssess.getCooperateDeptEndDate())
                .setDeptId(deptId)
                .setState(AWAIT_ALLOT.getState())
                .setLeadCount(leadCount)
                .setLeadAwaitCount(leadCount)
                .setLeadScore(leadScore)
                .setLeadAlreadyScore(ZERO)
                .setCooperateCount(cooperateCount)
                .setCooperateAwaitCount(cooperateCount)
                .setCooperateScore(cooperateScore)
                .setCooperateAlreadyScore(ZERO)
                .setRegionId(orgAssess.getRegionId())
                .setCountryId(orgAssess.getCountryId())
                .setProvinceId(orgAssess.getProvinceId())
                .setCityId(orgAssess.getCityId())
                .setAreaId(orgAssess.getAreaId())
                .setOrganizeId(deptId.toString());
    }

    /**
     * 构建部门考核指标
     *
     * @param index 单位考核指标
     * @param type  指标类型
     * @return 部门考核指标
     */
    private OrgAssessDeptIndex buildOrgAssessDeptIndex(OrgAssessIndex index, Boolean type) {
        return new OrgAssessDeptIndex()
                .setId(IdWorker.getIdStr())
                .setOrgAssessIndexId(index.getId())
                .setTemplateIndexId(index.getTemplateIndexId())
                .setTemplateIndexParentId(index.getTemplateIndexParentId())
                .setName(index.getName())
                .setTopic(index.getTopic())
                .setScore(index.getScore())
                .setStandard(index.getStandard())
                .setIllustrate(index.getIllustrate())
                .setLeadDeptId(index.getLeadDeptId())
                .setCooperateDeptIds(index.getCooperateDeptIds())
                .setType(type);
    }
}