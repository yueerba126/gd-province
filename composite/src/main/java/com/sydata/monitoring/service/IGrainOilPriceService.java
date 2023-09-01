package com.sydata.monitoring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.GrainOilPriceAddDto;
import com.sydata.monitoring.dto.GrainOilPriceBillQueryDto;
import com.sydata.monitoring.dto.GrainOilPriceQueryDto;
import com.sydata.monitoring.dto.GrainOilPriceRemoveDto;
import com.sydata.monitoring.entity.GrainOilPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.monitoring.vo.GrainOilPriceDtlVo;
import com.sydata.monitoring.vo.MonitoringInStockSettlementVo;
import com.sydata.monitoring.vo.MonitoringOutStockSettlementVo;

/**
 * <p>
 * 流通检测-粮油价格采集明主表 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
public interface IGrainOilPriceService extends IService<GrainOilPrice> {

    /**
     * 分页查询
     *
     * @param queryDto 查询参数
     * @return 结果
     */
    Page<GrainOilPriceDtlVo> detailPagination(GrainOilPriceQueryDto queryDto);

    /**
     * 新增
     *
     * @param addDto 新增参数
     * @return 结果
     */
    Boolean add(GrainOilPriceAddDto addDto);

    /**
     * 删除
     *
     * @param removeDto 参数
     * @return 结果
     */
    Boolean remove(GrainOilPriceRemoveDto removeDto);

    /**
     * 入库单查询
     *
     * @param queryDto 查询条件
     * @return 结果
     */
    Page<MonitoringInStockSettlementVo> inBillPage(GrainOilPriceBillQueryDto queryDto);

    /**
     * 出库单查询
     *
     * @param queryDto 查询条件
     * @return 结果
     */
    Page<MonitoringOutStockSettlementVo> outBillPage(GrainOilPriceBillQueryDto queryDto);
}
