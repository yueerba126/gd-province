package com.sydata.safe.asess.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 粮食安全考核-考核模板对象 safe_assess_template
 *
 * @author system
 * @date 2023-02-13
 */
@ApiModel(description = "粮食安全考核-考核模板")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_template")
public class Template implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @NotBlank(message = "模板编号必填")
    @ApiModelProperty(value = "模板编号")
    private String number;

    @NotBlank(message = "模板名称必填")
    @ApiModelProperty(value = "模板名称")
    private String name;

    @NotBlank(message = "年份必填")
    @ApiModelProperty(value = "年份")
    private String year;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "行政区域IDS")
    private String regionIds;

    @ApiModelProperty(value = "考核分占比")
    private BigDecimal assessScoreProportion;

    @ApiModelProperty(value = "抽查分占比")
    private BigDecimal checkScoreProportion;

    @NotBlank(message = "状态必填")
    @ApiModelProperty(value = "状态：保存、待分配、待发布、已发布、已完成")
    private String state;

    @ApiModelProperty(value = "分配日期")
    private LocalDate allotDate;

    @ApiModelProperty(value = "自动分配部门IDS")
    private String autoAllotDeptIds;

    @ApiModelProperty(value = "发布日期")
    private LocalDate pushDate;

    @ApiModelProperty(value = "最晚提交日期")
    private LocalDate lastSubmitDate;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "部门总数量")
    private Integer deptTotalCount;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "部门已完成分配数量")
    private Integer deptAllotCount;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "部门已完成考核数量")
    private Integer deptAssessCount;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "地市总数量")
    private Integer regionTotalCount;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "地市已提交数量")
    private Integer regionSubmitCount;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "地市已考核数量")
    private Integer regionAssessCount;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "组织ID")
    private String organizeId;
}