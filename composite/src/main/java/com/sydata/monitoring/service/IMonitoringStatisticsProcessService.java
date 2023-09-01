package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsProcess;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-粮油加工信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsProcessService extends IService<MonitoringStatisticsProcess> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsProcessVO> page(MonitoringStatisticsProcessQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsProcessVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsProcessEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsProcessDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsProcessAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsProcessVO> getByStatisticsId(String statisticsId);
}
