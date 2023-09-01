package com.sydata.collect.api.param.filing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储库点-保存更新参数)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingStockSaveParam对象", description="仓储备案-仓储库点-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingStockSaveParam implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "仓储企业ID")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "dwdm" , value = "仓储单位统一社会信用代码" , example = "仓储单位统一社会信用代码")
    @Size(min = 0, max = 18, message = "仓储单位统一社会信用代码 长度必须在 0 - 18 之间")
    private String dwdm;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称" , example = "粮油仓储单位名称")
    @Size(min = 0, max = 200, message = "粮油仓储单位名称 长度必须在 0 - 200 之间")
    private String dwmc;

    @NotBlank(message = "kddm 不容许为空")
    @ApiModelProperty(name = "kddm" , value = "库点编号" , example = "库点编号")
    @Size(min = 0, max = 21, message = "库点编号 长度必须在 0 - 21 之间")
    private String kddm;

    @NotBlank(message = "kdmc 不容许为空")
    @ApiModelProperty(name = "kdmc" , value = "库点名称" , example = "库点名称")
    @Size(min = 0, max = 256, message = "库点名称 长度必须在 0 - 256 之间")
    private String kdmc;

    @NotNull(message = "zdmj 不容许为空")
    @ApiModelProperty(name = "zdmj" , value = "占地面积(m)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "占地面积(m)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "占地面积(m)不能小于零")
    private BigDecimal zdmj;

    @NotNull(message = "whcr 不容许为空")
    @ApiModelProperty(name = "whcr" , value = "完好仓容(吨)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "完好仓容(吨)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "完好仓容(吨)不能小于零")
    private BigDecimal whcr;

    @NotNull(message = "yggr 不容许为空")
    @ApiModelProperty(name = "yggr" , value = "油罐罐容(吨)" , example = "1")
    @Digits(integer = 20, fraction = 6, message = "油罐罐容(吨)整数位上限为2位，小数位上限为6位")
    @DecimalMin(value = "0", message = "油罐罐容(吨)不能小于零")
    private BigDecimal yggr;

    @NotBlank(message = "xzqhdm 不容许为空")
    @ApiModelProperty(name = "xzqhdm" , value = "所属区域" , example = "1")
    @Size(min = 0, max = 6, message = "所属区域 长度必须在 0 - 6 之间")
    private String xzqhdm;

    @ApiModelProperty(name = "zyjhys" , value = "有专用检验化验室 0:有 1:无" , example = "1")
    @Size(min = 0, max = 1, message = "有专用检验化验室 0:有 1:无 长度必须在 0 - 1 之间")
    private String zyjhys;

    @ApiModelProperty(name = "lycgjynl" , value = "具有粮油常规检验能力 0:有 1:无" , example = "1")
    @Size(min = 0, max = 1, message = "具有粮油常规检验能力 0:有 1:无 长度必须在 0 - 1 之间")
    private String lycgjynl;

    @ApiModelProperty(name = "lypzjynl" , value = "具有粮油品质检验能力 0:有 1:无" , example = "1")
    @Size(min = 0, max = 1, message = "具有粮油品质检验能力 0:有 1:无 长度必须在 0 - 1 之间")
    private String lypzjynl;

    @ApiModelProperty(name = "zbywwxy" , value = "周边有无危险源 0:有 1:无" , example = "1")
    @Size(min = 0, max = 1, message = "周边有无危险源 0:有 1:无 长度必须在 0 - 1 之间")
    private String zbywwxy;

    @ApiModelProperty(name = "zbywwry" , value = "周边有无污染源 0:有 1:无" , example = "1")
    @Size(min = 0, max = 1, message = "周边有无污染源 0:有 1:无 长度必须在 0 - 1 之间")
    private String zbywwry;

    @ApiModelProperty(name = "czbz" , value = "操作标志" , example = "String" ,hidden = true)
    @Size(min = 0, max = 1, message = "操作标志 长度必须在 0 - 1 之间")
    private String czbz;

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

}
