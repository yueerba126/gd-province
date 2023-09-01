package com.sydata.reserve.layout.param;

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
 * @author lzq
 * @describe 储备布局地理信息-质检机构分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "储备布局地理信息-质检机构分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestingInstitutesPageParam extends PageQueryParam implements Serializable {


    @ApiModelProperty("质检机构名称")
    private String zjjgmc;


}
