package com.sydata.admin.vo;

import com.sydata.admin.domain.Project;
import com.sydata.common.basis.annotation.DataBindDict;
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
 * @describe 行政管理-项目信息VO
 * @date 2022-07-25 18:37
 */
@ApiModel(description = "行政管理-项目信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectVo extends Project implements Serializable {

    @ApiModelProperty(value = "法人证照类型名称")
    @DataBindDict(sourceField = "#fddbrzzlx", sourceFieldCombination = "fddbrzzlx")
    private String fddbrzzlxName;

    @ApiModelProperty(value = "项目类型名称")
    @DataBindDict(sourceField = "#xmlx", sourceFieldCombination = "xmlx")
    private String xmlxName;

    @ApiModelProperty(value = "建设状态名称")
    @DataBindDict(sourceField = "#jszt", sourceFieldCombination = "jszt")
    private String jsztName;
}
