package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.param.ReviewIndexAllotParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import com.sydata.safe.asess.vo.ReviewIndexTreeVo;

import java.util.Collection;
import java.util.List;

/**
 * 粮食安全考核-考核评审指标Service接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface IReviewIndexService extends IService<ReviewIndex> {

    /**
     * 列表
     *
     * @param reviewId 考核评审ID
     * @return 列表
     */
    List<ReviewIndex> listByReviewId(String reviewId);

    /**
     * 列表
     *
     * @param reviewIds 考核评审ID列表
     * @return 列表
     */
    List<ReviewIndex> listByReviewIds(Collection<String> reviewIds);

    /**
     * 树形列表
     *
     * @param reviewId 考核评审ID
     * @return 树形列表
     */
    List<ReviewIndexTreeVo> treeByReviewId(String reviewId);

    /**
     * 考核评审分配
     *
     * @param reviewIds 考核评审ID列表
     * @param list      考核评审指标分配参数列表
     * @return 分配结果
     */
    Boolean allot(List<String> reviewIds, List<ReviewIndexAllotParam> list);

    /**
     * 考核评分牵头指标提交
     *
     * @param reviewId 考核评审ID
     * @param leads    考核评分牵头指标列表
     * @return 操作状态
     */
    Boolean scoreLeadIndexSubmit(String reviewId, List<ScoreIndexAssessParam> leads);

    /**
     * 考核
     *
     * @param reviewId 考核评审ID
     * @param list     考核评审指标考核参数列表
     * @return 操作状态
     */
    Boolean assess(String reviewId, List<ReviewIndexAssessParam> list);

    /**
     * 根据考核评审ID列表删除
     *
     * @param reviewIds 考核评审ID列表
     */
    void removeByReviewIds(List<String> reviewIds);
}
