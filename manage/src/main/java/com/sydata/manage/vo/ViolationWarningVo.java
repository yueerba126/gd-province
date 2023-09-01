package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.ViolationWarning;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 粮库管理-违规预警信息VO
 * @date 2022-07-25 14:56
 */
@ApiModel(description = "粮库管理-违规预警信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ViolationWarningVo extends ViolationWarning implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "违规主体类型名称")
    @DataBindDict(sourceField = "#wgztlx", sourceFieldCombination = "wgztlx")
    private String wgztlxName;

    @ApiModelProperty(value = "违规类型名称")
    @DataBindDict(sourceField = "#wglx", sourceFieldCombination = "wglx")
    private String wglxName;

    @ApiModelProperty(value = "当前处置状态名称")
    @DataBindDict(sourceField = "#czzt", sourceFieldCombination = "czzt")
    private String czztName;
}
