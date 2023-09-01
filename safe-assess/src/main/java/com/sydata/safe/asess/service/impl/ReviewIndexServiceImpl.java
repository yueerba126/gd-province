package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.mapper.ReviewIndexMapper;
import com.sydata.safe.asess.param.ReviewIndexAllotParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import com.sydata.safe.asess.service.IReviewIndexService;
import com.sydata.safe.asess.vo.ReviewIndexTreeVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * 粮食安全考核-考核评审指标Service业务层处理
 *
 * @author system
 * @date 2023-04-03
 */
@CacheConfig(cacheNames = ReviewIndexServiceImpl.CACHE_NAME)
@Service("reviewIndexService")
public class ReviewIndexServiceImpl extends ServiceImpl<ReviewIndexMapper, ReviewIndex> implements IReviewIndexService {

    final static String CACHE_NAME = "safeAssess:reviewIndex";

    @Resource
    private ReviewIndexMapper reviewIndexMapper;


    @Override
    public boolean updateBatchById(Collection<ReviewIndex> entityList, int batchSize) {
        // 删除缓存
        List<String> reviewIds = StreamEx.of(entityList)
                .map(ReviewIndex::getReviewId)
                .distinct()
                .toList();
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, ReviewIndex, List<ReviewIndex>>composeExecute()
                .fields(ReviewIndex::getReviewId)
                .params(reviewIds)
                .remove();
        return super.updateBatchById(entityList, batchSize);
    }

    @Cacheable(key = "'reviewId='+#reviewId")
    @Override
    public List<ReviewIndex> listByReviewId(String reviewId) {
        return super.lambdaQuery().eq(ReviewIndex::getReviewId, reviewId).list();
    }

    @Override
    public List<ReviewIndex> listByReviewIds(Collection<String> reviewIds) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, ReviewIndex, List<ReviewIndex>>composeExecute()
                .fields(ReviewIndex::getReviewId)
                .params(reviewIds)
                .targets(ids -> super.lambdaQuery().in(ReviewIndex::getReviewId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(ReviewIndex::getReviewId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<ReviewIndexTreeVo> treeByReviewId(String reviewId) {
        ReviewIndexServiceImpl bean = SpringUtil.getBean(this.getClass());
        List<ReviewIndex> list = bean.listByReviewId(reviewId);
        list.sort(Comparator.comparing(ReviewIndex::getTemplateIndexId));
        List<ReviewIndexTreeVo> vos = BeanUtils.copyToList(list, ReviewIndexTreeVo.class);

        return TreeUtils.toTree(vos, ReviewIndexTreeVo::getTemplateIndexId,
                ReviewIndexTreeVo::getTemplateIndexParentId,
                ReviewIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @Override
    public Boolean allot(List<String> reviewIds, List<ReviewIndexAllotParam> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, ReviewIndex, List<ReviewIndex>>composeExecute()
                .fields(ReviewIndex::getReviewId)
                .params(reviewIds)
                .remove();
        return reviewIndexMapper.allot(reviewIds, list);
    }

    @CacheEvict(key = "'reviewId='+#reviewId")
    @Override
    public Boolean scoreLeadIndexSubmit(String reviewId, List<ScoreIndexAssessParam> leads) {
        return isEmpty(leads) ? FALSE : reviewIndexMapper.scoreLeadIndexSubmit(leads);
    }

    @CacheEvict(key = "'reviewId='+#reviewId")
    @Override
    public Boolean assess(String reviewId, List<ReviewIndexAssessParam> list) {
        return isEmpty(list) ? FALSE : reviewIndexMapper.assess(list);
    }

    @Override
    public void removeByReviewIds(List<String> reviewIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, ReviewIndex, ReviewIndex>composeExecute()
                .fields(ReviewIndex::getReviewId)
                .params(reviewIds)
                .remove();
        super.lambdaUpdate().in(ReviewIndex::getReviewId, reviewIds).remove();
    }
}
