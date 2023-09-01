package com.sydata.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.trade.domain.StockGrain;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author chenzx
 * @Date 2022/8/18 18:25
 * @Description:
 * @Version 1.0
 */
public interface StockGrainMapper extends BaseMapper<StockGrain> {

    /**
     * 获取库存最后更新时间
     *
     * @param hwdm   货位代码
     * @param lspzdm 粮食品种代码
     * @param rcsj   入仓时间
     * @return 库存最后更新时间
     */
    LocalDateTime lastUpdateTime(@Param("hwdm") String hwdm,
                                 @Param("lspzdm") String lspzdm,
                                 @Param("rcsj") LocalDateTime rcsj);


    /**
     * 数据质量校验
     *
     * @param ids 主键列表
     * @return 校验不通过的库存ID
     */
    List<String> qualityCheck(@Param("ids") Collection<String> ids);
}
