package com.sydata.video.vo;

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
 * @description 区域设备数统计VO
 * @date 2022/12/26 11:27
 */
@ApiModel(description = "区域设备数统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegionEquipmentCountVo implements Serializable {

    @ApiModelProperty(value = "设备总数")
    private Integer count;

    @ApiModelProperty(value = "设备在线数")
    private Integer onLineCount;

    @ApiModelProperty(value = "设备离线数")
    private Integer offLineCount;

    public RegionEquipmentCountVo(Integer onLineCount, Integer offLineCount) {
        this.onLineCount = onLineCount;
        this.offLineCount = offLineCount;
        this.count = onLineCount + offLineCount;
    }
}
