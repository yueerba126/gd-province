package com.sydata.dostrict.reserve.controller;

import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;
import com.sydata.dostrict.plan.vo.StockHouseDistributionVo;
import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.param.GrainWheetSelectParam;
import com.sydata.dostrict.reserve.service.IGrainPurSaleMangeService;
import com.sydata.dostrict.reserve.vo.CompanyDistributionVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo;
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
 * @description: 粮食储备-粮油购销管理
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-粮油购销管理")
@Validated
@RestController
@RequestMapping("/apparitor/grainpursalemange")
public class GrainPurSaleMangeController {
    @Resource
    private IGrainPurSaleMangeService grainPurSaleMangeService;

    @ApiOperation("根据行政区划和库点代码获取储备计划量和实际库存量在省储市储县储的统计信息")
    @PostMapping("/realandplanningquantity/list")
    public RealAndPlanningQuantityVo listRealAndPlanningQuantityVo(@RequestBody @Valid GrainSelectParam param) {
        RealAndPlanningQuantityVo realAndPlanningQuantityVo = grainPurSaleMangeService.listRealAndPlanningQuantityVo(param);
        return realAndPlanningQuantityVo;
    }

    @ApiOperation("根据行政区划获取企业分布信息")
    @PostMapping("/companydistribution/list")
    public List<CompanyDistributionVo> listCompanyDistributionVos(@RequestBody @Valid GrainSelectParam param) {
        List<CompanyDistributionVo> companyDistributionVos = grainPurSaleMangeService.listCompanyDistributionVos(param);
        return companyDistributionVos;
    }

    @ApiOperation("根据行政区划和库点代码获取不同年份的轮入轮出在省储市储县储的统计信息")
    @PostMapping("/realandplanningwheeloutandin/list")
    public List<RealAndPlanningWheelOutAndInVo> listRealAndPlanningWheelOutAndInVos(@RequestBody @Valid GrainWheetSelectParam param) {
        List<RealAndPlanningWheelOutAndInVo> realAndPlanningWheelOutAndInVos = grainPurSaleMangeService.listRealAndPlanningWheelOutAndInVos(param);
        return realAndPlanningWheelOutAndInVos;
    }
}
