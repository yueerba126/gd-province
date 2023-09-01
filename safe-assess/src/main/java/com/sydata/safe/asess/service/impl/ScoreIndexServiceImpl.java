package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.mapper.ScoreIndexMapper;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import com.sydata.safe.asess.service.IScoreIndexService;
import com.sydata.safe.asess.vo.ScoreIndexTreeVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;

/**
 * 粮食安全考核-考核评分指标Service业务层处理
 *
 * @author system
 * @date 2023-04-03
 */
@CacheConfig(cacheNames = ScoreIndexServiceImpl.CACHE_NAME)
@Service("scoreIndexService")
public class ScoreIndexServiceImpl extends ServiceImpl<ScoreIndexMapper, ScoreIndex> implements IScoreIndexService {

    final static String CACHE_NAME = "safeAssess:scoreIndex";

    @Resource
    private ScoreIndexMapper scoreIndexMapper;

    @Cacheable(key = "'scoreId='+#scoreId")
    @Override
    public List<ScoreIndex> listByScoreId(String scoreId) {
        return super.lambdaQuery().eq(ScoreIndex::getScoreId, scoreId).list();
    }

    @Override
    public List<ScoreIndex> listByScoreIds(List<String> scoreIds) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, ScoreIndex, List<ScoreIndex>>composeExecute()
                .fields(ScoreIndex::getScoreId)
                .params(scoreIds)
                .targets(ids -> super.lambdaQuery().in(ScoreIndex::getScoreId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(ScoreIndex::getId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<ScoreIndexTreeVo> treeByScoreId(String scoreId) {
        ScoreIndexServiceImpl bean = SpringUtil.getBean(this.getClass());
        List<ScoreIndex> list = bean.listByScoreId(scoreId);
        list.sort(Comparator.comparing(ScoreIndex::getTemplateIndexId));
        List<ScoreIndexTreeVo> vos = BeanUtils.copyToList(list, ScoreIndexTreeVo.class);

        return TreeUtils.toTree(vos, ScoreIndexTreeVo::getTemplateIndexId,
                ScoreIndexTreeVo::getTemplateIndexParentId,
                ScoreIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @CacheEvict(key = "'scoreId='+#scoreId")
    @Override
    public boolean assess(String scoreId, List<ScoreIndexAssessParam> list) {
        return scoreIndexMapper.assess(list);
    }

    @Override
    public void removeByScoreIds(List<String> scoreIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, ScoreIndex, ScoreIndex>composeExecute()
                .fields(ScoreIndex::getScoreId)
                .params(scoreIds)
                .remove();
        super.lambdaUpdate().in(ScoreIndex::getScoreId, scoreIds).remove();
    }
}