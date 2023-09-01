package com.sydata.chart.service;

import com.sydata.chart.vo.*;

/**
 * 卡片信息-接口
 *
 * @author lzq
 * @date 2022-07-26
 */
public interface IChartService {

    /**
     * 质检报告卡片信息
     *
     * @param id 质检数据唯一id
     * @return 质检报告卡片信息
     */
    QualityCheckChartVo qualityCheckChart(String id);

    /**
     * 入库检斤质检结算单卡片信息
     *
     * @param id 入库信息唯一id
     * @return
     */
    InStockCheckChartVo inStockCheckChart(String id);


    /**
     * 出库检斤质检结算单卡片信息
     *
     * @param id 出库信息唯一id
     * @return
     */
    OutStockCheckChartVo outStockCheckChart(String id);


    /**
     * 货位卡片信息
     * @param id 货位id-货位代码
     * @return
     */
    CargoChartVo cargoChart(String id);



    /**
     * 损溢单卡片信息
     *
     * @param id 损溢单id
     * @return
     */
    LossChartVo lossChart(String id);


    /**
     * 性质转变单卡片信息
     *
     * @param id 性质转变单id
     * @return
     */
    TransferNatureChartVo transferNatureChar(String id);


    /**
     * 通风作业卡片信息
     *
     * @param id 通风作业id
     * @return
     */
    VentilationChartVo ventilationChart(String id);


    /**
     * 熏蒸作业卡片信息
     *
     * @param id 熏蒸作业id
     * @return
     */
    SteamTaskChartVo steamTaskChart(String id);
}
