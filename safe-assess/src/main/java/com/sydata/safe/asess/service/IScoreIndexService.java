package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import com.sydata.safe.asess.vo.ScoreIndexTreeVo;

import java.util.List;

/**
 * 粮食安全考核-考核评分指标Service接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface IScoreIndexService extends IService<ScoreIndex> {

    /**
     * 列表
     *
     * @param scoreId 考核评分ID
     * @return 列表
     */
    List<ScoreIndex> listByScoreId(String scoreId);

    /**
     * 列表
     *
     * @param scoreIds 考核评分ID列表
     * @return 列表
     */
    List<ScoreIndex> listByScoreIds(List<String> scoreIds);

    /**
     * 树形列表
     *
     * @param scoreId 考核评分ID
     * @return 树形列表
     */
    List<ScoreIndexTreeVo> treeByScoreId(String scoreId);

    /**
     * 考核
     *
     * @param scoreId 考核评分ID
     * @param list    考核评分指标考核参数列表
     * @return 考核结果
     */
    boolean assess(String scoreId, List<ScoreIndexAssessParam> list);

    /**
     * 根据考核评分ID列表删除
     *
     * @param scoreIds 考核评分ID列表
     */
    void removeByScoreIds(List<String> scoreIds);
}
