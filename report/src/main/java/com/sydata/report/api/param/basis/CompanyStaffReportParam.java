package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 企业人员信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "企业人员信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyStaffReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @ApiModelProperty("隶属部门")
    private String lsbm;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty("姓名")
    private String xm;

    @ApiModelProperty("性别")
    private String xb;

    @ApiModelProperty("身份证号码")
    private String sfzhm;

    @ApiModelProperty("入职日期")
    private LocalDate rzrq;

    @ApiModelProperty("岗位性质")
    private String gwxz;

    @ApiModelProperty("在岗状态")
    private String zgzt;

    @ApiModelProperty("离职日期")
    private LocalDate lzrq;

    @ApiModelProperty("座机电话")
    private String zjdh;

    @ApiModelProperty("移动电话")
    private String yddh;

    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @ApiModelProperty("民族")
    private String mz;

    @ApiModelProperty("政治面貌")
    private String zzmm;

    @ApiModelProperty("人员类别")
    private String rylb;

    @ApiModelProperty("专业")
    private String zy;

    @ApiModelProperty("取得最高职称或职业资格时间")
    private LocalDate qdzgzchzyzgsj;

    @ApiModelProperty("学历")
    private String xl;

    @ApiModelProperty("职务")
    private String zw;
}