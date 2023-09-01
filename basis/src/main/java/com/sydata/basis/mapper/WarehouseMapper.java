package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.domain.Warehouse;
import com.sydata.basis.dto.WarehouseQualityCheckDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 基础信息-仓房信息Mapper接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    /**
     * 数据质量校验
     *
     * @param ids 主键列表
     * @return 数据质量校验列表
     */
    List<WarehouseQualityCheckDto> qualityCheck(@Param("ids") Collection<String> ids);
}