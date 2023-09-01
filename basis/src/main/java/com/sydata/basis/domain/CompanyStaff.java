package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * <p>
 * 企业人员信息表
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_company_staff")
@ApiModel(value = "BasicDataCompanyStaff对象", description = "企业人员信息表")
public class CompanyStaff extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id(单位代码+身份证号码)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

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

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}
