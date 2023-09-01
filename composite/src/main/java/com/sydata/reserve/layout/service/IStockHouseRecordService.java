package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.reserve.layout.domain.StockHouseRecord;
import com.sydata.reserve.layout.param.StockHouseRecordPageParam;
import com.sydata.reserve.layout.param.StockHouseRecordSaveParam;
import com.sydata.reserve.layout.param.StockHouseRecordUpdateParam;
import com.sydata.reserve.layout.vo.StockHouseRecordVo;

import java.util.Collection;


/**
 * 储备布局地理信息-库区信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IStockHouseRecordService extends IService<StockHouseRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<StockHouseRecordVo> pages(StockHouseRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    StockHouseRecordVo detail(String id);


    Boolean save(StockHouseRecordSaveParam stockHouseRecordSaveParam);

    Boolean update(StockHouseRecordUpdateParam stockHouseRecordUpdateParam);

    Boolean updateStatus(String id, String status);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}