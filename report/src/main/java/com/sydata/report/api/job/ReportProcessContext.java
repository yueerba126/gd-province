package com.sydata.report.api.job;

import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.vo.ReportResultVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 上报数据处理上下文
 * @date 2022/11/2 14:42
 */
@Data
@ToString
@Accessors(chain = true)
public class ReportProcessContext {

    @ApiModelProperty(value = "数据API枚举")
    private DataApiEnum dataApiEnum;

    @ApiModelProperty(value = "数据处理列表")
    private List<DataHandle> dataHandles;

    @ApiModelProperty(value = "数据ID和上报参数Map")
    private Map<String, BaseApiParam> dataIdParamMap;

    @ApiModelProperty(value = "上报结果Map")
    private Map<BaseApiParam, ReportResultVo> resultMap;

    private ReportProcessContext() {
    }

    /**
     * 申请上报数据处理上下文
     *
     * @param dataApiEnum 数据API枚举
     * @param dataHandles 数据处理列表
     * @return 上报数据处理上下文
     */
    public static ReportProcessContext apply(DataApiEnum dataApiEnum, List<DataHandle> dataHandles) {
        return new ReportProcessContext().setDataApiEnum(dataApiEnum).setDataHandles(dataHandles);
    }
}
