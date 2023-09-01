package com.sydata.filing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.param.WarehousingFilingAttachmentPageParam;
import com.sydata.filing.vo.WarehousingFilingAttachmentVo;
import com.sydata.filing.mapper.WarehousingFilingAttachmentMapper;
import com.sydata.filing.service.IWarehousingFilingAttachmentService;
import com.sydata.filing.service.IBusWarehousingFilingAttachmentService;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储附件业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingAttachmentService")
public class BusWarehousingFilingAttachmentServiceImpl extends ServiceImpl<WarehousingFilingAttachmentMapper, WarehousingFilingAttachment> implements IBusWarehousingFilingAttachmentService {

    @Override
    public void beforeReturnPage(Page<WarehousingFilingAttachment> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingAttachmentVo warehousingFilingAttachmentVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingAttachmentVo>  warehousingFilingAttachmentVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingAttachment  warehousingFilingAttachment){};

    @Override
    public void beforeDoUpdate(WarehousingFilingAttachment  warehousingFilingAttachment){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}