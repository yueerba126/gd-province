package com.sydata.safe.asess.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
 * 粮食安全考核-抽查计划明细对象 safe_assess_plan_dtl
 *
 * @author system
 * @date 2023-02-14
 */
@ApiModel(description = "粮食安全考核-抽查计划明细")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_check_plan_dtl")
public class CheckPlanDtl implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "计划ID")
    private String planId;

    @NotBlank(message = "单位考核ID不能为空")
    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

    @NotBlank(message = "行政区域ID不能为空")
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @NotBlank(message = "国ID不能为空")
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @NotBlank(message = "省ID不能为空")
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @NotBlank(message = "市ID不能为空")
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "地址(逗号分隔)")
    private String address;

    @ApiModelProperty(value = "抽查状态: 抽查中、已抽查")
    private String state;
}