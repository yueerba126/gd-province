package com.sydata.filing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import com.sydata.filing.param.WarehousingFilingCompanyPageParam;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储企业业务服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IBusWarehousingFilingCompanyService extends IService<WarehousingFilingCompany> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<WarehousingFilingCompany> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(WarehousingFilingCompanyVo warehousingFilingCompanyVo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<WarehousingFilingCompanyVo>  warehousingFilingCompanyVoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(WarehousingFilingCompany  warehousingFilingCompany);

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(WarehousingFilingCompany  warehousingFilingCompany);

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}