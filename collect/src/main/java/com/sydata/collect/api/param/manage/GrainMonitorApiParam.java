package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
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
 * @author ：xy
 * @description：温湿度检测接收参数Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "温湿度检测API操作参数")
public class GrainMonitorApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "温湿度检测单号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "温湿度检测单号必须以货位代码和检测日期开始", target = {"#hwdm", "#jcsj"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9a-zA-Z]{42}$", message = "温湿度检测单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("温湿度检测单号")
    private String wsdjcdh;

    @NotNull(message = "检测时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("检测时间")
    private LocalDateTime jcsj;

    @NotBlank(message = "货位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("货位代码")
    private String hwdm;

    @Digits(integer = 5, fraction = 1, message = "仓房外温长度不正确 BigDecimal(5,1)", groups = BasicCheck.class)
    @ApiModelProperty("仓房外温")
    private BigDecimal cfww;

    @Min(value = 0, message = "仓房外湿不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "仓房外湿长度不正确 BigDecimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty("仓房外湿")
    private BigDecimal cfws;

    @Digits(integer = 5, fraction = 1, message = "仓房内温长度不正确 BigDecimal(5,1)", groups = BasicCheck.class)
    @ApiModelProperty("仓房内温")
    private BigDecimal cfnw;

    @Min(value = 0, message = "仓房内湿不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "仓房内湿长度不正确 BigDecimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty("仓房内湿")
    private BigDecimal cfns;

    @NotNull(message = "粮食最高温必填", groups = BasicCheck.class)
    @Digits(integer = 5, fraction = 1, message = "粮食最高温长度不正确 BigDecimal(5,1)", groups = BasicCheck.class)
    @ApiModelProperty("粮食最高温")
    private BigDecimal lszgw;

    @NotNull(message = "粮食最低温必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#lspjw", message = "粮食最低温必须小于粮食平均温", groups = BasicCheck.class)
    @Digits(integer = 5, fraction = 1, message = "粮食最低温长度不正确 BigDecimal(5,1)", groups = BasicCheck.class)
    @ApiModelProperty("粮食最低温")
    private BigDecimal lszdw;

    @NotNull(message = "粮食平均温必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#lszgw", message = "粮食平均温必须小于粮食最高温", groups = BasicCheck.class)
    @Digits(integer = 5, fraction = 1, message = "粮食平均温长度不正确 BigDecimal(5,1)", groups = BasicCheck.class)
    @ApiModelProperty("粮食平均温")
    private BigDecimal lspjw;

    @NotBlank(message = "粮食温度值集合不能为空", groups = BasicCheck.class)
    @StringSize(max = 8000, message = "粮食温度值集合最大长度为8000", groups = BasicCheck.class)
    @Pattern(regexp = "((-?\\d+)(\\.\\d+)?,\\d+,\\d+,\\d+?\\|)*(-?\\d+)(\\.\\d+)?,\\d+,\\d+,\\d+?"
            , message = "粮食温度值集合不符合规范", groups = BasicCheck.class)
    @ApiModelProperty("粮食温度值集合")
    private String lswdzjh;

    @StringSize(max = 8000, message = "粮食湿度值集合最大长度为8000", groups = BasicCheck.class)
    @Pattern(regexp = "((-?\\d+)(\\.\\d+)?,\\d+,\\d+,\\d+?\\|)*(-?\\d+)(\\.\\d+)?,\\d+,\\d+,\\d+?"
            , message = "粮食湿度值集合不符合规范", groups = BasicCheck.class)
    @ApiModelProperty("粮食湿度值集合")
    private String lssdzjh;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @Override
    public String buildId() {
        return wsdjcdh;
    }

    @Override
    public String buildCompanyId() {
        return hwdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return hwdm.substring(0, 21);
    }

}
