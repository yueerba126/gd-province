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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 粮食安全考核-部门考核对象 safe_assess_org_assess_dept
 *
 * @author system
 * @date 2023-02-18
 */
@ApiModel(description = "粮食安全考核-部门考核")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_org_assess_dept")
public class OrgAssessDept implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板编号")
    private String templateNumber;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "最晚提交日期")
    private LocalDate lastSubmitDate;

    @ApiModelProperty(value = "配合部门填报截止日期")
    private LocalDate cooperateDeptEndDate;

    @ApiModelProperty(value = "提交自评是否超时：1超时 0不超时")
    private Boolean submitOvertime;

    @ApiModelProperty(value = "提交自评时间")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "状态：待分配、已分配、待自评、已自评、已退回")
    private String state;

    @ApiModelProperty(value = "牵头指标数")
    private Integer leadCount;

    @ApiModelProperty(value = "待自评牵头指标数")
    private Integer leadAwaitCount;

    @ApiModelProperty(value = "牵头指标总分")
    private BigDecimal leadScore;

    @ApiModelProperty(value = "已自评牵头指标总分")
    private BigDecimal leadAlreadyScore;

    @ApiModelProperty(value = "配合指标数")
    private Integer cooperateCount;

    @ApiModelProperty(value = "待自评配合指标数")
    private Integer cooperateAwaitCount;

    @ApiModelProperty(value = "配合指标总分")
    private BigDecimal cooperateScore;

    @ApiModelProperty(value = "已自评配合指标总分")
    private BigDecimal cooperateAlreadyScore;

    @ApiModelProperty(value = "自评部门数")
    private Integer deptCount;

    @ApiModelProperty(value = "提交自评部门数")
    private Integer deptSubmitCount;

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

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;
}