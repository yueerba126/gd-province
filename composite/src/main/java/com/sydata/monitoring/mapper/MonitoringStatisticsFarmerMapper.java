package com.sydata.monitoring.mapper;

import com.sydata.monitoring.entity.MonitoringStatisticsFarmer;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerQueryDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 流通检测-居民农户信息 Mapper 接口
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
public interface MonitoringStatisticsFarmerMapper extends BaseMapper<MonitoringStatisticsFarmer> {

    /**
     * 分页查询
     *
     * @param page       分页参数
     * @param queryDTO   查询参数
     * @return 分页结果
     */
    Page<Long> idPage(Page<Long> page,  @Param("queryParam") MonitoringStatisticsFarmerQueryDTO queryDTO);

}
