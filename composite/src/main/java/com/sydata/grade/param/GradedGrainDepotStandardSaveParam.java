/**
 * @filename:GradedGrainDepotStandardSaveParam 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.DataBindDict;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准-保存参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotStandardSaveParam对象", description = "等级粮库评定管理-等级粮库评定标准-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedGrainDepotStandardSaveParam implements Serializable {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
}
