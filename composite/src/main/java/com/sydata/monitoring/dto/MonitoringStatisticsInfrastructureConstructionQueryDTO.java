package com.sydata.monitoring.dto;

import com.sydata.common.param.PageQueryParam;
import com.sydata.monitoring.entity.MonitoringStatisticsInfrastructureConstruction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.Valid;
/**
 * <p>
 * 流通检测-基础设施建设信息查询DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="流通检测-基础设施建设信息查询参数", description="流通检测-基础设施建设信息查询参数")
public class MonitoringStatisticsInfrastructureConstructionQueryDTO  extends PageQueryParam {
    @ApiModelProperty(value = "主表查询参数")
    @Valid
    private MonitoringStatisticsInfrastructureConstruction entity;

    public MonitoringStatisticsInfrastructureConstructionQueryDTO(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public MonitoringStatisticsInfrastructureConstructionQueryDTO() {
    }
}
