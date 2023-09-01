package com.sydata.collect.api.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.trade.*;
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
 * @author czx
 * @description 数据收集-粮油购销API(开放对接)
 * @date 2022/10/19 11:06
 */
@Api(tags = "数据收集-粮油购销API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022")
public class TradeDataApiController {

    @ApiOperation("合同信息API")
    @PostMapping("/htxx")
    @Validated(BasicCheck.class)
    @DataApi(CONTRACT)
    @WebSecurity
    public void contract(@RequestBody @Valid ContractApiParam apiParam) {
    }


    @ApiOperation("粮食入库信息API")
    @PostMapping("/lsrkxx")
    @Validated(BasicCheck.class)
    @DataApi(IN_STOCK)
    @WebSecurity
    public void inStock(@RequestBody @Valid InStockApiParam apiParam) {
    }

    @ApiOperation("粮食入库检验信息API")
    @PostMapping("/rkjyxx")
    @Validated(BasicCheck.class)
    @DataApi(IN_STOCK_CHECK)
    @WebSecurity
    public void inStockCheck(@RequestBody @Valid InStockCheckApiParam apiParam) {
    }

    @ApiOperation("粮食入库结算信息API")
    @PostMapping("/rkjsxx")
    @Validated(BasicCheck.class)
    @DataApi(IN_STOCK_SETTLEMENT)
    @WebSecurity
    public void inStockSettlement(@RequestBody @Valid InStockSettlementApiParam apiParam) {
    }

    @ApiOperation("粮食出库信息API")
    @PostMapping("/lsckxx")
    @Validated(BasicCheck.class)
    @DataApi(OUT_STOCK)
    @WebSecurity
    public void outStock(@RequestBody @Valid OutStockApiParam apiParam) {
    }

    @ApiOperation("粮食出库结算信息API")
    @PostMapping("/lsckjs")
    @Validated(BasicCheck.class)
    @DataApi(OUT_STOCK_SETTLEMENT)
    @WebSecurity
    public void outStockSettlement(@RequestBody @Valid OutStockSettlementApiParam apiParam) {
    }

    @ApiOperation("倒仓信息API")
    @PostMapping("/dcxx")
    @Validated(BasicCheck.class)
    @DataApi(TRANSFER_BARN)
    @WebSecurity
    public void transferBarn(@RequestBody @Valid TransferBarnApiParam apiParam) {
    }

    @ApiOperation("粮食库存信息API")
    @PostMapping("/lskc")
    @Validated(BasicCheck.class)
    @DataApi(STOCK_GRAIN)
    @WebSecurity
    public void stockGrain(@RequestBody @Valid StockGrainApiParam apiParam) {
    }

    @ApiOperation("损溢单信息API")
    @PostMapping("/syd")
    @Validated(BasicCheck.class)
    @DataApi(LOSS)
    @WebSecurity
    public void loss(@RequestBody @Valid LossApiParam apiParam) {
    }

    @ApiOperation("性质转变单信息API")
    @PostMapping("/xzzbd")
    @Validated(BasicCheck.class)
    @DataApi(TRANSFER_NATURE)
    @WebSecurity
    public void transferNature(@RequestBody @Valid TransferNatureApiParam apiParam) {
    }

    @ApiOperation("账面库存信息API")
    @PostMapping("/zmkcxx")
    @Validated(BasicCheck.class)
    @DataApi(MONTH_STOCK)
    @WebSecurity
    public void monthStock(@RequestBody @Valid MonthStockApiParam apiParam) {
    }

    @ApiOperation("客户信息API")
    @PostMapping("/khxx")
    @Validated(BasicCheck.class)
    @DataApi(CUSTOMER)
    @WebSecurity
    public void customer(@RequestBody @Valid CustomerApiParam apiParam) {
    }
}
