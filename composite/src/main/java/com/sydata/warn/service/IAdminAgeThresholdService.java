package com.sydata.warn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.warn.domain.AdminAgeThreshold;
import com.sydata.warn.param.AdminAgeThresholdPageParam;
import com.sydata.warn.vo.AdminAgeThresholdVo;

import java.util.List;

/**
 * 库存年限告警阈值设置Service接口
 *
 * @author fuql
 * @date 2023-05-09
 */
public interface IAdminAgeThresholdService extends IService<AdminAgeThreshold> {

    /**
     * 库存年限告警阈值设置新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(AdminAgeThreshold param);
    
    /**
     * 库存年限告警阈值设置page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<AdminAgeThresholdVo> pageData(AdminAgeThresholdPageParam param);

    /**
     * 库存年限告警阈值设置修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(AdminAgeThreshold param);

    /**
     * 库存年限告警阈值设置删除
     *
     * @param ids param
     * @return 人员制度管理id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取库存年限告警阈值设置详情
     *
     * @param id id
     * @return 人员制度管理
     */
    AdminAgeThresholdVo getDataById(Long id);
}
