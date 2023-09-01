package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lzq
 * @description 抽查计划修改参数
 * @date 2023/2/27 16:57
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查计划修改参数")
public class CheckPlanUpdateParam extends CheckPlanSaveParam implements Serializable {

    @NotBlank(message = "id必填")
    @ApiModelProperty(value = "id")
    private String id;
}
