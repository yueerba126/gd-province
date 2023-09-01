package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * <p>
 * 异常监控告警事件
 * </p>
 *
 * @author xy
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "异常监控告警事件API操作参数")
public class ExceptionMonitoringEventApiParam extends BaseApiParam implements Serializable {

    @NotNull(message = "告警时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("告警时间")
    private LocalDateTime gjsj;

    @NotBlank(message = "库区名称不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,256}$", message = "库区名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区名称")
    private String kqmc;

    @NotBlank(message = "库区代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @Pattern(regexp = "^$|^.{1,256}$", message = "库区地址格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区地址")
    private String kqdz;

    @NotBlank(message = "视频监控设备id不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{8}[4][0][1][3][1-2]{1}[6-7]{1}[0-9]{6}$",
            message = "视频监控设备id请遵循国家设备ID编码规则的详细编码规则", groups = BasicCheck.class)
    @TargetStartsWith(message = "视频监控设备id前6位行政区划代码需与库区行政区划代码一致", target = "#kqdmXzqhdm",
            groups = CorrectCheck.class)
    @ApiModelProperty("视频监控设备id")
    private String spjksbid;

    @NotBlank(message = "安装位置类型不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02|03|04|05|06|07|08|09|10|11|12|99$", message = "安装位置类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("安装位置类型")
    private String azwzlx;

    @StringSize(max = 256, message = "监视区域说明超过最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("监视区域说明")
    private String jsqysm;

    @NotBlank(message = "异常告警说明不能为空", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "异常告警说明超过最大长度1024", groups = BasicCheck.class)
    @ApiModelProperty("异常告警说明")
    private String ycgjsm;

    @NotBlank(message = "文件存储ID必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{19}$", message = "文件存储ID格式错误(19位数字)", groups = BasicCheck.class)
    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    @NotBlank(message = "视频文件后缀名必填", groups = BasicCheck.class)
    @Pattern(regexp = "^mp4$", message = "视频文件后缀名只能是mp4", groups = BasicCheck.class)
    @ApiModelProperty(value = "视频文件后缀名")
    private String spwjhzm;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm", dataValue = "#xzqhdm")
    @ApiModelProperty(value = "库区代码行政区划代码", hidden = true)
    private String kqdmXzqhdm;

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
        return new StringJoiner(DASH).add(kqdm).add(gjsj.format(NORM_DATETIME_FORMATTER)).toString();
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
