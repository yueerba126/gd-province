package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsCheckPoint;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsCheckPointVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-粮食市场监测点信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsCheckPointService extends IService<MonitoringStatisticsCheckPoint> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsCheckPointVO> page(MonitoringStatisticsCheckPointQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsCheckPointVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsCheckPointEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsCheckPointDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsCheckPointAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsCheckPointVO> getByStatisticsId(String statisticsId);
}
