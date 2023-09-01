package com.sydata.dostrict.reserve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.composite.annotation.DataBindApparitorReserveScale;
import com.sydata.dostrict.reserve.domain.ApparitorReserveScale;
import com.sydata.dostrict.reserve.mapper.ApparitorReserveScaleMapper;
import com.sydata.dostrict.reserve.param.ApparitorReserveScalePageParam;
import com.sydata.dostrict.reserve.param.ApparitorReserveScaleSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorReserveScaleService;
import com.sydata.dostrict.reserve.vo.ApparitorReserveScaleVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-储备规模Service业务层处理
 * @author: lzq
 * @create: 2023-04-26 18:47
 */
@CacheConfig(cacheNames = ApparitorReserveScaleServiceImpl.CACHE_NAME)
@Service("apparitorReserveScaleServiceImpl")
public class ApparitorReserveScaleServiceImpl
        extends ServiceImpl<ApparitorReserveScaleMapper, ApparitorReserveScale>
        implements IApparitorReserveScaleService {

    final static String CACHE_NAME = "composite:apparitorreserveScale";

    @Resource
    private ApparitorReserveScaleMapper apparitorReserveScaleMapper;

    @DataBindFieldConvert
    @Override
    public Page<ApparitorReserveScaleVo> pages(ApparitorReserveScalePageParam pageParam) {
        Page<ApparitorReserveScale> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ApparitorReserveScale::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getCbjhId()), ApparitorReserveScale::getCbjhId, pageParam.getCbjhId())
                .likeRight(isNotEmpty(pageParam.getCbgmjhwh()), ApparitorReserveScale::getCbgmjhwh, pageParam.getCbgmjhwh())
                .eq(isNotEmpty(pageParam.getYlpz()), ApparitorReserveScale::getYlpz, pageParam.getYlpz())
                .eq(isNotEmpty(pageParam.getYlxz()), ApparitorReserveScale::getYlxz, pageParam.getYlxz())
                .eq(isNotEmpty(pageParam.getNf()), ApparitorReserveScale::getNf, pageParam.getNf())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ApparitorReserveScale::getZhgxsj, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ApparitorReserveScale::getZhgxsj, pageParam.getEndUpdateTime())
                .ne(ApparitorReserveScale::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorReserveScale::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorReserveScaleVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorReserveScaleVo detail(String id) {
        IApparitorReserveScaleService reserveScaleService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(reserveScaleService.getById(id), ApparitorReserveScaleVo.class);
    }

    @DataBindService(strategy = DataBindApparitorReserveScale.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorReserveScaleMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorReserveScaleSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorReserveScale apparitorReserveScale = BeanUtils.copyByClass(param, ApparitorReserveScale.class);
        apparitorReserveScale.setId(IdUtil.simpleUUID());
        apparitorReserveScale.setCbjhId(param.getCbjhId());
        apparitorReserveScale.setCzbz(CzBzEnum.I.getCzBz());
        apparitorReserveScale.setCreateBy(loginUser.getName());
        apparitorReserveScale.setUpdateBy(loginUser.getName());
        apparitorReserveScale.setUpdateTime(LocalDateTime.now());
        apparitorReserveScale.setCreateTime(LocalDateTime.now());
        apparitorReserveScale.setZhgxsj(LocalDateTime.now());
        super.save(apparitorReserveScale);
        param.setId(apparitorReserveScale.getId());
        return apparitorReserveScale.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorReserveScaleSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorReserveScale apparitorReserveScale = BeanUtils.copyByClass(param, ApparitorReserveScale.class);
        apparitorReserveScale.setCzbz(CzBzEnum.U.getCzBz());
        apparitorReserveScale.setUpdateBy(loginUser.getName());
        apparitorReserveScale.setUpdateTime(LocalDateTime.now());
        apparitorReserveScale.setZhgxsj(LocalDateTime.now());
        super.updateById(apparitorReserveScale);
        param.setId(apparitorReserveScale.getId());
        return apparitorReserveScale.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorReserveScale::getId, ids)
                .set(ApparitorReserveScale::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorReserveScale::getUpdateBy ,loginUser.getName())
                .set(ApparitorReserveScale::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorReserveScale::getZhgxsj ,LocalDateTime.now())
                .update();
    }
}