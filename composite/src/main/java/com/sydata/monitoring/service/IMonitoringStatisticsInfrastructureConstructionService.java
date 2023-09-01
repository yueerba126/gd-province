package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsInfrastructureConstruction;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsInfrastructureConstructionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-基础设施建设信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsInfrastructureConstructionService extends IService<MonitoringStatisticsInfrastructureConstruction> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsInfrastructureConstructionVO> page(MonitoringStatisticsInfrastructureConstructionQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsInfrastructureConstructionVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsInfrastructureConstructionEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsInfrastructureConstructionDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsInfrastructureConstructionAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsInfrastructureConstructionVO> getByStatisticsId(String statisticsId);
}
