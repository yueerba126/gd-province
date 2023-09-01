package com.sydata.safe.asess.param;

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

import static cn.hutool.core.lang.RegexPool.MOBILE;

/**
 * @author xy
 * @describe 抽查小组人员新增参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查小组人员新增参数")
public class CheckGroupPersonnelSaveParam implements Serializable {

    @NotBlank(message = "抽查人员名称不能为空")
    @ApiModelProperty(value = "抽查人员名称")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = MOBILE, message = "手机号不符合格式")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "职务")
    private String job;

}
