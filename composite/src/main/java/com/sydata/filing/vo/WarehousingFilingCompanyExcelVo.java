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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储企业ExcelVo)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingCompanyExcelVo对象", description="仓储备案-仓储企业ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingCompanyExcelVo implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @Excel(name = "仓储单位统一社会信用代码")
    @ApiModelProperty(name = "dwdm" ,value = "仓储单位统一社会信用代码")
    private String dwdm;

    @Excel(name = "粮油仓储单位名称")
    @ApiModelProperty(name = "dwmc" ,value = "粮油仓储单位名称")
    private String dwmc;

    @Excel(name = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    @ApiModelProperty(name = "balx" ,value = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    private String balx;

    @Excel(name = "审核人")
    @ApiModelProperty(name = "qyshr" ,value = "审核人")
    private String qyshr;

    @Excel(name = "审核意见")
    @ApiModelProperty(name = "shyj" ,value = "审核意见")
    private String shyj;

    @Excel(name = "审核时间")
    @ApiModelProperty(name = "shsj" ,value = "审核时间")
    private LocalDateTime shsj;

    @Excel(name = "备案日期")
    @ApiModelProperty(name = "barq" ,value = "备案日期")
    private LocalDate barq;

    @Excel(name = "备案处理部门(省平台对应的审核人部门)")
    @ApiModelProperty(name = "baclbm" ,value = "备案处理部门(省平台对应的审核人部门)")
    private String baclbm;

    @Excel(name = "营业执照")
    @ApiModelProperty(name = "yyzz" ,value = "营业执照")
    private String yyzz;

    @Excel(name = "营业执照发证机关")
    @ApiModelProperty(name = "fzjg" ,value = "营业执照发证机关")
    private String fzjg;

    @Excel(name = "食品许可证")
    @ApiModelProperty(name = "spxkz" ,value = "食品许可证")
    private String spxkz;

    @Excel(name = "法人")
    @ApiModelProperty(name = "fddbr" ,value = "法人")
    private String fddbr;

    @Excel(name = "注册时间")
    @ApiModelProperty(name = "zcsj" ,value = "注册时间")
    private LocalDateTime zcsj;

    @Excel(name = "注册资本(万元)")
    @ApiModelProperty(name = "zczb" ,value = "注册资本(万元)")
    private BigDecimal zczb;

    @Excel(name = "注册地址")
    @ApiModelProperty(name = "zcdz" ,value = "注册地址")
    private String zcdz;

    @Excel(name = "公司电话")
    @ApiModelProperty(name = "gsdh" ,value = "公司电话")
    private String gsdh;

    @Excel(name = "上级单位代码")
    @ApiModelProperty(name = "sjdwdm" ,value = "上级单位代码")
    private String sjdwdm;

    @Excel(name = "上级单位名称")
    @ApiModelProperty(name = "sjdwmc" ,value = "上级单位名称")
    private String sjdwmc;

    @Excel(name = "隶属关系")
    @ApiModelProperty(name = "lsgx" ,value = "隶属关系")
    private String lsgx;

    @Excel(name = "经营范围")
    @ApiModelProperty(name = "jyfw" ,value = "经营范围")
    private String jyfw;

    @Excel(name = "经营地址经度")
    @ApiModelProperty(name = "jd" ,value = "经营地址经度")
    private BigDecimal jd;

    @Excel(name = "经营地址纬度")
    @ApiModelProperty(name = "wd" ,value = "经营地址纬度")
    private BigDecimal wd;

    @Excel(name = "经营地址")
    @ApiModelProperty(name = "jydz" ,value = "经营地址")
    private String jydz;

    @Excel(name = "收购资格")
    @ApiModelProperty(name = "sgzg" ,value = "收购资格")
    private String sgzg;

    @Excel(name = "代储资格")
    @ApiModelProperty(name = "dczg" ,value = "代储资格")
    private String dczg;

    @Excel(name = "粮油销售额(千万)")
    @ApiModelProperty(name = "lyxse" ,value = "粮油销售额(千万)")
    private BigDecimal lyxse;

    @Excel(name = "产品销售额(千万)")
    @ApiModelProperty(name = "cpxse" ,value = "产品销售额(千万)")
    private BigDecimal cpxse;

    @Excel(name = "杂粮销售额(千万)")
    @ApiModelProperty(name = "zlxse" ,value = "杂粮销售额(千万)")
    private BigDecimal zlxse;

    @Excel(name = "制品销售额(千万)")
    @ApiModelProperty(name = "zpxse" ,value = "制品销售额(千万)")
    private BigDecimal zpxse;

    @Excel(name = "库软件库点ID")
    @ApiModelProperty(name = "warehouseId" ,value = "库软件库点ID")
    private String warehouseId;

    @Excel(name = "企业类型(字典表dwlx)")
    @ApiModelProperty(name = "dwlxName" , value = "企业类型(字典表dwlx)")
    private String dwlxName;

    @Excel(name = "企业性质 0:国有 1:外资 2:民营 3:其它")
    @ApiModelProperty(name = "dwxzName" , value = "企业性质 0:国有 1:外资 2:民营 3:其它")
    private String dwxzName;

    @Excel(name = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    @ApiModelProperty(name = "balxName" , value = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    private String balxName;

    @Excel(name = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销")
    @ApiModelProperty(name = "baztName" , value = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销")
    private String baztName;

    @Excel(name = "备案来源(0:库软件,1:粤商局)")
    @ApiModelProperty(name = "balyName" , value = "备案来源(0:库软件,1:粤商局)")
    private String balyName;

    @Excel(name = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    @ApiModelProperty(name = "ccywlxName" , value = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    private String ccywlxName;

    @Excel(name = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    @ApiModelProperty(name = "ccpzName" , value = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    private String ccpzName;

    @Excel(name = "粮食平台注册(0:未注册,1:已注册) 默认已注册")
    @ApiModelProperty(name = "lsptzcName" , value = "粮食平台注册(0:未注册,1:已注册) 默认已注册")
    private String lsptzcName;

    @Excel(name = "经营区域")
    @ApiModelProperty(name = "xzqhdmName" , value = "经营区域")
    private String xzqhdmName;

}
