package com.sydata.organize.vo;

import com.sydata.organize.domain.Region;
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
 * @describe 行政区域树VO
 * @date 2022-06-29 17:58
 */
@ApiModel(description = "行政区域树VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegionTreeVo extends Region implements Serializable {

    @ApiModelProperty(value = "子节点")
    private List<RegionTreeVo> child;
}
