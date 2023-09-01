package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.entity.CheckPointPrice;
import com.sydata.monitoring.mapper.CheckPointPriceMapper;
import com.sydata.monitoring.service.ICheckPointPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流通检测-监测点价格关联表 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class CheckPointPriceServiceImpl extends ServiceImpl<CheckPointPriceMapper, CheckPointPrice> implements ICheckPointPriceService {

}
