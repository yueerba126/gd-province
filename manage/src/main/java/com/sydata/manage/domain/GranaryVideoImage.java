package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * 粮库管理-仓内视频图像对象 manage_warehouse_file
 *
 * @author lzq
 * @date 2022-07-25
 */
@ApiModel(description = "粮库管理-仓内视频图像")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("manage_granary_video_image")
public class GranaryVideoImage extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(视频监控设备id-仓房代码-抓拍时间-预置位编号)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("视频监控设备id")
    private String spjksbid;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty("货位代码")
    private String hwdm;

    @ApiModelProperty("抓拍时间")
    private LocalDateTime zpsj;

    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    @ApiModelProperty("图像文件后缀名")
    private String txwjhzm;

    @ApiModelProperty("预置位编号")
    private String yzwbh;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}