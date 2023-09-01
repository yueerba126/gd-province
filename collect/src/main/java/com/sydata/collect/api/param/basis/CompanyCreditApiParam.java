package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author lzq
 * @description 企业信用信息API参数
 * @date 2022/10/21 11:30
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "企业信用信息API参数")
public class CompanyCreditApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotBlank(message = "信用等级必填", groups = BasicCheck.class)
    @StringSize(max = 10, message = "信用等级最大长度10", groups = BasicCheck.class)
    @ApiModelProperty(value = "信用等级")
    private String xydj;

    @NotBlank(message = "评定年份必填", groups = BasicCheck.class)
    @StringSize(max = 4, min = 4, message = "评定年份长度4", groups = BasicCheck.class)
    @ApiModelProperty(value = "评定年份")
    private String pdnf;

    @StringSize(max = 64, message = "评定单位/机构名称最大长度64", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,64}$", message = "评定单位/机构名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "评定单位/机构名称")
    private String pddw;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @Override
    public String buildId() {
        return dwdm;
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }
}
