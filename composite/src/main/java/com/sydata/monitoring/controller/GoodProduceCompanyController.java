package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IGoodProduceCompanyService;
import com.sydata.monitoring.vo.GoodProduceCompanyVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/放心粮油企业 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/25
 */
@Api(tags = "流通检测-放心粮油企业")
@RestController
@RequestMapping("/monitoring/good/produce/company")
public class GoodProduceCompanyController{
    @Resource
    private IGoodProduceCompanyService iGoodProduceCompanyService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public Page<GoodProduceCompanyVO> list(@RequestBody GoodProduceCompanyQueryDTO queryDTO){
        return iGoodProduceCompanyService.pagination(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public GoodProduceCompanyVO detailById(@RequestParam(value = "id") String id){
        return iGoodProduceCompanyService.detailById(id);
    }

    @ApiOperation(value = "审核")
    @PostMapping(value = "/audit")
    public Boolean audit(@RequestBody @Validated GoodProduceCompanyAuditDTO auditDTO){
        return iGoodProduceCompanyService.audit(auditDTO);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated GoodProduceCompanyEditDTO editDTO){
        return iGoodProduceCompanyService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated GoodProduceCompanyAddDTO addDTO){
        return iGoodProduceCompanyService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated GoodProduceCompanyDeleteDTO deleteDTO){
        return iGoodProduceCompanyService.delete(deleteDTO);
    }

}
