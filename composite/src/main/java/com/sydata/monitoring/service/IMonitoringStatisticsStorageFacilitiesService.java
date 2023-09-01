package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsStorageFacilities;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStorageFacilitiesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 流通检测-仓储设施 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsStorageFacilitiesService extends IService<MonitoringStatisticsStorageFacilities> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsStorageFacilitiesVO> page(MonitoringStatisticsStorageFacilitiesQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsStorageFacilitiesVO detailById(String id);

    /**
     * 修改
     *
     * @param editDTO 修改参数
     * @return 结果
     */
    Boolean edit(MonitoringStatisticsStorageFacilitiesEditDTO editDTO);

    /**
     * 删除
     *
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsStorageFacilitiesDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsStorageFacilitiesAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    MonitoringStatisticsStorageFacilitiesVO getByStatisticsId(String statisticsId);
}
