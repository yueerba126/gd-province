package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.domain.Review;
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
 * @description 粮食安全考核-考核评审VO
 * @date 2023/4/3 16:54
 */
@ApiModel(description = "粮食安全考核-考核评审VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReviewVo extends Review implements Serializable {

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindOrganize(sourceField = "#deptId")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @DataBindRegion(sourceField = "#assessRegionId")
    @ApiModelProperty(value = "考核区域名称")
    private String assessRegionName;
}
