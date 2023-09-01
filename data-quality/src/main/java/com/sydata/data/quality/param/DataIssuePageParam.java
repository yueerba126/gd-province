package com.sydata.data.quality.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量-数据问题分页参数
 * @date 2023/4/25 9:43
 */
@ApiModel(description = "数据质量-数据问题分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataIssuePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "查询值")
    private String fieldValue;

    @ApiModelProperty(value = "库区ID列表")
    private List<String> stockHouseIds;
}
