
package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.ImgPositionCheck;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
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

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 库区图视频监控设备点位标注API参数
 * @date 2022/10/21 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区图视频监控设备点位标注API参数")
public class WebcamLabelApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @NotBlank(message = "视频监控设备名称必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "视频监控设备名称最大长度256", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "视频监控设备名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "视频监控设备名称")
    private String spjksbmc;

    @NotBlank(message = "视频监控设备id必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{8}[4][0][1][3][1-2]{1}[6-7]{1}[0-9]{6}$",
            message = "视频监控设备id请遵循国家设备ID编码规则的详细编码规则", groups = BasicCheck.class)
    @TargetStartsWith(message = "视频监控设备id前6位行政区划代码需与库区行政区划代码一致", target = "#kqdmXzqhdm",
            groups = CorrectCheck.class)
    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ImgPositionCheck(message = "视频监控设备相对位置不在库区平面图xy轴范围之内", groups = BasicCheck.class)
    @NotBlank(message = "视频监控设备相对位置必填", groups = BasicCheck.class)
    @StringSize(max = 20, message = "视频监控设备相对位置最大长度20", groups = BasicCheck.class)
    @ApiModelProperty(value = "视频监控设备相对位置")
    private String spjksbxdwz;

    @StringSize(max = 256, message = "视频监控设备位置样式最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "视频监控设备位置样式")
    private String spjksbwzys;

    @NotBlank(message = "视频监控类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "视频监控类型不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "视频监控类型")
    private String spjklx;

    @StringSize(max = 1024, message = "备注最大长度1024", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;

    @StringSize(max = 32, min = 32, message = "海康监控点唯一标识只能是32位", groups = BasicCheck.class)
    @ApiModelProperty(value = "海康监控点唯一标识")
    private String hkCameraIndexCode;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm", dataValue = "#xzqhdm")
    @ApiModelProperty(value = "库区代码行政区划代码", hidden = true)
    private String kqdmXzqhdm;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(dwdm).add(kqdm).add(spjksbid).toString();
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
