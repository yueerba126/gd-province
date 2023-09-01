package com.sydata.safe.asess.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.organize.domain.Dept;
import com.sydata.organize.permission.DataPermissionSqlQueryIntercept;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.domain.*;
import com.sydata.safe.asess.dto.ReviewAllotDto;
import com.sydata.safe.asess.mapper.ReviewMapper;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.*;
import com.sydata.safe.asess.vo.ReviewGroupVo;
import com.sydata.safe.asess.vo.ReviewVo;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.safe.asess.enums.SafeAssessStateEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * 粮食安全考核-考核评审Service业务层处理
 *
 * @author system
 * @date 2023-04-03
 */
@CacheConfig(cacheNames = ReviewServiceImpl.CACHE_NAME)
@Service("reviewService")
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements IReviewService {

    final static String CACHE_NAME = "safeAssess:review";

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private IReviewIndexService reviewIndexService;

    @Resource
    private IScoreService scoreService;

    @Resource
    private ITemplateService templateService;

    @Resource
    private IOrgAssessService orgAssessService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Review getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateBatchById(Collection<Review> entityList, int batchSize) {
        // 删除缓存
        List<String> reviewIds = StreamEx.of(entityList)
                .map(Review::getId)
                .distinct()
                .toList();
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Review, Review>composeExecute()
                .fields(Review::getId)
                .params(reviewIds)
                .remove();
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Review, Review>composeExecute()
                .fields(Review::getId)
                .params((Collection<String>) list)
                .remove();
        reviewIndexService.removeByReviewIds((List<String>) list);
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<ReviewGroupVo> groupPage(ReviewPageParam pageParam) {
        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return reviewMapper.groupPage(page, pageParam.setOrganizeId(UserSecurity.organizeId()));
    }

    @DataBindFieldConvert
    @Override
    public Page<ReviewVo> page(ReviewPageParam pageParam) {
        Page<Review> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getTemplateId()), Review::getTemplateId, pageParam.getTemplateId())
                .eq(isNotEmpty(pageParam.getDeptId()), Review::getDeptId, pageParam.getDeptId())
                .eq(isNotEmpty(pageParam.getOrgAssessId()), Review::getOrgAssessId, pageParam.getOrgAssessId())
                .orderByDesc(Review::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ReviewVo.class);
    }

    @Override
    public Boolean operationAuth(Long deptId) {
        return UserSecurity.organizeId().equals(deptId.toString());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(Template template, List<TemplateIndex> list,
                         LocalDate allotDate, Map<String, Dept> autoAllotMap) {
        // 解析考核模板内容
        Set<String> regionIds = SetUtils.hashSet(template.getRegionIds().split(COMMA));

        Map<String, TemplateIndex> templateIndexIdMap = StreamEx.of(list).toMap(TemplateIndex::getId, identity());

        // 构建考核评审
        List<Review> reviews = new ArrayList<>();
        List<ReviewIndex> reviewDtls = new ArrayList<>();
        regionIds.forEach(regionId -> {
            Map<Long, List<ReviewIndex>> deptIdMap = toDeptIdMap(list, autoAllotMap, regionId);

            deptIdMap.forEach((deptId, topics) -> {
                Map<Boolean, List<ReviewIndex>> typeMap = StreamEx.of(topics).filter(ReviewIndex::getEnable)
                        .groupingBy(ReviewIndex::getType);
                List<ReviewIndex> leads = typeMap.getOrDefault(TRUE, Collections.emptyList());
                List<ReviewIndex> coops = typeMap.getOrDefault(FALSE, Collections.emptyList());
                boolean autoAllot = autoAllotMap.containsKey(deptId.toString());

                Review review = new Review()
                        .setId(IdWorker.getIdStr())
                        .setAllotDate(allotDate)
                        .setAutoAllot(autoAllot)
                        .setTemplateId(template.getId())
                        .setTemplateName(template.getName())
                        .setTemplateYear(template.getYear())
                        .setAssessRegionId(regionId)
                        .setDeptId(deptId)
                        .setState(autoAllot ? AWAIT_PUSH.getState() : AWAIT_ALLOT.getState())
                        .setLeadTotalCount(leads.size())
                        .setLeadTotalScore(leads.stream().map(ReviewIndex::getScore).reduce(ZERO, BigDecimal::add))
                        .setLeadReviewScore(ZERO)
                        .setCooperateTotalCount(coops.size())
                        .setCooperateTotalScore(coops.stream().map(ReviewIndex::getScore).reduce(ZERO, BigDecimal::add))
                        .setCooperateReviewScore(ZERO)
                        .setDeptTotalCount(autoAllot ? 1 : 0)
                        .setLeadAllotCount(autoAllot ? leads.size() : 0)
                        .setCooperateAllotCount(autoAllot ? coops.size() : 0)
                        .setOrganizeId(deptId.toString());
                reviews.add(review);

                // 寻找题目所有父节点ID (题目标题)并追加到考核评审指标列表中
                Set<String> parentIds = new HashSet<>();
                topics.forEach(topic -> {
                    topic.setReviewId(review.getId());

                    TemplateIndex parent = templateIndexIdMap.get(topic.getTemplateIndexParentId());
                    while (parent != null) {
                        parentIds.add(parent.getId());
                        parent = templateIndexIdMap.get(parent.getParentId());
                    }
                });
                parentIds.forEach(parentId -> {
                    TemplateIndex templateIndex = templateIndexIdMap.get(parentId);
                    ReviewIndex title = buildReviewIndex(templateIndex, deptId, regionId, autoAllotMap);
                    topics.add(title.setReviewId(review.getId()));
                });
                reviewDtls.addAll(topics);
            });
        });

        boolean state = super.saveBatch(reviews);
        reviewIndexService.saveBatch(reviewDtls);

        // 自动分配考核评分
        List<Review> reviewAutoAllots = StreamEx.of(reviews).filter(Review::getAutoAllot).toList();
        if (CollectionUtils.isNotEmpty(reviewAutoAllots)) {
            allotScore(reviewAutoAllots, reviewDtls);
            templateService.operationDeptAllotCount(template.getId(), autoAllotMap.size());
        }
        return state;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(ReviewAllotParam allotParam) {
        Assert.state(operationAuth(allotParam.getDeptId()), "非考核评审部门人员,不可操作");
        List<Review> reviews = super.lambdaQuery()
                .eq(Review::getTemplateId, allotParam.getTemplateId())
                .eq(Review::getDeptId, allotParam.getDeptId())
                .list();
        Assert.notEmpty(reviews, "考核评审待分配才能分配");
        Review review = reviews.get(0);

        // 校验是否提交完所有题目
        List<ReviewIndexAllotParam> list = allotParam.getList();
        List<ReviewIndex> indexList = reviewIndexService.listByReviewId(review.getId());
        Set<String> topicIds = StreamEx.of(indexList).filter(ReviewIndex::getTopic)
                .map(ReviewIndex::getTemplateIndexId).toSet();
        StreamEx.of(list).map(ReviewIndexAllotParam::getTemplateIndexId).toSetAndThen(topicIds::removeAll);
        Assert.state(isEmpty(topicIds), "考核评审需要提交完所有指标才能分配");

        // 解析参数：牵头/配合 指标分配情况
        Map<String, ReviewIndex> indexMap = StreamEx.of(indexList).toMap(ReviewIndex::getTemplateIndexId, identity());
        Map<Boolean, List<ReviewIndexAllotParam>> typeMap = StreamEx.of(list)
                .filter(v -> indexMap.get(v.getTemplateIndexId()).getTopic())
                .filter(v -> v.getAllotLeadDeptId() != null)
                .groupingBy(v -> indexMap.get(v.getTemplateIndexId()).getType());
        List<ReviewIndexAllotParam> leadAllots = typeMap.getOrDefault(TRUE, Collections.emptyList());
        List<ReviewIndexAllotParam> cooperateAllots = typeMap.getOrDefault(FALSE, Collections.emptyList());

        // 提交分配时需要校验是否已分配完
        boolean isSubmit = AWAIT_PUSH.getState().equals(allotParam.getState());
        if (isSubmit) {
            Integer lead = StreamEx.of(reviews).map(Review::getLeadTotalCount).max(Integer::compareTo).orElse(0);
            Integer coop = StreamEx.of(reviews).map(Review::getCooperateTotalCount).max(Integer::compareTo).orElse(0);
            int totalCount = lead + coop;

            int allotTotalCount = leadAllots.size() + cooperateAllots.size();
            int count = totalCount - allotTotalCount;
            Assert.state(count == 0, String.format("考核评审指标剩余%s个未分配牵头部门", count));
        }

        // 统计分配部门数
        int deptTotalCount = StreamEx.of(list)
                .map(index -> {
                    Set<Long> deptIds = new HashSet<>();
                    // 牵头部门
                    Long allotLeadDeptId = index.getAllotLeadDeptId();
                    if (allotLeadDeptId != null) {
                        deptIds.add(allotLeadDeptId);
                    }
                    // 配合部门
                    String allotCooperateDeptIds = index.getAllotCooperateDeptIds();
                    if (StringUtils.isNotEmpty(allotCooperateDeptIds)) {
                        StreamEx.of(allotCooperateDeptIds.split(COMMA))
                                .map(Long::parseLong)
                                .toListAndThen(deptIds::addAll);
                    }
                    return deptIds;
                })
                .flatMap(Collection::stream)
                .toSet()
                .size();

        AbstractWrapper wrapper = super.lambdaUpdate()
                .set(Review::getState, allotParam.getState())
                .setSql(String.format("lead_allot_count=least(%s,lead_total_count)", leadAllots.size()))
                .setSql(String.format("cooperate_allot_count=least(%s,cooperate_total_count)", cooperateAllots.size()))
                .set(Review::getDeptTotalCount, deptTotalCount)
                .eq(Review::getTemplateId, allotParam.getTemplateId())
                .eq(Review::getDeptId, allotParam.getDeptId())
                .eq(Review::getState, AWAIT_ALLOT.getState())
                .getWrapper();
        int rows = reviewMapper.update(null, wrapper);
        boolean update = rows == reviews.size();

        Assert.state(!isSubmit || update, "考核评审待分配才能分配");
        List<String> ids = StreamEx.of(reviews).map(Review::getId).toList();
        reviewIndexService.allot(ids, list);

        // 分配考核评分
        if (isSubmit) {
            allotScore(reviews, reviewIndexService.listByReviewIds(ids));
            templateService.operationDeptAllotCount(review.getTemplateId(), 1);
        }
        return update;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean regionBind(String templateId, List<OrgAssess> assesses, List<OrgAssessIndex> orgAssessIndexList) {
        // 根据考核模板ID查找相关考核评审列表
        List<Review> reviews = super.lambdaQuery().eq(Review::getTemplateId, templateId).list();
        Map<String, List<Review>> regionMap = StreamEx.of(reviews).groupingBy(Review::getAssessRegionId);

        List<String> ids = StreamEx.of(reviews).map(Review::getId).toList();
        List<ReviewIndex> reviewDtls = reviewIndexService.listByReviewIds(ids);
        Map<String, List<ReviewIndex>> reviewMap = StreamEx.of(reviewDtls).groupingBy(ReviewIndex::getReviewId);

        // 单位考核根据行政区域映射,单位考核指标根据单位考核ID分组
        Map<String, OrgAssess> regionIdMap = StreamEx.of(assesses).toMap(OrgAssess::getRegionId, identity());
        Map<String, List<OrgAssessIndex>> orgAssessIndexMap = StreamEx.of(orgAssessIndexList)
                .groupingBy(OrgAssessIndex::getOrgAssessId);

        // 遍历行政区域映射Map,绑定单位考核和考核评审的关系
        List<ReviewIndex> indexList = new ArrayList<>();
        regionIdMap.forEach((regionId, orgAssess) -> {

            Map<String, OrgAssessIndex> templateIndexIdMap = StreamEx
                    .of(orgAssessIndexMap.get(orgAssess.getId()))
                    .toMap(OrgAssessIndex::getTemplateIndexId, identity());

            // 考核评审设置单位考核ID、考核评审指标设置单位考核指标ID
            List<ReviewIndex> regionIndexList = new ArrayList<>();
            List<Review> reviewList = regionMap.get(regionId);
            reviewList.forEach(review -> {
                review.setOrgAssessId(orgAssess.getId()).setState(AWAIT_SUBMIT.getState());

                List<ReviewIndex> list = reviewMap.get(review.getId());
                list.forEach(index -> {
                    OrgAssessIndex assessIndex = templateIndexIdMap.get(index.getTemplateIndexId());
                    index.setOrgAssessIndexId(assessIndex.getId());
                });
                regionIndexList.addAll(list);
            });
            indexList.addAll(regionIndexList);


            // 单位考核指标绑定：考核评评审分配牵头部门ID/考核评分分配配合部门IDS
            Map<Boolean, Long> typeCountMap = StreamEx.of(regionIndexList)
                    .filter(ReviewIndex::getTopic)
                    .filter(ReviewIndex::getEnable)
                    .groupingBy(ReviewIndex::getType, counting());

            int deptLeadCount = Math.toIntExact(typeCountMap.getOrDefault(TRUE, 0L));
            int deptCooperateCount = Math.toIntExact(typeCountMap.getOrDefault(FALSE, 0L));

            orgAssess.setDeptAssessCount(reviewList.size())
                    .setDeptLeadCount(deptLeadCount)
                    .setDeptLeadAwaitCount(deptLeadCount)
                    .setDeptCooperateCount(deptCooperateCount)
                    .setDeptCooperateAwaitCount(deptCooperateCount);
        });

        super.updateBatchById(reviews);
        reviewIndexService.updateBatchById(indexList);

        // 考核评分绑定单位考核
        return scoreService.regionBind(reviews);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean regionSubmit(String orgAssessId) {
        return DataPermissionSqlQueryIntercept.releaseRun(() -> {
            // 根据考核单位ID查询ID列表
            List<String> ids = super.lambdaQuery()
                    .select(Review::getId)
                    .eq(Review::getOrgAssessId, orgAssessId)
                    .eq(Review::getState, AWAIT_SUBMIT.getState())
                    .list()
                    .stream()
                    .map(Review::getId)
                    .collect(Collectors.toList());

            // 修改考核评审状态为待评分
            AbstractWrapper wrapper = super.lambdaUpdate()
                    .set(Review::getState, AWAIT_SCORE.getState())
                    .in(Review::getId, ids)
                    .eq(Review::getState, AWAIT_SUBMIT.getState())
                    .getWrapper();
            int rows = reviewMapper.update(null, wrapper);
            Assert.state(ids.size() == rows, "考核评审修改状态为待评分失败");
            MultiCacheBatchHelp.apply(CACHE_NAME).<String, Review, Review>composeExecute()
                    .fields(Review::getId)
                    .params(ids)
                    .remove();

            // 修改考核评分状态为待评分
            return scoreService.updateStateByReviewIds(ids, AWAIT_SCORE.getState(), AWAIT_SUBMIT.getState());
        });
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean regionReset(String orgAssessId) {
        List<String> ids = super.lambdaQuery()
                .select(Review::getId)
                .eq(Review::getOrgAssessId, orgAssessId)
                .in(Review::getState, AWAIT_SCORE.getState(), AWAIT_ASSESS.getState(), ASSESS.getState())
                .list()
                .stream()
                .map(Review::getId)
                .collect(toList());

        // 考核评审重置为待提交
        AbstractWrapper wrapper = super.lambdaUpdate()
                .set(Review::getState, AWAIT_SUBMIT.getState())
                .set(Review::getDeptSubmitCount, 0)
                .set(Review::getLeadReviewCount, 0)
                .set(Review::getLeadReviewScore, ZERO)
                .set(Review::getCooperateReviewCount, 0)
                .set(Review::getCooperateReviewScore, ZERO)
                .in(Review::getState, AWAIT_SCORE.getState(), AWAIT_ASSESS.getState(), ASSESS.getState())
                .in(Review::getId, ids)
                .getWrapper();
        int rows = reviewMapper.update(null, wrapper);
        Assert.state(ids.size() == rows, "考核评审修改状态为待提交失败");
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Review, Review>composeExecute()
                .fields(Review::getId)
                .params(ids)
                .remove();

        // 修改考核评分状态为待提交
        return scoreService.updateStateByReviewIds(ids, AWAIT_SUBMIT.getState(), AWAIT_SCORE.getState(), SCORE.getState());
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean operationDeptSubmitCount(String id, int count) {
        Boolean state = reviewMapper.operationDeptAssessCount(id, count);
        Assert.state(state, "考核评审统计考核评分提交总数失败");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#assessParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean assess(ReviewAssessParam assessParam) {
        Review review = SpringUtil.getBean(this.getClass()).getById(assessParam.getId());
        Assert.state(operationAuth(review.getDeptId()), "非考核评审部门人员,不可操作");
        Assert.state(AWAIT_ASSESS.getState().equals(review.getState()), "考核评审状态为待考核才能考核");

        // 校验是否提交完所有题目
        List<ReviewIndexAssessParam> list = assessParam.getList();
        List<ReviewIndex> indexList = reviewIndexService.listByReviewId(review.getId());
        Set<String> topicIds = StreamEx.of(indexList).filter(ReviewIndex::getTopic).map(ReviewIndex::getId).toSet();
        StreamEx.of(list).map(ReviewIndexAssessParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(isEmpty(topicIds), "考核评审需要提交完所有指标才能考核");

        // 解析参数：牵头/配合 指标分配情况
        Map<String, ReviewIndex> indexMap = StreamEx.of(indexList).toMap(ReviewIndex::getId, identity());
        Map<Boolean, List<ReviewIndexAssessParam>> typeMap = StreamEx.of(list)
                .filter(v -> indexMap.get(v.getId()).getTopic())
                .filter(v -> indexMap.get(v.getId()).getEnable())
                .filter(v -> v.getAssessScore() != null)
                .peek(v -> v.setOrgAssessIndexId(indexMap.get(v.getId()).getOrgAssessIndexId()))
                .groupingBy(v -> indexMap.get(v.getId()).getType());
        List<ReviewIndexAssessParam> leads = typeMap.getOrDefault(TRUE, Collections.emptyList());
        List<ReviewIndexAssessParam> cooperates = typeMap.getOrDefault(FALSE, Collections.emptyList());


        boolean isSubmit = ASSESS.getState().equals(assessParam.getState());
        if (isSubmit) {
            int totalCount = review.getLeadTotalCount() + review.getCooperateTotalCount();
            int allotTotalCount = leads.size() + cooperates.size();
            int count = totalCount - allotTotalCount;
            Assert.state(count == 0, String.format("考核评审指标剩余%s个未填写考核分数", count));
        }

        BigDecimal leadAssessScore = StreamEx.of(leads).map(ReviewIndexAssessParam::getAssessScore)
                .reduce(ZERO, BigDecimal::add);

        BigDecimal cooperateAssessScore = StreamEx.of(cooperates).map(ReviewIndexAssessParam::getAssessScore)
                .reduce(ZERO, BigDecimal::add);

        boolean update = super.lambdaUpdate()
                .set(Review::getState, assessParam.getState())
                .set(Review::getLeadReviewCount, leads.size())
                .set(Review::getLeadReviewScore, leadAssessScore)
                .set(Review::getCooperateReviewCount, cooperates.size())
                .set(Review::getCooperateReviewScore, cooperateAssessScore)
                .eq(Review::getId, review.getId())
                .eq(Review::getState, AWAIT_ASSESS.getState())
                .update();
        Assert.state(!isSubmit || update, "考核评审状态为待考核才能考核");
        reviewIndexService.assess(review.getId(), list);


        // 将考核结果赋值到单位考核、当该部门考核完这个模板下的所有地市才去操作考核模板部门已完成考核数量
        if (isSubmit) {
            orgAssessService.assess(review.getOrgAssessId(), review.getTemplateId(), leads, cooperates.size());

            Long count = super.lambdaQuery()
                    .eq(Review::getTemplateId, review.getTemplateId())
                    .eq(Review::getDeptId, review.getDeptId())
                    .ne(Review::getState, ASSESS.getState())
                    .count();
            if (count == 0) {
                templateService.operationDeptAssessCount(review.getTemplateId(), 1);
            }
        }
        return update;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean reset(String id) {
        // 考核评分状态校验并修改
        Review review = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(ASSESS.getState().equals(review.getState()), "考核评审状态为已考核才能退回");
        boolean update = super.lambdaUpdate()
                .set(Review::getState, AWAIT_ASSESS.getState())
                .eq(Review::getId, review.getId())
                .eq(Review::getState, ASSESS.getState())
                .update();
        Assert.state(update, "考核评审状态为已考核才能退回");

        // 考核模板统计考核评审已考核数量-1
        Template template = templateService.getById(review.getTemplateId());
        Assert.state(templateService.operationAuth(template.getId()), "非考核模板组织人员不可操作");
        Long count = super.lambdaQuery()
                .eq(Review::getTemplateId, review.getTemplateId())
                .eq(Review::getDeptId, review.getDeptId())
                .ne(Review::getState, ASSESS.getState())
                .count();
        if (count == 1) {
            templateService.operationDeptAssessCount(review.getTemplateId(), -1);
        }

        // 单位考核退回
        List<ReviewIndex> indexList = reviewIndexService.listByReviewId(review.getId());
        Map<Boolean, List<ReviewIndex>> map = StreamEx.of(indexList)
                .filter(ReviewIndex::getTopic)
                .filter(ReviewIndex::getEnable)
                .groupingBy(ReviewIndex::getType);
        List<ReviewIndex> leads = map.getOrDefault(TRUE, Collections.emptyList());
        List<ReviewIndex> cooperates = map.getOrDefault(FALSE, Collections.emptyList());
        return orgAssessService.assessReset(review.getOrgAssessId(), review.getTemplateId(), leads, cooperates.size());
    }

    @Override
    public Map<String, Map<Long, Map<String, ReviewIndex>>> indexMaps(String templateId, List<String> assessRegionIds) {
        // 查询考核评审列表和指标
        Map<String, Review> idMap = super.lambdaQuery()
                .select(Review::getId, Review::getDeptId, Review::getAssessRegionId)
                .eq(Review::getTemplateId, templateId)
                .in(Review::getAssessRegionId, assessRegionIds)
                .list()
                .stream()
                .collect(toMap(Review::getId, Function.identity()));

        List<ReviewIndex> reviewIndices = reviewIndexService.listByReviewIds(idMap.keySet());

        // 指标map方法
        Function<List<ReviewIndex>, Map<String, ReviewIndex>> indexMapFunction = list ->
                list.stream().collect(toMap(ReviewIndex::getTemplateIndexId, identity()));

        // 部门指标map方法
        Function<ReviewIndex, Long> deptId = dtl -> idMap.get(dtl.getReviewId()).getDeptId();
        Function<List<ReviewIndex>, Map<Long, Map<String, ReviewIndex>>> deptMapFunction = list ->
                list.stream().collect(groupingBy(deptId, collectingAndThen(toList(), indexMapFunction)));

        // 区域部门指标map
        Function<ReviewIndex, String> regionId = dtl -> idMap.get(dtl.getReviewId()).getAssessRegionId();
        return StreamEx.of(reviewIndices).groupingBy(regionId, collectingAndThen(toList(), deptMapFunction));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(Review::getId)
                .eq(Review::getTemplateId, templateId)
                .list()
                .stream()
                .map(Review::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    /**
     * 转成部门ID和考核评审指标列表的映射Map
     *
     * @param list         考核模板指标列表
     * @param autoAllotMap 自动分配map
     * @param regionId     行政区划ID
     * @return 部门ID和考核评审指标列表的映射Map
     */
    private Map<Long, List<ReviewIndex>> toDeptIdMap(List<TemplateIndex> list, Map<String, Dept> autoAllotMap, String regionId) {
        Map<Long, List<ReviewIndex>> deptIdMap = MapUtil.newHashMap();

        List<TemplateIndex> indexList = StreamEx.of(list).filter(TemplateIndex::getTopic).toList();
        indexList.forEach(index -> {
            Set<Long> deptIds = SetUtils.hashSet(index.getLeadDeptId());

            String cooperateDeptIds = index.getCooperateDeptIds();
            if (StringUtils.isNotEmpty(cooperateDeptIds)) {
                StreamEx.of(cooperateDeptIds.split(COMMA)).map(Long::parseLong).toListAndThen(deptIds::addAll);
            }

            deptIds.forEach(deptId -> {
                ReviewIndex reviewIndex = buildReviewIndex(index, deptId, regionId, autoAllotMap);
                deptIdMap.computeIfAbsent(deptId, (key) -> new ArrayList<>()).add(reviewIndex);
            });
        });
        return deptIdMap;
    }

    /**
     * 构建考核评审指标
     *
     * @param templateIndex 考核模板指标
     * @param deptId        部门ID
     * @param regionId      行政区划ID
     * @param autoAllotMap  自动分配map
     * @return 考核评审指标
     */
    private ReviewIndex buildReviewIndex(TemplateIndex templateIndex, Long deptId,
                                         String regionId, Map<String, Dept> autoAllotMap) {
        boolean enable = true;
        String lackRegionIds = templateIndex.getLackRegionIds();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotEmpty(lackRegionIds)) {
            enable = !SetUtils.hashSet(lackRegionIds.split(COMMA)).contains(regionId);
        }

        Dept dept = autoAllotMap.get(deptId.toString());
        return new ReviewIndex()
                .setTemplateIndexId(templateIndex.getId())
                .setTemplateIndexParentId(templateIndex.getParentId())
                .setName(templateIndex.getName())
                .setTopic(templateIndex.getTopic())
                .setScore(templateIndex.getScore())
                .setStandard(templateIndex.getStandard())
                .setIllustrate(templateIndex.getIllustrate())
                .setLeadDeptId(templateIndex.getLeadDeptId())
                .setCooperateDeptIds(templateIndex.getCooperateDeptIds())
                .setEnable(enable)
                .setType(templateIndex.getTopic() ? deptId.equals(templateIndex.getLeadDeptId()) : FALSE)
                .setAllotLeadDeptId(dept == null ? null : dept.getId());
    }

    /**
     * 分配考核评分
     *
     * @param reviews    考核评审列表
     * @param reviewDtls 考核评审指标列表
     */
    private void allotScore(List<Review> reviews, List<ReviewIndex> reviewDtls) {
        Map<String, List<ReviewIndex>> reviewMap = StreamEx.of(reviewDtls).groupingBy(ReviewIndex::getReviewId);
        StreamEx.of(reviews)
                .map(review -> BeanUtils.copyByClass(review, ReviewAllotDto.class))
                .peek(reviewDto -> reviewDto.setReviewDtls(reviewMap.get(reviewDto.getId())))
                .toListAndThen(scoreService::allot);
    }
}