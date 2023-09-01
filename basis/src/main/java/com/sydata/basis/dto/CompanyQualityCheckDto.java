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
 * @description 单位信息数据质量校验DTO
 * @date 2023/5/4 16:02
 */
@ApiModel(description = "单位信息数据质量校验DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyQualityCheckDto implements Serializable {

    @ApiModelProperty(value = "单位ID")
    private String id;

    @ApiModelProperty("库区数")
    private int kqs;

    @ApiModelProperty("仓房数")
    private int cfs;

    @ApiModelProperty("油罐数")
    private int ygs;

    @ApiModelProperty("上传库区数")
    private int sckqs;

    @ApiModelProperty("上传仓房数")
    private int sccfs;

    @ApiModelProperty("上传油罐数")
    private int scygs;
}
