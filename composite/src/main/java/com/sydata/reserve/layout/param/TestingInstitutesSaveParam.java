package com.sydata.reserve.layout.param;

import com.sydata.reserve.layout.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 储备布局地理信息-质检机构
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-质检机构新增参数")
public class TestingInstitutesSaveParam extends BaseEntity implements Serializable {


    @ApiModelProperty("质检机构名称")
    private String zjjgmc;

    @ApiModelProperty("单位联系人")
    private String dwlxr;

    @ApiModelProperty("联系电话")
    private String lxdh;

    @ApiModelProperty("是否启用，0：启用，1：未启用")
    private String sfqy;

    @ApiModelProperty("单位地址")
    private String dwdz;

    @ApiModelProperty("备注")
    private String bz;


}
