package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lzq
 * @description 应用库区分页参数
 * @date 2023/5/30 14:21
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "应用库区分页参数")
public class AppApiStockHousePageParam extends PageQueryParam implements Serializable {

    @NotBlank(message = "应用id必填")
    @ApiModelProperty(value = "应用id")
    private String appid;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}
