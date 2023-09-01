package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
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
 * 库区图视频监控设备点位标注对象 basis_webcam_label
 *
 * @author lzq
 * @date 2022-10-11
 */
@ApiModel(description = "库区图视频监控设备点位标注")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_webcam_label")
public class WebcamLabel extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(库区代码-视频监控设备id)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "视频监控设备名称")
    private String spjksbmc;

    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ApiModelProperty(value = "视频监控设备相对位置")
    private String spjksbxdwz;

    @ApiModelProperty(value = "视频监控设备位置样式")
    private String spjksbwzys;

    @ApiModelProperty(value = "视频监控类型")
    private String spjklx;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;

    @ApiModelProperty(value = "海康监控点唯一标识")
    private String hkCameraIndexCode;
}