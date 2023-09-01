package com.sydata.collect.domain;

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

import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 数据收集-请求日志对象 collect_request_log
 *
 * @author lzq
 * @date 2022-10-21
 */
@ApiModel(description = "数据收集-请求日志")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("collect_request_log")
public class RequestLog implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @TableField(insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @TableField(insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "请求开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "请求结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "请求总耗时(毫秒)")
    private Long timeConsuming;

    @ApiModelProperty(value = "请求参数")
    private String param;

    @ApiModelProperty(value = "响应参数")
    private String result;

    @ApiModelProperty(value = "请求状态 1成功 0失败")
    private Boolean state;

    @ApiModelProperty(value = "处理状态")
    private Boolean handleState;

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

    @ApiModelProperty(value = "组织ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "创建者")
    private String createBy;
}