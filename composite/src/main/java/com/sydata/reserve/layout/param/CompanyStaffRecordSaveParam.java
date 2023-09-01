package com.sydata.reserve.layout.param;

import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.reserve.layout.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

import static cn.hutool.core.lang.RegexPool.MOBILE;

/**
 * <p>
 * 储备布局地理信息-人员信息备案
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-人员信息备案新增参数")
public class CompanyStaffRecordSaveParam extends BaseEntity implements Serializable {


    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "单位名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "单位名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位名称")
    private String dwmc;

    @NotBlank(message = "隶属部门必填", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,256}$", message = "隶属部门格式错误", groups = BasicCheck.class)
    @ApiModelProperty("隶属部门")
    private String lsbm;

    @NotBlank(message = "行政区划代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "姓名必填", groups = BasicCheck.class)
    @StringSize(max = 32, message = "姓名最大长度32", groups = BasicCheck.class)
    @PersonNameRule(message = "人员姓名为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty("姓名")
    private String xm;

    @NotBlank(message = "性别必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$", message = "性别不存在", groups = BasicCheck.class)
    @ApiModelProperty("性别")
    private String xb;

    @NotBlank(message = "身份证号码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("身份证号码")
    private String sfzhm;

    @NotNull(message = "入职日期必填", groups = BasicCheck.class)
    @ApiModelProperty("入职日期")
    private LocalDate rzrq;

    @NotBlank(message = "岗位性质", groups = BasicCheck.class)
    @Pattern(regexp = "^11$|^12$|^20$", message = "岗位性质不存在", groups = BasicCheck.class)
    @ApiModelProperty("岗位性质")
    private String gwxz;

    @NotBlank(message = "在岗状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$", message = "在岗状态不存在", groups = BasicCheck.class)
    @ApiModelProperty("在岗状态")
    private String zgzt;

    @ApiModelProperty("离职日期")
    private LocalDate lzrq;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "座机电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty("座机电话")
    private String zjdh;

    @NotBlank(message = "移动电话必填", groups = BasicCheck.class)
    @Pattern(regexp = MOBILE, message = "移动电话不符合格式", groups = BasicCheck.class)
    @ApiModelProperty("移动电话")
    private String yddh;

    @StringSize(max = 32, message = "电子邮箱最大长度32", groups = BasicCheck.class)
    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @NotBlank(message = "民族必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$|^07$|^08$|^09$|^10$|^11$|^12$|^13$|^14$|^15$|^16$|^17$|^18$|" +
            "^19$|^20$|^21$|^22$|^23$|^24$|^25$|^26$|^27$|^28$|^29$|^30$|^31$|^32$|^33$|^34$|^35$|^36$|^37$|^38$|" +
            "^39$|^40$|^41$|^42$|^43$|^44$|^45$|^46$|^47$|^48$|^49$|^50$|^51$|^52$|^53$|^54$|^55$|^56$",
            message = "民族不存在", groups = BasicCheck.class)
    @ApiModelProperty("民族")
    private String mz;

    @NotBlank(message = "政治面貌必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$|^07$|^08$|^09$|^10$|^11$|^12$|^13$", message = "政治面貌不存在",
            groups = BasicCheck.class)
    @ApiModelProperty("政治面貌")
    private String zzmm;

    @NotBlank(message = "人员类别必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^1100$|^1200$|^1300$|^1400$|^1410$|^1411$|^1420$|^1430$|^1431$|^1432$|^1500$|^1510$|^1511$|" +
            "^1512$|^1513$|^1514$|^1515$", message = "人员类别不存在", groups = BasicCheck.class)
    @ApiModelProperty("人员类别")
    private String rylb;

    @NotBlank(message = "专业必填", groups = BasicCheck.class)
    @PersonNameRule(message = "专业为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @StringSize(max = 128, message = "专业最大长度128", groups = BasicCheck.class)
    @ApiModelProperty("专业")
    private String zy;

    @ApiModelProperty("取得最高职称或职业资格时间")
    private LocalDate qdzgzchzyzgsj;

    @NotBlank(message = "学历必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$|^3$|^4$", message = "学历不存在", groups = BasicCheck.class)
    @ApiModelProperty("学历")
    private String xl;

    @NotBlank(message = "职务必填", groups = BasicCheck.class)
    @PersonNameRule(message = "职务为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @StringSize(max = 16, message = "职务最大长度16", groups = BasicCheck.class)
    @ApiModelProperty("职务")
    private String zw;


    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("职业资格证书编号")
    private String zyzgzsbh;

    @ApiModelProperty("发证机关")
    private String fzjg;


}
