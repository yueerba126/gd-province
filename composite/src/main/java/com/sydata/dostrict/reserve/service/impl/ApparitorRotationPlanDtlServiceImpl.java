package com.sydata.dostrict.reserve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.composite.annotation.DataBindApparitorRotationPlanDtl;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlanDtl;
import com.sydata.dostrict.reserve.mapper.ApparitorRotationPlanDtlMapper;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlPageParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorRotationPlanDtlService;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanDtlVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-储备计划明细表Service业务层处理
 * @author: lzq
 * @create: 2023-04-26 18:47
 */
@CacheConfig(cacheNames = ApparitorRotationPlanDtlServiceImpl.CACHE_NAME)
@Service("apparitorRotationPlanDtlService")
public class ApparitorRotationPlanDtlServiceImpl
        extends ServiceImpl<ApparitorRotationPlanDtlMapper, ApparitorRotationPlanDtl>
        implements IApparitorRotationPlanDtlService {

    final static String CACHE_NAME = "composite:apparitorrotationPlanDtl";

    @Resource
    private ApparitorRotationPlanDtlMapper apparitorRotationPlanDtlMapper;

    @Override
    @DataBindFieldConvert
    public Page<ApparitorRotationPlanDtlVo> pages(ApparitorRotationPlanDtlPageParam pageParam) {
        Page<ApparitorRotationPlanDtl> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ApparitorRotationPlanDtl::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getLhjhdh()), ApparitorRotationPlanDtl::getLhjhdh, pageParam.getLhjhdh())
                .eq(isNotEmpty(pageParam.getJhmxdh()), ApparitorRotationPlanDtl::getJhmxdh, pageParam.getJhmxdh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ApparitorRotationPlanDtl::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ApparitorRotationPlanDtl::getUpdateTime, pageParam.getEndUpdateTime())
                .ne(ApparitorRotationPlanDtl::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorRotationPlanDtl::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorRotationPlanDtlVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorRotationPlanDtlVo detail(String id) {
        IApparitorRotationPlanDtlService rotationPlanDtlService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(rotationPlanDtlService.getById(id), ApparitorRotationPlanDtlVo.class);
    }

    @Override
    public String generate(String lhjhdh, String hwdm) {
        // 生成自增编码
        String key = new StringJoiner(COLON).add(CACHE_NAME).add(lhjhdh).add(hwdm.substring(0, 21)).toString();
        String jhmxdh = GenerateNoUtil.generate(key, 4);

        // 去掉拼接的属性
        return jhmxdh.substring(CACHE_NAME.length()).replace(COLON, EMPTY);
    }

    @DataBindService(strategy = DataBindApparitorRotationPlanDtl.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorRotationPlanDtlMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorRotationPlanDtlSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorRotationPlanDtl apparitorRotationPlanDtl = BeanUtils.copyByClass(param, ApparitorRotationPlanDtl.class);
        apparitorRotationPlanDtl.setId(IdUtil.simpleUUID());
        apparitorRotationPlanDtl.setJhmxdh(generate(param.getLhjhdh(),param.getLhhwdm()));
        apparitorRotationPlanDtl.setCzbz(CzBzEnum.I.getCzBz());
        apparitorRotationPlanDtl.setCreateBy(loginUser.getName());
        apparitorRotationPlanDtl.setUpdateBy(loginUser.getName());
        apparitorRotationPlanDtl.setUpdateTime(LocalDateTime.now());
        apparitorRotationPlanDtl.setCreateTime(LocalDateTime.now());
        apparitorRotationPlanDtl.setZhgxsj(LocalDateTime.now());
        super.save(apparitorRotationPlanDtl);
        param.setId(apparitorRotationPlanDtl.getId());
        return apparitorRotationPlanDtl.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorRotationPlanDtlSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorRotationPlanDtl apparitorRotationPlanDtl = BeanUtils.copyByClass(param, ApparitorRotationPlanDtl.class);
        apparitorRotationPlanDtl.setCzbz(CzBzEnum.U.getCzBz());
        apparitorRotationPlanDtl.setUpdateBy(loginUser.getName());
        apparitorRotationPlanDtl.setUpdateTime(LocalDateTime.now());
        apparitorRotationPlanDtl.setZhgxsj(LocalDateTime.now());
        super.updateById(apparitorRotationPlanDtl);
        param.setId(apparitorRotationPlanDtl.getId());
        return apparitorRotationPlanDtl.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorRotationPlanDtl::getId, ids)
                .set(ApparitorRotationPlanDtl::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorRotationPlanDtl::getUpdateBy ,loginUser.getName())
                .set(ApparitorRotationPlanDtl::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorRotationPlanDtl::getZhgxsj ,LocalDateTime.now())
                .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeDataNotInList(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .notIn(ApparitorRotationPlanDtl::getId, ids)
                .set(ApparitorRotationPlanDtl::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorRotationPlanDtl::getUpdateBy ,loginUser.getName())
                .set(ApparitorRotationPlanDtl::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorRotationPlanDtl::getZhgxsj ,LocalDateTime.now())
                .update();
    }
}
