package com.sydata.monitoring.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * <p>
 * 流通检测-好粮油企业
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_good_company")
@ApiModel(value="GoodCompany对象", description="流通检测-好粮油企业")
public class GoodCompany extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String status;

    @ApiModelProperty(value = "备案来源")
    private String recordSource;

    @ApiModelProperty(value = "备案日期")
    private String recordDate;

    @ApiModelProperty(value = "企业类型，字典：dwlx")
    private String companyType;

    @ApiModelProperty(value = "企业性质")
    private String companyNature;

    @ApiModelProperty(value = "仓储业务类型")
    private String stockStoreType;

    @ApiModelProperty("统一社会信用代码")
    private String creditCode ;

    @ApiModelProperty("仓储品种")
    private String storageVarieties ;

    @ApiModelProperty("粮食平台注册")
    private String platformRegister ;

    @ApiModelProperty(value = "营业执照发证机关")
    private String businessLicenseIssuingAuthority;

    @ApiModelProperty(value = "注册时间")
    private LocalDate registryDate;

    @ApiModelProperty(value = "注册资本（万元）")
    private Integer registryCapital;

    @ApiModelProperty(value = "公司电话")
    private String companyPhone;

    @ApiModelProperty(value = "注册地址")
    private String address;

    @ApiModelProperty(value = "上级粮食部门")
    private String superiorFoodSector;

    @ApiModelProperty(value = "隶属关系")
    private String affiliation;

    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    @ApiModelProperty(value = "经营地址经纬度")
    private String businessAddressLngLat;

    @ApiModelProperty(value = "收购资格")
    private String acquisitionEligibility;

    @ApiModelProperty(value = "代储资格")
    private String proxyEligibility;

    @ApiModelProperty(value = "经营地址")
    private String businessAddress;

    @ApiModelProperty(value = "法人")
    private String corporation;

    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "食品许可证")
    private String foodLicenses;

    @ApiModelProperty(value = "粮油销售额（千万）")
    private BigDecimal grainOilSales;

    @ApiModelProperty(value = "产品销售额（千万）")
    private BigDecimal productSales;

    @ApiModelProperty(value = "杂粮销售额（千万）")
    private BigDecimal grainsSales;

    @ApiModelProperty(value = "制品销售额（千万）")
    private BigDecimal salesProducts;

    @ApiModelProperty(value = "学校粮油销售量（吨/年）")
    private BigDecimal schoolAnnualSales;
}
