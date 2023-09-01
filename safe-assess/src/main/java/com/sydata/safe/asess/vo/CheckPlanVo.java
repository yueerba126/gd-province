package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.annotation.DataBindCheckGroup;
import com.sydata.safe.asess.annotation.DataBindTemplate;
import com.sydata.safe.asess.domain.CheckPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author czx
 * @describe 抽查计划Vo
 * @date 2023-02-14 14:10
 */
@ApiModel(description = "抽查计划Vo")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CheckPlanVo extends CheckPlan implements Serializable {

    @DataBindRegion(sourceField = "#checkRegionIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "抽查对象行政区域名称,逗号分割")
    private String checkRegionIdsNames;

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindCheckGroup(sourceField = "#groupId")
    @ApiModelProperty(value = "小组名称")
    private String groupName;
}
