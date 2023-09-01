package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.CargoRecord;
import com.sydata.reserve.layout.mapper.CargoRecordMapper;
import com.sydata.reserve.layout.param.CargoRecordPageParam;
import com.sydata.reserve.layout.param.CargoRecordSaveParam;
import com.sydata.reserve.layout.param.CargoRecordUpdateParam;
import com.sydata.reserve.layout.service.ICargoRecordService;
import com.sydata.reserve.layout.vo.CargoRecordVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-货位信息备案Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = CargoRecordServiceImpl.CACHE_NAME)
@Service("cargoRecordService")
public class CargoRecordServiceImpl
        extends ServiceImpl<CargoRecordMapper, CargoRecord>
        implements ICargoRecordService {

    final static String CACHE_NAME = "reserveLayout:CargoRecord";


    @Override
    public boolean removeById(CargoRecord entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<CargoRecordVo> pages(CargoRecordPageParam pageParam) {
        Page<CargoRecord> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getHwmc()), CargoRecord::getHwmc, pageParam.getHwmc())
                .eq(isNotEmpty(pageParam.getStatus()), CargoRecord::getStatus, pageParam.getStatus())
                .eq(isNotEmpty(pageParam.getDwdm()), CargoRecord::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getId()), CargoRecord::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getKqdm()), CargoRecord::getKqdm, pageParam.getKqdm())
                .eq(isNotEmpty(pageParam.getAjdh()), CargoRecord::getAjdm, pageParam.getAjdh())
                .orderByDesc(CargoRecord::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CargoRecordVo.class);
    }



    @DataBindFieldConvert
    @Override
    public CargoRecordVo detail(String id) {
        ICargoRecordService CargoRecordService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(CargoRecordService.getById(id), CargoRecordVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(CargoRecordSaveParam CargoRecordSaveParam) {
        CargoRecord CargoRecord = BeanUtils.copyByClass(CargoRecordSaveParam, CargoRecord.class);
        return super.save(CargoRecord.setId(CargoRecord.getHwdm()));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(CargoRecordUpdateParam CargoRecordUpdateParam) {
        CargoRecord cargoRecord = this.getById(CargoRecordUpdateParam.getId());
        Assert.notNull(cargoRecord, "储备布局地理信息-货位信息备案不存在");
        return super.updateById(BeanUtils.copyByClass(CargoRecordUpdateParam, CargoRecord.class));
    }

    @Override
    public Boolean updateStatus(String id, String status) {
        CargoRecord cargoRecord = this.getById(id);
        Assert.notNull(cargoRecord, "储备布局地理信息-货位信息备案不存在");
        return super.updateById(cargoRecord.setStatus(status));
    }


}