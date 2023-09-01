package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.dashboard.domain.StorePlanCompleteDetail;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangcy
 * @since 2023/5/6 19:14
 */
@ApiModel("储备计划完成情况")
@Data
public class StorePlanCompleteVo {

    /**
     * 粮食性质代码
     */
    private static final String PROVINCE_GRAIN_NUTRUE = "121";
    private static final String CITY_GRAIN_NUTRUE = "122";
    private static final String AREA_GRAIN_NUTRUE = "123";
    /**
     * 粮食品种代码
     */
    private static final String WHEAT_GRAIN_VARIETY = "1120000";
    private static final String PADDY_GRAIN_VARIETY = "1130000";
    private static final String MAIZE_GRAIN_VARIETY = "1140000";

    @ApiModelProperty("地市ID")
    private String cityId;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty("地市名称")
    private String cityName;

    @ApiModelProperty("企业ID")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty("企业名称")
    private String enterpriseName;

    @ApiModelProperty("库点ID")
    private String stockHouseId;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty("库点名称")
    private String storehouseName;

    @ApiModelProperty("省级储备小麦计划量")
    private BigDecimal provinceWheatPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备小麦库存量")
    private BigDecimal provinceWheatStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备玉米计划量")
    private BigDecimal provinceMaizePlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备玉米库存量")
    private BigDecimal provinceMaizeStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备稻谷计划量")
    private BigDecimal provincePaddyPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备稻谷库存量")
    private BigDecimal provincePaddyStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("省级储备计划完成率")
    private BigDecimal provinceCompleteRatio = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty("市级储备小麦计划量")
    private BigDecimal cityWheatPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备小麦库存量")
    private BigDecimal cityWheatStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备玉米计划量")
    private BigDecimal cityMaizePlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备玉米库存量")
    private BigDecimal cityMaizeStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备稻谷计划量")
    private BigDecimal cityPaddyPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备稻谷库存量")
    private BigDecimal cityPaddyStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("市级储备计划完成率")
    private BigDecimal cityCompleteRatio = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty("县级储备小麦计划量")
    private BigDecimal areaWheatPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备小麦库存量")
    private BigDecimal areaWheatStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备玉米计划量")
    private BigDecimal areaMaizePlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备玉米库存量")
    private BigDecimal areaMaizeStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备稻谷计划量")
    private BigDecimal areaPaddyPlanQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备稻谷库存量")
    private BigDecimal areaPaddyStockQty = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    @ApiModelProperty("县级储备计划完成率")
    private BigDecimal areaCompleteRatio = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public StorePlanCompleteVo(StorePlanCompleteDetail.PositionKey positionKey, List<StorePlanCompleteDetail> planCompleteDetails) {
        setCityId(positionKey.getCityId());
        setEnterpriseId(positionKey.getEnterpriseId());
        setStockHouseId(positionKey.getStockHouseId());

        // Map<粮食性质，Map<粮食品种, 计划完成情况>>
        Map<String, Map<String, StorePlanCompleteDetail>> storeNutureGrainVarietyMap = planCompleteDetails.stream()
                .collect(Collectors.groupingBy(StorePlanCompleteDetail::getLsxzdm,
                        Collectors.toMap(StorePlanCompleteDetail::getLspzdm, Function.identity())));


        if (storeNutureGrainVarietyMap.containsKey(PROVINCE_GRAIN_NUTRUE)) {
            fillProvinceProperties(storeNutureGrainVarietyMap.get(PROVINCE_GRAIN_NUTRUE));
        }

        if (storeNutureGrainVarietyMap.containsKey(CITY_GRAIN_NUTRUE)) {
            fillCityProperties(storeNutureGrainVarietyMap.get(CITY_GRAIN_NUTRUE));
        }

        if (storeNutureGrainVarietyMap.containsKey(AREA_GRAIN_NUTRUE)) {
            fillAreaProperties(storeNutureGrainVarietyMap.get(AREA_GRAIN_NUTRUE));
        }
    }

    private void fillProvinceProperties(Map<String, StorePlanCompleteDetail> grainVarietyMap) {
        BigDecimal completeRatio = BigDecimal.ZERO;

        // 小麦
        if (grainVarietyMap.containsKey(WHEAT_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(WHEAT_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setProvinceWheatPlanQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setProvinceWheatStockQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 稻谷
        if (grainVarietyMap.containsKey(PADDY_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(PADDY_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setProvincePaddyPlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setProvincePaddyStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 玉米
        if (grainVarietyMap.containsKey(MAIZE_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(MAIZE_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setProvinceMaizePlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setProvinceMaizeStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        setProvinceCompleteRatio(completeRatio.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
    }

    private void fillCityProperties(Map<String, StorePlanCompleteDetail> grainVarietyMap) {
        BigDecimal completeRatio = BigDecimal.ZERO;

        // 小麦
        if (grainVarietyMap.containsKey(WHEAT_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(WHEAT_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setCityWheatPlanQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setCityWheatStockQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 稻谷
        if (grainVarietyMap.containsKey(PADDY_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(PADDY_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setCityPaddyPlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setCityPaddyStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 玉米
        if (grainVarietyMap.containsKey(MAIZE_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(MAIZE_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setCityMaizePlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setCityMaizeStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        setCityCompleteRatio(completeRatio.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
    }

    private void fillAreaProperties(Map<String, StorePlanCompleteDetail> grainVarietyMap) {
        BigDecimal completeRatio = BigDecimal.ZERO;

        // 小麦
        if (grainVarietyMap.containsKey(WHEAT_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(WHEAT_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setAreaWheatPlanQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setAreaWheatStockQty(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 稻谷
        if (grainVarietyMap.containsKey(PADDY_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(PADDY_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setAreaPaddyPlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setAreaPaddyStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        // 玉米
        if (grainVarietyMap.containsKey(MAIZE_GRAIN_VARIETY)) {
            StorePlanCompleteDetail storePlanCompleteDetail = grainVarietyMap.get(MAIZE_GRAIN_VARIETY);
            Optional<StorePlanCompleteDetail> storePlanCompleteDetailOpt = Optional.ofNullable(storePlanCompleteDetail);
            setAreaMaizePlanQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getPlanQty).orElse(BigDecimal.ZERO));
            setAreaMaizeStockQty(Optional.ofNullable(storePlanCompleteDetail).map(StorePlanCompleteDetail::getStockQty).orElse(BigDecimal.ZERO));
            completeRatio = completeRatio.add(storePlanCompleteDetailOpt.map(StorePlanCompleteDetail::getCompleteRatio).orElse(BigDecimal.ZERO));
        }

        setAreaCompleteRatio(completeRatio.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
    }

}
