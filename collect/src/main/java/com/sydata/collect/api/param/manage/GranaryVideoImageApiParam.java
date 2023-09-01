package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.common.file.annotation.DataBindFileStorage;
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
import java.util.StringJoiner;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_FORMATTER;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * <p>
 * 仓内视频图像
 * </p>
 *
 * @author xy
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "仓内视频图像API操作参数")
public class GranaryVideoImageApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "库区代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "视频监控设备id不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{8}[4][0][1][3][1-2]{1}[6-7]{1}[0-9]{6}$",
            message = "视频监控设备id请遵循国家设备ID编码规则的详细编码规则", groups = BasicCheck.class)
    @TargetStartsWith(message = "视频监控设备id前6位行政区划代码需与库区行政区划代码一致", target = "#kqdmXzqhdm",
            groups = CorrectCheck.class)
    @ApiModelProperty("视频监控设备id")
    private String spjksbid;

    @NotBlank(message = "仓房代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "仓房代码必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @ApiModelProperty("仓房代码")
    private String cfdm;

    @NotBlank(message = "货位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "货位代码必须以仓房代码开始", target = "#cfdm", groups = BasicCheck.class)
    @ApiModelProperty("货位代码")
    private String hwdm;

    @NotNull(message = "抓拍时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("抓拍时间")
    private LocalDateTime zpsj;

    @NotBlank(message = "文件存储ID必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{19}$", message = "文件存储ID格式错误(19位数字)", groups = BasicCheck.class)
    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    @NotBlank(message = "图像文件后缀名必填", groups = BasicCheck.class)
    @Pattern(regexp = "^jpg$|^mp4$", message = "图像文件后缀名只能是jpg或mp4", groups = BasicCheck.class)
    @ApiModelProperty("图像文件后缀名")
    private String txwjhzm;

    @JsonProperty("Yzwbh")
    @NotBlank(message = "预置位编号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "预置位编号需以上报的货位代码开始", target = "#hwdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{14}", message = "预置位编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("预置位编号")
    private String yzwbh;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm", dataValue = "#xzqhdm")
    @ApiModelProperty(value = "库区代码行政区划代码", hidden = true)
    private String kqdmXzqhdm;

    @JsonIgnore
    @NotBlank(message = "仓房(或油罐)编码不存在", groups = CorrectCheck.class)
    @DataBindWarehouse(sourceField = "#cfdm")
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)编码名称", hidden = true)
    private String cfdmName;

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwdmName;

    @JsonIgnore
    @NotBlank(message = "文件存储ID不存在", groups = CorrectCheck.class)
    @DataBindFileStorage
    @ApiModelProperty(value = "文件存储状态", hidden = true)
    private String fileStorageState;

    @JsonIgnore
    @DataBindFileStorage(dataValue = "#fileType")
    @ApiModelProperty(value = "文件存储类型", hidden = true)
    private String fileStorageType;

    @Override
    public String buildId() {
        return new StringJoiner(DASH)
                .add(spjksbid)
                .add(cfdm)
                .add(zpsj.format(PURE_DATETIME_FORMATTER)).add(yzwbh).toString();
    }

    @Override
    public String buildCompanyId() {
        return kqdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return kqdm;
    }
}
