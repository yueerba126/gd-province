package com.sydata.collect.controller;

import com.sydata.collect.service.IDataApiService;
import com.sydata.collect.vo.DataApiColumnsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lzq
 * @description 数据收集-数据API
 * @date 2023/4/27 16:23
 */
@Api(tags = "数据收集-数据API")
@RestController
@Validated
@RequestMapping("/collect/data/api")
public class DataApiCollect {

    @Resource
    private IDataApiService dataApiService;

    @ApiOperation("获取接口字段信息")
    @PostMapping("/columns")
    public List<DataApiColumnsVo> columns(@RequestParam("apiCode") String apiCode) {
        return dataApiService.columns(apiCode);
    }
}
