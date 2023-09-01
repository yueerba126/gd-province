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
 * @description 库区信息数据质量校验DTO
 * @date 2023/5/4 16:02
 */
@ApiModel(description = "库区信息数据质量校验DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockHouseQualityCheckDto implements Serializable {

    @ApiModelProperty(value = "库区ID")
    private String id;

    @ApiModelProperty("仓房数")
    private int cfs;

    @ApiModelProperty("油罐数")
    private int ygs;

    @ApiModelProperty("上传仓房数")
    private int sccfs;

    @ApiModelProperty("上传油罐数")
    private int scygs;

    @ApiModelProperty("文件ID-库区平面图")
    private String fileId;
}
