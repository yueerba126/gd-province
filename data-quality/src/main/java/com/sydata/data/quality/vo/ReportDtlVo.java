package com.sydata.data.quality.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.data.quality.domain.ReportDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 数据质量-报告明细VO
 * @date 2023/4/25 11:46
 */
@ApiModel(description = "数据质量-报告明细VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportDtlVo extends ReportDtl implements Serializable {

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode")
    @ApiModelProperty(value = "接口名称")
    private String apiName;
}
