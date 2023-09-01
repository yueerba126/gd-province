package com.sydata.filing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.filing.domain.WarehousingFilingStock;
import com.sydata.filing.param.WarehousingFilingStockSaveParam;
import com.sydata.filing.param.WarehousingFilingStockPageParam;
import com.sydata.filing.vo.WarehousingFilingStockVo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储库点业务服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IBusWarehousingFilingStockService extends IService<WarehousingFilingStock> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<WarehousingFilingStock> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(WarehousingFilingStockVo warehousingFilingStockVo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<WarehousingFilingStockVo>  warehousingFilingStockVoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(WarehousingFilingStock  warehousingFilingStock);

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(WarehousingFilingStock  warehousingFilingStock);

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}