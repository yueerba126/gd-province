package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.safe.asess.domain.Review;
import com.sydata.safe.asess.domain.Score;
import com.sydata.safe.asess.param.ScorePageParam;
import com.sydata.safe.asess.vo.ScoreGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 粮食安全考核-考核评分Mapper接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface ScoreMapper extends BaseMapper<Score> {

    /**
     * 分组分页列表
     *
     * @param page      分页对象
     * @param pageParam 分页参数
     * @return 分组分页列表
     */
    @PageOptimizationExclude
    Page<ScoreGroupVo> groupPage(@Param("page") Page page, @Param("param") ScorePageParam pageParam);

    /**
     * 考核评分绑定单位考核
     *
     * @param reviews 考核模板ID
     * @return 绑定结果
     */
    boolean regionBind(List<Review> reviews);
}