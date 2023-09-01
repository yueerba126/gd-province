package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.reserve.layout.domain.WarehouseRecord;
import com.sydata.reserve.layout.param.WarehouseRecordPageParam;
import com.sydata.reserve.layout.param.WarehouseRecordSaveParam;
import com.sydata.reserve.layout.param.WarehouseRecordUpdateParam;
import com.sydata.reserve.layout.vo.WarehouseRecordVo;

import java.util.Collection;


/**
 * 储备布局地理信息-仓房信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IWarehouseRecordService extends IService<WarehouseRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<WarehouseRecordVo> pages(WarehouseRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    WarehouseRecordVo detail(String id);


    Boolean save(WarehouseRecordSaveParam warehouseRecordSaveParam);

    Boolean update(WarehouseRecordUpdateParam warehouseRecordUpdateParam);

    Boolean updateStatus(String id, String status);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}