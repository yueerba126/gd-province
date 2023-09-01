package com.sydata.monitoring.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IGoodCompanyService;
import com.sydata.monitoring.vo.GoodCompanyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/好粮油企业 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/25
 */
@Api(tags = "流通检测-好粮油企业")
@RestController
@RequestMapping("/monitoring/good/company")
public class GoodCompanyController  {
    @Resource
    private IGoodCompanyService iGoodCompanyService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public Page<GoodCompanyVO> page(@RequestBody GoodCompanyQueryDTO queryDTO){
        return iGoodCompanyService.pagination(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public GoodCompanyVO detail(@RequestParam(value = "id") String id){
        return iGoodCompanyService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated GoodCompanyEditDTO editDTO){
        return iGoodCompanyService.edit(editDTO);
    }

    @ApiOperation(value = "审核")
    @PostMapping(value = "/audit")
    public Boolean audit(@RequestBody @Validated GoodCompanyAuditDTO auditDTO){
        return iGoodCompanyService.aduit(auditDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated GoodCompanyAddDTO addDTO){
        return iGoodCompanyService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated GoodCompanyDeleteDTO deleteDTO){
        return iGoodCompanyService.delete(deleteDTO);
    }

}
