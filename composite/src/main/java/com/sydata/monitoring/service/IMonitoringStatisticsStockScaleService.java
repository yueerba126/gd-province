package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsStockScale;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStockScaleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-库存规模 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsStockScaleService extends IService<MonitoringStatisticsStockScale> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsStockScaleVO> page(MonitoringStatisticsStockScaleQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsStockScaleVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsStockScaleEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsStockScaleDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsStockScaleAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsStockScaleVO> getByStatisticsId(String statisticsId);
}
