package com.sydata.grade.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: gd-province-platform
 * @description: 小项
 * @author: lzq
 * @create: 2023-05-24 18:48
 */
@ApiModel(value="XxDto对象", description="等级粮库评定管理-小项")
@Data
@ToString
public class XxDto implements Serializable {
    @ApiModelProperty(name = "id" , value = "id")
    private String id;
    @ApiModelProperty(name = "xxfz" , value = "项目分值")
    private BigDecimal xxfz;
    @ApiModelProperty(name = "jfff" , value = "计分方法")
    private String jfff;
    @ApiModelProperty(name = "jfff" , value = "得分")
    private BigDecimal df;
    @ApiModelProperty(name = "jfzb" , value = "加分指标")
    private String jfzb;
    @ApiModelProperty(name = "citySdpf", value = "市实地评分")
    private BigDecimal citySddf;
    @ApiModelProperty(name = "cityKfyy", value = "市实地扣分原因")
    private String cityKfyy;
    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分")
    private BigDecimal provinceSddf;
    @ApiModelProperty(name = "provinceKfyy", value = "省实扣分原因")
    private String provinceKfyy;
    @ApiModelProperty(name = "sjzpf", value = "市级自评分")
    private BigDecimal sjzpf;
    @ApiModelProperty(name = "zpf", value = "自评分")
    private BigDecimal zpf;
    @ApiModelProperty(name = "jfzbName", value = "加分指标名称")
    private String jfzbName;
    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;
    @ApiModelProperty(name = "projectSort", value = "字段顺序")
    private int projectSort;
    @ApiModelProperty(name = "scoringMethodId", value = "模板树叶子节点ID")
    private String scoringMethodId;
}
