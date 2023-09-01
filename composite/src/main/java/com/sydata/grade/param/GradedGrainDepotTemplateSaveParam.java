/**
 * @filename:GradedGrainDepotTemplateSaveParam 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
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
import java.util.List;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板-保存参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotTemplateSaveParam对象", description = "等级粮库评定管理-等级粮库评定模板-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedGrainDepotTemplateSaveParam implements Serializable {

    private static final long serialVersionUID = 1684314836909L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;
    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;
    @ApiModelProperty(name = "fileId", value = "附件id")
    private String fileId;
    @ApiModelProperty(name = "fileName", value = "附件名称")
    private String fileName;
    @ApiModelProperty(name = "nf", value = "年份")
    private String nf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "qysj", value = "启用时间")
    private LocalDateTime qysj;
    @ApiModelProperty(name = "templateName", value = "模板名称")
    private String templateName;
    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(name = "templateNumber", value = "模板编号")
    private String templateNumber;
    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(name = "templateState", value = "状态")
    private String templateState;
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
    @ApiModelProperty(name = "gradedGrainDepotStandardSaveParamList", value = "指标列表")
    List<GradedGrainDepotStandardSaveParam> gradedGrainDepotStandardSaveParamList;
}
