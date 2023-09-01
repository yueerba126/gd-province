package com.sydata.monitoring.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流通检测-库点信息
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("monitoring_statistics_storehouse")
@ApiModel(value="MonitoringStatisticsStorehouse对象", description="流通检测-库点信息")
public class MonitoringStatisticsStorehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "粮油流通统计ID")
    private String statisticId;

    @ApiModelProperty(value = "库点编号")
    private String code;

    @ApiModelProperty(value = "库点名称")
    private String name;

    @ApiModelProperty(value = "占地面积")
    private BigDecimal area;

    @ApiModelProperty(value = "建设仓容（吨）")
    private BigDecimal buildWarehouseCap;

    @ApiModelProperty(value = "实际仓容（吨）")
    private BigDecimal actualWarehouseCap;

    @ApiModelProperty(value = "建设罐容（吨）")
    private BigDecimal buildTankCap;

    @ApiModelProperty(value = "实际罐容（吨）")
    private BigDecimal actualTankCap;

    @ApiModelProperty(value = "添加人员")
    private String createBy;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

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
