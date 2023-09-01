package com.sydata.manage.param;

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
 * @describe 粮库管理-仓内视频图像分页参数
 * @date 2022-07-25 14:54
 */
@ApiModel(description = "粮库管理-仓内视频图像分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GranaryVideoImagePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(视频监控设备id-仓房代码-抓拍时间-预置位编号)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "视频监控设备ID")
    private String spjksbid;

    @ApiModelProperty(value = "开始抓拍时间")
    private LocalDateTime beginZpsj;

    @ApiModelProperty(value = "结束抓拍时间")
    private LocalDateTime endZpsj;

    @ApiModelProperty(value = "预置位编号")
    private String yzwbh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
