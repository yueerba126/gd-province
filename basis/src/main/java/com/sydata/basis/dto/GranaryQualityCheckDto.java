package com.sydata.basis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 廒间信息数据质量校验DTO
 * @date 2023/5/4 16:02
 */
@ApiModel(description = "廒间信息数据质量校验DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GranaryQualityCheckDto implements Serializable {

    @ApiModelProperty(value = "廒间ID")
    private String id;

    @ApiModelProperty("货位数")
    private int hws;
}
