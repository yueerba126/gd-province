package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.param.FoodOwnerPageParam;
import com.sydata.organize.service.IFoodOwnerService;
import com.sydata.organize.vo.FoodOwnerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lzq
 * @description 组织架构-粮权关系维护API
 * @date 2022/10/21 19:41
 */
@Api(tags = "组织架构-粮权关系维护API")
@Validated
@RestController
@RequestMapping("/org/food/owner")
public class FoodOwnerController {

    @Resource
    private IFoodOwnerService foodOwnerService;


    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<FoodOwnerVo> page(@RequestBody @Valid FoodOwnerPageParam pageParam) {
        return foodOwnerService.pages(pageParam);
    }

    @ApiOperation("批量删除")
    @PostMapping("/remove")
    public boolean removeByIds(@NotEmpty(message = "id不能为空") @RequestBody List<String> ids) {
        return foodOwnerService.removeByIds(ids);
    }
}
