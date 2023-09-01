package com.sydata.filing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.filing.domain.WarehousingFilingDevice;
import com.sydata.filing.param.WarehousingFilingDeviceSaveParam;
import com.sydata.filing.param.WarehousingFilingDevicePageParam;
import com.sydata.filing.vo.WarehousingFilingDeviceVo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(仓储备案-仓储设备业务服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IBusWarehousingFilingDeviceService extends IService<WarehousingFilingDevice> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<WarehousingFilingDevice> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(WarehousingFilingDeviceVo warehousingFilingDeviceVo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<WarehousingFilingDeviceVo>  warehousingFilingDeviceVoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(WarehousingFilingDevice  warehousingFilingDevice);

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(WarehousingFilingDevice  warehousingFilingDevice);

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}