package com.sydata.custody.dto;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangcy
 * @since 2023/4/23 15:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GrainCustodyJobsQueryDto extends PageQueryParam {

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty("廒间代码")
    private String ajdh;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("货位代码")
    private String hwdm;

}
