/**
 * @filename:GradedGrainDepotTemplate 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedGrainDepotStandardVo;
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
import java.util.List;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板实体类)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotTemplate对象", description = "等级粮库评定管理-等级粮库评定模板")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_grain_depot_template")
public class GradedGrainDepotTemplate implements Serializable {

    private static final long serialVersionUID = 1684314836909L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
    @TableField(exist = false)
    @ApiModelProperty(name = "xmDtoList", value = "指标列表")
    private List<XmDto> xmDtoList;
    @TableField(exist = false)
    @ApiModelProperty(name = "gradedGrainDepotStandardExcelVoList", value = "指标列表")
    private List<GradedGrainDepotStandardExcelVo> gradedGrainDepotStandardExcelVoList;
}
