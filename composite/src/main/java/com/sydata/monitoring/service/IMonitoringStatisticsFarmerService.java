package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsFarmer;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsFarmerVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-居民农户信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsFarmerService extends IService<MonitoringStatisticsFarmer> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsFarmerVO> page(MonitoringStatisticsFarmerQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsFarmerVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsFarmerEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsFarmerDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsFarmerAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsFarmerVO> getByStatisticsId(String statisticsId);
}
