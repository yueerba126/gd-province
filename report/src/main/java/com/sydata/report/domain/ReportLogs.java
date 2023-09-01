package com.sydata.report.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 数据上报-上报日志对象 report_logs
 *
 * @author lzq
 * @date 2022-11-02
 */
@ApiModel(description = "数据上报-上报日志")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("report_logs")
public class ReportLogs implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "上报开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "上报结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "上报总耗时(毫秒)")
    private Long timeConsuming;

    @ApiModelProperty(value = "上报参数")
    private String param;

    @ApiModelProperty(value = "响应参数")
    private String result;

    @ApiModelProperty(value = "上报状态 1成功 0失败")
    private Boolean state;

    @ApiModelProperty(value = "调用批次ID")
    private String invokerBatchId;

    @ApiModelProperty(value = "处理状态 1已处理 0未处理")
    private Boolean handleState;

    @ApiModelProperty(value = "重报投递：1已投递 0未投递")
    private Boolean delivery;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}