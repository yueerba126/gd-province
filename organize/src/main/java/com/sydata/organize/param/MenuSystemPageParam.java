package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
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
 * @description 菜单系统分页参数
 * @date 2023/5/22 15:06
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "菜单系统分页参数")
public class MenuSystemPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "系统名称")
    private String name;

    @ApiModelProperty(value = "系统简称")
    private String shortName;
}
