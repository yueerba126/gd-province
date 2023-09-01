package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author xy
 * @since 2023/5/12 16:40
 */
@Data
@ApiModel("仓房廒间货位链参数")
public class LibraryChainQueryParam {

    @NotNull(message = "库区代码或仓房代码或廒间代码不能为空")
    @ApiModelProperty("库区代码或仓房代码或廒间代码")
    private String id;

    @NotNull(message = "查询类型不能为空")
    @ApiModelProperty("查询类型 仓房: cf  廒间: aj  货位: hw")
    private String type;
}
