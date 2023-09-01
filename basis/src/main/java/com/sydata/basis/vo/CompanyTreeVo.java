package com.sydata.basis.vo;

import com.sydata.basis.domain.Company;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 单位树VO
 * @date 2022/12/6 10:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CompanyTreeVo extends Company implements Serializable {

    @ApiModelProperty(value = "子节点")
    private List<CompanyTreeVo> child;
}
