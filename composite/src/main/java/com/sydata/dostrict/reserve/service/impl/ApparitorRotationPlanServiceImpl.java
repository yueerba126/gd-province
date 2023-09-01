package com.sydata.dostrict.reserve.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.composite.annotation.DataBindApparitorRotationPlan;
import com.sydata.dostrict.reserve.domain.ApparitorReserveScale;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlan;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlanDtl;
import com.sydata.dostrict.reserve.mapper.ApparitorRotationPlanDtlMapper;
import com.sydata.dostrict.reserve.mapper.ApparitorRotationPlanMapper;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlSaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorRotationPlanDtlService;
import com.sydata.dostrict.reserve.service.IApparitorRotationPlanService;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanDtlVo;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.framework.util.StringUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import nonapi.io.github.classgraph.utils.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-轮换计划主表Service业务层处理
 * @author: lzq
 * @create: 2023-04-26 18:47
 */
@CacheConfig(cacheNames = ApparitorRotationPlanServiceImpl.CACHE_NAME)
@Service("apparitorRotationPlanServiceImpl")
public class ApparitorRotationPlanServiceImpl
        extends ServiceImpl<ApparitorRotationPlanMapper, ApparitorRotationPlan>
        implements IApparitorRotationPlanService {

    final static String CACHE_NAME = "composite:apparitorrotationPlan";

    @Resource
    private ApparitorRotationPlanMapper apparitorRotationPlanMapper;

    @Resource
    private ApparitorRotationPlanDtlMapper apparitorRotationPlanDtlMapper;

    @Resource
    private IApparitorRotationPlanService apparitorRotationPlanService;

    @Resource
    private IApparitorRotationPlanDtlService apparitorRotationPlanDtlService;

    @Override
    @DataBindFieldConvert
    public Page<ApparitorRotationPlanVo> pages(ApparitorRotationPlanPageParam pageParam) {
        Page<ApparitorRotationPlan> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ApparitorRotationPlan::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getJhxddw()), ApparitorRotationPlan::getJhxddw, pageParam.getJhxddw())
                .eq(isNotEmpty(pageParam.getJhnd()), ApparitorRotationPlan::getJhnd, pageParam.getJhnd())
                .like(isNotEmpty(pageParam.getJhmc()), ApparitorRotationPlan::getJhmc, pageParam.getJhmc())
                .ge(isNotEmpty(pageParam.getBeginJhxdsj()), ApparitorRotationPlan::getJhxdsj, pageParam.getBeginJhxdsj())
                .le(isNotEmpty(pageParam.getEndJhxdsj()), ApparitorRotationPlan::getJhxdsj, pageParam.getEndJhxdsj())
                .likeRight(isNotEmpty(pageParam.getLhjhdh()), ApparitorRotationPlan::getLhjhdh, pageParam.getLhjhdh())
                .likeRight(isNotEmpty(pageParam.getJhwh()), ApparitorRotationPlan::getJhwh, pageParam.getJhwh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ApparitorRotationPlan::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ApparitorRotationPlan::getUpdateTime, pageParam.getEndUpdateTime())
                .ne(ApparitorRotationPlan::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorRotationPlan::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorRotationPlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorRotationPlanVo detail(String id) {
        IApparitorRotationPlanService rotationPlanService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(rotationPlanService.getById(id), ApparitorRotationPlanVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorRotationPlanMasterSlaveVo detailMasterSlave(String id) {
        ApparitorRotationPlan apparitorRotationPlan = apparitorRotationPlanService.getById(id);
        ApparitorRotationPlanMasterSlaveVo apparitorRotationPlanMasterSlaveVo = BeanUtils.copyByClass(apparitorRotationPlan,
                ApparitorRotationPlanMasterSlaveVo.class);

        QueryWrapper<ApparitorRotationPlanDtl> apparitorRotationPlanDtlQueryWrapper = new QueryWrapper();
        apparitorRotationPlanDtlQueryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(apparitorRotationPlanMasterSlaveVo.getLhjhdh()),
                        ApparitorRotationPlanDtl::getLhjhdh,apparitorRotationPlanMasterSlaveVo.getLhjhdh())
                .ne(ApparitorRotationPlanDtl::getCzbz,CzBzEnum.D);

        List<ApparitorRotationPlanDtl> apparitorRotationPlanDtlList = apparitorRotationPlanDtlMapper.selectList(apparitorRotationPlanDtlQueryWrapper);
        List<ApparitorRotationPlanDtlVo> apparitorRotationPlanDtlVoList = BeanUtils.copyToList(apparitorRotationPlanDtlList, ApparitorRotationPlanDtlVo.class);

        apparitorRotationPlanMasterSlaveVo.setItemList(apparitorRotationPlanDtlVoList);
        return apparitorRotationPlanMasterSlaveVo;
    }

    @Override
    public String generate(String jhxddw, String jhnd) {
        // 生成自增编码
        String key = new StringJoiner(COLON).add(CACHE_NAME).add(jhxddw).add(jhnd).toString();
        String lhjhdh = GenerateNoUtil.generate(key, 3);

        // 去掉拼接的属性
        return lhjhdh.substring(CACHE_NAME.length()).replace(COLON, EMPTY);
    }

    @DataBindService(strategy = DataBindApparitorRotationPlan.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorRotationPlanMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorRotationPlanSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorRotationPlan apparitorRotationPlan = BeanUtils.copyByClass(param, ApparitorRotationPlan.class);
        apparitorRotationPlan.setId(IdUtil.simpleUUID());
        apparitorRotationPlan.setCzbz(CzBzEnum.I.getCzBz());
        apparitorRotationPlan.setCreateBy(loginUser.getName());
        apparitorRotationPlan.setUpdateBy(loginUser.getName());
        apparitorRotationPlan.setUpdateTime(LocalDateTime.now());
        apparitorRotationPlan.setCreateTime(LocalDateTime.now());
        apparitorRotationPlan.setZhgxsj(LocalDateTime.now());
        super.save(apparitorRotationPlan);
        param.setId(apparitorRotationPlan.getId());
        return apparitorRotationPlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveOrUpdateMasterSlaveData(ApparitorRotationPlanMasterSlaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 新增或是更新主表
        ApparitorRotationPlanSaveParam apparitorRotationPlanSaveParam = BeanUtils.copyByClass(param, ApparitorRotationPlanMasterSlaveParam.class);
        if(StringUtils.isNotEmpty(param.getId())){
            updateData(apparitorRotationPlanSaveParam);
        }else{
            saveData(apparitorRotationPlanSaveParam);
        }

        List<ApparitorRotationPlanDtlSaveParam> apparitorRotationPlanDtlSaveParams = BeanUtils.copyToList(param.getItemList(),ApparitorRotationPlanDtlSaveParam.class);
        List<String> ids = apparitorRotationPlanDtlSaveParams.stream().filter(e->StringUtils.isNotEmpty(e.getId())).map(ApparitorRotationPlanDtlSaveParam::getId).collect(Collectors.toList());
        // 删除库中不包含在明细列表中的数据
        if(CollectionUtil.isNotEmpty(ids)){
            apparitorRotationPlanDtlService.removeDataNotInList(ids);
        }
        // 新增或是更新从表
        for (int i = 0; i < apparitorRotationPlanDtlSaveParams.size(); i++) {
            ApparitorRotationPlanDtlSaveParam apparitorRotationPlanDtlSaveParam = apparitorRotationPlanDtlSaveParams.get(i);
            if(StringUtils.isNotEmpty(apparitorRotationPlanDtlSaveParam.getId())){
                apparitorRotationPlanDtlService.updateData(apparitorRotationPlanDtlSaveParam);
            }else{
                apparitorRotationPlanDtlService.saveData(apparitorRotationPlanDtlSaveParam);
            }
        }
        return param.getLhjhdh();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorRotationPlanSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorRotationPlan apparitorRotationPlan = BeanUtils.copyByClass(param, ApparitorRotationPlan.class);
        apparitorRotationPlan.setCzbz(CzBzEnum.U.getCzBz());
        apparitorRotationPlan.setUpdateBy(loginUser.getName());
        apparitorRotationPlan.setUpdateTime(LocalDateTime.now());
        apparitorRotationPlan.setZhgxsj(LocalDateTime.now());
        super.updateById(apparitorRotationPlan);
        param.setId(apparitorRotationPlan.getId());
        return apparitorRotationPlan.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorRotationPlan::getId, ids)
                .set(ApparitorRotationPlan::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorRotationPlan::getUpdateBy ,loginUser.getName())
                .set(ApparitorRotationPlan::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorRotationPlan::getZhgxsj ,LocalDateTime.now())
                .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeDataNotInList(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .notIn(ApparitorRotationPlan::getId, ids)
                .set(ApparitorRotationPlan::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorRotationPlan::getUpdateBy ,loginUser.getName())
                .set(ApparitorRotationPlan::getUpdateTime ,LocalDateTime.now())
                .set(ApparitorRotationPlan::getZhgxsj ,LocalDateTime.now())
                .update();
    }
}
