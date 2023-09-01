package com.sydata.reserve.layout.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 储备布局地理信息-库区信息备案分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "储备布局地理信息-库区信息备案分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockHouseRecordPageParam extends PageQueryParam implements Serializable {


    @ApiModelProperty(value = "企业名称")
    private String dwmc;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "库区id")
    private String id;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty("状态")
    private String status;


}
