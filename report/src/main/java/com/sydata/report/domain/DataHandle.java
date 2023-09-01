package com.sydata.report.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;

/**
 * 数据上报-数据处理对象 report_data_handle
 *
 * @author lzq
 * @date 2022-10-21
 */
@ApiModel(description = "数据上报-数据处理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("report_data_handle")
public class DataHandle implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "api参数")
    private String param;

    @ApiModelProperty(value = "处理状态 1已处理 0未处理")
    private Boolean handleState;

    @ApiModelProperty(value = "数据来源：数据收集、审核通过、失败投递")
    private String source;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}