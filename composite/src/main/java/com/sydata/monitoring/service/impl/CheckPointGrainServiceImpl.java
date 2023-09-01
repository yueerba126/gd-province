package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.entity.CheckPointGrain;
import com.sydata.monitoring.mapper.CheckPointGrainMapper;
import com.sydata.monitoring.service.ICheckPointGrainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流通检测-监测点粮食品种关联表 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class CheckPointGrainServiceImpl extends ServiceImpl<CheckPointGrainMapper, CheckPointGrain> implements ICheckPointGrainService {

}
