/**
 * @filename:GradedGrainDepotTemplatePageParam 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotTemplatePageParam对象", description = "等级粮库评定管理-等级粮库评定模板-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedGrainDepotTemplatePageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684314836909L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "templateName", value = "模板名称")
    private String templateName;
    @ApiModelProperty(name = "templateNumber", value = "模板编号")
    private String templateNumber;
    @ApiModelProperty(name = "templateState", value = "状态")
    private String templateState;
    @ApiModelProperty(name = "beginQysj", value = "开始启用时间")
    private LocalDateTime beginQysj;
    @ApiModelProperty(name = "endQysj", value = "结束启用时间")
    private LocalDateTime endQysj;
    @ApiModelProperty(name = "beginUpdateTime", value = "开始更新时间")
    private LocalDateTime beginUpdateTime;
    @ApiModelProperty(name = "endUpdateTime", value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
