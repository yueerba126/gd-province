package com.sydata.collect.api.param.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 轮换计划信息API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划信息API操作参数")
public class RotationPlanApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "轮换计划单号必填", groups = BasicCheck.class)
    @StringSize(max = 25, message = "轮换计划单号最大长度25", groups = BasicCheck.class)
    @ApiModelProperty(value = "轮换计划单号 由下达计划单位统一社会信用代码+ 计划年度（yyyy）+3 位顺序号组成")
    private String lhjhdh;

    @StringSize(max = 64, message = "计划文号最大长度64", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @StringSize(max = 256, message = "计划名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @NotNull(message = "开始执行日期必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#jzzxrq", message = "开始执行日期不能晚于截止执行日期", groups = BasicCheck.class)
    @ApiModelProperty(value = "开始执行日期")
    private LocalDate kszxrq;

    @NotNull(message = "截止执行日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "截止执行日期")
    private LocalDate jzzxrq;

    @NotBlank(message = "计划下达单位单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "计划下达单位单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达单位")
    private String jhxddw;

    @StringSize(max = 256, message = "计划下达单位名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达单位名称")
    private String jhxddwmc;

    @TargetIsBefore(target = "#kszxrq", message = "计划下达时间不能晚于开始执行日期", groups = BasicCheck.class)
    @NotNull(message = "计划下达时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "计划下达单位单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#jhxddw")
    @ApiModelProperty(value = "计划下达单位单位代码名称", hidden = true)
    private String jhxddwName;

    @Override
    public String buildId() {
        return lhjhdh;
    }
}
