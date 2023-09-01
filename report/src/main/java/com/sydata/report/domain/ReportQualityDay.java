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
import java.time.LocalDate;

/**
 * 数据上报-上报数据质量日报 report_quality_day
 *
 * @author lzq
 * @date 2022-10-27
 */
@ApiModel(description = "数据上报-上报数据质量日报")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("report_quality_day")
public class ReportQualityDay implements Serializable {

    @ApiModelProperty(value = "主键ID(日期-企业ID-库区ID-接口编号)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "日期")
    private LocalDate days;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "上报总数")
    private Integer total;

    @ApiModelProperty(value = "上报成功数")
    private Integer success;

    @ApiModelProperty(value = "上报失败数")
    private Integer fail;

    @ApiModelProperty(value = "处理状态")
    private Boolean handleState;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;
}