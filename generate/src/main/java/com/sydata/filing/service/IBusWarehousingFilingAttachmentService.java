package com.sydata.filing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.param.WarehousingFilingAttachmentPageParam;
import com.sydata.filing.vo.WarehousingFilingAttachmentVo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储附件业务服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IBusWarehousingFilingAttachmentService extends IService<WarehousingFilingAttachment> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<WarehousingFilingAttachment> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(WarehousingFilingAttachmentVo warehousingFilingAttachmentVo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<WarehousingFilingAttachmentVo>  warehousingFilingAttachmentVoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(WarehousingFilingAttachment  warehousingFilingAttachment);

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(WarehousingFilingAttachment  warehousingFilingAttachment);

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}