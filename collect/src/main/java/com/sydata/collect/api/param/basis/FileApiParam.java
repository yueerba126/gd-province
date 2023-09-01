package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.FileNameCheck;
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
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 文件信息API操作参数
 * @date 2022/10/21 9:57
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "文件信息API操作参数")
public class FileApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "文件名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,100}$", message = "文件名称格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "文件名必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @FileNameCheck(message = "文件命名不规范", wjlx = "#wjlx", groups = BasicCheck.class)
    @ApiModelProperty("文件名称")
    private String wjmc;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "文件类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$", message = "文件类型不存在", groups = BasicCheck.class)
    @ApiModelProperty("文件类型")
    private String wjlx;

    @NotBlank(message = "文件存储ID必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{19}$", message = "文件存储ID格式错误(19位数字)", groups = BasicCheck.class)
    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

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
        return new StringJoiner(DASH).add(wjmc).add(kqdm).add(wjlx).toString();
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
