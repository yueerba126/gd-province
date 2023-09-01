package com.sydata.organize.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @describe 组织架构-组织树VO
 * @date 2022-06-29 17:58
 */
@ApiModel(description = "组织架构-组织树VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrganizeTreeVo extends OrganizeVo implements Serializable {

    @ApiModelProperty(value = "子节点")
    private List<OrganizeTreeVo> child;
}
