/**
 * @filename:GradedGrainDepotStandard 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import com.sydata.common.domain.BaseFiledEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准实体类)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotStandard对象", description = "等级粮库评定管理-等级粮库评定标准")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_grain_depot_standard")
public class GradedGrainDepotStandard implements Serializable {

    private static final long serialVersionUID = 1684315566154L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;
    @ApiModelProperty(name = "assessmentContent", value = "评定内容")
    private String assessmentContent;
    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;
    @ApiModelProperty(name = "bczt", value = "保存状态(1:暂存，2:提交)")
    private String bczt;
    @ApiModelProperty(name = "jfzb", value = "加分指标")
    private String jfzb;
    @ApiModelProperty(name = "parentId", value = "本表上级ID")
    private String parentId;
    @ApiModelProperty(name = "projectSort", value = "字段顺序")
    private int projectSort;
    @ApiModelProperty(name = "projectName", value = "项目名称")
    private String projectName;
    @ApiModelProperty(name = "projectScore", value = "项目分值")
    private BigDecimal projectScore;
    @ApiModelProperty(name = "score", value = "得分")
    private BigDecimal score;
    @ApiModelProperty(name = "scoringMethod", value = "计分方法")
    private String scoringMethod;
    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;
    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
}
