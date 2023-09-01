package com.sydata.chart.vo;

import com.sydata.chart.pojo.in.CheckIn;
import com.sydata.chart.pojo.in.CustomInformationIn;
import com.sydata.chart.pojo.in.WeighingIn;
import com.sydata.common.basis.annotation.*;
import com.sydata.chart.pojo.Carrier;
import com.sydata.chart.pojo.Register;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 入库检斤质检结算单卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "入库检斤质检结算单卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class InStockCheckChartVo implements Serializable {

    @ApiModelProperty(value = "编号")
    private String rkywdh;

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

    @ApiModelProperty(value = "客户及结算信息")
    private CustomInformationIn khjjs;

    @ApiModelProperty(value = "承运")
    private Carrier cy;

    @ApiModelProperty(value = "登记")
    private Register dj;

    @ApiModelProperty(value = "检斤")
    private WeighingIn jj;

    @ApiModelProperty(value = "检验")
    private CheckIn jy;

    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @ApiModelProperty(value = "检验人姓名")
    private String jyrxm;

    @ApiModelProperty(value = "检验时间 格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime jysj;

    @ApiModelProperty(value = "备注")
    private String bz;

}
