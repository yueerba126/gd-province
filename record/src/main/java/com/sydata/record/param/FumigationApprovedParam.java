
package com.sydata.record.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author lzq
 * @description 备案管理-熏蒸审核参数
 * @date 2022/12/10 9:46
 */
@ApiModel(description = "备案管理-熏蒸审核参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FumigationApprovedParam implements Serializable {

    @NotBlank(message = "熏蒸备案ID必填")
    @ApiModelProperty(value = "主键ID(库区代码+填报日期+熏蒸编码)")
    private String id;

    @NotBlank(message = "审核意见必填")
    @ApiModelProperty(value = "审核意见")
    private String shyj;

    @NotBlank(message = "审核状态必填")
    @Pattern(regexp = "^2$|^3$", message = "审核状态不存在")
    @ApiModelProperty(value = "审核状态：1待审核、2审核通过、3退回")
    private String shzt;
}
