package com.sydata.collect.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.basis.CompanyApiParam;
import com.sydata.collect.api.param.basis.CompanyCreditApiParam;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sydata.collect.api.enums.DataApiEnum.COMPANY;
import static com.sydata.collect.api.enums.DataApiEnum.COMPANY_CREDIT;

/**
 * @author lzq
 * @description 数据收集-基础数据API
 * @date 2022/10/19 11:06
 */
@Api(tags = "数据收集-基础数据API")
@RestController
@Validated
@RequestMapping("/collect/basis/data")
public class BasisDataController {

    @ApiOperation("单位信息")
    @PostMapping("/company")
    @Validated(BasicCheck.class)
    @DataApi(COMPANY)
    public void company(@RequestBody @Valid CompanyApiParam apiParam) {
    }

    @ApiOperation("企业信用信息")
    @PostMapping("/company/credit")
    @Validated(BasicCheck.class)
    @DataApi(COMPANY_CREDIT)
    public void companyCredit(@RequestBody @Valid CompanyCreditApiParam apiParam) {
    }
}
