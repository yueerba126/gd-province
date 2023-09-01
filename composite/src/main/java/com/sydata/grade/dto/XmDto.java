package com.sydata.grade.dto;

import com.sydata.grade.dto.DxDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 项目
 * @author: lzq
 * @create: 2023-05-24 18:47
 */
@ApiModel(value="XmDto对象", description="等级粮库评定管理-项目")
@Data
@ToString
public class XmDto implements Serializable {
    @ApiModelProperty(name = "Xmmc" , value = "项目名称")
    private String Xmmc;
    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;
    @ApiModelProperty(name = "dxInfos" , value = "大项集合")
    private List<DxDto> dxInfos;
}
