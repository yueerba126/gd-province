package com.sydata.dashboard.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xy
 * @describe 货位卡详情VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "货位卡详情VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CargoCalDtlVo implements Serializable {

    @DataBindCargo( sourceField = "#hwdm")
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @ApiModelProperty(value = "粮食品种")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质名称")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature", dataValue = "#remark")
    private String lsxzName;

    @ApiModelProperty(value = "粮食性质")
    private String lsxzdm;

//    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
//    @ApiModelProperty(value = "粮食等级名称")
//    private String lsdjdmName;
//
//    @ApiModelProperty(value = "粮食等级")
//    private String lsdjdm;

    @ApiModelProperty(value = "收获年底")
    private String shnd;

    @JsonIgnore
    @ApiModelProperty(value = "库存数")
    private BigDecimal sjsl;



    @ApiModelProperty(value = "数据统计")
    private List<CargoCalDtlNumVo> calDtlNumVos;




}
