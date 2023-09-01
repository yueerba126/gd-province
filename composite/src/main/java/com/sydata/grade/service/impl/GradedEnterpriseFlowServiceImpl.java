/**
 * @filename:GradedEnterpriseFlowServiceImpl 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.service.IDictService;
import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.sydata.grade.enums.GradeFlowEnum;
import com.sydata.grade.enums.GradeFlowNodeEnum;
import com.sydata.grade.enums.GradeFlowStatusEnum;
import com.sydata.grade.enums.GradeStockEnum;
import com.sydata.grade.param.GradedEnterpriseFlowInitParam;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentSaveParam;
import com.sydata.grade.service.IGradedEnterpriseReviewService;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.sydata.grade.mapper.GradedEnterpriseFlowMapper;
import com.sydata.grade.service.IGradedEnterpriseFlowService;
import com.sydata.grade.param.GradedEnterpriseFlowPageParam;
import com.sydata.grade.param.GradedEnterpriseFlowSaveParam;
import com.sydata.grade.vo.GradedEnterpriseFlowVo;
import com.sydata.grade.annotation.DataBindGradedEnterpriseFlow;
import com.sydata.common.api.enums.CzBzEnum;

/**
 * @Description:TODO(等级粮库评定管理-企业申报流程表服务实现)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Service("gradedEnterpriseFlowService")
public class GradedEnterpriseFlowServiceImpl extends ServiceImpl<GradedEnterpriseFlowMapper, GradedEnterpriseFlow> implements IGradedEnterpriseFlowService {

    final static String CACHE_NAME = "composite:gradedEnterpriseFlow";

    @Resource
    private GradedEnterpriseFlowMapper gradedEnterpriseFlowMapper;

    @Resource
    private IGradedEnterpriseReviewService gradedEnterpriseReviewService;

    @Resource
    private IDictService dictService;

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseFlowVo> pages(GradedEnterpriseFlowPageParam pageParam) {
        Page<GradedEnterpriseFlow> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseFlow::getId, pageParam.getId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getQyid()), GradedEnterpriseFlow::getQyid, pageParam.getQyid())
                .eq(ObjectUtils.isNotEmpty(pageParam.getXzqhdm()), GradedEnterpriseFlow::getXzqhdm, pageParam.getXzqhdm())
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseFlow::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedEnterpriseFlowVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseFlowVo> list(String qyid) {
        List<GradedEnterpriseFlow> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getQyid, qyid)
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByAsc(GradedEnterpriseFlow::getFlowSort).list();
        return BeanUtils.copyToList(list, GradedEnterpriseFlowVo.class);
    }

    @Override
    public List<GradedEnterpriseFlowSaveParam> listByQyid(String qyid) {
        List<GradedEnterpriseFlow> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getQyid, qyid)
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseFlow::getUpdateTime).list();
        return BeanUtils.copyToList(list, GradedEnterpriseFlowSaveParam.class);
    }

    @Override
    public List<GradedEnterpriseFlowSaveParam> pickNode1(String qyid) {
        List<GradedEnterpriseFlow> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getQyid, qyid)
                .ne(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getFlowCode, GradeFlowNodeEnum.H.getState())
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseFlow::getFlowSort).list();
        return BeanUtils.copyToList(list, GradedEnterpriseFlowSaveParam.class);
    }

    @Override
    public List<GradedEnterpriseFlowSaveParam> pickNode2(String qyid) {
        List<GradedEnterpriseFlow> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getQyid, qyid)
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getFlowCode, GradeFlowNodeEnum.H.getState())
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseFlow::getFlowSort).list();
        return BeanUtils.copyToList(list, GradedEnterpriseFlowSaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseFlowVo> verifyNotPass(String qyid) {
        List<GradedEnterpriseFlow> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseFlow::getQyid, qyid)
                .eq(GradedEnterpriseFlow::getFlowStatus, GradeFlowStatusEnum.NO_PASS.getState())
                .ne(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseFlow::getUpdateTime).list();
        return BeanUtils.copyToList(list, GradedEnterpriseFlowVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseFlowVo detail(String id) {
        IGradedEnterpriseFlowService gradedEnterpriseFlowService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseFlowService.getById(id), GradedEnterpriseFlowVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseFlow.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseFlowMapper);
    }

    
    @Override
    public String saveData(GradedEnterpriseFlowSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseFlow gradedEnterpriseFlow = BeanUtils.copyByClass(param, GradedEnterpriseFlow.class);
        gradedEnterpriseFlow.setId(IdUtil.simpleUUID());
        gradedEnterpriseFlow.setCreateBy(loginUser.getName());
        gradedEnterpriseFlow.setUpdateBy(loginUser.getName());
        gradedEnterpriseFlow.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseFlow.setCreateTime(LocalDateTime.now());
        gradedEnterpriseFlow.setCzbz(CzBzEnum.I.getCzBz());
        super.save(gradedEnterpriseFlow);
        param.setId(gradedEnterpriseFlow.getId());
        return gradedEnterpriseFlow.getId();
    }


    
    @Override
    public Boolean initData(GradedEnterpriseFlowInitParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<Dict> dictList = Lists.newArrayList();
        List<GradedEnterpriseFlow> gradedEnterpriseFlowList = Lists.newArrayList();
        int sbdj = Integer.valueOf(param.getSbdj());
        if(sbdj >= GradeStockEnum.AAA.getState()){
            dictList = dictService.listByDictType(GradeFlowEnum.PROVINCE.getState()).stream().sorted(Comparator.comparing(Dict::getDictSort)).collect(Collectors.toList());
        }else{
            dictList = dictService.listByDictType(GradeFlowEnum.CITY.getState()).stream().sorted(Comparator.comparing(Dict::getDictSort)).collect(Collectors.toList());
        }

        List<String> xzqhdmList = gradedEnterpriseReviewService.assertXzqhdm(param.getXzqhdm());

        for (int i = 0; i < dictList.size(); i++) {
            Dict dict = dictList.get(i);
            GradedEnterpriseFlow gradedEnterpriseFlow = new GradedEnterpriseFlow();
            gradedEnterpriseFlow.setId(IdUtil.simpleUUID());
            gradedEnterpriseFlow.setCreateBy(loginUser.getName());
            gradedEnterpriseFlow.setUpdateBy(loginUser.getName());
            gradedEnterpriseFlow.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseFlow.setCreateTime(LocalDateTime.now());
            gradedEnterpriseFlow.setCzbz(CzBzEnum.I.getCzBz());
            gradedEnterpriseFlow.setFlowStatus(GradeFlowStatusEnum.WAIT.getState());
            gradedEnterpriseFlow.setQyid(param.getQyid());
            gradedEnterpriseFlow.setFlowCode(dict.getDictKey());
            gradedEnterpriseFlow.setFlowSort(dict.getDictSort());
            if(i<=1){
                gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(0));
                gradedEnterpriseFlow.setFlowStatus(GradeFlowStatusEnum.PASS.getState());
            }else{
                if(i==2){
                    gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(0));
                }
                if(i==3||i==4){
                    gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(1));
                }
                if(i>4&&i<=7){
                    if(sbdj >= GradeStockEnum.AAA.getState()){
                        gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(2));
                    }else{
                        gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(1));
                    }
                }
                gradedEnterpriseFlow.setFlowStatus(null);
            }
            gradedEnterpriseFlowList.add(gradedEnterpriseFlow);
        }
        return super.saveBatch(gradedEnterpriseFlowList);
    }

    
    @Override
    public List<GradedEnterpriseFlow> getInitData(GradedEnterpriseFlowInitParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<Dict> dictList = Lists.newArrayList();
        List<GradedEnterpriseFlow> gradedEnterpriseFlowList = Lists.newArrayList();
        int sbdj = Integer.valueOf(param.getSbdj());
        if(sbdj >= GradeStockEnum.AAA.getState()){
            dictList = dictService.listByDictType(GradeFlowEnum.PROVINCE.getState()).stream().sorted(Comparator.comparing(Dict::getDictSort)).collect(Collectors.toList());
        }else{
            dictList = dictService.listByDictType(GradeFlowEnum.CITY.getState()).stream().sorted(Comparator.comparing(Dict::getDictSort)).collect(Collectors.toList());
        }

        List<String> xzqhdmList = gradedEnterpriseReviewService.assertXzqhdm(param.getXzqhdm());

        for (int i = 0; i < dictList.size(); i++) {
            Dict dict = dictList.get(i);
            GradedEnterpriseFlow gradedEnterpriseFlow = new GradedEnterpriseFlow();
            gradedEnterpriseFlow.setId(IdUtil.simpleUUID());
            gradedEnterpriseFlow.setCreateBy(loginUser.getName());
            gradedEnterpriseFlow.setUpdateBy(loginUser.getName());
            gradedEnterpriseFlow.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseFlow.setCreateTime(LocalDateTime.now());
            gradedEnterpriseFlow.setCzbz(CzBzEnum.I.getCzBz());
            gradedEnterpriseFlow.setFlowStatus(GradeFlowStatusEnum.WAIT.getState());
            gradedEnterpriseFlow.setQyid(param.getQyid());
            gradedEnterpriseFlow.setFlowCode(dict.getDictKey());
            gradedEnterpriseFlow.setFlowSort(dict.getDictSort());
            if(i<=1){
                gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(0));
            }else{
                if(i==2){
                    gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(0));
                }
                if(i==3||i==4){
                    gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(1));
                }
                if(i>4&&i<=7){
                    if(sbdj >= GradeStockEnum.AAA.getState()){
                        gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(2));
                    }else{
                        gradedEnterpriseFlow.setXzqhdm(xzqhdmList.get(1));
                    }
                }
                gradedEnterpriseFlow.setFlowStatus(null);
            }
            gradedEnterpriseFlowList.add(gradedEnterpriseFlow);
        }
        return gradedEnterpriseFlowList;
    }

    
    @Override
    public Boolean saveOrUpdateBatchData(List<GradedEnterpriseFlowSaveParam> params) {
        Boolean returnStatus = true;
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedEnterpriseFlow> gradedEnterpriseFlows = BeanUtils.copyToList(params, GradedEnterpriseFlow.class);
        if(CollectionUtil.isNotEmpty(gradedEnterpriseFlows)){
            for (int i = 0; i < gradedEnterpriseFlows.size(); i++) {
                GradedEnterpriseFlow gradedEnterpriseFlow = gradedEnterpriseFlows.get(i);
                gradedEnterpriseFlow.setCreateBy(loginUser.getName());
                gradedEnterpriseFlow.setUpdateBy(loginUser.getName());
                gradedEnterpriseFlow.setUpdateTime(LocalDateTime.now());
                gradedEnterpriseFlow.setCreateTime(LocalDateTime.now());
                gradedEnterpriseFlow.setCzbz(CzBzEnum.I.getCzBz());
                returnStatus = super.saveOrUpdate(gradedEnterpriseFlow);
            }
        }
        return returnStatus;
    }

    
    @Override
    public String updateData(GradedEnterpriseFlowSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseFlow gradedEnterpriseFlow = BeanUtils.copyByClass(param, GradedEnterpriseFlow.class);
        gradedEnterpriseFlow.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseFlow.setUpdateBy(loginUser.getName());
        gradedEnterpriseFlow.setUpdateTime(LocalDateTime.now());
        super.updateById(gradedEnterpriseFlow);
        param.setId(gradedEnterpriseFlow.getId());
        return gradedEnterpriseFlow.getId();
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseFlow::getId, ids)
                .set(GradedEnterpriseFlow::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseFlow::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseFlow::getUpdateTime, LocalDateTime.now())
                .update();
    }

}