package com.sydata.reserve.layout.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.TestingInstitutes;
import com.sydata.reserve.layout.mapper.TestingInstitutesMapper;
import com.sydata.reserve.layout.param.TestingInstitutesPageParam;
import com.sydata.reserve.layout.param.TestingInstitutesSaveParam;
import com.sydata.reserve.layout.param.TestingInstitutesUpdateParam;
import com.sydata.reserve.layout.service.ITestingInstitutesService;
import com.sydata.reserve.layout.vo.TestingInstitutesVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-质检机构Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = TestingInstitutesServiceImpl.CACHE_NAME)
@Service("testingInstitutesService")
public class TestingInstitutesServiceImpl
        extends ServiceImpl<TestingInstitutesMapper, TestingInstitutes>
        implements ITestingInstitutesService {

    final static String CACHE_NAME = "reserveLayout:TestingInstitutes";


    @Override
    public boolean removeById(TestingInstitutes entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<TestingInstitutesVo> pages(TestingInstitutesPageParam pageParam) {
        Page<TestingInstitutes> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getZjjgmc()), TestingInstitutes::getZjjgmc, pageParam.getZjjgmc())
                .orderByDesc(TestingInstitutes::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TestingInstitutesVo.class);
    }



    @DataBindFieldConvert
    @Override
    public TestingInstitutesVo detail(String id) {
        ITestingInstitutesService TestingInstitutesService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(TestingInstitutesService.getById(id), TestingInstitutesVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(TestingInstitutesSaveParam TestingInstitutesSaveParam) {
        TestingInstitutes testingInstitutes = BeanUtils.copyByClass(TestingInstitutesSaveParam, TestingInstitutes.class);
        return super.save(testingInstitutes);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(TestingInstitutesUpdateParam TestingInstitutesUpdateParam) {
        TestingInstitutes testingInstitutes = this.getById(TestingInstitutesUpdateParam.getId());
        Assert.notNull(testingInstitutes, "储备布局地理信息-质检机构不存在");
        return super.updateById(BeanUtils.copyByClass(TestingInstitutesUpdateParam, TestingInstitutes.class));
    }

    @Override
    public Boolean updateStatus(String id, String sfqy) {
        TestingInstitutes testingInstitutes = this.getById(id);
        Assert.notNull(testingInstitutes, "储备布局地理信息-质检机构不存在");
        return super.updateById(testingInstitutes.setSfqy(sfqy));
    }


}