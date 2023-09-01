package com.sydata.collect.api.param.manage;

import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.group.BasicCheck;
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

/**
 * 违规预警信息
 *
 * @author xy
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "违规预警信息API操作参数")
public class ViolationWarningApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "预警信息代码不能为空", groups = BasicCheck.class)
    @StringSize(min = 32, max = 32, message = "预警信息代码长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "预警信息代码")
    private String yjxxdm;

    @TargetIsBefore(target = "#hxczsj", message = "预警发布时间不能晚于处置时间", groups = BasicCheck.class)
    @NotNull(message = "预警发布时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty(value = "预警发布时间")
    private LocalDateTime fbsj;

    @NotBlank(message = "违规主体类型不能为空", groups = BasicCheck.class)
    @StringSize(max = 2, message = "违规主体类型最大长度为2", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02$", message = "违规主体类型不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "违规主体类型（01:单位，02:个人）")
    private String wgztlx;

    @NotBlank(message = "违规单位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "违规单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "违规单位代码（统一社会信用代码）")
    private String qydm;

    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 128, message = "违规行为人最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "违规行为人")
    private String wgxwr;

    @NotBlank(message = "涉及库点不能为空", groups = BasicCheck.class)
    @StringSize(max = 128, message = "涉及库点最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "涉及库点")
    private String sjkd;

    @NotBlank(message = "违规类型不能为空", groups = BasicCheck.class)
    @StringSize(max = 2, message = "违规类型最大长度为2", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02|03|04|05|06|07|08$", message = "违规类型不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "违规类型")
    private String wglx;

    @NotBlank(message = "违规详情不能为空", groups = BasicCheck.class)
    @StringSize(max = 512, message = "违规详情最大长度为512", groups = BasicCheck.class)
    @ApiModelProperty(value = "违规详情")
    private String wgqk;

    @NotBlank(message = "当前处置状态不能为空", groups = BasicCheck.class)
    @StringSize(max = 2, message = "当前处置状态最大长度为2", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02$", message = "当前处置状态不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "当前处置状态（01:未处置，02:已处置）")
    private String czzt;

    @StringSize(max = 512, message = "处置内容最大长度为512", groups = BasicCheck.class)
    @ApiModelProperty(value = "处置内容")
    private String hxczqk;

    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 32, message = "处置人最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "处置人")
    private String hxczr;

    @ApiModelProperty(value = "处置时间")
    private LocalDateTime hxczsj;

    @Override
    public String buildId() {
        return yjxxdm;
    }

    @Override
    public String buildCompanyId() {
        return yjxxdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return yjxxdm.substring(0, 21);
    }
}