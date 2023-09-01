package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.Review;
import com.sydata.safe.asess.domain.Score;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.dto.ReviewAllotDto;
import com.sydata.safe.asess.param.ScoreAssessParam;
import com.sydata.safe.asess.param.ScorePageParam;
import com.sydata.safe.asess.vo.ScoreGroupVo;
import com.sydata.safe.asess.vo.ScoreVo;

import java.util.List;
import java.util.Map;

/**
 * 粮食安全考核-考核评分Service接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface IScoreService extends IService<Score> {

    /**
     * 分组分页列表
     *
     * @param pageParam 分页参数
     * @return
     */
    Page<ScoreGroupVo> groupPage(ScorePageParam pageParam);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ScoreVo> page(ScorePageParam pageParam);

    /**
     * 操作权限
     *
     * @param id 考核模板ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);

    /**
     * 考核评审分配
     *
     * @param reviewAllotDtoList 考核评审分配DTO列表
     * @return 分配结果
     */
    Boolean allot(List<ReviewAllotDto> reviewAllotDtoList);

    /**
     * 考核评分绑定单位考核
     *
     * @param reviews 考核模板ID
     * @return 绑定结果
     */
    boolean regionBind(List<Review> reviews);

    /**
     * 根据考核评审ID修改状态
     *
     * @param reviewIds 考核评审ID列表
     * @param newState  新状态
     * @param oldStates 旧状态数组
     * @return 修改状态
     */
    Boolean updateStateByReviewIds(List<String> reviewIds, String newState, String... oldStates);

    /**
     * 评分考核
     *
     * @param assessParam 考核评分考核参数
     * @return 考核结果
     */
    boolean assess(ScoreAssessParam assessParam);

    /**
     * 退回
     *
     * @param id 考核评分ID
     * @return 操作状态
     */
    Boolean reset(String id);

    /**
     * 根据考核评审ID查询考核评分指标映射
     *
     * @param reviewId 考核评审ID
     * @return 考核评分指标映射《部门ID,《考核模板指标ID,考核评分指标》》
     */
    Map<Long, Map<String, ScoreIndex>> indexMap(String reviewId);

    /**
     * 考核模板撤回
     *
     * @param templateId 考核模板ID
     */
    void templateRevoke(String templateId);
}