package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangcy
 * @since 2023/5/6 16:40
 */
@Data
@ApiModel("区域查询参数")
public class AreaQueryParam {
    private String provinceId;
    private String cityId;
    private String areaId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
