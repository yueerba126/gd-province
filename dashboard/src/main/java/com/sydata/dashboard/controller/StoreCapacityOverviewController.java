package com.sydata.dashboard.controller;

import com.sydata.dashboard.param.AreaQueryParam;
import com.sydata.dashboard.service.IStoreCapacityOverviewService;
import com.sydata.dashboard.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库存分析总览 controller
 *
 * @author zhangcy
 * @since 2023/5/5 14:42
 */
@Api(tags = "全省信息总览")
@RequestMapping("/store/capacity/overview")
@RestController
public class StoreCapacityOverviewController {

    @Resource
    private IStoreCapacityOverviewService storeCapacityOverviewService;

    @ApiOperation("库容分析总览-查询明细")
    @PostMapping("/detail")
    public List<DashboardWarehouseCapacityVo> storeCapacityStoreCapacityDetail(@RequestParam String kqdm) {
        return storeCapacityOverviewService.storeCapacityDetail(kqdm);
    }


    @ApiOperation("库容分析总览-统计各地市仓房个数")
    @PostMapping("/count/city/warehouse/num")
    public List<CountCityWarehouseNumVo> countCityWarehouseNum() {
        return storeCapacityOverviewService.countCityWarehouseNum();
    }

    @ApiOperation("库容分析总览-区域分析")
    @PostMapping("/area")
    public List<DashboardWarehouseCapacityVo> storeCapacityAreaAnalysis(@RequestBody AreaQueryParam queryParam) {
        return storeCapacityOverviewService.storeCapacityAreaAnalysis(queryParam.getProvinceId(), queryParam.getCityId(), queryParam.getAreaId());
    }

    @ApiOperation("库容分析总览-饼图数据")
    @PostMapping("/pie/chart")
    public List<StoreCapacityTypePieVo> storeCapacityPieChartAnalysis(@RequestParam String kqdm) {
        return storeCapacityOverviewService.storeCapacityTypePieChartAnalysis(kqdm);
    }

    @ApiOperation("库容分析总览-库容变化年代折线图数据")
    @PostMapping("/line/chart")
    public List<StoreCapacityTypeYearLineVo> storeCapacityYearLineChartAnalysis(@RequestParam String kqdm) {
        return storeCapacityOverviewService.storeCapacityYearLineChartAnalysis(kqdm);
    }

    @ApiOperation("库容分析总览-库容变化年代柱状图数据")
    @PostMapping("/bar/chart")
    public List<StoreCapacityTypeBarVo> storeCapacityBarChartAnalysis(@RequestParam String kqdm) {
        return storeCapacityOverviewService.storeCapacityBarChartAnalysis(kqdm);
    }

    @ApiOperation("储备粮计划执行总览-储备粮计划总览（吨）")
    @PostMapping("/plan/statistics")
    public List<StorePlanExecuteStatisticsVo> storePlanStatistics(@RequestBody AreaQueryParam queryParam) {
        return storeCapacityOverviewService.storePlanStatistics(queryParam);
    }

    @ApiOperation("储备粮计划执行总览-储备粮轮换总览")
    @PostMapping("/rotation/statistics")
    public List<StoreRotationStatisticsVo> storeRotationStatistics(@RequestBody AreaQueryParam queryParam) {
        return storeCapacityOverviewService.storeRotationStatistics(queryParam);
    }


    @ApiOperation("储备粮计划执行总览-粮食品种轮换总览")
    @PostMapping("/grain/rotation/statistics")
    public List<StoreRotationStatisticsVo> storeGrainRotationStatistics(@RequestBody AreaQueryParam queryParam) {
        return storeCapacityOverviewService.storeGrainRotationStatistics(queryParam);
    }

    @ApiOperation("储备粮计划执行总览-完成情况")
    @PostMapping("/plan/complete/detail")
    public List<StorePlanCompleteVo> planCompleteDetail(@RequestBody AreaQueryParam queryParam) {
        return storeCapacityOverviewService.planCompleteDetail(queryParam);
    }

}
