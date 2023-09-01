package com.sydata.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sydata.trade.domain.InStockSettlement;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 入库结算信息表 Mapper 接口
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@Mapper
public interface InStockSettlementMapper extends BaseMapper<InStockSettlement> {

}
