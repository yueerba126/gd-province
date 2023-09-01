package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;
import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author ：xy
 * @description：虫害信息接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "虫害信息API操作参数")
public class PestInformationApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "害虫监测单号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "害虫监测单号必须以货位代码和检测日期开始", target = {"#hwdm", "#jcsj"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9a-zA-Z]{42}$", message = "害虫监测单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("害虫监测单号")
    private String hcjcdh;

    @NotNull(message = "检测时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("检测时间")
    private LocalDateTime jcsj;

    @NotBlank(message = "货位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("货位代码")
    private String hwdm;

    @NotBlank(message = "检查虫害方法不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$", message = "检查虫害方法格式错误", groups = BasicCheck.class)
    @ApiModelProperty("检查虫害方法")
    private String jchcff;

    @Pattern(regexp = "^$|^.{0,128}$", message = "发生部位格式错误", groups = BasicCheck.class)
    @ApiModelProperty("发生部位")
    private String fsbw;

    @NotBlank(message = "虫害种类不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,128}$", message = "害虫种类格式错误", groups = BasicCheck.class)
    @ApiModelProperty("害虫种类")
    private String hczl;

    @NotBlank(message = "虫口密度值集合不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,5000}$", message = "虫口密度值集合格式错误", groups = BasicCheck.class)
    @ApiModelProperty("虫口密度值集合")
    private String ckmdzjh;

    @Pattern(regexp = "^$|^531$|^532$|^533$|^534$", message = "虫粮等级判定格式错误", groups = BasicCheck.class)
    @NotBlank(message = "虫粮等级判定不能为空", groups = BasicCheck.class)
    @ApiModelProperty("虫粮等级判定")
    private String cldjpd;

    @Pattern(regexp = "^$|^.{0,128}$", message = "害虫抗药性分析格式错误", groups = BasicCheck.class)
    @ApiModelProperty("害虫抗药性分析")
    private String hckyxfx;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @JsonIgnore
    @NotBlank(message = "害虫种类代码请参考LST1709-2018粮食信息分类与编码_储粮病虫害分类与代码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#hczl", sourceFieldCombination = "hczl", valueBindStrategy = SEPARATED, bindSeparated = HASH)
    @ApiModelProperty(value = "害虫种类名称", hidden = true)
    private String hczlName;

    @Override
    public String buildId() {
        return hcjcdh;
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
