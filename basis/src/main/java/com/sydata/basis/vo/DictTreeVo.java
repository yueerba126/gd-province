package com.sydata.basis.vo;

import com.sydata.basis.domain.Dict;
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
 * @describe 基础信息-字典树
 * @date 2022-08-09 18:38
 */
@ApiModel(description = "基础信息-字典树")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DictTreeVo extends Dict implements Serializable {

    @ApiModelProperty(value = "字典子节点")
    private List<DictTreeVo> child;
}
