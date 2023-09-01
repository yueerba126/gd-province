package com.sydata.basis.param;

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
 * @describe 基础信息-字典分页参数
 * @date 2022-07-26 10:25
 */
@ApiModel(description = "基础信息-字典分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DictPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典键")
    private String dictKey;

    @ApiModelProperty(value = "字典值")
    private String dictValue;
}
