package com.sydata.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.trade.domain.MonthStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 粮油购销-账面库存Mapper接口
 *
 * @author lzq
 * @date 2022-07-22
 */
public interface MonthStockMapper extends BaseMapper<MonthStock> {

    /**
     * 通过指定字段查询
     *
     * @param queryColumn    查询字段
     * @param dataBindQuerys 查询值
     * @return 实体列表
     */
    List<MonthStock> selectByQueryColumns(@Param("queryColumn") Object queryColumn,
                                          @Param("dataBindQuerys") List<DataBindQuery> dataBindQuerys);
}