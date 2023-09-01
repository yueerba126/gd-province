package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.reserve.layout.domain.RranaryRecord;
import com.sydata.reserve.layout.param.RranaryRecordPageParam;
import com.sydata.reserve.layout.param.RranaryRecordSaveParam;
import com.sydata.reserve.layout.param.RranaryRecordUpdateParam;
import com.sydata.reserve.layout.vo.RranaryRecordVo;

import java.util.Collection;


/**
 * 储备布局地理信息-廒间信息备案Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IRranaryRecordService extends IService<RranaryRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<RranaryRecordVo> pages(RranaryRecordPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    RranaryRecordVo detail(String id);


    Boolean save(RranaryRecordSaveParam rranaryRecordSaveParam);

    Boolean update(RranaryRecordUpdateParam rranaryRecordUpdateParam);

    Boolean updateStatus(String id, String status);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}