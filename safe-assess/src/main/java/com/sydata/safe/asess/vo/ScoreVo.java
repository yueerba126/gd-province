
package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.domain.Score;
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
 * @description 粮食安全考核-考核评分VO
 * @date 2023/4/3 16:54
 */
@ApiModel(description = "粮食安全考核-考核评分VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ScoreVo extends Score implements Serializable {

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @DataBindRegion(sourceField = "#assessRegionId")
    @ApiModelProperty(value = "考核区域名称")
    private String assessRegionName;
}
