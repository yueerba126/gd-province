package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Finance;
import com.sydata.basis.param.FinancePageParam;
import com.sydata.basis.vo.FinanceVo;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import org.apache.ibatis.annotations.Param;

/**
 * 财务报信息Mapper接口
 *
 * @author system
 * @date 2022-12-07
 */
public interface FinanceMapper extends BaseMapper<Finance> {

    /**
     * 分页报表
     *
     * @param page  分页参数
     * @param param 数据质量统计年报表参数
     * @return 分页报表
     */
    @PageOptimizationExclude
    Page<FinanceVo> reportPages(@Param("page") Page page, @Param("param") FinancePageParam param);
}