package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author chenzx
 * @Date 2022/8/19 16:03
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutStockPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "货位代码", example = "T01")
    private String hwdm;

    @ApiModelProperty(value = "出库业务单号")
    private String ckywdh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
