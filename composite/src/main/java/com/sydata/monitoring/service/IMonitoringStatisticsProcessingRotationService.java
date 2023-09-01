package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsProcessingRotation;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-加工轮换 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsProcessingRotationService extends IService<MonitoringStatisticsProcessingRotation> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsProcessingRotationVO> page(MonitoringStatisticsProcessingRotationQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsProcessingRotationVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsProcessingRotationEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsProcessingRotationDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsProcessingRotationAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsProcessingRotationVO> getByStatisticsId(String statisticsId);
}
