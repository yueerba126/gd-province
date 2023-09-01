package com.sydata.report.api.param.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 客户信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "客户信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "客户类型")
    private String khlx;

    @ApiModelProperty(value = "客户统一社会信用代码或身份证号")
    private String khtyshxydmhsfzh;

    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @ApiModelProperty(value = "通讯地址")
    private String txdz;

    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @ApiModelProperty(value = "联系人姓名")
    private String lxrxm;

    @ApiModelProperty(value = "联系电话")
    private String lxrdh;

    @ApiModelProperty(value = "联系人身份证号")
    private String lxrsfzh;

    @ApiModelProperty(value = "电子信箱")
    private String dzyx;

    @ApiModelProperty(value = "客户方开户行")
    private String khfkhh;

    @ApiModelProperty(value = "客户方账号")
    private String khfzh;
}