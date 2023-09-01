package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.layout.domain.CargoRecord;
import com.sydata.reserve.layout.param.CargoRecordPageParam;
import com.sydata.reserve.layout.param.CargoRecordSaveParam;
import com.sydata.reserve.layout.param.CargoRecordUpdateParam;
import com.sydata.reserve.layout.vo.CargoRecordVo;


/**
 * 储备布局地理信息-货位信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface ICargoRecordService extends IService<CargoRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CargoRecordVo> pages(CargoRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CargoRecordVo detail(String id);


    Boolean save(CargoRecordSaveParam cargoRecordSaveParam);

    Boolean update(CargoRecordUpdateParam cargoRecordUpdateParam);

    Boolean updateStatus(String id, String status);
}