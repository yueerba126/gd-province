package com.sydata.collect.quality.dto;

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
 * @description 数据质量-数据问题明细DTO
 * @date 2023/4/23 16:26
 */
@ApiModel(description = "数据质量-数据问题明细DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataIssueDtlDto implements Serializable {

    @ApiModelProperty(value = "数据问题ID")
    private String issueDataId;

    @ApiModelProperty(value = "字段名")
    private String fieldName;

    @ApiModelProperty(value = "问题说明")
    private String issueRemark;
}
