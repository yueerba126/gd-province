package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author xy
 * @describe 货位卡VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "货位卡VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CargoCalVo implements Serializable {


    @ApiModelProperty(value = "仓房名称")
    private String cfmc;

    @ApiModelProperty(value = "廒间名称")
    private String ajmc;

    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;




}
