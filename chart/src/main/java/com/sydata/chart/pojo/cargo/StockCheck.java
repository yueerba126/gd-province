package com.sydata.chart.pojo.cargo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 货位卡片-检测记录
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "货位卡片-检测记录")
public class StockCheck {

    @ApiModelProperty(value = "扦样时间")
    private LocalDateTime qysj;

    @ApiModelProperty(value = "检验类别")
    private String jylb;

    @ApiModelProperty(value = "检验类别名称")
    @DataBindDict(sourceField = "#jylb", sourceFieldCombination = "jylb")
    private String jylbName;

    @ApiModelProperty(value = "检验单位")
    private String jydw;

}
