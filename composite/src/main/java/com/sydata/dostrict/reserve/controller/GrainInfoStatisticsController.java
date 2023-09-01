package com.sydata.dostrict.reserve.controller;

import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.service.IGrainInfoStatisticsService;
import com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo;
import com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo;
import com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-粮油信息统计
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-粮油信息统计")
@Validated
@RestController
@RequestMapping("/apparitor/graininfostatistics")
public class GrainInfoStatisticsController {
    @Resource
    private IGrainInfoStatisticsService grainInfoStatisticsService;

    @ApiOperation("根据行政区划和库点代码获取按物料分组的库存的统计信息")
    @PostMapping("/grainmaterialstatistics/list")
    public List<GrainMaterialStatisticsVo> listGrainMaterialStatisticsVo(@RequestBody @Valid GrainSelectParam param) {
        List<GrainMaterialStatisticsVo> realAndPlanningWheelOutAndInVos = grainInfoStatisticsService.listGrainMaterialStatisticsVo(param);
        return realAndPlanningWheelOutAndInVos;
    }

    @ApiOperation("根据行政区划和库点代码获取按收获年度分组的库存的统计信息")
    @PostMapping("/grainharveststatistics/list")
    public List<GrainHarvestStatisticsVo> listGrainHarvestStatisticsVos(@RequestBody @Valid GrainSelectParam param) {
        List<GrainHarvestStatisticsVo> realAndPlanningWheelOutAndInVos = grainInfoStatisticsService.listGrainHarvestStatisticsVos(param);
        return realAndPlanningWheelOutAndInVos;
    }

    @ApiOperation("根据行政区划和库点代码获取按业务日期、物料分组的物料价格的统计信息")
    @PostMapping("/materialpricetrendstatistics/list")
    public List<MaterialPriceTrendStatisticsVo> listMaterialPriceTrendStatisticsVos(@RequestBody @Valid GrainSelectParam param) {
        List<MaterialPriceTrendStatisticsVo> realAndPlanningWheelOutAndInVos = grainInfoStatisticsService.listMaterialPriceTrendStatisticsVos(param);
        return realAndPlanningWheelOutAndInVos;
    }
}
