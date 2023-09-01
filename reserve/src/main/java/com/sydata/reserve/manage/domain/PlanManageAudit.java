package com.sydata.reserve.manage.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 轮换计划审核对象 plan_manage_audit
 *
 * @author fuql
 * @date 2023-05-31
 */
@ApiModel(description = "轮换计划审核")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_manage_audit")
public class PlanManageAudit extends BaseFiledEntity implements Serializable {

    @Excel(name = "轮换计划审核Id")
    @ApiModelProperty(value = "轮换计划审核Id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Excel(name = "单据编号")
    @ApiModelProperty(value = "单据编号")
    private String mainCode;

    @Excel(name = "计划下达单位")
    @ApiModelProperty(value = "计划下达单位")
    private String planTenantName;

    @Excel(name = "计划下达单位统一社会信用代码")
    @ApiModelProperty(value = "计划下达单位统一社会信用代码")
    private String jhxddw;

    @Excel(name = "计划年度")
    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @Excel(name = "轮换计划单号")
    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @Excel(name = "计划名称")
    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @Excel(name = "管理方式")
    @ApiModelProperty(value = "管理方式")
    private String managementStyle;

    @Excel(name = "管理方式名称")
    @ApiModelProperty(value = "管理方式名称")
    private String managementStyleName;

    @Excel(name = "计划文号")
    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @Excel(name = "轮入数量（t）")
    @ApiModelProperty(value = "轮入数量（t）")
    private BigDecimal planInQty;

    @Excel(name = "轮出数量（t）")
    @ApiModelProperty(value = "轮出数量（t）")
    private BigDecimal planOutQty;

    @Excel(name = "粮食性质代码")
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @Excel(name = "计划下达日期")
    @ApiModelProperty(value = "计划下达日期")
    private LocalDate jhxdsj;

    @Excel(name = "计划执行日期开始")
    @ApiModelProperty(value = "计划执行日期开始")
    private LocalDate kszxrq;

    @Excel(name = "计划执行日期截止")
    @ApiModelProperty(value = "计划执行日期截止")
    private LocalDate jzzxrq;

    @Excel(name = "单据状态")
    @ApiModelProperty(value = "单据状态")
    private String billStatus;

    @Excel(name = "单据状态名称")
    @ApiModelProperty(value = "单据状态名称")
    private String billStatusName;

    @Excel(name = "轮换状态")
    @ApiModelProperty(value = "轮换状态")
    private String planStatus;

    @Excel(name = "轮换状态名称")
    @ApiModelProperty(value = "轮换状态名称")
    private String planStatusName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Excel(name = "库软件轮换计划Id")
    @ApiModelProperty(value = "库软件轮换计划Id")
    private String mainId;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
