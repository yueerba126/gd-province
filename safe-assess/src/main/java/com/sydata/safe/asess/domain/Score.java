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

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 粮食安全考核-考核评分对象 safe_assess_score
 *
 * @author system
 * @date 2023-04-03
 */
@ApiModel(description = "粮食安全考核-考核评分")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_score")
public class Score implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "考核模板年份")
    private String templateYear;

    @ApiModelProperty(value = "考核评审ID")
    private String reviewId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "考核区域ID")
    private String assessRegionId;

    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

    @ApiModelProperty(value = "状态：待发布、待提交、待评分、已评分")
    private String state;

    @ApiModelProperty(value = "牵头指标总数")
    private Integer leadTotalCount;

    @ApiModelProperty(value = "牵头指标考核数")
    private Integer leadAssessCount;

    @ApiModelProperty(value = "牵头指标总分")
    private BigDecimal leadTotalScore;

    @ApiModelProperty(value = "牵头指标考核总分")
    private BigDecimal leadAssessScore;

    @ApiModelProperty(value = "配合指标总数")
    private Integer cooperateTotalCount;

    @ApiModelProperty(value = "配合指标考核数")
    private Integer cooperateAssessCount;

    @ApiModelProperty(value = "配合指标总分")
    private BigDecimal cooperateTotalScore;

    @ApiModelProperty(value = "配合指标考核总分")
    private BigDecimal cooperateAssessScore;

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