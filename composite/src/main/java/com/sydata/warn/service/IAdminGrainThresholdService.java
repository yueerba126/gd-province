package com.sydata.warn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.warn.domain.AdminGrainThreshold;

import java.util.List;

/**
 * 粮情预警阈值Service接口
 *
 * @author fuql
 * @date 2023-05-09
 */
public interface IAdminGrainThresholdService extends IService<AdminGrainThreshold> {

    /**
     * 粮情预警阈值设置新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(AdminGrainThreshold param);

    /**
     * 粮情预警阈值设置修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(AdminGrainThreshold param);

    /**
     * 粮情预警阈值设置删除
     *
     * @param ids param
     * @return 人员制度管理id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取粮情预警阈值设置详情
     *
     * @param id id
     * @return 人员制度管理
     */
    AdminGrainThreshold getDataById(Long id);

}
