package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.domain.Dept;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.domain.OrgAssessDept;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.domain.OrgAssessReview;
import com.sydata.safe.asess.domain.OrgAssessReviewIndex;
import com.sydata.safe.asess.mapper.OrgAssessReviewMapper;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessReviewPageParam;
import com.sydata.safe.asess.param.OrgAssessReviewSubmitParam;
import com.sydata.safe.asess.service.IOrgAssessDeptIndexService;
import com.sydata.safe.asess.service.IOrgAssessDeptService;
import com.sydata.safe.asess.service.IOrgAssessReviewIndexService;
import com.sydata.safe.asess.service.IOrgAssessReviewService;
import com.sydata.safe.asess.vo.OrgAssessReviewVo;
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
import java.util.stream.Collectors;

import static com.sydata.safe.asess.enums.SafeAssessStateEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮食安全考核-部门自评Service业务层处理
 *
 * @author system
 * @date 2023-02-20
 */
@CacheConfig(cacheNames = OrgAssessReviewServiceImpl.CACHE_NAME)
@Service("orgAssessReviewService")
public class OrgAssessReviewServiceImpl extends ServiceImpl<OrgAssessReviewMapper, OrgAssessReview>
        implements IOrgAssessReviewService {

    final static String CACHE_NAME = "safeAssess:orgAssessReview";

    @Resource
    private OrgAssessReviewMapper orgAssessReviewMapper;

    @Resource
    private IOrgAssessReviewIndexService orgAssessReviewIndexService;

    @Resource
    private IOrgAssessDeptService orgAssessDeptService;

    @Resource
    private IOrgAssessDeptIndexService orgAssessDeptIndexService;

    @Cacheable(key = "'id='+#id")
    @Override
    public OrgAssessReview getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssessReview, OrgAssessReview>composeExecute()
                .fields(OrgAssessReview::getId)
                .params((Collection<String>) list)
                .remove();

        orgAssessReviewIndexService.removeByOrgAssessDeptReviewIds((List<String>) list);
        return super.removeByIds(list);
    }

    @Override
    public void allot(OrgAssessDept orgAssessDept, List<OrgAssessDeptIndex> indexList) {
        Map<String, OrgAssessDeptIndex> idMap = StreamEx.of(indexList).toMap(OrgAssessDeptIndex::getTemplateIndexId, identity());
        List<OrgAssessDeptIndex> topicList = StreamEx.of(indexList).filter(OrgAssessDeptIndex::getTopic).toList();

        List<OrgAssessReview> reviews = new ArrayList<>();
        List<OrgAssessReviewIndex> reviewIndexList = new ArrayList<>();
        StreamEx.of(topicList).groupingBy(OrgAssessDeptIndex::getDeptId).forEach((deptId, topics) -> {
            // 构建部门考核
            OrgAssessReview orgAssessReview = buildOrgAssessReview(orgAssessDept, deptId, topics);
            reviews.add(orgAssessReview);

            Set<String> parentIds = new HashSet<>();
            topics.forEach(topic -> {
                // 构建部门自评指标
                OrgAssessReviewIndex orgAssessReviewIndex = buildOrgAssessReviewIndex(orgAssessReview, topic);
                reviewIndexList.add(orgAssessReviewIndex);

                // 寻找每道题目的所有父节点ID (题目的模板标题)
                OrgAssessDeptIndex parent = idMap.get(topic.getTemplateIndexParentId());
                while (parent != null) {
                    parentIds.add(parent.getTemplateIndexId());
                    parent = idMap.get(parent.getTemplateIndexParentId());
                }
            });

            // 部门考核指标 追加模板标题
            parentIds.forEach(parentId -> {
                OrgAssessDeptIndex orgAssessDeptIndex = idMap.get(parentId);
                OrgAssessReviewIndex title = buildOrgAssessReviewIndex(orgAssessReview, orgAssessDeptIndex);
                reviewIndexList.add(title);
            });
        });

        super.saveBatch(reviews);
        orgAssessReviewIndexService.saveBatch(reviewIndexList);
    }

    @DataBindFieldConvert
    @Override
    public Page<OrgAssessReviewVo> page(OrgAssessReviewPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        Long deptId = Optional.ofNullable(loginUser).map(LoginUser::getDept).map(Dept::getId).orElse(null);

        Page<OrgAssessReview> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getNumber()), OrgAssessReview::getTemplateNumber, pageParam.getNumber())
                .likeRight(isNotEmpty(pageParam.getName()), OrgAssessReview::getTemplateName, pageParam.getName())
                .eq(isNotEmpty(pageParam.getOrgAssessDeptId()), OrgAssessReview::getOrgAssessDeptId, pageParam.getOrgAssessDeptId())
                .eq(isEmpty(pageParam.getOrgAssessDeptId()), OrgAssessReview::getDeptId, deptId)
                .eq(isNotEmpty(pageParam.getOrganizeId()), OrgAssessReview::getOrganizeId, pageParam.getOrganizeId())
                .eq(isNotEmpty(pageParam.getDeptId()), OrgAssessReview::getDeptId, pageParam.getDeptId())
                .ge(isNotEmpty(pageParam.getCooperateDeptEndDateBegin()), OrgAssessReview::getCooperateDeptEndDate, pageParam.getCooperateDeptEndDateBegin())
                .le(isNotEmpty(pageParam.getCooperateDeptEndDateEnd()), OrgAssessReview::getCooperateDeptEndDate, pageParam.getCooperateDeptEndDateEnd())
                .orderByDesc(OrgAssessReview::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OrgAssessReviewVo.class);
    }

    @CacheEvict(key = "'id='+#submitParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean submit(OrgAssessReviewSubmitParam submitParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(submitParam.getId()), "非部门自评部门人员不可操作");

        OrgAssessReview review = SpringUtil.getBean(this.getClass()).getById(submitParam.getId());
        Set<String> states = SetUtils.hashSet(AWAIT_SELF_ASSESSMENT.getState(), RESET.getState());
        Assert.state(states.contains(review.getState()), "非待自评或已退回状态无法进行自评");

        // 查询指标列表
        List<OrgAssessReviewIndex> indexList = orgAssessReviewIndexService.listByAssessDeptReviewId(review.getId());
        Map<String, OrgAssessReviewIndex> indexMap = StreamEx.of(indexList).toMap(OrgAssessReviewIndex::getId, identity());

        // 找出已自评的指标列表
        List<OrgAssessReviewIndexSubmitParam> list = submitParam.getList();
        List<OrgAssessReviewIndexSubmitParam> submits = StreamEx.of(list)
                .filter(v -> isNotEmpty(v.getOrgIllustrate())).toList();

        // 校验是否已提交所有考核指标
        Set<String> topicIds = StreamEx.of(indexList)
                .filter(OrgAssessReviewIndex::getTopic)
                .map(OrgAssessReviewIndex::getId)
                .toSet();
        StreamEx.of(list).map(OrgAssessReviewIndexSubmitParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(CollectionUtils.isEmpty(topicIds), "并未提交所有考核指标,无法进行自评");

        // 如果是提交:所有指标自评完才能提交
        boolean isSubmit = SELF_ASSESSMENT.getState().equals(submitParam.getState());
        int count = review.getLeadCount() + review.getCooperateCount();
        Assert.state(!isSubmit || count == submits.size(), "所有指标自评完才能提交");

        submits.forEach(submit -> {
            OrgAssessReviewIndex index = indexMap.get(submit.getId());
            submit.setOrgAssessDeptIndexId(index.getOrgAssessDeptIndexId());
        });

        // 部门自评统计：待自评牵头指标数、已自评牵头指标数、已自评牵头指标总分、待自评配合指标数、已自评配合指标数、已自评配合指标总分
        Map<Boolean, List<OrgAssessReviewIndexSubmitParam>> typeMap = StreamEx.of(submits)
                .groupingBy(v -> indexMap.get(v.getId()).getType());

        List<OrgAssessReviewIndexSubmitParam> leadList = typeMap.getOrDefault(TRUE, emptyList());
        List<OrgAssessReviewIndexSubmitParam> cooperateList = typeMap.getOrDefault(FALSE, emptyList());

        int leadAwaitCount = review.getLeadCount() - leadList.size();
        BigDecimal leadAlreadyScore = StreamEx.of(leadList).map(OrgAssessReviewIndexSubmitParam::getOrgScore)
                .nonNull().reduce(ZERO, BigDecimal::add);

        int cooperateAwaitCount = review.getCooperateCount() - cooperateList.size();
        BigDecimal cooperateAlreadyScore = StreamEx.of(cooperateList).map(OrgAssessReviewIndexSubmitParam::getOrgScore)
                .nonNull().reduce(ZERO, BigDecimal::add);

        LocalDate cooperateDeptEndDate = review.getCooperateDeptEndDate();
        LocalDateTime submitTime = LocalDateTime.now();
        boolean submitOvertime = submitTime.toLocalDate().isAfter(cooperateDeptEndDate);

        boolean update = super.lambdaUpdate()
                .set(OrgAssessReview::getState, submitParam.getState())
                .set(OrgAssessReview::getLeadFinishCount, leadList.size())
                .set(OrgAssessReview::getLeadAwaitCount, leadAwaitCount)
                .set(OrgAssessReview::getLeadAlreadyScore, leadAlreadyScore)
                .set(OrgAssessReview::getCooperateFinishCount, cooperateList.size())
                .set(OrgAssessReview::getCooperateAwaitCount, cooperateAwaitCount)
                .set(OrgAssessReview::getCooperateAlreadyScore, cooperateAlreadyScore)
                .set(isSubmit, OrgAssessReview::getSubmitOvertime, submitOvertime)
                .set(isSubmit, OrgAssessReview::getSubmitTime, submitTime)
                .eq(OrgAssessReview::getId, review.getId())
                .in(OrgAssessReview::getState, AWAIT_SELF_ASSESSMENT.getState(), RESET.getState())
                .update();
        Assert.state(!isSubmit || update, "非待自评或已退回状态无法进行自评");
        Boolean submit = orgAssessReviewIndexService.submit(review.getId(), list);

        // 统计部门考核：提交自评部门数,将指标自评结果汇集到部门考核指标
        if (isSubmit) {
            orgAssessDeptService.operationDeptSubmitCount(review.getOrgAssessDeptId(), 1);
            orgAssessDeptIndexService.reviewSubmit(review.getOrgAssessDeptId(), submits);
        }
        return submit;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean reset(String id) {
        boolean update = super.lambdaUpdate()
                .set(OrgAssessReview::getState, RESET.getState())
                .setSql("lead_await_count = lead_count")
                .set(OrgAssessReview::getLeadFinishCount, 0)
                .set(OrgAssessReview::getLeadAlreadyScore, ZERO)
                .setSql("cooperate_await_count = cooperate_count")
                .set(OrgAssessReview::getCooperateFinishCount, 0)
                .set(OrgAssessReview::getCooperateAlreadyScore, ZERO)
                .set(OrgAssessReview::getSubmitOvertime, null)
                .set(OrgAssessReview::getSubmitTime, null)
                .eq(OrgAssessReview::getId, id)
                .eq(OrgAssessReview::getState, SELF_ASSESSMENT.getState())
                .update();
        Assert.state(update, "部门自评为已自评状态才允许退回");

        OrgAssessReview review = SpringUtil.getBean(this.getClass()).getById(id);
        return orgAssessDeptService.operationDeptSubmitCount(review.getOrgAssessDeptId(), -1);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssessReview::getId)
                .eq(OrgAssessReview::getTemplateId, templateId)
                .list()
                .stream()
                .map(OrgAssessReview::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void orgAssessRevoke(String orgAssessId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssessReview::getId)
                .eq(OrgAssessReview::getOrgAssessId, orgAssessId)
                .list()
                .stream()
                .map(OrgAssessReview::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void orgAssessDeptRevoke(String orgAssessDeptId) {
        List<String> ids = super.lambdaQuery()
                .select(OrgAssessReview::getId)
                .eq(OrgAssessReview::getOrgAssessDeptId, orgAssessDeptId)
                .list()
                .stream()
                .map(OrgAssessReview::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Override
    public Boolean operationAuth(String id) {
        OrgAssessReview orgAssessReview = SpringUtil.getBean(this.getClass()).getById(id);
        return Optional.ofNullable(UserSecurity.deptId())
                .map(deptId -> deptId.equals(orgAssessReview.getDeptId()))
                .orElse(FALSE);
    }


    /**
     * 构建部门考核
     *
     * @param orgAssessDept 部门考核
     * @param deptId        部门ID
     * @param topics        部门考核指标列表
     * @return 部门自评
     */
    private OrgAssessReview buildOrgAssessReview(OrgAssessDept orgAssessDept,
                                                 Long deptId,
                                                 List<OrgAssessDeptIndex> topics) {
        // 将考核指标拆分为牵头/配合部门列表,便于计算
        Map<Boolean, List<OrgAssessDeptIndex>> typeMap = StreamEx.of(topics).groupingBy(OrgAssessDeptIndex::getType);
        List<OrgAssessDeptIndex> leads = typeMap.getOrDefault(TRUE, emptyList());
        List<OrgAssessDeptIndex> cooperates = typeMap.getOrDefault(FALSE, emptyList());

        BigDecimal leadScore = StreamEx.of(leads)
                .map(OrgAssessDeptIndex::getScore)
                .reduce(ZERO, BigDecimal::add);

        BigDecimal cooperateScore = StreamEx.of(cooperates)
                .map(OrgAssessDeptIndex::getScore)
                .reduce(ZERO, BigDecimal::add);

        return new OrgAssessReview()
                .setId(IdWorker.getIdStr())
                .setOrgAssessId(orgAssessDept.getOrgAssessId())
                .setOrgAssessDeptId(orgAssessDept.getId())
                .setTemplateId(orgAssessDept.getTemplateId())
                .setTemplateNumber(orgAssessDept.getTemplateNumber())
                .setTemplateName(orgAssessDept.getTemplateName())
                .setYear(orgAssessDept.getYear())
                .setLastSubmitDate(orgAssessDept.getLastSubmitDate())
                .setCooperateDeptEndDate(orgAssessDept.getCooperateDeptEndDate())
                .setDeptId(deptId)
                .setState(AWAIT_SELF_ASSESSMENT.getState())
                .setLeadCount(leads.size())
                .setLeadAwaitCount(leads.size())
                .setLeadFinishCount(0)
                .setLeadScore(leadScore)
                .setLeadAlreadyScore(ZERO)
                .setCooperateCount(cooperates.size())
                .setCooperateAwaitCount(cooperates.size())
                .setCooperateFinishCount(0)
                .setCooperateScore(cooperateScore)
                .setCooperateAlreadyScore(ZERO)
                .setRegionId(orgAssessDept.getRegionId())
                .setCountryId(orgAssessDept.getCountryId())
                .setProvinceId(orgAssessDept.getProvinceId())
                .setCityId(orgAssessDept.getCityId())
                .setAreaId(orgAssessDept.getAreaId())
                .setOrganizeId(orgAssessDept.getOrganizeId());
    }

    /**
     * 构建部门自评指标
     *
     * @param orgAssessReview 部门自评
     * @param topic           部门考核指标
     * @return 部门自评指标
     */
    private OrgAssessReviewIndex buildOrgAssessReviewIndex(OrgAssessReview orgAssessReview, OrgAssessDeptIndex topic) {
        return new OrgAssessReviewIndex()
                .setId(IdWorker.getIdStr())
                .setOrgAssessDeptReviewId(orgAssessReview.getId())
                .setOrgAssessDeptIndexId(topic.getId())
                .setTemplateIndexId(topic.getTemplateIndexId())
                .setTemplateIndexId(topic.getTemplateIndexId())
                .setTemplateIndexParentId(topic.getTemplateIndexParentId())
                .setName(topic.getName())
                .setTopic(topic.getTopic())
                .setScore(topic.getScore())
                .setStandard(topic.getStandard())
                .setIllustrate(topic.getIllustrate())
                .setLeadDeptId(topic.getLeadDeptId())
                .setCooperateDeptIds(topic.getCooperateDeptIds())
                .setType(topic.getType())
                .setDeptId(topic.getDeptId());
    }
}