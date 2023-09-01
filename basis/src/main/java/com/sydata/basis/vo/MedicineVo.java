package com.sydata.basis.vo;

import com.sydata.basis.domain.Medicine;
import com.sydata.common.basis.annotation.DataBindDict;
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
 * @describe 基础信息-药剂信息VO
 * @date 2022-07-09 15:14
 */
@ApiModel(description = "基础信息-药剂信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MedicineVo extends Medicine implements Serializable {

    @ApiModelProperty("包装物名称")
    @DataBindDict(sourceField = "#bzw", sourceFieldCombination = "yjbzw")
    private String bzwName;

    @ApiModelProperty("储存地点名称")
    @DataBindDict(sourceField = "#ccdd", sourceFieldCombination = "ccdd")
    private String ccddName;

    @ApiModelProperty("库存数量单位名称")
    @DataBindDict(sourceField = "#kcsldw", sourceFieldCombination = "kcsldw")
    private String kcsldwName;
}
