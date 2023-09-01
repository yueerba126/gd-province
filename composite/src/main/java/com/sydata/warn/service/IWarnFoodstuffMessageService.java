package com.sydata.warn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.warn.domain.WarnFoodstuffMessage;
import com.sydata.warn.param.WarnFoodstuffMessagePageParam;
import com.sydata.warn.param.WarnFoodstuffMessageUpdateParam;
import com.sydata.warn.vo.WarnFoodstuffMessageVo;

/**
 * 库存数量-粮食库存异常告警Service接口
 *
 * @author fuql
 * @date 2023-04-28
 */
public interface IWarnFoodstuffMessageService extends IService<WarnFoodstuffMessage> {

    /**
     * 库存数量-粮食库存异常告警page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<WarnFoodstuffMessageVo> pageData(WarnFoodstuffMessagePageParam param);

    /**
     * 库存数量-粮食库存异常告警修改
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    String updateData(WarnFoodstuffMessageUpdateParam param);

    /**
     * 库存数量-粮食库存异常告警相信信息
     *
     * @param id id
     * @return 异常告警相信信息
     */
    WarnFoodstuffMessageVo getDataById(Long id);

    /**
     * 立即执行-去检查当前组织的粮食库存异常
     *
     * @return 操作状态
     */
    boolean execute();

}
