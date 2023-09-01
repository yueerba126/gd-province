package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.ExceptionMonitoringEvent;
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
 * @describe 粮库管理-库区视频监控异常事件告警VO
 * @date 2022-07-25 14:56
 */
@ApiModel(description = "粮库管理-库区视频监控异常事件告警VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExceptionMonitoringEventVo extends ExceptionMonitoringEvent implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty("安装位置类型名称")
    @DataBindDict(sourceField = "#azwzlx", sourceFieldCombination = "azwzlx")
    private String azwzlxName;
}
