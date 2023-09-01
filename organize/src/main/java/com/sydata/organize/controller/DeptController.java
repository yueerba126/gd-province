package com.sydata.organize.controller;

import com.sydata.organize.domain.Dept;
import com.sydata.organize.param.DeptRemoveParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IDeptService;
import com.sydata.organize.vo.DeptTreeVo;
import com.sydata.organize.vo.DeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 组织架构-部门Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-部门")
@Validated
@RestController
@RequestMapping("/org/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;


    @ApiOperation("根据组织ID查询部门树")
    @PostMapping("/tree/organize")
    public List<DeptTreeVo> treeByOrganizeId(@RequestParam(value = "organizeId", required = false) String organizeId) {
        return deptService.treeByOrganizeId(isNotEmpty(organizeId) ? organizeId : UserSecurity.organizeId());
    }

    @ApiOperation("查询当前人所属部门的部门树")
    @PostMapping("/tree/parents")
    public List<DeptTreeVo> treeByParentIds() {
        return deptService.treeByParentIds();
    }

    @ApiOperation("详情")
    @PostMapping("/get/id")
    public DeptVo details(@RequestParam("id") Long id) {
        return deptService.details(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid Dept dept) {
        return deptService.save(dept);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid Dept dept) {
        return deptService.updateById(dept);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid DeptRemoveParam removeParam) {
        return deptService.remove(removeParam);
    }
}
