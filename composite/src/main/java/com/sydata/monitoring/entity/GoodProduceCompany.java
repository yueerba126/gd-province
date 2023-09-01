package com.sydata.monitoring.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 流通检测-放心粮油企业
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_good_produce_company")
@ApiModel(value="GoodProduceCompany对象", description="流通检测-放心粮油企业")
public class GoodProduceCompany extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String status;

    @ApiModelProperty(value = "建设情况")
    private String buildDetail;

    @ApiModelProperty(value = "是否配送中心")
    private Integer ifDistributionCenter;

    @ApiModelProperty(value = "是否经营店")
    private Integer ifSaleShop;

    @ApiModelProperty(value = "经营情况")
    private String saleDetail;

    @ApiModelProperty(value = "粮油销售量（吨/年）")
    private BigDecimal grainOilAnnualSales;

    @ApiModelProperty(value = "盈利情况（千万）")
    private BigDecimal profit;

    @ApiModelProperty(value = "是否进学校")
    private Integer ifSaleSchool;

    @ApiModelProperty(value = "进学校数量")
    private BigDecimal schoolQty;

    @ApiModelProperty(value = "学校粮油销售量（吨/年）")
    private BigDecimal schoolAnnualSales;


}
