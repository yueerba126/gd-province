package com.sydata.reserve.layout.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @author xy
 * @describe 储备布局地理信息-质检机构修改参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-质检机构修改参数")
public class TestingInstitutesUpdateParam extends TestingInstitutesSaveParam implements Serializable {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;
}
