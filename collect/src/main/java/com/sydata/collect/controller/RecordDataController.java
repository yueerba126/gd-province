package com.sydata.collect.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.record.FumigationApiParam;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sydata.collect.api.enums.DataApiEnum.FUMIGATION;

/**
 * @author lzq
 * @description 数据收集-备案管理API
 * @date 2022/12/10 10:54
 */
@Api(tags = "数据收集-备案管理API")
@RestController
@Validated
@RequestMapping("/collect/record/data")
public class RecordDataController {

    @ApiOperation("熏蒸备案")
    @PostMapping("/fumigation")
    @Validated(BasicCheck.class)
    @DataApi(value = FUMIGATION, isDataHandle = false)
    public void fumigation(@RequestBody @Valid FumigationApiParam apiParam) {
    }
}
