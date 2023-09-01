package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.layout.domain.TankRecord;
import com.sydata.reserve.layout.param.TankRecordPageParam;
import com.sydata.reserve.layout.param.TankRecordSaveParam;
import com.sydata.reserve.layout.param.TankRecordUpdateParam;
import com.sydata.reserve.layout.vo.TankRecordVo;


/**
 * 储备布局地理信息-油罐信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface ITankRecordService extends IService<TankRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<TankRecordVo> pages(TankRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    TankRecordVo detail(String id);


    Boolean save(TankRecordSaveParam tankRecordSaveParam);

    Boolean update(TankRecordUpdateParam tankRecordUpdateParam);

    Boolean updateStatus(String id, String status);
}