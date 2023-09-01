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
import com.sydata.organize.domain.Dept;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.domain.Review;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.domain.Score;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.dto.ReviewAllotDto;
import com.sydata.safe.asess.mapper.ScoreMapper;
import com.sydata.safe.asess.param.ScoreAssessParam;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import com.sydata.safe.asess.param.ScorePageParam;
import com.sydata.safe.asess.service.IReviewIndexService;
import com.sydata.safe.asess.service.IReviewService;
import com.sydata.safe.asess.service.IScoreIndexService;
import com.sydata.safe.asess.service.IScoreService;
import com.sydata.safe.asess.vo.ScoreGroupVo;
import com.sydata.safe.asess.vo.ScoreVo;
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
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮食安全考核-考核评分Service业务层处理
 *
 * @author system
 * @date 2023-04-03
 */
@CacheConfig(cacheNames = ScoreServiceImpl.CACHE_NAME)
@Service("scoreService")
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

    final static String CACHE_NAME = "safeAssess:score";

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private IScoreIndexService scoreIndexService;

    @Resource
    private IReviewService reviewService;

    @Resource
    private IReviewIndexService reviewIndexService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Score getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Score, Score>composeExecute()
                .fields(Score::getId)
                .params((Collection<String>) list)
                .remove();
        scoreIndexService.removeByScoreIds((List<String>) list);
        return super.removeByIds(list);
    }

    @DataBindFieldConvert
    @Override
    public Page<ScoreGroupVo> groupPage(ScorePageParam pageParam) {
        Optional.ofNullable(UserSecurity.loginUser()).map(LoginUser::getDept).map(Dept::getId)
                .ifPresent(pageParam::setDeptId);

        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return scoreMapper.groupPage(page, pageParam);
    }

    @DataBindFieldConvert
    @Override
    public Page<ScoreVo> page(ScorePageParam pageParam) {
        Page<Score> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getTemplateId()), Score::getTemplateId, pageParam.getTemplateId())
                .eq(isNotEmpty(pageParam.getReviewId()), Score::getReviewId, pageParam.getReviewId())
                .eq(isNotEmpty(pageParam.getDeptId()), Score::getDeptId, pageParam.getDeptId())
                .orderByDesc(Score::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ScoreVo.class);
    }

    @Override
    public Boolean operationAuth(String id) {
        Score score = SpringUtil.getBean(this.getClass()).getById(id);
        return Optional.ofNullable(UserSecurity.loginUser())
                .map(LoginUser::getDept)
                .map(Dept::getId)
                .map(deptId -> deptId.equals(score.getDeptId()))
                .orElse(FALSE);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(List<ReviewAllotDto> reviewAllotDtoList) {
        List<Score> scores = new ArrayList<>();
        List<ScoreIndex> dtls = new ArrayList<>();

        // 构建考核评分
        reviewAllotDtoList.forEach(review -> {
            List<ReviewIndex> reviewDtls = review.getReviewDtls();
            Map<String, ReviewIndex> indexIdMap = StreamEx.of(reviewDtls).toMap(ReviewIndex::getTemplateIndexId, identity());
            Map<Long, List<ScoreIndex>> deptIdMap = toDeptIdMap(reviewDtls);

            deptIdMap.forEach((deptId, topics) -> {
                Map<Boolean, List<ScoreIndex>> allotTypeMap = StreamEx.of(topics)
                        .filter(ScoreIndex::getEnable)
                        .groupingBy(ScoreIndex::getAllotType);
                List<ScoreIndex> leads = allotTypeMap.getOrDefault(TRUE, Collections.emptyList());
                List<ScoreIndex> coops = allotTypeMap.getOrDefault(FALSE, Collections.emptyList());

                Score score = new Score()
                        .setId(IdWorker.getIdStr())
                        .setTemplateId(review.getTemplateId())
                        .setTemplateName(review.getTemplateName())
                        .setTemplateYear(review.getTemplateYear())
                        .setReviewId(review.getId())
                        .setDeptId(deptId)
                        .setAssessRegionId(review.getAssessRegionId())
                        .setLeadTotalCount(leads.size())
                        .setLeadTotalScore(leads.stream().map(ScoreIndex::getScore).reduce(ZERO, BigDecimal::add))
                        .setLeadAssessScore(ZERO)
                        .setCooperateTotalCount(coops.size())
                        .setCooperateTotalScore(coops.stream().map(ScoreIndex::getScore).reduce(ZERO, BigDecimal::add))
                        .setCooperateAssessScore(ZERO)
                        .setState(AWAIT_PUSH.getState());
                scores.add(score);

                // 寻找题目所有父节点ID (题目标题)并追加到考核评分指标列表中
                Set<String> parentIds = new HashSet<>();
                topics.forEach(topic -> {
                    topic.setScoreId(score.getId());

                    ReviewIndex parent = indexIdMap.get(topic.getTemplateIndexParentId());
                    while (parent != null) {
                        parentIds.add(parent.getTemplateIndexId());
                        parent = indexIdMap.get(parent.getTemplateIndexParentId());
                    }
                });
                parentIds.forEach(parentId -> {
                    ReviewIndex reviewIndex = indexIdMap.get(parentId);
                    ScoreIndex title = buildScoreIndex(reviewIndex, deptId);
                    topics.add(title.setScoreId(score.getId()));
                });

                dtls.addAll(topics);
            });
        });

        super.saveBatch(scores);
        return scoreIndexService.saveBatch(dtls);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean regionBind(List<Review> reviews) {
        boolean state = scoreMapper.regionBind(reviews);

        // 清除缓存
        List<String> ids = super.lambdaQuery()
                .select(Score::getId)
                .in(Score::getReviewId, StreamEx.of(reviews).map(Review::getId).toList())
                .list()
                .stream()
                .map(Score::getId)
                .collect(Collectors.toList());
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Score, Score>composeExecute()
                .fields(Score::getId)
                .params(ids)
                .remove();

        return state;
    }

    @Override
    public Boolean updateStateByReviewIds(List<String> reviewIds, String newState, String... oldStates) {
        // 根据考核评审ID查询ID列表
        List<String> ids = super.lambdaQuery()
                .select(Score::getId)
                .in(Score::getReviewId, reviewIds)
                .in(isNotEmpty(oldStates), Score::getState, StreamEx.of(oldStates).toList())
                .list()
                .stream()
                .map(Score::getId)
                .collect(Collectors.toList());

        // 修改考核评分状态
        AbstractWrapper wrapper = super.lambdaUpdate()
                .set(Score::getState, newState)
                .in(isNotEmpty(oldStates), Score::getState, StreamEx.of(oldStates).toList())
                .in(Score::getId, ids)
                .getWrapper();
        int rows = scoreMapper.update(null, wrapper);
        Assert.state(ids.size() == rows, String.format("考核评分修改状态为%s失败", newState));
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Score, Score>composeExecute()
                .fields(Score::getId)
                .params(ids)
                .remove();
        return TRUE;
    }

    @CacheEvict(key = "'id='+#assessParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean assess(ScoreAssessParam assessParam) {
        Assert.state(operationAuth(assessParam.getId()), "非考核自评部门人员,不可操作");

        Score score = SpringUtil.getBean(this.getClass()).getById(assessParam.getId());
        Assert.state(AWAIT_SCORE.getState().equals(score.getState()), "考核评分状态为待评分才能考核");

        // 校验是否提交完所有题目
        List<ScoreIndexAssessParam> list = assessParam.getList();
        List<ScoreIndex> indexList = scoreIndexService.listByScoreId(score.getId());
        Set<String> topicIds = StreamEx.of(indexList).filter(ScoreIndex::getTopic).map(ScoreIndex::getId).toSet();
        StreamEx.of(list).map(ScoreIndexAssessParam::getId).toSetAndThen(topicIds::removeAll);
        Assert.state(CollectionUtils.isEmpty(topicIds), "考核评分需要提交完所有指标才能考核");

        // 解析参数：牵头/配合 指标分配情况
        Map<String, ScoreIndex> indexMap = StreamEx.of(indexList).toMap(ScoreIndex::getId, identity());
        Map<Boolean, List<ScoreIndexAssessParam>> allotTypeMap = StreamEx.of(list)
                .filter(v -> indexMap.get(v.getId()).getTopic())
                .filter(v -> indexMap.get(v.getId()).getEnable())
                .filter(v -> v.getAssessScore() != null)
                .peek(v -> v.setReviewIndexId(indexMap.get(v.getId()).getReviewIndexId()))
                .groupingBy(v -> indexMap.get(v.getId()).getAllotType());
        List<ScoreIndexAssessParam> leads = allotTypeMap.getOrDefault(TRUE, Collections.emptyList());
        List<ScoreIndexAssessParam> cooperates = allotTypeMap.getOrDefault(FALSE, Collections.emptyList());

        boolean isSubmit = SCORE.getState().equals(assessParam.getState());
        if (isSubmit) {
            int totalCount = score.getLeadTotalCount() + score.getCooperateTotalCount();
            int allotTotalCount = leads.size() + cooperates.size();
            int count = totalCount - allotTotalCount;
            Assert.state(count == 0, String.format("考核评分指标剩余%s个未填写考核分数", count));
        }

        BigDecimal leadAssessScore = StreamEx.of(leads).map(ScoreIndexAssessParam::getAssessScore)
                .reduce(ZERO, BigDecimal::add);

        BigDecimal cooperateAssessScore = StreamEx.of(cooperates).map(ScoreIndexAssessParam::getAssessScore)
                .reduce(ZERO, BigDecimal::add);

        boolean update = super.lambdaUpdate()
                .set(Score::getState, assessParam.getState())
                .set(Score::getLeadAssessCount, leads.size())
                .set(Score::getLeadAssessScore, leadAssessScore)
                .set(Score::getCooperateAssessCount, cooperates.size())
                .set(Score::getCooperateAssessScore, cooperateAssessScore)
                .eq(Score::getId, score.getId())
                .eq(Score::getState, AWAIT_SCORE.getState())
                .update();
        Assert.state(!isSubmit || update, "考核评分状态为待评分才能考核");
        scoreIndexService.assess(score.getId(), list);

        // 统计考核评审：考核评分提交总数,将牵头指标评分结果汇集到考核评审指标
        if (isSubmit) {
            reviewService.operationDeptSubmitCount(score.getReviewId(), 1);
            reviewIndexService.scoreLeadIndexSubmit(score.getReviewId(), leads);
        }
        return update;
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean reset(String id) {
        // 考核评分状态校验并修改
        Score score = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(SCORE.getState().equals(score.getState()), "考核评分状态为已评分才能退回");
        boolean update = super.lambdaUpdate()
                .set(Score::getState, AWAIT_SCORE.getState())
                .eq(Score::getId, score.getId())
                .eq(Score::getState, SCORE.getState())
                .update();
        Assert.state(update, "考核评分状态为已评分才能退回");

        // 考核评审中的考核评分提交总数-1
        Review review = reviewService.getById(score.getReviewId());
        Assert.state(reviewService.operationAuth(review.getDeptId()), "非考核评审部门人员,不可操作");
        Set<String> states = SetUtils.hashSet(AWAIT_SCORE.getState(), SCORE.getState(), AWAIT_ASSESS.getState());
        Assert.state(states.contains(review.getState()), "考核评审状态为【待评分、已评分、待考核】才能退回考核评分");
        return reviewService.operationDeptSubmitCount(score.getReviewId(), -1);
    }

    @Override
    public Map<Long, Map<String, ScoreIndex>> indexMap(String reviewId) {
        Map<String, Long> idMap = super.lambdaQuery()
                .select(Score::getId, Score::getDeptId)
                .eq(Score::getReviewId, reviewId)
                .list()
                .stream()
                .collect(toMap(Score::getId, Score::getDeptId));

        Function<List<ScoreIndex>, Map<String, ScoreIndex>> mapFunction =
                list -> list.stream().collect(toMap(ScoreIndex::getTemplateIndexId, identity()));

        return StreamEx.of(scoreIndexService.listByScoreIds(new ArrayList<>(idMap.keySet())))
                .groupingBy(i -> idMap.get(i.getScoreId()), collectingAndThen(toList(), mapFunction));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void templateRevoke(String templateId) {
        List<String> ids = super.lambdaQuery()
                .select(Score::getId)
                .eq(Score::getTemplateId, templateId)
                .list()
                .stream()
                .map(Score::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }


    /**
     * 转成部门ID和考核评分指标列表的映射Map
     *
     * @param list 考核评审指标列表
     * @return 部门ID和考核评分指标列表的映射Map
     */
    private Map<Long, List<ScoreIndex>> toDeptIdMap(List<ReviewIndex> list) {
        Map<Long, List<ScoreIndex>> deptIdMap = MapUtil.newHashMap();

        List<ReviewIndex> indexList = StreamEx.of(list).filter(ReviewIndex::getTopic).toList();
        indexList.forEach(index -> {
            Set<Long> deptIds = SetUtils.hashSet(index.getAllotLeadDeptId());

            String cooperateDeptIds = index.getAllotCooperateDeptIds();
            if (com.sydata.framework.util.StringUtils.isNotEmpty(cooperateDeptIds)) {
                StreamEx.of(cooperateDeptIds.split(COMMA)).map(Long::parseLong).toListAndThen(deptIds::addAll);
            }

            deptIds.forEach(deptId -> {
                ScoreIndex scoreIndex = buildScoreIndex(index, deptId);
                deptIdMap.computeIfAbsent(deptId, (key) -> new ArrayList<>()).add(scoreIndex);
            });
        });

        return deptIdMap;
    }

    /**
     * 构建考考核评分指标
     *
     * @param reviewIndex 考核评审指标
     * @param deptId      部门ID
     * @return 考核评分指标
     */
    private ScoreIndex buildScoreIndex(ReviewIndex reviewIndex, Long deptId) {
        return new ScoreIndex()
                .setTemplateIndexId(reviewIndex.getTemplateIndexId())
                .setTemplateIndexParentId(reviewIndex.getTemplateIndexParentId())
                .setName(reviewIndex.getName())
                .setTopic(reviewIndex.getTopic())
                .setScore(reviewIndex.getScore())
                .setStandard(reviewIndex.getStandard())
                .setIllustrate(reviewIndex.getIllustrate())
                .setLeadDeptId(reviewIndex.getLeadDeptId())
                .setCooperateDeptIds(reviewIndex.getCooperateDeptIds())
                .setEnable(reviewIndex.getEnable())
                .setType(reviewIndex.getType())
                .setReviewIndexId(reviewIndex.getId())
                .setAllotLeadDeptId(reviewIndex.getAllotLeadDeptId())
                .setAllotCooperateDeptIds(reviewIndex.getAllotCooperateDeptIds())
                .setAllotType(reviewIndex.getTopic() ? deptId.equals(reviewIndex.getAllotLeadDeptId()) : FALSE);
    }
}