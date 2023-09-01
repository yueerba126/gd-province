package com.sydata.monitoring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.GrainOilPriceDtlQueryDto;
import com.sydata.monitoring.entity.GrainOilPriceDtl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.monitoring.vo.GrainOilPriceDtlVo;

/**
 * <p>
 * 流通检测-粮油价格采集明细表 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
public interface IGrainOilPriceDtlService extends IService<GrainOilPriceDtl> {


    Page<GrainOilPriceDtlVo> pagination(GrainOilPriceDtlQueryDto dtlQueryDto);
}
