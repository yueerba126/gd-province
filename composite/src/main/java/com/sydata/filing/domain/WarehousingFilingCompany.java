package com.sydata.filing.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.param.WarehousingFilingDeviceSaveParam;
import com.sydata.filing.param.WarehousingFilingPersonnelSaveParam;
import com.sydata.filing.param.WarehousingFilingStockSaveParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储企业实体类)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingCompany对象", description="仓储备案-仓储企业")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warehousing_filing_company")
public class WarehousingFilingCompany extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "zhId" , value = "库软件租户ID",hidden = true)
    private String zhId;

    @ApiModelProperty(name = "kdId" , value = "库软件库点ID",hidden = true)
    private String kdId;

    @ApiModelProperty(name = "dwdm" , value = "仓储单位统一社会信用代码")
    private String dwdm;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称")
    private String dwmc;

    @ApiModelProperty(name = "dwlx" , value = "企业类型(字典表dwlx)")
    private String dwlx;

    @ApiModelProperty(name = "dwxz" , value = "企业性质 0:国有 1:外资 2:民营 3:其它")
    private String dwxz;

    @ApiModelProperty(name = "balx" , value = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    private String balx;

    @ApiModelProperty(name = "bazt" , value = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销")
    private String bazt;

    @ApiModelProperty(name = "qyshr" , value = "审核人")
    private String qyshr;

    @ApiModelProperty(name = "shyj" , value = "审核意见")
    private String shyj;

    @ApiModelProperty(name = "shsj" , value = "审核时间")
    private LocalDateTime shsj;

    @ApiModelProperty(name = "barq" , value = "备案日期")
    private LocalDate barq;

    @ApiModelProperty(name = "baly" , value = "备案来源(0:库软件,1:粤商局)")
    private String baly;

    @ApiModelProperty(name = "baclbm" , value = "备案处理部门(省平台对应的审核人部门)")
    private String baclbm;

    @ApiModelProperty(name = "ccywlx" , value = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    private String ccywlx;

    @ApiModelProperty(name = "ccpz" , value = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    private String ccpz;

    @ApiModelProperty(name = "yyzz" , value = "营业执照")
    private String yyzz;

    @ApiModelProperty(name = "fzjg" , value = "营业执照发证机关")
    private String fzjg;

    @ApiModelProperty(name = "spxkz" , value = "食品许可证")
    private String spxkz;

    @ApiModelProperty(name = "fddbr" , value = "法人")
    private String fddbr;

    @ApiModelProperty(name = "zcsj" , value = "注册时间")
    private LocalDate zcsj;

    @ApiModelProperty(name = "zczb" , value = "注册资本(万元)")
    private BigDecimal zczb;

    @ApiModelProperty(name = "zcdz" , value = "注册地址")
    private String zcdz;

    @ApiModelProperty(name = "lsptzc" , value = "粮食平台注册(0:未注册,1:已注册) 默认已注册")
    private String lsptzc;

    @ApiModelProperty(name = "gsdh" , value = "公司电话")
    private String gsdh;

    @ApiModelProperty(name = "sjdwdm" , value = "上级单位代码")
    private String sjdwdm;

    @ApiModelProperty(name = "sjdwmc" , value = "上级单位名称")
    private String sjdwmc;

    @ApiModelProperty(name = "lsgx" , value = "隶属关系")
    private String lsgx;

    @ApiModelProperty(name = "jyfw" , value = "经营范围")
    private String jyfw;

    @ApiModelProperty(name = "xzqhdm" , value = "经营区域")
    private String xzqhdm;

    @ApiModelProperty(name = "jd" , value = "经营地址经度")
    private BigDecimal jd;

    @ApiModelProperty(name = "wd" , value = "经营地址纬度")
    private BigDecimal wd;

    @ApiModelProperty(name = "jydz" , value = "经营地址")
    private String jydz;

    @ApiModelProperty(name = "sgzg" , value = "收购资格")
    private String sgzg;

    @ApiModelProperty(name = "dczg" , value = "代储资格")
    private String dczg;

    @ApiModelProperty(name = "lyxse" , value = "粮油销售额(千万)")
    private BigDecimal lyxse;

    @ApiModelProperty(name = "cpxse" , value = "产品销售额(千万)")
    private BigDecimal cpxse;

    @ApiModelProperty(name = "zlxse" , value = "杂粮销售额(千万)")
    private BigDecimal zlxse;

    @ApiModelProperty(name = "zpxse" , value = "制品销售额(千万)")
    private BigDecimal zpxse;

    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;

    @TableField(exist = false)
    @ApiModelProperty(name = "warehousingFilingStockSaveParams", value = "仓储库点")
    private List<WarehousingFilingStockSaveParam> warehousingFilingStockSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "warehousingFilingAttachmentSaveParams", value = "仓储附件")
    private List<WarehousingFilingAttachmentSaveParam> warehousingFilingAttachmentSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "warehousingFilingDeviceSaveParams", value = "仓储设备")
    private List<WarehousingFilingDeviceSaveParam> warehousingFilingDeviceSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "warehousingFilingPersonnelSaveParams", value = "仓储人员")
    private List<WarehousingFilingPersonnelSaveParam> warehousingFilingPersonnelSaveParams;

}
