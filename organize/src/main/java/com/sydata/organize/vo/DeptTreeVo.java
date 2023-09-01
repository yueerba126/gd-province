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
 * @description 部门树VO
 * @date 2023/2/9 18:53
 */
@ApiModel(description = "部门树VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptTreeVo extends DeptVo implements Serializable {

    @ApiModelProperty(value = "子节点")
    private List<DeptTreeVo> child;
}
