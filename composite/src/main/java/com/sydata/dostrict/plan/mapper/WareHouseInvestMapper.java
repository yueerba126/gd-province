/**
 * @filename:AdminPlanBeanDao 2023年04月24日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.vo.StockHouseVo;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.dostrict.plan.param.WarehousInvestManageSelectParam;

import java.util.List;

/**   
 * @Description:TODO(规划建设-仓储设施投资管理数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface WareHouseInvestMapper extends BaseMapper<ApparitorPlan> {
    /**
     * 根据行政区划和库点代码查询库点信息
     * @param param
     * @return
     */
    List<StockHouseVo> queryStockHouseList(WarehousInvestManageSelectParam param);
    /**
     * 根据行政区划和库点代码查询廒间数量
     * @param param
     * @return
     */
    Integer queryGranaryCount(WarehousInvestManageSelectParam param);
    /**
     * 根据行政区划和库点代码查询货位数量
     * @param param
     * @return
     */
    Integer queryCargoCount(WarehousInvestManageSelectParam param);
    /**
     * 根据行政区划和库点代码查询设备数量
     * @param param
     * @return
     */
    Integer queryDeviceCount(WarehousInvestManageSelectParam param);
}
