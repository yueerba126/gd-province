package com.sydata.filing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingStock;
import com.sydata.filing.param.WarehousingFilingStockSaveParam;
import com.sydata.filing.param.WarehousingFilingStockPageParam;
import com.sydata.filing.vo.WarehousingFilingStockVo;
import com.sydata.filing.mapper.WarehousingFilingStockMapper;
import com.sydata.filing.service.IWarehousingFilingStockService;
import com.sydata.filing.service.IBusWarehousingFilingStockService;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储库点业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingStockService")
public class BusWarehousingFilingStockServiceImpl extends ServiceImpl<WarehousingFilingStockMapper, WarehousingFilingStock> implements IBusWarehousingFilingStockService {

    @Override
    public void beforeReturnPage(Page<WarehousingFilingStock> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingStockVo warehousingFilingStockVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingStockVo>  warehousingFilingStockVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingStock  warehousingFilingStock){};

    @Override
    public void beforeDoUpdate(WarehousingFilingStock  warehousingFilingStock){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}