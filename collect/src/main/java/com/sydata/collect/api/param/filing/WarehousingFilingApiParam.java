package com.sydata.collect.api.param.filing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sydata.collect.api.param.BaseApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储企业-保存更新参数)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingCompanySaveParam对象", description="仓储备案-仓储企业-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingApiParam extends BaseApiParam implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    private String id;

    @ApiModelProperty(name = "zhId" , value = "库软件租户ID",hidden = true)
    private String zhId;

    @ApiModelProperty(name = "kdId" , value = "库软件库点ID",hidden = true)
    private String kdId;

    @ApiModelProperty(name = "dwdm" , value = "仓储单位统一社会信用代码" , example = "仓储单位统一社会信用代码")
    @Size(min = 0, max = 18, message = "仓储单位统一社会信用代码 长度必须在 0 - 18 之间")
    private String dwdm;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称" , example = "粮油仓储单位名称")
    @Size(min = 0, max = 200, message = "粮油仓储单位名称 长度必须在 0 - 200 之间")
    private String dwmc;

    @ApiModelProperty(name = "dwlx" , value = "企业类型(字典表dwlx)" , example = "1")
    @Size(min = 0, max = 2, message = "企业类型(字典表dwlx) 长度必须在 0 - 2 之间")
    private String dwlx;

    @ApiModelProperty(name = "dwxz" , value = "企业性质 0:国有 1:外资 2:民营 3:其它" , example = "1")
    @Size(min = 0, max = 1, message = "企业性质 0:国有 1:外资 2:民营 3:其它 长度必须在 0 - 1 之间")
    private String dwxz;

    @NotBlank(message = "balx 不容许为空")
    @ApiModelProperty(name = "balx" , value = "备案类型 0:初始备案 1:变更备案 2:注销备案" , example = "1")
    @Size(min = 0, max = 1, message = "备案类型 0:初始备案 1:变更备案 2:注销备案 长度必须在 0 - 1 之间")
    private String balx;

    @NotBlank(message = "bazt 不容许为空")
    @ApiModelProperty(name = "bazt" , value = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销" , example = "1")
    @Size(min = 0, max = 1, message = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销 长度必须在 0 - 1 之间")
    private String bazt;

    @ApiModelProperty(name = "qyshr" , value = "审核人" , example = "审核人")
    @Size(min = 0, max = 255, message = "审核人 长度必须在 0 - 255 之间")
    private String qyshr;

    @ApiModelProperty(name = "shyj" , value = "审核意见" , example = "审核意见")
    @Size(min = 0, max = 1000, message = "审核意见 长度必须在 0 - 1000 之间")
    private String shyj;

    @ApiModelProperty(name = "shsj" , value = "审核时间" , example = "2023-06-27 17:43:25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shsj;

    @ApiModelProperty(name = "barq" , value = "备案日期" , example = "2023-06-27")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate barq;

    @ApiModelProperty(name = "baly" , value = "备案来源(0:库软件,1:粤商局)" , example = "1")
    @Size(min = 0, max = 1, message = "备案来源(0:库软件,1:粤商局) 长度必须在 0 - 1 之间")
    private String baly;

    @ApiModelProperty(name = "baclbm" , value = "备案处理部门(省平台对应的审核人部门)" , example = "备案处理部门(省平台对应的审核人部门)")
    @Size(min = 0, max = 200, message = "备案处理部门(省平台对应的审核人部门) 长度必须在 0 - 200 之间")
    private String baclbm;

    @NotBlank(message = "ccywlx 不容许为空")
    @ApiModelProperty(name = "ccywlx" , value = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他" , example = "1")
    @Size(min = 0, max = 44, message = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他 长度必须在 0 - 44 之间")
    private String ccywlx;

    @NotBlank(message = "ccpz 不容许为空")
    @ApiModelProperty(name = "ccpz" , value = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他" , example = "1")
    @Size(min = 0, max = 44, message = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他 长度必须在 0 - 44 之间")
    private String ccpz;

    @ApiModelProperty(name = "yyzz" , value = "营业执照" , example = "营业执照")
    @Size(min = 0, max = 300, message = "营业执照 长度必须在 0 - 300 之间")
    private String yyzz;

    @ApiModelProperty(name = "fzjg" , value = "营业执照发证机关" , example = "营业执照发证机关")
    @Size(min = 0, max = 300, message = "营业执照发证机关 长度必须在 0 - 300 之间")
    private String fzjg;

    @ApiModelProperty(name = "spxkz" , value = "食品许可证" , example = "食品许可证")
    @Size(min = 0, max = 300, message = "食品许可证 长度必须在 0 - 300 之间")
    private String spxkz;

    @ApiModelProperty(name = "fddbr" , value = "法人" , example = "法人")
    @Size(min = 0, max = 100, message = "法人 长度必须在 0 - 100 之间")
    private String fddbr;

    @ApiModelProperty(name = "zcsj" , value = "注册时间" , example = "2023-06-27 17:43:25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate zcsj;

    @ApiModelProperty(name = "zczb" , value = "注册资本(万元)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "注册资本(万元)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "注册资本(万元)不能小于零")
    private BigDecimal zczb;

    @ApiModelProperty(name = "zcdz" , value = "注册地址" , example = "注册地址")
    @Size(min = 0, max = 300, message = "注册地址 长度必须在 0 - 300 之间")
    private String zcdz;

    @ApiModelProperty(name = "lsptzc" , value = "粮食平台注册(0:未注册,1:已注册) 默认已注册" , example = "1")
    @Size(min = 0, max = 1, message = "粮食平台注册(0:未注册,1:已注册) 默认已注册 长度必须在 0 - 1 之间")
    private String lsptzc;

    @ApiModelProperty(name = "gsdh" , value = "公司电话" , example = "公司电话")
    @Size(min = 0, max = 30, message = "公司电话 长度必须在 0 - 30 之间")
    private String gsdh;

    @ApiModelProperty(name = "sjdwdm" , value = "上级单位代码" , example = "上级单位代码")
    @Size(min = 0, max = 18, message = "上级单位代码 长度必须在 0 - 18 之间")
    private String sjdwdm;

    @ApiModelProperty(name = "sjdwmc" , value = "上级单位名称" , example = "上级单位名称")
    @Size(min = 0, max = 200, message = "上级单位名称 长度必须在 0 - 200 之间")
    private String sjdwmc;

    @ApiModelProperty(name = "lsgx" , value = "隶属关系" , example = "隶属关系")
    @Size(min = 0, max = 300, message = "隶属关系 长度必须在 0 - 300 之间")
    private String lsgx;

    @ApiModelProperty(name = "jyfw" , value = "经营范围" , example = "经营范围")
    @Size(min = 0, max = 500, message = "经营范围 长度必须在 0 - 500 之间")
    private String jyfw;

    @NotBlank(message = "xzqhdm 不容许为空")
    @ApiModelProperty(name = "xzqhdm" , value = "经营区域" , example = "1")
    @Size(min = 0, max = 6, message = "经营区域 长度必须在 0 - 6 之间")
    private String xzqhdm;

    @ApiModelProperty(name = "jd" , value = "经营地址经度" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "经营地址经度整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "经营地址经度不能小于零")
    private BigDecimal jd;

    @ApiModelProperty(name = "wd" , value = "经营地址纬度" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "经营地址纬度整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "经营地址纬度不能小于零")
    private BigDecimal wd;

    @ApiModelProperty(name = "jydz" , value = "经营地址" , example = "经营地址")
    @Size(min = 0, max = 300, message = "经营地址 长度必须在 0 - 300 之间")
    private String jydz;

    @ApiModelProperty(name = "sgzg" , value = "收购资格" , example = "收购资格")
    @Size(min = 0, max = 300, message = "收购资格 长度必须在 0 - 300 之间")
    private String sgzg;

    @ApiModelProperty(name = "dczg" , value = "代储资格" , example = "代储资格")
    @Size(min = 0, max = 300, message = "代储资格 长度必须在 0 - 300 之间")
    private String dczg;

    @ApiModelProperty(name = "lyxse" , value = "粮油销售额(千万)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "粮油销售额(千万)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "粮油销售额(千万)不能小于零")
    private BigDecimal lyxse;

    @ApiModelProperty(name = "cpxse" , value = "产品销售额(千万)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "产品销售额(千万)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "产品销售额(千万)不能小于零")
    private BigDecimal cpxse;

    @ApiModelProperty(name = "zlxse" , value = "杂粮销售额(千万)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "杂粮销售额(千万)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "杂粮销售额(千万)不能小于零")
    private BigDecimal zlxse;

    @ApiModelProperty(name = "zpxse" , value = "制品销售额(千万)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "制品销售额(千万)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "制品销售额(千万)不能小于零")
    private BigDecimal zpxse;

    @ApiModelProperty(name = "createBy" , value = "创建者" , example = "String" ,hidden = true)
    @Size(min = 0, max = 50, message = "创建者 长度必须在 0 - 50 之间")
    private String createBy;

    @ApiModelProperty(name = "createTime" , value = "创建时间" , example = "LocalDateTime" ,hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy" , value = "更新者" , example = "String" ,hidden = true)
    @Size(min = 0, max = 50, message = "更新者 长度必须在 0 - 50 之间")
    private String updateBy;

    @ApiModelProperty(name = "updateTime" , value = "修改时间" , example = "LocalDateTime" ,hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "warehousingFilingStockSaveParams", value = "仓储库点")
    private List<WarehousingFilingStockSaveParam> warehousingFilingStockSaveParams;

    @ApiModelProperty(name = "warehousingFilingAttachmentSaveParams", value = "仓储附件")
    private List<WarehousingFilingAttachmentSaveParam> warehousingFilingAttachmentSaveParams;

    @ApiModelProperty(name = "warehousingFilingDeviceSaveParams", value = "仓储设备")
    private List<WarehousingFilingDeviceSaveParam> warehousingFilingDeviceSaveParams;

    @ApiModelProperty(name = "warehousingFilingPersonnelSaveParams", value = "仓储人员")
    private List<WarehousingFilingPersonnelSaveParam> warehousingFilingPersonnelSaveParams;

    @Override
    public String buildId() {
        return id;
    }
}
