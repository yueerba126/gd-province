package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsStorehouse;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStorehouseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-库点信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsStorehouseService extends IService<MonitoringStatisticsStorehouse> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsStorehouseVO> page(MonitoringStatisticsStorehouseQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsStorehouseVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsStorehouseEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsStorehouseDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsStorehouseAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsStorehouseVO> getByStatisticsId(String statisticsId);
}
