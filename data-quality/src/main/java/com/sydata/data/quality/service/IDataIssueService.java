package com.sydata.data.quality.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.data.quality.domain.DataIssue;
import com.sydata.data.quality.param.DataIssueExportParam;
import com.sydata.data.quality.param.DataIssuePageParam;
import com.sydata.data.quality.vo.DataIssueVo;

import java.util.List;
import java.util.Set;

/**
 * 数据质量-数据问题Service接口
 *
 * @author system
 * @date 2023-04-18
 */
public interface IDataIssueService extends IService<DataIssue> {

    /**
     * 库区统计分页列表
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pageByStockHouse(DataIssuePageParam pageParam);

    /**
     * 库区接口统计分页列表
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pageByStockHouseApi(DataIssuePageParam pageParam);

    /**
     * 分页查询
     *
     * @param pageParam 数据问题分页参数
     * @return 数据问题vo
     */
    Page<DataIssueVo> pages(DataIssuePageParam pageParam);

    /**
     * 导出问题数据
     *
     * @param exportParam 导出参数
     */
    void export(DataIssueExportParam exportParam);

    /**
     * 忽略插入
     *
     * @param dataIssues 数据问题实体列表
     * @return 插入成功的ID集合
     */
    Set<String> ignoreBatchInsert(List<DataIssue> dataIssues);
}