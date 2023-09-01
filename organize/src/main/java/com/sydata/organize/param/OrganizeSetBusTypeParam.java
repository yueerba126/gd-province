
package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @describe 组织设置业务类型参数
 * @date 2022-06-30 19:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "组织设置业务类型参数")
public class OrganizeSetBusTypeParam implements Serializable {

    @ApiModelProperty(value = "组织业务类型：粮食监管单位")
    private String busType;

    @NotEmpty(message = "组织不能为空")
    @ApiModelProperty(value = "组织IDS")
    private List<String> ids;
}
