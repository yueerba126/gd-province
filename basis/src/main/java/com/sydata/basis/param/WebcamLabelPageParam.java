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
 * @description 库区图视频监控设备点位标注分页参数
 * @date 2022/10/11 17:54
 */
@ApiModel(description = "库区图视频监控设备点位标注分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WebcamLabelPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(库区代码-视频监控设备id)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ApiModelProperty(value = "视频监控设备名称")
    private String spjksbmc;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
