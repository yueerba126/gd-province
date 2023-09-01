package com.sydata.filing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingDevice;
import com.sydata.filing.param.WarehousingFilingDeviceSaveParam;
import com.sydata.filing.param.WarehousingFilingDevicePageParam;
import com.sydata.filing.vo.WarehousingFilingDeviceVo;
import com.sydata.filing.mapper.WarehousingFilingDeviceMapper;
import com.sydata.filing.service.IWarehousingFilingDeviceService;
import com.sydata.filing.service.IBusWarehousingFilingDeviceService;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储设备业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingDeviceService")
public class BusWarehousingFilingDeviceServiceImpl extends ServiceImpl<WarehousingFilingDeviceMapper, WarehousingFilingDevice> implements IBusWarehousingFilingDeviceService {

    @Override
    public void beforeReturnPage(Page<WarehousingFilingDevice> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingDeviceVo warehousingFilingDeviceVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingDeviceVo>  warehousingFilingDeviceVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingDevice  warehousingFilingDevice){};

    @Override
    public void beforeDoUpdate(WarehousingFilingDevice  warehousingFilingDevice){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}