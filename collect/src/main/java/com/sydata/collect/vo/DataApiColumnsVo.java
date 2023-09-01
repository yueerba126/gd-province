package com.sydata.collect.vo;

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
 * @description 数据收集-数据API接口字段信息
 * @date 2023/4/27 16:20
 */
@ApiModel(description = "数据收集-数据API接口字段信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataApiColumnsVo implements Serializable {

    @ApiModelProperty(value = "字段名")
    private String fieldName;

    @ApiModelProperty(value = "字段描述")
    private String fieldMsg;
}
