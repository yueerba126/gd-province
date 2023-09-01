package com.sydata.warn.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.personnel.vo.ApparitorCultureVo;
import com.sydata.warn.param.WarnFoodstuffMessagePageParam;
import com.sydata.warn.param.WarnFoodstuffMessageUpdateParam;
import com.sydata.warn.service.IWarnFoodstuffMessageService;
import com.sydata.warn.vo.WarnFoodstuffMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 库存数量-粮食库存异常告警Controller
 *
 * @author fuql
 * @date 2023-04-28
 */
@Api(tags = "库存数量-粮食库存异常告警")
@Validated
@RestController
@RequestMapping("/warn/message")
public class WarnFoodstuffMessageController {

    @Resource
    private IWarnFoodstuffMessageService warnFoodstuffMessageService;

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<WarnFoodstuffMessageVo> list(@RequestBody @Valid WarnFoodstuffMessagePageParam param) {
        return warnFoodstuffMessageService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid WarnFoodstuffMessageUpdateParam param) {
        return warnFoodstuffMessageService.updateData(param);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public WarnFoodstuffMessageVo getInfo(@RequestParam("id") Long id) {
        return warnFoodstuffMessageService.getDataById(id);
    }

    @ApiOperation("立即执行-去检查当前组织的粮食库存异常")
    @PostMapping(value = "/execute")
    public boolean execute() {
        return warnFoodstuffMessageService.execute();
    }

}
