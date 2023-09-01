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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 基础信息-药剂信息分页参数
 * @date 2022-07-09 15:10
 */
@ApiModel(description = "基础信息-药剂信息分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MedicinePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(药剂编号-库区代码-采购日期)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "开始采购日期")
    private LocalDate beginCgrq;

    @ApiModelProperty(value = "结束采购日期")
    private LocalDate endCgrq;

    @ApiModelProperty(value = "药剂名称")
    private String yjmc;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
