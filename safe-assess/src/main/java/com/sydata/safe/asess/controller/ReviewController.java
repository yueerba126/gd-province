package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.param.ReviewAllotParam;
import com.sydata.safe.asess.param.ReviewAssessParam;
import com.sydata.safe.asess.param.ReviewPageParam;
import com.sydata.safe.asess.service.IReviewIndexService;
import com.sydata.safe.asess.service.IReviewService;
import com.sydata.safe.asess.vo.ReviewGroupVo;
import com.sydata.safe.asess.vo.ReviewIndexTreeVo;
import com.sydata.safe.asess.vo.ReviewVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;

/**
 * @author lzq
 * @description 粮食安全考核-考核评审API
 * @date 2023/4/3 16:41
 */
@Api(tags = "粮食安全考核-考核评审API")
@Validated
@RestController
@RequestMapping("/safe/assess/review")
public class ReviewController {

    @Resource
    private IReviewService reviewService;

    @Resource
    private IReviewIndexService reviewIndexService;

    @ApiOperation("分组分页列表")
    @PostMapping("/group/page")
    public Page<ReviewGroupVo> groupPage(@RequestBody ReviewPageParam pageParam) {
        return reviewService.groupPage(pageParam);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReviewVo> page(@RequestBody ReviewPageParam pageParam) {
        return reviewService.page(pageParam);
    }

    @ApiOperation("指标树形结构")
    @PostMapping("/index/tree")
    public List<ReviewIndexTreeVo> tree(@RequestParam("reviewId") String reviewId) {
        return reviewIndexService.treeByReviewId(reviewId);
    }

    @ApiOperation("分配")
    @PostMapping("/allot")
    public Boolean allot(@RequestBody @Valid ReviewAllotParam allotParam) {
        return reviewService.allot(allotParam);
    }

    @ApiOperation("考核")
    @PostMapping("/assess")
    public Boolean assess(@RequestBody @Valid ReviewAssessParam assessParam) {
        return reviewService.assess(assessParam);
    }

    @ApiOperation("退回")
    @PostMapping("/reset")
    public Boolean reset(@RequestParam("id") String id) {
        return reviewService.reset(id);
    }

    @ApiOperation("根据考核模板ID查询考核评审映射map<部门ID,map<考核模板指标ID,考核评审指标>>")
    @PostMapping("/index/map")
    public Map<Long, Map<String, ReviewIndex>> indexMap(@RequestParam("templateId") String templateId,
                                                        @RequestParam("assessRegionId") String assessRegionId) {
        return reviewService.indexMaps(templateId, singletonList(assessRegionId)).getOrDefault(assessRegionId, emptyMap());
    }

    @ApiOperation("根据考核模板ID查询考核评审映射map<地区ID,map<部门ID,map<考核模板指标ID,考核评审指标>>>")
    @PostMapping("/index/maps")
    public Map<String, Map<Long, Map<String, ReviewIndex>>> indexMaps(@RequestParam("templateId") String templateId,
                                                                      @RequestBody @Valid List<String> assessRegionIds) {
        return reviewService.indexMaps(templateId, assessRegionIds);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("deptId") Long deptId) {
        return reviewService.operationAuth(deptId);
    }
}
