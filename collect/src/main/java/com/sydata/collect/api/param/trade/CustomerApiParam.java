package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetAppointValue;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.StringJoiner;

import static com.sydata.common.util.RegexpUtil.SPECIAL_CHECK;
import static jodd.util.StringPool.DASH;

/**
 * @author czx
 * @description 客户信息API操作参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "客户信息API操作参数")
public class CustomerApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @NotBlank(message = "客户类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[1|2]$", message = "客户类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "客户类型1:企业 2:个人")
    private String khlx;

    @NotBlank(message = "客户统一社会信用代码或身份证号必填", groups = BasicCheck.class)
    @StringSize(max = 18, message = "客户统一社会信用代码或身份证号最大长度为18", groups = BasicCheck.class)
    @ApiModelProperty(value = "客户统一社会信用代码或身份证号")
    private String khtyshxydmhsfzh;

    @NotBlank(message = "客户名称必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "客户名称最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @TargetAppointValue(message = "客户类型企业时,法定代表人必填", target = "#khlx", targetValue = {"1"}, groups = BasicCheck.class)
    @StringSize(max = 64, message = "法定代表人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @StringSize(max = 512, message = "通讯地址最大长度为512", groups = BasicCheck.class)
    @ApiModelProperty(value = "通讯地址")
    private String txdz;

    @Pattern(regexp = "^\\d{6}$", message = "邮政编码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @StringSize(max = 64, message = "联系人姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "联系人姓名")
    private String lxrxm;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "联系电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "联系电话")
    private String lxrdh;

    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "联系人身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "联系人身份证号")
    private String lxrsfzh;

    @StringSize(max = 64, message = "电子信箱最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "电子信箱")
    private String dzyx;

    @Pattern(regexp = "^$|^102$|^103$|^104$|^105$|^203$|^314$|^402$|^403$|^999$", message = "银行行别代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "客户方开户行")
    private String khfkhh;

    @Pattern(regexp = SPECIAL_CHECK, message = "客户方账号存在特殊字符", groups = BasicCheck.class)
    @StringSize(max = 30, message = "客户方账号大长度为30", groups = BasicCheck.class)
    @ApiModelProperty(value = "客户方账号")
    private String khfzh;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @TargetNotEmpty(message = "库区代码不存在", target = "#kqdm", groups = CorrectCheck.class)
    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany
    @ApiModelProperty(value = "单位名称", hidden = true)
    private String dwdmName;


    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(dwdm).add(khtyshxydmhsfzh).toString();
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }

    @Override
    public String buildStockHouseId() {
        return kqdm;
    }

}
