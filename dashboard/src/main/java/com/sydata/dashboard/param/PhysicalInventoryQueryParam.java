package com.sydata.dashboard.param;

import com.sydata.common.param.PageQueryParam;
import com.sydata.framework.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 储备粮实物库存报表参数
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理-库存归属报表分页参数")
public class PhysicalInventoryQueryParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "导出数据专用")
    private String ids;

    @ApiModelProperty(value = "导出数据专用", hidden = true)
    private List<String> idList;

    public List<String> getIdList() {
        if (StringUtils.isNotEmpty(ids)) {
            return Arrays.asList(ids.split(","));
        }
        return idList;
    }

    @ApiModelProperty(value = "粮权所属行政区划列表")
    private String regionIds;

    @ApiModelProperty(value = "粮权所属行政区划列表", hidden = true)
    private List<String> regionIdList;

    public List<String> getRegionIdList() {
        if (StringUtils.isNotEmpty(regionIds)) {
            return Arrays.asList(regionIds.split(","));
        }
        return regionIdList;
    }

    @ApiModelProperty(value = "查询内容：0, 全部储备 ,1省储，2市储，3区县储备, 4其他储备, 5 市储 + 区县储备")
    private String reserveTitle;

    @ApiModelProperty(value = "粮权所属行政区划")
    private String lqxzqhdm;

    @ApiModelProperty(value = "库区所属行政区划")
    private String kqxzqhdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食性质代码查询 字典：inventory_title")
    private String lsxzdms;

    @ApiModelProperty(value = "粮食性质代码查询")
    private List<String> lsxzdmList;

    public List<String> getLsxzdmList() {
        if (StringUtils.isNotEmpty(lsxzdms)) {
            return Arrays.asList(lsxzdms.split(","));
        }
        return lsxzdmList;
    }

    @ApiModelProperty(value = "库区省编码列表，用‘,’分割")
    private String stockProvince;

    @ApiModelProperty(value = "库区地市编码列表，用‘,’分割")
    private String stockCity;

    @ApiModelProperty(value = "库区区县编码列表，用‘,’分割")
    private String stockAreas;

    @ApiModelProperty(value = "粮权省编码列表，用‘,’分割")
    private String province;

    @ApiModelProperty(value = "粮权地市编码列表，用‘,’分割")
    private String city;

    @ApiModelProperty(value = "粮权区县编码列表，用‘,’分割")
    private String areas;

    @ApiModelProperty(value = "库区ID，用‘,’分割")
    private String stockHouseId;

    @ApiModelProperty(value = "粮食品种 字典：food_big_variety，用‘,’分割")
    private String ylpzs;

    @ApiModelProperty(value = "企业ID，用‘,’分割")
    private String enterpriseIds;

    @ApiModelProperty(value = "库区省编码编码列表，用‘,’分割", hidden = true)
    private List<String> stockProvinceList;

    public List<String> getStockProvinceList() {
        if (StringUtils.isNotEmpty(stockProvince)) {
            return Arrays.asList(stockProvince.split(","));
        }
        return stockProvinceList;
    }

    @ApiModelProperty(value = "库区区县编码列表，用‘,’分割", hidden = true)
    private List<String> stockCityList;

    public List<String> getStockCityList() {
        if (StringUtils.isNotEmpty(stockCity)) {
            return Arrays.asList(stockCity.split(","));
        }
        return stockCityList;
    }

    @ApiModelProperty(value = "库区地市编码列表，用‘,’分割", hidden = true)
    private List<String> stockAreasList;

    public List<String> getStockAreasList() {
        if (StringUtils.isNotEmpty(stockAreas)) {
            return Arrays.asList(stockAreas.split(","));
        }
        return stockAreasList;
    }

    @ApiModelProperty(value = "粮权省编码编码列表，用‘,’分割", hidden = true)
    private List<String> provinceList;

    @ApiModelProperty(value = "粮权区县编码列表，用‘,’分割", hidden = true)
    private List<String> areaList;

    @ApiModelProperty(value = "粮权地市编码列表，用‘,’分割", hidden = true)
    private List<String> cityList;

    @ApiModelProperty(value = "库区ID，用‘,’分割", hidden = true)
    private List<String> stockHouseIdList;

    @ApiModelProperty(value = "粮食品种列表，用‘,’分割", hidden = true)
    private List<String> ylpzList;

    @ApiModelProperty(value = "企业ID列表，用‘,’分割", hidden = true)
    private List<String> enterpriseIdList;

    public List<String> getAreaList() {
        if (StringUtils.isNotEmpty(areas)) {
            return Arrays.asList(areas.split(","));
        }
        return areaList;
    }

    public List<String> getProvinceList() {
        if (StringUtils.isNotEmpty(province)) {
            return Arrays.asList(province.split(","));
        }
        return provinceList;
    }

    public List<String> getYlpzList() {
        if (StringUtils.isNotEmpty(ylpzs)) {
            return Arrays.asList(ylpzs.split(","));
        }
        return ylpzList;
    }

    public List<String> getEnterpriseIdList() {
        if (StringUtils.isNotEmpty(enterpriseIds)) {
            return Arrays.asList(enterpriseIds.split(","));
        }
        return enterpriseIdList;
    }

    public List<String> getCityList() {
        if (StringUtils.isNotEmpty(city)) {
            return Arrays.asList(city.split(","));
        }
        return cityList;
    }

    public List<String> getStockHouseIdList() {
        if (StringUtils.isNotEmpty(stockHouseId)) {
            return Arrays.asList(stockHouseId.split(","));
        }
        return stockHouseIdList;
    }

    @ApiModelProperty(value = "省ID", hidden = true)
    private String provinceId;

    @ApiModelProperty(value = "市ID", hidden = true)
    private String cityId;

    @ApiModelProperty(value = "区ID", hidden = true)
    private String areaId;
}
