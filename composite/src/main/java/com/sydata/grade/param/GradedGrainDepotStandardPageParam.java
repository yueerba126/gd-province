/**
 * @filename:GradedGrainDepotStandardPageParam 2023年05月17日
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
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotStandardPageParam对象", description = "等级粮库评定管理-等级粮库评定标准-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedGrainDepotStandardPageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684315566154L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;
    @ApiModelProperty(name = "parentId", value = "本表上级ID")
    private String parentId;
    @ApiModelProperty(name = "jfzb", value = "加分指标")
    private String jfzb;
    @ApiModelProperty(name = "bczt", value = "保存状态(1:暂存，2:提交)")
    private String bczt;
    @ApiModelProperty(name = "beginUpdateTime", value = "开始更新时间")
    private LocalDateTime beginUpdateTime;
    @ApiModelProperty(name = "endUpdateTime", value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
