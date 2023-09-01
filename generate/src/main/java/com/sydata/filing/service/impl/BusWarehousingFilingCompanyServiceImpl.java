package com.sydata.filing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import com.sydata.filing.param.WarehousingFilingCompanyPageParam;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
import com.sydata.filing.mapper.WarehousingFilingCompanyMapper;
import com.sydata.filing.service.IWarehousingFilingCompanyService;
import com.sydata.filing.service.IBusWarehousingFilingCompanyService;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储企业业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingCompanyService")
public class BusWarehousingFilingCompanyServiceImpl extends ServiceImpl<WarehousingFilingCompanyMapper, WarehousingFilingCompany> implements IBusWarehousingFilingCompanyService {

    @Override
    public void beforeReturnPage(Page<WarehousingFilingCompany> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingCompanyVo warehousingFilingCompanyVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingCompanyVo>  warehousingFilingCompanyVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingCompany  warehousingFilingCompany){};

    @Override
    public void beforeDoUpdate(WarehousingFilingCompany  warehousingFilingCompany){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}