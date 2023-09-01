package com.sydata.monitoring.mapper;

import com.sydata.monitoring.entity.MonitoringStatisticsStorageFacilities;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesQueryDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 流通检测-仓储设施 Mapper 接口
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface MonitoringStatisticsStorageFacilitiesMapper extends BaseMapper<MonitoringStatisticsStorageFacilities> {

    /**
     * 分页查询
     *
     * @param page       分页参数
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<Long> idPage(Page<Long> page,  @Param("queryParam") MonitoringStatisticsStorageFacilitiesQueryDTO queryDTO);

}
