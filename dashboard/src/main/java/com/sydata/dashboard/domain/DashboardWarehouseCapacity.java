package com.sydata.dashboard.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 报管理-仓库容量报对象 dashboard_warehouse_capacity
 *
 * @author ruoyi
 * @date 2023-05-06
 */
@Data
@ApiModel("报管理-仓库容量报对象")
@TableName("dashboard_warehouse_capacity")
public class DashboardWarehouseCapacity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓房类型
     */
    @ApiModelProperty(name = "仓房类型")
    private String cflxdm;
    /**
     * 仓房类型
     */
    @ApiModelProperty(name = "仓房代码")
    private String cfdm;

    /**
     * 设计仓容
     */
    @ApiModelProperty(name = "设计仓容")
    private BigDecimal sjcr;

    /**
     * 交付使用日期
     */
    @ApiModelProperty(name = "交付使用日期")
    private LocalDate jfsyrq;

    /**
     * 计价数量
     */
    @ApiModelProperty(name = "计价数量")
    private BigDecimal jjsl;

    /**
     * 行政区域ID
     */
    @ApiModelProperty(name = "行政区域ID")
    private String regionId;

    /**
     * 国ID
     */
    @ApiModelProperty(name = "国ID")
    private String countryId;

    /**
     * 省ID
     */
    @ApiModelProperty(name = "省ID")
    private String provinceId;

    /**
     * 市ID
     */
    @ApiModelProperty(name = "市ID")
    private String cityId;

    /**
     * 区ID
     */
    @ApiModelProperty(name = "区ID")
    private String areaId;

    /**
     * 企业ID
     */
    @ApiModelProperty(name = "企业ID")
    private String enterpriseId;

    /**
     * 库区ID
     */
    @ApiModelProperty(name = "库区ID")
    private String stockHouseId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("cflxdm", getCflxdm())
                .append("sjcr", getSjcr())
                .append("jjsl", getJjsl())
                .append("regionId", getRegionId())
                .append("countryId", getCountryId())
                .append("provinceId", getProvinceId())
                .append("cityId", getCityId())
                .append("areaId", getAreaId())
                .append("enterpriseId", getEnterpriseId())
                .append("stockHouseId", getStockHouseId())
                .toString();
    }
}
