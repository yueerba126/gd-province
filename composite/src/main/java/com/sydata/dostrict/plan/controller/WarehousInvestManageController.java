package com.sydata.dostrict.plan.controller;

import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.plan.service.IWarehouseInvestManageService;
import com.sydata.dostrict.plan.vo.FacilitiesAndBuildingsInfoVo;
import com.sydata.dostrict.plan.vo.StockHouseDistributionVo;
import com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 规划建设-仓储设施投资管理 Controller
 *
 * @author lzq
 * @date 2023-04-24
 */
@Api(tags = "规划建设-仓储设施投资管理")
@Validated
@RestController
@RequestMapping("/apparitor/warehousinvest")
public class WarehousInvestManageController {

    @Resource
    private IWarehouseInvestManageService iWarehouseInvestManageService;

    @ApiOperation("根据行政区划和库点代码获取仓容、仓房数量、廒间数量、货位数量、设备数量的统计信息")
    @PostMapping("/facilitiesandbuildingsinfo/list")
    public FacilitiesAndBuildingsInfoVo listFacilitiesAndBuildingsInfoVo(@RequestBody @Valid WarehousInvestManageSelectParam param) {
        FacilitiesAndBuildingsInfoVo facilitiesAndBuildingsInfoVo = iWarehouseInvestManageService.listFacilitiesAndBuildingsInfoVo(param);
        return facilitiesAndBuildingsInfoVo;
    }

    @ApiOperation("根据行政区划获取库区分布信息")
    @PostMapping("/stockhousedistribution/list")
    public List<StockHouseDistributionVo> listStockHouseDistributionVos(@RequestBody @Valid WarehousInvestManageSelectParam param) {
        List<StockHouseDistributionVo> stockHouseDistributionVos = iWarehouseInvestManageService.listStockHouseDistributionVos(param);
        return stockHouseDistributionVos;
    }

    @ApiOperation("根据行政区划和库点代码获取仓储设施建设管理信息统计信息")
    @PostMapping("/warehousefacilityconstructionmanagement/list")
    public List<WarehouseFacilityConstructionManagementVo> listWarehouseFacilityConstructionManagementVos(@RequestBody @Valid WarehousInvestManageSelectParam param) {
        List<WarehouseFacilityConstructionManagementVo> warehouseFacilityConstructionManagementVos = iWarehouseInvestManageService.listWarehouseFacilityConstructionManagementVos(param);
        return warehouseFacilityConstructionManagementVos;
    }

}
