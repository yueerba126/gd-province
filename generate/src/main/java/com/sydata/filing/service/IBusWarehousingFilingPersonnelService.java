package com.sydata.filing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.filing.domain.WarehousingFilingPersonnel;
import com.sydata.filing.param.WarehousingFilingPersonnelSaveParam;
import com.sydata.filing.param.WarehousingFilingPersonnelPageParam;
import com.sydata.filing.vo.WarehousingFilingPersonnelVo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储人员业务服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IBusWarehousingFilingPersonnelService extends IService<WarehousingFilingPersonnel> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<WarehousingFilingPersonnel> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(WarehousingFilingPersonnelVo warehousingFilingPersonnelVo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<WarehousingFilingPersonnelVo>  warehousingFilingPersonnelVoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(WarehousingFilingPersonnel  warehousingFilingPersonnel);

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(WarehousingFilingPersonnel  warehousingFilingPersonnel);

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}