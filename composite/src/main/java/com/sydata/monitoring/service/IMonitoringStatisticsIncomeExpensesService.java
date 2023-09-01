package com.sydata.monitoring.service;

import com.sydata.monitoring.entity.MonitoringStatisticsIncomeExpenses;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsIncomeExpensesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 流通检测-粮油收支平衡数据 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface IMonitoringStatisticsIncomeExpensesService extends IService<MonitoringStatisticsIncomeExpenses> {

    /**
     * 分页查询
     *
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<MonitoringStatisticsIncomeExpensesVO> page(MonitoringStatisticsIncomeExpensesQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    MonitoringStatisticsIncomeExpensesVO detailById(String id);

    /**
     * 修改
     * @param editDTO 修改参数
     * @return 结果
     */
     Boolean edit(MonitoringStatisticsIncomeExpensesEditDTO editDTO);

    /**
     * 删除
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(MonitoringStatisticsIncomeExpensesDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(MonitoringStatisticsIncomeExpensesAddDTO addDTO);

    /**
     * 根据统计ID查询
     *
     * @param statisticsId 统计ID
     * @return 结果
     */
    List<MonitoringStatisticsIncomeExpensesVO> getByStatisticsId(String statisticsId);
}
