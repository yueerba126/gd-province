package com.sydata.data.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.data.quality.domain.DataIssue;
import com.sydata.data.quality.param.DataIssuePageParam;
import com.sydata.data.quality.vo.DataIssueVo;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import org.apache.ibatis.annotations.Param;

/**
 * 数据质量-数据问题Mapper接口
 *
 * @author system
 * @date 2023-04-18
 */
@PageOptimizationExclude
public interface DataIssueMapper extends BaseMapper<DataIssue> {

    /**
     * 库区统计分页查询
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pageByStockHouse(@Param("page") Page page, @Param("param") DataIssuePageParam pageParam);

    /**
     * 库区接口统计分页列表
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pageByStockHouseApi(@Param("page") Page page, @Param("param") DataIssuePageParam pageParam);

    /**
     * 分页查询
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pages(@Param("page") Page page, @Param("param") DataIssuePageParam pageParam);

    /**
     * 忽略插入
     *
     * @param dataIssue 数据问题实体
     * @return 插入是否成功
     */
    boolean ignoreInsert(DataIssue dataIssue);

    /**
     * 清库表数据
     */
    void clear();
}