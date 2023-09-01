package com.sydata.organize.vo;

import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.organize.domain.RegionGis;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 行政区划地图数据VO
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */

@ApiModel(description = "行政区划地图数据VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegionGisVo extends RegionGis implements Serializable {

    @DataBindRegion(sourceField = "#id", dataValue = "#hkRegionId")
    @ApiModelProperty("海康区域id")
    private String hkRegionId;
}
