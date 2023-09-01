package com.sydata.chart.vo;


import com.sydata.chart.pojo.CustomInformation;
import com.sydata.common.basis.annotation.*;
import com.sydata.chart.pojo.Carrier;
import com.sydata.chart.pojo.Register;
import com.sydata.chart.pojo.out.PlanNo;
import com.sydata.chart.pojo.out.WeighingOut;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 出库检斤质检结算单卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "出库检斤质检结算单卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class OutStockCheckChartVo implements Serializable {

    @ApiModelProperty(value = "编号")
    private String ckywdh;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)名称")
    private String cfmc;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindCargo(dataValue = "#bgy")
    @ApiModelProperty(value = "保管员")
    private String bgy;

    @ApiModelProperty(value = "客户")
    private CustomInformation kh;

    @ApiModelProperty(value = "承运")
    private Carrier cy;

    @ApiModelProperty(value = "计划通知")
    private PlanNo jhtz;

    @ApiModelProperty(value = "登记")
    private Register dj;

    @ApiModelProperty(value = "检斤")
    private WeighingOut jj;

    @ApiModelProperty(value = "备注")
    private String bz;
}
