package com.sydata.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.record.domain.FumigationWay;

import java.util.List;

/**
 * @author lzq
 * @description 备案管理-熏蒸方式Service接口
 * @date 2022/12/10 12:22
 */
public interface IFumigationWayService extends IService<FumigationWay> {

    /**
     * 根据熏蒸备案ID查询方式列表
     *
     * @param fumigationId 熏蒸备案ID
     * @return 方式列表
     */
    List<FumigationWay> listByFumigationId(String fumigationId);

    /**
     * 设置熏蒸方式列表
     *
     * @param fumigationId 熏蒸备案ID
     * @param list         熏蒸方式列表
     */
    void build(String fumigationId, List<FumigationWay> list);
}
