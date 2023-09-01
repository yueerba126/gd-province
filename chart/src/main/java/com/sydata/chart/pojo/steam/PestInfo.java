package com.sydata.chart.pojo.steam;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;
import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * 熏蒸作业-虫害发生情况
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "熏蒸作业-虫害发生情况")
public class PestInfo {

    @ApiModelProperty(value = "发生部位")
    private String fsbw;

    @ApiModelProperty(value = "虫口密度值集合")
    private String ckmdzjh;

    @ApiModelProperty(value = "虫害种类")
    private String hczl;

    @DataBindDict(sourceField = "#hczl", sourceFieldCombination = "hczl", valueBindStrategy = SEPARATED, bindSeparated = HASH)
    @ApiModelProperty(value = "指标类别名称")
    private String hczlName;

    @ApiModelProperty(value = "害虫抗药性分析")
    private String hckyxfx;


}
