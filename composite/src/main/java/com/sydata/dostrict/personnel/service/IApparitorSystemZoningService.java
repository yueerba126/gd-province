package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.personnel.domain.ApparitorSystemZoning;

import java.util.List;

/**
 * 行政管理-人员制度管理管理行政区划Service接口
 *
 * @author fuql
 * @date 2023-04-24
 */
public interface IApparitorSystemZoningService extends IService<ApparitorSystemZoning> {

    /**
     * 新增行政管理-人员制度管理管理行政区划
     *
     * @param zoningList zoningList
     * @param id 人员制度管理id
     */
    void saveData(List<ApparitorSystemZoning> zoningList, String id);
}
