package com.sydata.collect.api.param.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.*;
import com.sydata.common.file.annotation.DataBindFileStorage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import static cn.hutool.core.date.DatePattern.PURE_DATE_FORMATTER;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 熏蒸备案API操作参数
 * @date 2022/12/10 10:57
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "熏蒸备案API操作参数")
public class FumigationApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @NotNull(message = "填报日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "填报日期")
    private LocalDate tbrq;

    @NotBlank(message = "熏蒸编码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "熏蒸编码格式错误(4位数字)", groups = BasicCheck.class)
    @Size(max = 4, min = 4, message = "熏蒸编码长度为4", groups = BasicCheck.class)
    @ApiModelProperty(value = "熏蒸编码(4位顺序码)")
    private String xzbm;

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotNull(message = "申请熏蒸日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "申请熏蒸日期")
    private LocalDate sqxzrq;

    @NotBlank(message = "负责人必填", groups = BasicCheck.class)
    @Size(max = 6, message = "负责人最大长度为6", groups = BasicCheck.class)
    @PersonNameRule(message = "负责人为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty(value = "负责人")
    private String fzr;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "负责人电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "负责人电话")
    private String fzrdh;

    @NotBlank(message = "现场负责人必填", groups = BasicCheck.class)
    @Size(max = 6, message = "现场负责人最大长度为6", groups = BasicCheck.class)
    @PersonNameRule(message = "现场负责人为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty(value = "现场负责人")
    private String xcfzr;

    @NotBlank(message = "现场负责人职务必填", groups = BasicCheck.class)
    @Size(max = 10, message = "现场负责人职务最大长度为10", groups = BasicCheck.class)
    @PersonNameRule(message = "现场负责人职务为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty(value = "现场负责人职务")
    private String xcfzrzw;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "现场负责人电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "现场负责人电话")
    private String xcfzrdh;

    @NotBlank(message = "填表人必填", groups = BasicCheck.class)
    @Size(max = 6, message = "填表人最大长度为6", groups = BasicCheck.class)
    @PersonNameRule(message = "填表人为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty(value = "填表人")
    private String tbr;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "填表人电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "填表人电话")
    private String tbrdh;

    @NotBlank(message = "是否设置警戒线(东)必填", groups = BasicCheck.class)
    @Pattern(regexp = "^是$|^否$", message = "是否设置警戒线(东)不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "是否设置警戒线(东)：是或否")
    private String sfszjjxd;

    @NotBlank(message = "是否设置警戒线(西)必填", groups = BasicCheck.class)
    @Pattern(regexp = "^是$|^否$", message = "是否设置警戒线(西)不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "是否设置警戒线(西)：是或否")
    private String sfszjjxx;

    @NotBlank(message = "是否设置警戒线(南)必填", groups = BasicCheck.class)
    @Pattern(regexp = "^是$|^否$", message = "是否设置警戒线(南)不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "是否设置警戒线(南)：是或否")
    private String sfszjjxn;

    @NotBlank(message = "是否设置警戒线(北)必填", groups = BasicCheck.class)
    @Pattern(regexp = "^是$|^否$", message = "是否设置警戒线(北)不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "是否设置警戒线(北)：是或否")
    private String sfszjjxb;

    @Size(max = 256, message = "实施熏蒸作业时天气预报情况最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "实施熏蒸作业时天气预报情况")
    private String ssxzzystqybqk;

    @NotBlank(message = "熏蒸安排及实施过程必填", groups = BasicCheck.class)
    @Size(max = 256, message = "熏蒸安排及实施过程最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "熏蒸安排及实施过程")
    private String xzssgcap;

    @Size(max = 256, message = "安全防护及应急处置措施最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "安全防护及应急处置措施")
    private String aqfhjyjcccs;

    @Size(max = 256, message = "熏蒸注意事项最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "熏蒸注意事项")
    private String xzzysx;

    @Pattern(regexp = "^\\d{19}$", message = "文件存储ID格式错误(19位数字)", groups = BasicCheck.class)
    @ApiModelProperty(value = "文件存储ID")
    private String fileStorageId;

    @NotBlank(message = "药剂名称必填", groups = BasicCheck.class)
    @Size(max = 20, message = "药剂名称最大长度为20", groups = BasicCheck.class)
    @ApiModelProperty(value = "药剂名称")
    private String yjmc;

    @NotBlank(message = "药剂类型/型号必填", groups = BasicCheck.class)
    @Size(max = 10, message = "药剂名称最大长度为10", groups = BasicCheck.class)
    @ApiModelProperty(value = "药剂类型/型号")
    private String yjlx;

    @NotNull(message = "药剂有效期至必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "药剂有效期至")
    private LocalDate yjyxqz;

    @NotNull(message = "领取数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "领取数量精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty(value = "领取数量(公斤)")
    private BigDecimal lqsl;

    @NotBlank(message = "施药方式及设备必填", groups = BasicCheck.class)
    @Size(max = 20, message = "施药方式及设备最大长度为20", groups = BasicCheck.class)
    @ApiModelProperty(value = "施药方式及设备")
    private String sysbjfs;

    @NotBlank(message = "暂存地点必填", groups = BasicCheck.class)
    @Size(max = 30, message = "暂存地点最大长度为30", groups = BasicCheck.class)
    @ApiModelProperty(value = "暂存地点")
    private String zcdd;

    @NotBlank(message = "领取人必填", groups = BasicCheck.class)
    @Size(max = 12, message = "领取人最大长度为12", groups = BasicCheck.class)
    @PersonNameRule(message = "领取人为中文，且不允许填写:暂无,无,空,测试等，多个人员使用 | 分隔", split = true, groups = BasicCheck.class)
    @ApiModelProperty(value = "领取人")
    private String lqr;

    @NotNull(message = "领取日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "领取日期")
    private LocalDate lqrq;

    @Valid
    @ApiModelProperty(value = "熏蒸明细列表")
    private List<Dtl> dtls;

    @Valid
    @ApiModelProperty(value = "熏蒸人员列表")
    private List<People> peoples;

    @Valid
    @ApiModelProperty(value = "熏蒸方式列表")
    private List<Way> ways;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "文件存储ID不存在", target = "#fileStorageId", groups = CorrectCheck.class)
    @DataBindFileStorage
    @ApiModelProperty(value = "文件存储状态", hidden = true)
    private String fileStorageState;

    @Override
    protected String buildId() {
        return new StringJoiner(DASH)
                .add(kqdm)
                .add(tbrq.format(PURE_DATE_FORMATTER))
                .add(xzbm).toString();
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }

    @Override
    public String buildStockHouseId() {
        return kqdm;
    }


    /**
     * @author lzq
     * @description 熏蒸备案明细API操作参数
     * @date 2022/12/10 10:57
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @ApiModel(description = "熏蒸备案明细API操作参数")
    public static class Dtl implements Serializable {

        @NotBlank(message = "仓房(或油罐)编码必填", groups = BasicCheck.class)
        @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房(或油罐)编码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "仓房/油罐代码")
        private String cfdm;

        @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
        @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮食品种代码")
        private String lspzdm;

        @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
        @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮食性质代码")
        private String lsxzdm;

        @NotBlank(message = "粮食等级必填", groups = BasicCheck.class)
        @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮食等级代码")
        private String lsdjdm;

        @NotNull(message = "粮食数量必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "粮食数量 Decimal(20,3)", groups = BasicCheck.class)
        @Min(value = 0, message = "粮食数量不能小于0", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮食数量(吨)")
        private BigDecimal lssl;

        @NotNull(message = "水份必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "水份 Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "水份")
        private BigDecimal sf;

        @NotNull(message = "杂质必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "杂质 Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "杂质(%)")
        private BigDecimal zz;

        @NotNull(message = "粮温必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "粮温 Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮温")
        private BigDecimal lw;

        @NotNull(message = "仓温必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "仓温 Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "仓温")
        private BigDecimal cw;

        @NotNull(message = "仓内湿度必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "仓内湿度 Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "仓内湿度")
        private BigDecimal cnsd;

        @NotBlank(message = "储粮方式必填", groups = BasicCheck.class)
        @Pattern(regexp = "^1|2|3|9$", message = "储粮方式格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "储粮方式 1：散装储粮，2：包装，3：围包散存，9：其他")
        private String clfs;

        @NotNull(message = "入库日期必填", groups = BasicCheck.class)
        @ApiModelProperty(value = "入库日期")
        private LocalDate rkrq;

        @NotBlank(message = "害虫必填", groups = BasicCheck.class)
        @Size(max = 30, message = "害虫长度最大30", groups = BasicCheck.class)
        @ApiModelProperty(value = "害虫")
        private String hc;

        @NotBlank(message = "虫粮等级判定不能为空", groups = BasicCheck.class)
        @Pattern(regexp = "^$|^531$|^532$|^533$|^534$", message = "虫粮等级判定格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "虫粮等级判定")
        private String cldjpd;

        @NotNull(message = "粮堆体积必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "粮堆体积Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮堆体积（立方米）")
        private BigDecimal ldtj;

        @NotNull(message = "空间体积必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "空间体积Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "空间体积（立方米）")
        private BigDecimal kjtj;

        @NotNull(message = "粮堆单位用药量必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "粮堆单位用药量Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮堆单位用药量")
        private BigDecimal lddwyyl;

        @NotNull(message = "空间单位用药量必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "空间单位用药量Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "空间单位用药量")
        private BigDecimal kjdwyyl;

        @NotNull(message = "总用药量必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "总用药量Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "总用药量")
        private BigDecimal zyyl;

        @NotBlank(message = "气密性必填", groups = BasicCheck.class)
        @Pattern(regexp = "^1$|^2$|^3$|^4$", message = "气密性不存在", groups = BasicCheck.class)
        @ApiModelProperty(value = "气密性")
        private String qmx;

        @NotNull(message = "计划熏蒸开始日期必填")
        @ApiModelProperty(value = "计划熏蒸开始日期")
        private LocalDate jhxzksrq;

        @NotNull(message = "计划熏蒸结束日期必填")
        @ApiModelProperty(value = "计划熏蒸结束日期")
        private LocalDate jhxzjsrq;

        /****************以下字段不需要传输，做为表间校验的二次校验**********************/

        @JsonIgnore
        @NotBlank(message = "仓房(或油罐)编码不存在", groups = CorrectCheck.class)
        @DataBindWarehouse(sourceField = "#cfdm", dataValue = "#kqdm")
        @DataBindTank(sourceField = "#cfdm", dataValue = "#kqdm")
        @ApiModelProperty(value = "仓房(或油罐)编码所属库区编码", hidden = true)
        private String kqdm;

        @JsonIgnore
        @NotBlank(message = "粮食品种代码国标不存在", groups = CorrectCheck.class)
        @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
        @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
        private String lspzdmName;
    }

    /**
     * @author lzq
     * @description 熏蒸备案人员API操作参数
     * @date 2022/12/10 10:57
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @ApiModel(description = "熏蒸备案人员API操作参数")
    public static class People implements Serializable {

        @NotBlank(message = "姓名必填", groups = BasicCheck.class)
        @Size(max = 6, message = "姓名最大长度为6", groups = BasicCheck.class)
        @PersonNameRule(message = "姓名为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
        @ApiModelProperty(value = "姓名")
        private String xm;

        @NotBlank(message = "职务必填", groups = BasicCheck.class)
        @Size(max = 10, message = "职务最大长度为10", groups = BasicCheck.class)
        @PersonNameRule(message = "职务为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
        @ApiModelProperty(value = "职务")
        private String zw;

        @NotBlank(message = "人员职业资格必填", groups = BasicCheck.class)
        @Pattern(regexp = "^初级$|^中级$|^高级$", message = "职业资格不存在", groups = BasicCheck.class)
        @ApiModelProperty(value = "职业资格：初级、中级、高级")
        private String zyzg;

        @NotBlank(message = "身体状况必填", groups = BasicCheck.class)
        @Size(max = 10, message = "身体状况最大长度为10", groups = BasicCheck.class)
        @ApiModelProperty(value = "身体状况")
        private String stzk;

        @NotBlank(message = "熏蒸任务分工必填", groups = BasicCheck.class)
        @Size(max = 30, message = "熏蒸任务分工最大长度为30", groups = BasicCheck.class)
        @ApiModelProperty(value = "熏蒸任务分工")
        private String xzrwfg;

        @NotBlank(message = "是否外包必填", groups = BasicCheck.class)
        @Pattern(regexp = "^是$|^否$", message = "是否外包不存在", groups = BasicCheck.class)
        @ApiModelProperty(value = "是否外包：是或否")
        private String sfwb;
    }

    /**
     * @author lzq
     * @description 熏蒸备案方式API操作参数
     * @date 2022/12/10 10:57
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @ApiModel(description = "熏蒸备案方式API操作参数")
    public static class Way implements Serializable {

        @NotNull(message = "设定熏蒸浓度必填", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "设定熏蒸浓度Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "设定熏蒸浓度(ml/m³)")
        private BigDecimal xznd;

        @NotNull(message = "密闭时间必填", groups = BasicCheck.class)
        @Positive(message = "密闭时间不能小于等于0", groups = BasicCheck.class)
        @ApiModelProperty(value = "密闭时间(天)")
        private Integer mbsj;

        @NotBlank(message = "蒸熏方式必填", groups = BasicCheck.class)
        @Size(max = 30, message = "蒸熏方式最大长度30", groups = BasicCheck.class)
        @ApiModelProperty(value = "蒸熏方式")
        private String xzfs;

        @NotBlank(message = "散气方式必填", groups = BasicCheck.class)
        @Size(max = 30, message = "散气方式最大长度30", groups = BasicCheck.class)
        @ApiModelProperty(value = "散气方式")
        private String sqfs;
    }
}
