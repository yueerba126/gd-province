package com.sydata.safe.asess.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xy
 * @describe 抽查小组分页参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查小组分页参数")
public class CheckGroupPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "组长用户id")
    private String leaderId;

    @ApiModelProperty(value = "联络员用户id")
    private String liaisonManId;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;
}
