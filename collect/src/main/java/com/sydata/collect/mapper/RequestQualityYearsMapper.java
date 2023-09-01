package com.sydata.collect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.domain.RequestQualityYears;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.collect.vo.CollectQualityVo;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据收集-请求数据质量年报Mapper接口
 *
 * @author lzq
 * @date 2022-10-27
 */
@PageOptimizationExclude
public interface RequestQualityYearsMapper extends BaseMapper<RequestQualityYears> {


    /**
     * 累加统计
     *
     * @param years 请求数据质量年报
     * @return 统计结果
     */
    boolean statistics(RequestQualityYears years);

    /**
     * 请求数据质量年报-报表查询
     *
     * @param page  分页参数
     * @param param 数据质量统计年报表参数
     * @return 数据收集质量统计报表VO
     */
    Page<CollectQualityVo> report(@Param("page") Page page, @Param("param") QualityYearsPageParam param);


    /**
     * 根据库区ID和接口编号获取请求数据质量
     *
     * @param stockHouseIds 库区ID列表
     * @param apiCodes      接口编号列表
     * @return 请求数据质量
     */
    List<RequestQualityYears> listByStockHouseIds(@Param("stockHouseIds") List<String> stockHouseIds,
                                                  @Param("apiCodes") List<String> apiCodes);
}