package com.sydata.safe.asess.controller;

import com.sydata.safe.asess.domain.TemplateIndex;
import com.sydata.safe.asess.param.IndexRemoveParam;
import com.sydata.safe.asess.service.ITemplateIndexService;
import com.sydata.safe.asess.vo.TemplateIndexTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzq
 * @description 粮食安全考核-考核模板指标API
 * @date 2023/2/13 15:11
 */
@Api(tags = "粮食安全考核-考核模板指标API")
@Validated
@RestController
@RequestMapping("/safe/assess/template/index")
public class TemplateIndexController {

    @Resource
    private ITemplateIndexService templateIndexService;

    @ApiOperation("树形结构")
    @PostMapping("/tree")
    public List<TemplateIndexTreeVo> tree(@RequestParam("templateId") String templateId) {
        return templateIndexService.treeByTemplateId(templateId);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public Boolean add(@RequestBody @Valid TemplateIndex templateIndex) {
        return templateIndexService.save(templateIndex);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid TemplateIndex templateIndex) {
        return templateIndexService.updateById(templateIndex);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid IndexRemoveParam removeParam) {
        return templateIndexService.remove(removeParam);
    }
}
