package com.sydata.grade.dto;

import com.sydata.grade.dto.XxDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 大项
 * @author: lzq
 * @create: 2023-05-24 18:47
 */
@ApiModel(value="DxDto对象", description="等级粮库评定管理-大项")
@Data
@ToString
public class DxDto implements Serializable {
    @ApiModelProperty(name = "dxtm" , value = "大项题目")
    private String dxtm;
    @ApiModelProperty(name = "pdnr" , value = "评定内容")
    private String pdnr;
    @ApiModelProperty(name = "dxfz" , value = "大项分值")
    private BigDecimal dxfz;
    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;
    @ApiModelProperty(name = "xxDtos" , value = "小项集合")
    private List<XxDto> xxDtos;
}
