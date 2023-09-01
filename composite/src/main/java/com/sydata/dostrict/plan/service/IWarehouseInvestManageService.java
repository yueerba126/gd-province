package com.sydata.dostrict.plan.service;

import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.plan.vo.FacilitiesAndBuildingsInfoVo;
import com.sydata.dostrict.plan.vo.StockHouseDistributionVo;
import com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 规划建设-仓储设施投资管理 Service接口
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
public interface IWarehouseInvestManageService {
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取仓容、仓房数量、廒间数量、货位数量、设备数量的统计信息
     * @Date 16:14 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.FacilitiesAndBuildingsInfoVo>
     **/
    FacilitiesAndBuildingsInfoVo listFacilitiesAndBuildingsInfoVo(@RequestBody @Valid WarehousInvestManageSelectParam warehousInvestManageSelectParam);
    /**
     * @Author lzq
     * @Description 根据行政区划获取库区分布信息
     * @Date 16:15 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.StockHouseDistributionVo>
     **/
    List<StockHouseDistributionVo> listStockHouseDistributionVos(@RequestBody @Valid WarehousInvestManageSelectParam warehousInvestManageSelectParam);
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取仓储设施建设管理信息统计信息
     * @Date 16:15 2023/4/25
     * @Param [warehousInvestManageSelectParam]
     * @return java.util.List<com.sydata.dostrict.plan.vo.WarehouseFacilityConstructionManagementVo>
     **/
    List<WarehouseFacilityConstructionManagementVo> listWarehouseFacilityConstructionManagementVos(@RequestBody @Valid WarehousInvestManageSelectParam warehousInvestManageSelectParam);
}
