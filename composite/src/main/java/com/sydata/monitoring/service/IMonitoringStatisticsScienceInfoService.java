package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsScienceInfo;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsScienceInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-粮油科技信息 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsScienceInfoService extends IService<MonitoringStatisticsScienceInfo> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsScienceInfoVO> page(MonitoringStatisticsScienceInfoQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsScienceInfoVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsScienceInfoEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsScienceInfoDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsScienceInfoAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsScienceInfoVO> getByStatisticsId(String statisticsId);
}
