package com.sydata.monitoring.service;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatistics;
import com.sydata.monitoring.vo.MonitoringStatisticsCheckPointStatisticsVo;
import com.sydata.monitoring.vo.MonitoringStatisticsDetailEditDto;
import com.sydata.monitoring.vo.MonitoringStatisticsDetailVO;
import com.sydata.monitoring.vo.MonitoringStatisticsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-粮油流通统计 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsService extends IService<MonitoringStatistics> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsVO> page(MonitoringStatisticsQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsDetailVO detailById(String id);

    /**
     * 修改
     *
     * @param editDTO 修改参数
     * @return 结果
     */
    Boolean edit(MonitoringStatisticsEditDTO editDTO);

    /**
     * 删除
     *
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsAddDTO addDTO);

    /**
     * 粮食价格汇总分析
     *
     * @param queryDTO 查询参数
     * @return 汇总分析结果
     */
    List<MonitoringStatisticsCheckPointStatisticsVo> priceStatistics(MonitoringStatisticsPriceCheckStatisticsDTO queryDTO);

    /**
     * 明细批量保存
     *
     * @param editDTO 保存参数
     * @return 结果
     */
    Boolean detailBatchEdit(MonitoringStatisticsDetailEditDto editDTO);
}
