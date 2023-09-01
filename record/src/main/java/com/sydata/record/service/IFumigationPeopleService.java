package com.sydata.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.record.domain.FumigationPeople;

import java.util.List;

/**
 * 备案管理-熏蒸人员Service接口
 *
 * @author system
 * @date 2022-12-10
 */
public interface IFumigationPeopleService extends IService<FumigationPeople> {

    /**
     * 根据熏蒸备案ID查询人员列表
     *
     * @param fumigationId 熏蒸备案ID
     * @return 人员列表
     */
    List<FumigationPeople> listByFumigationId(String fumigationId);

    /**
     * 设置熏蒸人员列表
     *
     * @param fumigationId 熏蒸备案ID
     * @param list         熏蒸人员列表
     */
    void build(String fumigationId, List<FumigationPeople> list);

}