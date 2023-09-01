package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.dto.StockHouseQualityCheckDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 基础信息-库区信息Mapper接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface StockHouseMapper extends BaseMapper<StockHouse> {

    /**
     * 数据质量校验
     *
     * @param ids 主键列表
     * @return 数据质量校验列表
     */
    List<StockHouseQualityCheckDto> qualityCheck(@Param("ids") Collection<String> ids);
}