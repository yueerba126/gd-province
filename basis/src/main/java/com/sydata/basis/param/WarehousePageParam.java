
package com.sydata.basis.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 基础信息-仓库分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "基础信息-仓库分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WarehousePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（仓房代码）库区代码+4位顺序码")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty("仓房名称")
    private String cfmc;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
