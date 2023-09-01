package com.sydata.organize.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 组织架构-行政区划地图数据表
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_region_gis")
@ApiModel(value = "OrgRegionGis对象", description = "组织架构-行政区划地图数据表")
public class RegionGis implements Serializable {

    @ApiModelProperty("（主键ID）行政区域代码")
    @TableId("id")
    private String id;

    @ApiModelProperty("地图json数据")
    @TableField("gis_json")
    private String gisJson;


}
