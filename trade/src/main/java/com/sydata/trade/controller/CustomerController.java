package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.domain.Customer;
import com.sydata.trade.param.CustomerPageParam;
import com.sydata.trade.service.ICustomerService;
import com.sydata.trade.vo.CustomerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 粮油购销-客户Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "粮油购销-客户API")
@Validated
@RestController
@RequestMapping("/trade/customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CustomerVo> page(@RequestBody @Valid CustomerPageParam pageParam) {
        return customerService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public Customer detail(@RequestParam("id") String id) {
        return customerService.detail(id);
    }

}
