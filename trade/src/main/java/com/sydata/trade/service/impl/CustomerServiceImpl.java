package com.sydata.trade.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.CustomerApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.trade.annotation.DataBindCustomer;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.CustomerReportParam;
import com.sydata.trade.domain.Customer;
import com.sydata.trade.mapper.CustomerMapper;
import com.sydata.trade.param.CustomerPageParam;
import com.sydata.trade.service.ICustomerService;
import com.sydata.trade.vo.CustomerVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-客户信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@Service("customerService")
@CacheConfig(cacheNames = CustomerServiceImpl.CACHE_NAME)
public class CustomerServiceImpl extends BaseDataImpl<CustomerApiParam, CustomerMapper, Customer, CustomerReportParam>
        implements ICustomerService {

    final static String CACHE_NAME = "trade:customer";

    @Resource
    private CustomerMapper customerMapper;


    @Cacheable(key = "'id='+#id")
    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Customer entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Customer entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Customer entity) {
        return super.removeById(entity);
    }


    @DataBindFieldConvert
    @Override
    public Page<CustomerVo> pages(CustomerPageParam pageParam) {
        Page<Customer> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Customer::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Customer::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getKhmc()), Customer::getKhmc, pageParam.getKhmc())
                .eq(isNotEmpty(pageParam.getKhtyshxydmhsfzh()), Customer::getKhtyshxydmhsfzh, pageParam.getKhtyshxydmhsfzh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Customer::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Customer::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Customer::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CustomerVo.class);
    }


    @DataBindFieldConvert
    @Override
    public CustomerVo detail(String id) {
        ICustomerService customerService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(customerService.getById(id), CustomerVo.class);
    }

    @DataBindService(strategy = DataBindCustomer.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, customerMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.CUSTOMER;
    }

}
