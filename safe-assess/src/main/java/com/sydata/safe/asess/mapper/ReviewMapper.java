package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.safe.asess.domain.Review;
import com.sydata.safe.asess.param.ReviewPageParam;
import com.sydata.safe.asess.vo.ReviewGroupVo;
import org.apache.ibatis.annotations.Param;

/**
 * 粮食安全考核-考核评审Mapper接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface ReviewMapper extends BaseMapper<Review> {

    /**
     * 分组分页列表
     *
     * @param pageParam 分页参数
     * @return 分组分页列表
     */
    @PageOptimizationExclude
    Page<ReviewGroupVo> groupPage(@Param("page") Page page, @Param("param") ReviewPageParam pageParam);

    /**
     * 操作部门已考核总数
     *
     * @param id    考核评审ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptAssessCount(@Param("id") String id, @Param("count") int count);

}