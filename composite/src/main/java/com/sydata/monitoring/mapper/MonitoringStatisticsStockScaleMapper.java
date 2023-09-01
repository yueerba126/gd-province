package com.sydata.monitoring.mapper;

import com.sydata.monitoring.entity.MonitoringStatisticsStockScale;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleQueryDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 流通检测-库存规模 Mapper 接口
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface MonitoringStatisticsStockScaleMapper extends BaseMapper<MonitoringStatisticsStockScale> {

    /**
     * 分页查询
     *
     * @param page       分页参数
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<Long> idPage(Page<Long> page,  @Param("queryParam") MonitoringStatisticsStockScaleQueryDTO queryDTO);

}