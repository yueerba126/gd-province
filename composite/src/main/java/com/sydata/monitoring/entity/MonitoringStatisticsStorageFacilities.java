package com.sydata.monitoring.entity;

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
 * 流通检测-仓储设施
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("monitoring_statistics_storage_facilities")
@ApiModel(value="MonitoringStatisticsStorageFacilities对象", description="流通检测-仓储设施")
public class MonitoringStatisticsStorageFacilities implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "粮油流通统计ID")
    private String statisticId;

    @ApiModelProperty(value = "有专用检测化验室")
    private Integer hasAssaysRoom;

    @ApiModelProperty(value = "检化验相关情况：具有粮油常规检验能力")
    private Integer hasAssaysRuleCheckAbility;

    @ApiModelProperty(value = "具有粮油品质检验能力")
    private Integer hasGrainOilQualityCheckAbility;

    @ApiModelProperty(value = "周边有无危险源")
    private Integer hasDangerSource;

    @ApiModelProperty(value = "周边有无污染源")
    private Integer hasPolluteSource;

    @ApiModelProperty(value = "经营场地周边环境情况：经营具有粮油品质检验能力")
    private Integer hasGroundQualityCheckAbility;

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
