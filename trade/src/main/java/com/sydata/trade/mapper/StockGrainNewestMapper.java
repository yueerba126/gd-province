package com.sydata.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.trade.domain.StockGrainNewest;
import com.sydata.trade.vo.StockGrainStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author lzq
 * @description 粮油购销-粮食库存最新数据Mapper接口
 * @date 2023/4/21 10:28
 */
public interface StockGrainNewestMapper extends BaseMapper<StockGrainNewest> {

    /**
     * 统计库区库存统计
     *
     * @param stockHouseIds   库区ID列表
     * @param foodNatureCodes 粮食性质代码
     * @return 库区库存统计VO列表
     */
    List<StockGrainStatisticsVo> reserveStatistics(@Param("stockHouseIds") List<String> stockHouseIds,
                                                   @Param("foodNatureCodes") Set<String> foodNatureCodes);

    /**
     * 根据货位代码查询仓房代码
     * @param hwdm
     * @return
     */
    String getCfdm(@Param("hwdm") String hwdm);
}
