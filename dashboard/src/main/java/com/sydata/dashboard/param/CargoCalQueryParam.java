package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xy
 * @describe 货位卡查询参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "货位卡查询参数")
public class CargoCalQueryParam implements Serializable {

    @NotNull(message = "库区代码必传")
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "廒间代码")
    private String ajdm;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;
}
