package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.CompanyStaff;
import com.sydata.basis.domain.Device;
import com.sydata.basis.mapper.DeviceMapper;
import com.sydata.basis.param.DevicePageParam;
import com.sydata.basis.service.IDeviceService;
import com.sydata.basis.vo.DeviceVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.DeviceApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindDevice;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.report.api.param.basis.DeviceReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.DEVICE;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-设备信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = DeviceServiceImpl.CACHE_NAME)
@Service("deviceService")
public class DeviceServiceImpl extends BaseDataImpl<DeviceApiParam, DeviceMapper, Device, DeviceReportParam>
        implements IDeviceService {

    final static String CACHE_NAME = "basis:device";

    @Resource
    private DeviceMapper deviceMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Device getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Device entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Device entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Device entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<DeviceVo> pages(DevicePageParam pageParam) {
        Page<Device> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Device::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Device::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), Device::getStockHouseId, pageParam.getStockHouseId())
                .eq(isNotEmpty(pageParam.getSbbh()), Device::getSbbh, pageParam.getSbbh())
                .eq(isNotEmpty(pageParam.getSbyqmc()), Device::getSbyqmc, pageParam.getSbyqmc())
                .eq(isNotEmpty(pageParam.getSbyqdm()), Device::getSbyqdm, pageParam.getSbyqdm())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Device::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Device::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Device::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, DeviceVo.class);
    }

    @DataBindFieldConvert
    @Override
    public DeviceVo detail(String id) {
        DeviceServiceImpl deviceService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(deviceService.getById(id), DeviceVo.class);
    }

    @DataBindService(strategy = DataBindDevice.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, deviceMapper);
    }

    @Override
    public DataApiEnum api() {
        return DEVICE;
    }
}