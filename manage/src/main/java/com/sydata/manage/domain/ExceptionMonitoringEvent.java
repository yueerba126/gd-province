package com.sydata.manage.domain;

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
 * 粮库管理-库区视频监控异常事件告警对象 manage_monitor_abnormal_file
 *
 * @author lzq
 * @date 2022-07-25
 */
@ApiModel(description = "粮库管理-视频监控异常事件告警基本信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("manage_exception_monitoring_event")
public class ExceptionMonitoringEvent extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id:库区代码+告警时间")
    private String id;

    @ApiModelProperty(value = "告警时间")
    private LocalDateTime gjsj;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "库区地址")
    private String kqdz;

    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ApiModelProperty(value = "安装位置类型")
    private String azwzlx;

    @ApiModelProperty(value = "监视区域说明")
    private String jsqysm;

    @ApiModelProperty(value = "异常告警说明")
    private String ycgjsm;

    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    @ApiModelProperty(value = "视频文件后缀名")
    private String spwjhzm;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}