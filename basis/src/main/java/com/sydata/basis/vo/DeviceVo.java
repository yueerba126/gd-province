package com.sydata.basis.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.basis.domain.Device;
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
 * @describe 基础信息-设备信息VO
 * @date 2022-07-09 15:14
 */
@ApiModel(description = "基础信息-设备信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceVo extends Device implements Serializable {

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindDict(sourceField = "#sbzt", sourceFieldCombination = "sbzt")
    @ApiModelProperty(value = "设备状态名称")
    private String sbztName;
}
