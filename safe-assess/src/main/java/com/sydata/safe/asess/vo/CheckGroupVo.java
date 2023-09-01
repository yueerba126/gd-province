package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindUser;
import com.sydata.safe.asess.domain.CheckGroup;
import com.sydata.safe.asess.domain.CheckGroupPersonnel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xy
 * @describe 抽查人员VO
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "抽查小组VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CheckGroupVo extends CheckGroup implements Serializable {

    @DataBindUser(sourceField = "#leaderId")
    @ApiModelProperty(value = "组长用户名称")
    private String leaderName;

    @DataBindUser(sourceField = "#liaisonManId")
    @ApiModelProperty(value = "联络员用户名称")
    private String liaisonManName;

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @ApiModelProperty(value = "小组人员列表")
    List<CheckGroupPersonnel> groupPersonnelList;
}
