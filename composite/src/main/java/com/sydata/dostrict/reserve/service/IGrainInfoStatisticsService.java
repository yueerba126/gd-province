package com.sydata.dostrict.reserve.service;

import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo;
import com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo;
import com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-粮油信息统计 Service接口
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
public interface IGrainInfoStatisticsService {
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按物料分组的库存的统计信息
     * @Date 16:14 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.GrainMaterialStatisticsVo>
     **/
    List<GrainMaterialStatisticsVo> listGrainMaterialStatisticsVo(@RequestBody @Valid GrainSelectParam param);
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按收获年度分组的库存的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.GrainHarvestStatisticsVo>
     **/
    List<GrainHarvestStatisticsVo> listGrainHarvestStatisticsVos(@RequestBody @Valid GrainSelectParam param);
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取按业务日期、物料分组的入库单物料价格的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.MaterialPriceTrendStatisticsVo>
     **/
    List<MaterialPriceTrendStatisticsVo> listMaterialPriceTrendStatisticsVos(@RequestBody @Valid GrainSelectParam param);
}
