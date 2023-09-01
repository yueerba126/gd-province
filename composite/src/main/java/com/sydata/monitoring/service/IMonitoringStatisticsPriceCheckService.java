package com.sydata.monitoring.service;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsPriceCheck;
import com.sydata.monitoring.vo.MonitoringStatisticsPriceCheckVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 流通检测-价格检测信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsPriceCheckService extends IService<MonitoringStatisticsPriceCheck> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsPriceCheckVO> page(MonitoringStatisticsPriceCheckQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsPriceCheckVO detailById(String id);

    /**
     * 修改
     *
     * @param editDTO 修改参数
     * @return 结果
     */
    Boolean edit(MonitoringStatisticsPriceCheckEditDTO editDTO);

    /**
     * 删除
     *
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsPriceCheckDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsPriceCheckAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsPriceCheckVO> getByStatisticsId(String statisticsId);

    /**
     * 根据统计id列表查询
     *
     * @param statisticsIds 统计id列表查询
     * @return 结果
     */
    List<MonitoringStatisticsPriceCheckVO> selectByStatisticsIds(Set<String> statisticsIds);
}
