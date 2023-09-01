package com.sydata.safe.asess.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.param.ScoreAssessParam;
import com.sydata.safe.asess.param.ScorePageParam;
import com.sydata.safe.asess.service.IScoreIndexService;
import com.sydata.safe.asess.service.IScoreService;
import com.sydata.safe.asess.vo.ScoreGroupVo;
import com.sydata.safe.asess.vo.ScoreIndexTreeVo;
import com.sydata.safe.asess.vo.ScoreVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 粮食安全考核-考核评分API
 * @date 2023/4/3 16:41
 */
@Api(tags = "粮食安全考核-考核评分API")
@Validated
@RestController
@RequestMapping("/safe/assess/score")
public class ScoreController {

    @Resource
    private IScoreService scoreService;

    @Resource
    private IScoreIndexService scoreIndexService;

    @ApiOperation("分组分页列表")
    @PostMapping("/group/page")
    public Page<ScoreGroupVo> groupPage(@RequestBody ScorePageParam pageParam) {
        return scoreService.groupPage(pageParam);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ScoreVo> page(@RequestBody ScorePageParam pageParam) {
        return scoreService.page(pageParam);
    }

    @ApiOperation("指标树形结构")
    @PostMapping("/index/tree")
    public List<ScoreIndexTreeVo> tree(@RequestParam("scoreId") String scoreId) {
        return scoreIndexService.treeByScoreId(scoreId);
    }

    @ApiOperation("考核")
    @PostMapping("/assess")
    public boolean assess(@RequestBody @Valid ScoreAssessParam assessParam) {
        return scoreService.assess(assessParam);
    }

    @ApiOperation("退回")
    @PostMapping("/reset")
    public Boolean reset(@RequestParam("id") String id) {
        return scoreService.reset(id);
    }

    @ApiOperation("根据考核评审ID查询考核评分映射map<部门ID,map<考核模板指标ID,考核评分指标>>")
    @PostMapping("/index/map")
    public Map<Long, Map<String, ScoreIndex>> indexMap(@RequestParam("reviewId") String reviewId) {
        return scoreService.indexMap(reviewId);
    }

    @ApiOperation("操作权限")
    @PostMapping("/operation/auth")
    public Boolean operationAuth(@RequestParam("id") String id) {
        return scoreService.operationAuth(id);
    }
}
