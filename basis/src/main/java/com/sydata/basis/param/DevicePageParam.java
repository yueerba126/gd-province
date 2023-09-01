package com.sydata.basis.param;

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
 * @describe 基础信息-设备信息分页参数
 * @date 2022-07-09 15:10
 */
@ApiModel(description = "基础信息-设备信息分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DevicePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id(库区代码-设备编号)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "设备编号")
    private String sbbh;

    @ApiModelProperty(value = "设备仪器名称")
    private String sbyqmc;

    @ApiModelProperty(value = "设备仪器代码")
    private String sbyqdm;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
