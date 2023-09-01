package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;
import lombok.experimental.Accessors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储库点ExcelVo)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingStockExcelVo对象", description="仓储备案-仓储库点ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingStockExcelVo implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @Excel(name = "仓储单位统一社会信用代码")
    @ApiModelProperty(name = "dwdm" ,value = "仓储单位统一社会信用代码")
    private String dwdm;

    @Excel(name = "粮油仓储单位名称")
    @ApiModelProperty(name = "dwmc" ,value = "粮油仓储单位名称")
    private String dwmc;

    @Excel(name = "库点编号")
    @ApiModelProperty(name = "kddm" ,value = "库点编号")
    private String kddm;

    @Excel(name = "库点名称")
    @ApiModelProperty(name = "kdmc" ,value = "库点名称")
    private String kdmc;

    @Excel(name = "占地面积(m)")
    @ApiModelProperty(name = "zdmj" ,value = "占地面积(m)")
    private BigDecimal zdmj;

    @Excel(name = "完好仓容(吨)")
    @ApiModelProperty(name = "whcr" ,value = "完好仓容(吨)")
    private BigDecimal whcr;

    @Excel(name = "油罐罐容(吨)")
    @ApiModelProperty(name = "yggr" ,value = "油罐罐容(吨)")
    private BigDecimal yggr;

    @Excel(name = "库软件库点ID")
    @ApiModelProperty(name = "warehouseId" ,value = "库软件库点ID")
    private String warehouseId;

    @Excel(name = "所属区域")
    @ApiModelProperty(name = "xzqhdmName" , value = "所属区域")
    private String xzqhdmName;

    @Excel(name = "有专用检验化验室 0:有 1:无")
    @ApiModelProperty(name = "zyjhysName" , value = "有专用检验化验室 0:有 1:无")
    private String zyjhysName;

    @Excel(name = "具有粮油常规检验能力 0:有 1:无")
    @ApiModelProperty(name = "lycgjynlName" , value = "具有粮油常规检验能力 0:有 1:无")
    private String lycgjynlName;

    @Excel(name = "具有粮油品质检验能力 0:有 1:无")
    @ApiModelProperty(name = "lypzjynlName" , value = "具有粮油品质检验能力 0:有 1:无")
    private String lypzjynlName;

    @Excel(name = "周边有无危险源 0:有 1:无")
    @ApiModelProperty(name = "zbywwxyName" , value = "周边有无危险源 0:有 1:无")
    private String zbywwxyName;

    @Excel(name = "周边有无污染源 0:有 1:无")
    @ApiModelProperty(name = "zbywwryName" , value = "周边有无污染源 0:有 1:无")
    private String zbywwryName;

}
