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
 * 粮食安全考核-单位考核对象 safe_assess_org_assess
 *
 * @author system
 * @date 2023-02-16
 */
@ApiModel(description = "粮食安全考核-单位考核")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_org_assess")
public class OrgAssess implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板编号")
    private String templateNumber;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "发布日期")
    private LocalDate pushDate;

    @ApiModelProperty(value = "最晚提交日期")
    private LocalDate lastSubmitDate;

    @ApiModelProperty(value = "状态：待分配、待自评、已自评、待考核、已考核、已退回")
    private String state;

    @ApiModelProperty(value = "分配指标数")
    private Integer distributionCount;

    @ApiModelProperty(value = "自评指标数")
    private Integer orgCount;

    @ApiModelProperty(value = "待自评指标数")
    private Integer orgAwaitCount;

    @ApiModelProperty(value = "待抽查指标数")
    private Integer checkAwaitCount;

    @ApiModelProperty(value = "考核评审考核数")
    private Integer deptAssessCount;

    @ApiModelProperty(value = "考核评审考核提交数")
    private Integer deptAssessSubmitCount;

    @ApiModelProperty(value = "考核评审牵头部门指标数")
    private Integer deptLeadCount;

    @ApiModelProperty(value = "考核评审牵头部门待考核指标数")
    private Integer deptLeadAwaitCount;

    @ApiModelProperty(value = "考核评审配合部门指标数")
    private Integer deptCooperateCount;

    @ApiModelProperty(value = "考核评审配合部门待考核指标数")
    private Integer deptCooperateAwaitCount;

    @ApiModelProperty(value = "部门考核数")
    private Integer assessDeptCount;

    @ApiModelProperty(value = "部门考核提交数")
    private Integer assessDeptSubmitCount;

    @ApiModelProperty(value = "部门考核牵头部门自评指标数")
    private Integer leadCount;

    @ApiModelProperty(value = "部门考核牵头部门待自评指标数")
    private Integer leadAwaitCount;

    @ApiModelProperty(value = "部门考核配合部门自评指标数")
    private Integer cooperateCount;

    @ApiModelProperty(value = "部门考核配合部门待自评指标数")
    private Integer cooperateAwaitCount;

    @ApiModelProperty(value = "提交考核是否超时：1超时 0不超时")
    private Boolean submitOvertime;

    @ApiModelProperty(value = "提交考核时间")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "模板总分")
    private BigDecimal templateScore;

    @ApiModelProperty(value = "自评总分")
    private BigDecimal orgScore;

    @ApiModelProperty(value = "考核总分")
    private BigDecimal assessScore;

    @ApiModelProperty(value = "抽查总分")
    private BigDecimal checkScore;

    @ApiModelProperty(value = "最终得分")
    private BigDecimal score;

    @ApiModelProperty(value = "文件IDS")
    private String fileIds;

    @ApiModelProperty(value = "文件名称S")
    private String fileNames;

    @ApiModelProperty(value = "抽查文件IDS")
    private String checkFileIds;

    @ApiModelProperty(value = "抽查文件名称S")
    private String checkFileNames;

    @ApiModelProperty(value = "配合部门填报截止日期")
    private LocalDate cooperateDeptEndDate;

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