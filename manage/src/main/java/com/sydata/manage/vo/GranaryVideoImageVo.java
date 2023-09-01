package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.*;
import com.sydata.manage.domain.GranaryVideoImage;
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
 * @describe 粮库管理-仓内视频图像VO
 * @date 2022-07-25 14:56
 */
@ApiModel(description = "粮库管理-仓内视频图像VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GranaryVideoImageVo extends GranaryVideoImage implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓库名称")
    private String cfmc;

    @DataBindCargo
    @ApiModelProperty("货位名称")
    private String hwmc;

    @DataBindDevice(sourceField = "#spjksbid")
    @ApiModelProperty("设备名称")
    private String spjksbName;
}
