package com.sydata.filing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingPersonnel;
import com.sydata.filing.param.WarehousingFilingPersonnelSaveParam;
import com.sydata.filing.param.WarehousingFilingPersonnelPageParam;
import com.sydata.filing.vo.WarehousingFilingPersonnelVo;
import com.sydata.filing.mapper.WarehousingFilingPersonnelMapper;
import com.sydata.filing.service.IWarehousingFilingPersonnelService;
import com.sydata.filing.service.IBusWarehousingFilingPersonnelService;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储人员业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingPersonnelService")
public class BusWarehousingFilingPersonnelServiceImpl extends ServiceImpl<WarehousingFilingPersonnelMapper, WarehousingFilingPersonnel> implements IBusWarehousingFilingPersonnelService {

    @Override
    public void beforeReturnPage(Page<WarehousingFilingPersonnel> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingPersonnelVo warehousingFilingPersonnelVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingPersonnelVo>  warehousingFilingPersonnelVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingPersonnel  warehousingFilingPersonnel){};

    @Override
    public void beforeDoUpdate(WarehousingFilingPersonnel  warehousingFilingPersonnel){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}