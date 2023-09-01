package com.sydata.collect.api.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.basis.*;
import com.sydata.collect.api.security.annotation.WebSecurity;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sydata.collect.api.enums.DataApiEnum.*;


/**
 * @author lzq
 * @description 数据收集-基础数据API(开放对接)
 * @date 2022/10/19 11:06
 */
@Api(tags = "数据收集-基础数据API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022")
public class BasisDataApiController {

    @ApiOperation("单位信息API")
    @PostMapping("/dwxx")
    @Validated(BasicCheck.class)
    @DataApi(COMPANY)
    @WebSecurity
    public void company(@RequestBody @Valid CompanyApiParam apiParam) {
    }

    @ApiOperation("库区信息API")
    @PostMapping("/kqxx")
    @Validated(BasicCheck.class)
    @DataApi(STOCK_HOUSE)
    @WebSecurity
    public void stockHouse(@RequestBody @Valid StockHouseApiParam apiParam) {
    }

    @ApiOperation("仓房信息API")
    @PostMapping("/cfxx")
    @Validated(BasicCheck.class)
    @DataApi(WAREHOUSE)
    @WebSecurity
    public void warehouse(@RequestBody @Valid WarehouseApiParam apiParam) {
    }

    @ApiOperation("廒间信息API")
    @PostMapping("/ajxx")
    @Validated(BasicCheck.class)
    @DataApi(GRANARY)
    @WebSecurity
    public void granary(@RequestBody @Valid GranaryApiParam apiParam) {
    }

    @ApiOperation("货位信息API")
    @PostMapping("/hwxx")
    @Validated(BasicCheck.class)
    @DataApi(CARGO)
    @WebSecurity
    public void cargo(@RequestBody @Valid CargoApiParam apiParam) {
    }

    @ApiOperation("油罐信息API")
    @PostMapping("/ygxx")
    @Validated(BasicCheck.class)
    @DataApi(TANK)
    @WebSecurity
    public void tank(@RequestBody @Valid TankApiParam apiParam) {
    }

    @ApiOperation("设备信息API")
    @PostMapping("/sbxx")
    @Validated(BasicCheck.class)
    @DataApi(DEVICE)
    @WebSecurity
    public void device(@RequestBody @Valid DeviceApiParam apiParam) {
    }

    @ApiOperation("药剂信息API")
    @PostMapping("/yjxx")
    @Validated(BasicCheck.class)
    @DataApi(MEDICINE)
    @WebSecurity
    public void medicine(@RequestBody @Valid MedicineApiParam apiParam) {
    }

    @ApiOperation("文件信息API")
    @PostMapping("/wjxx")
    @Validated(BasicCheck.class)
    @DataApi(FILE)
    @WebSecurity
    public void file(@RequestBody @Valid FileApiParam apiParam) {
    }

    @ApiOperation("库区图仓房点位标注API")
    @PostMapping("/kqtcfdwbz")
    @Validated(BasicCheck.class)
    @DataApi(CARGO_LABEL)
    @WebSecurity
    public void cargoLabel(@RequestBody @Valid CargoLabelApiParam apiParam) {
    }

    @ApiOperation("库区图视频监控设备点位标注API")
    @PostMapping("/kqtspjksbdwbz")
    @Validated(BasicCheck.class)
    @DataApi(WEBCAM_LABEL)
    @WebSecurity
    public void webcamLabel(@RequestBody @Valid WebcamLabelApiParam apiParam) {
    }

    @ApiOperation("人员信息API")
    @PostMapping("/ryxx")
    @Validated(BasicCheck.class)
    @DataApi(COMPANY_STAFF)
    @WebSecurity
    public void companyStaff(@RequestBody @Valid CompanyStaffApiParam apiParam) {
    }

    @ApiOperation("财务报表信息API")
    @PostMapping("/cwbbxx")
    @Validated(BasicCheck.class)
    @DataApi(FINANCE)
    @WebSecurity
    public void finance(@RequestBody @Valid FinanceApiParam apiParam) {
    }
}