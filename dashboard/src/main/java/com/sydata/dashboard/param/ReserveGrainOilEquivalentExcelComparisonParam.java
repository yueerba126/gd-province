package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lzq
 * @description 报表管理-储备粮油折合报表Excel比对参数
 * @date 2023/6/25 15:02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理-储备粮油折合报表excel比对参数")
public class ReserveGrainOilEquivalentExcelComparisonParam extends ReserveGrainOilEquivalentParam implements Serializable {

    @NotNull(message = "excel不能为空")
    @ApiModelProperty(value = "导入Excel")
    private MultipartFile file;
}
