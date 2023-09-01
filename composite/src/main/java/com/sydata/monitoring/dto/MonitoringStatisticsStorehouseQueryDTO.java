package com.sydata.monitoring.dto;

import com.sydata.common.param.PageQueryParam;
import com.sydata.monitoring.entity.MonitoringStatisticsStorehouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.Valid;
/**
 * <p>
 * 流通检测-库点信息查询DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="流通检测-库点信息查询参数", description="流通检测-库点信息查询参数")
public class MonitoringStatisticsStorehouseQueryDTO extends PageQueryParam {
    @ApiModelProperty(value = "主表查询参数")
    @Valid
    private MonitoringStatisticsStorehouse entity;

    public MonitoringStatisticsStorehouseQueryDTO(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public MonitoringStatisticsStorehouseQueryDTO() {
    }
}
