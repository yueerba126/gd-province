package com.sydata.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 温湿度监测信息分页查询参数
 *
 * @author lzq
 * @date 2022/8/18 17:49
 */
@Data
public class GrainMonitorPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id-温湿度检测单号")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "库区代码")
    private String stockHouseId;

    @ApiModelProperty(value = "监测开始时间")
    private LocalDate beginJcsj;

    @ApiModelProperty(value = "监测结束时间")
    private LocalDate endJcsj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}


