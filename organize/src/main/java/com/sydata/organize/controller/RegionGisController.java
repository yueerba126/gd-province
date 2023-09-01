package com.sydata.organize.controller;


import com.sydata.organize.service.RegionGisService;
import com.sydata.organize.vo.RegionGisVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 地图穿透数据
 * </p>
 *
 * @author xy
 * @since 2022-12-30
 */
@Api(tags = "组织架构-地图穿透数据")
@Validated
@RestController
@RequestMapping("/org/region/gis")
public class RegionGisController {

    @Resource
    private RegionGisService regionGisService;

    @ApiOperation("根据区域id获取穿透数据")
    @PostMapping("/geo/json")
    public RegionGisVo geoJson(@RequestParam("regionId") String regionId) {
        return regionGisService.geoJson(regionId);
    }
}
