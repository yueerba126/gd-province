package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xy
 * @describe 仓房廒间货位链VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "仓房廒间货位链VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LibraryChainVo implements Serializable {

    @ApiModelProperty("仓房代码或廒间代码或货位代码")
    private String id;

    @ApiModelProperty("仓房名称或廒间名称或货位名称")
    private String name;



}
