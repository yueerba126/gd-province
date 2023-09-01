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
 * @author xy
 * @describe 抽查小组修改参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查小组修改参数")
public class CheckGroupUpdateParam extends CheckGroupSaveParam implements Serializable {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;
}
