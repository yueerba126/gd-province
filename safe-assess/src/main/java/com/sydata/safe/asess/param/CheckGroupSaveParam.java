package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


/**
 * @author xy
 * @describe 抽查小组新增参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查小组新增参数")
public class CheckGroupSaveParam implements Serializable {

    @NotBlank(message = "小组名称不能为空")
    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @NotBlank(message = "组长名称不能为空")
    @ApiModelProperty(value = "组长名称")
    private String leaderName;

    @NotBlank(message = "组长的用户id不能为空")
    @ApiModelProperty(value = "组长的用户id")
    private String leaderId;

    @NotBlank(message = "联络员的用户id不能为空")
    @ApiModelProperty(value = "联络员的用户id")
    private String liaisonManId;

    @ApiModelProperty(value = "组员数")
    private Long groupNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Valid
    @ApiModelProperty(value = "小组人员列表")
    List<CheckGroupPersonnelSaveParam> groupPersonnelList;
}
