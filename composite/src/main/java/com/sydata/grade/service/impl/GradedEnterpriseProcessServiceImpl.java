/**
 * @filename:GradedEnterpriseProcessServiceImpl 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.sydata.grade.domain.GradedEnterpriseMaterial;
import com.sydata.grade.param.GradedEnterpriseMaterialSaveParam;
import com.sydata.grade.vo.GradedEnterpriseFlowVo;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.grade.domain.GradedEnterpriseProcess;
import com.sydata.grade.mapper.GradedEnterpriseProcessMapper;
import com.sydata.grade.service.IGradedEnterpriseProcessService;
import com.sydata.grade.param.GradedEnterpriseProcessPageParam;
import com.sydata.grade.param.GradedEnterpriseProcessSaveParam;
import com.sydata.grade.vo.GradedEnterpriseProcessVo;
import com.sydata.grade.annotation.DataBindGradedEnterpriseProcess;
import com.sydata.common.api.enums.CzBzEnum;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核详情服务实现)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Service("gradedEnterpriseProcessService")
public class GradedEnterpriseProcessServiceImpl extends ServiceImpl<GradedEnterpriseProcessMapper, GradedEnterpriseProcess> implements IGradedEnterpriseProcessService {

    final static String CACHE_NAME = "composite:gradedEnterpriseProcess";

    @Resource
    private GradedEnterpriseProcessMapper gradedEnterpriseProcessMapper;

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseProcessVo> pages(GradedEnterpriseProcessPageParam pageParam) {
        Page<GradedEnterpriseProcess> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseProcess::getId, pageParam.getId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getQyid()), GradedEnterpriseProcess::getQyid, pageParam.getQyid())
                .eq(ObjectUtils.isNotEmpty(pageParam.getQyshr()), GradedEnterpriseProcess::getQyshr, pageParam.getQyshr())
                .eq(ObjectUtils.isNotEmpty(pageParam.getShjg()), GradedEnterpriseProcess::getShjg, pageParam.getShjg())
                .ge(ObjectUtils.isNotEmpty(pageParam.getBeginShsj()), GradedEnterpriseProcess::getShsj, pageParam.getBeginShsj())
                .le(ObjectUtils.isNotEmpty(pageParam.getEndShsj()), GradedEnterpriseProcess::getShsj, pageParam.getEndShsj())
                .ne(GradedEnterpriseProcess::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseProcess::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedEnterpriseProcessVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseProcessVo> list(String qyid) {
        List<GradedEnterpriseProcess> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseProcess::getQyid, qyid)
                .ne(GradedEnterpriseProcess::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseProcess::getShsj).list();
        return BeanUtils.copyToList(list, GradedEnterpriseProcessVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseProcessVo detail(String id) {
        IGradedEnterpriseProcessService gradedEnterpriseProcessService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseProcessService.getById(id), GradedEnterpriseProcessVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseProcess.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseProcessMapper);
    }

    
    @Override
    public String saveData(GradedEnterpriseProcessSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseProcess gradedEnterpriseProcess = BeanUtils.copyByClass(param, GradedEnterpriseProcess.class);
        gradedEnterpriseProcess.setId(IdUtil.simpleUUID());
        gradedEnterpriseProcess.setQyshr(loginUser.getName());
        gradedEnterpriseProcess.setShsj(LocalDateTime.now());
        gradedEnterpriseProcess.setCreateBy(loginUser.getName());
        gradedEnterpriseProcess.setUpdateBy(loginUser.getName());
        gradedEnterpriseProcess.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseProcess.setCreateTime(LocalDateTime.now());
        gradedEnterpriseProcess.setCzbz(CzBzEnum.I.getCzBz());
        super.save(gradedEnterpriseProcess);
        param.setId(gradedEnterpriseProcess.getId());
        return gradedEnterpriseProcess.getId();
    }

    
    @Override
    public Boolean saveOrUpdateBatchData(List<GradedEnterpriseProcessSaveParam> params) {
        Boolean returnStatus = true;
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedEnterpriseProcess> gradedEnterpriseProcesses = BeanUtils.copyToList(params, GradedEnterpriseProcess.class);
        if(CollectionUtil.isNotEmpty(gradedEnterpriseProcesses)){
            for (int i = 0; i < gradedEnterpriseProcesses.size(); i++) {
                GradedEnterpriseProcess gradedEnterpriseProcess = gradedEnterpriseProcesses.get(i);
                gradedEnterpriseProcess.setCreateBy(loginUser.getName());
                gradedEnterpriseProcess.setUpdateBy(loginUser.getName());
                gradedEnterpriseProcess.setUpdateTime(LocalDateTime.now());
                gradedEnterpriseProcess.setCreateTime(LocalDateTime.now());
                gradedEnterpriseProcess.setCzbz(CzBzEnum.I.getCzBz());
                returnStatus = super.saveOrUpdate(gradedEnterpriseProcess);
            }
        }
        return returnStatus;
    }

    
    @Override
    public String updateData(GradedEnterpriseProcessSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseProcess gradedEnterpriseProcess = BeanUtils.copyByClass(param, GradedEnterpriseProcess.class);
        gradedEnterpriseProcess.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseProcess.setQyshr(loginUser.getName());
        gradedEnterpriseProcess.setShsj(LocalDateTime.now());
        gradedEnterpriseProcess.setUpdateBy(loginUser.getName());
        gradedEnterpriseProcess.setUpdateTime(LocalDateTime.now());
        super.updateById(gradedEnterpriseProcess);
        param.setId(gradedEnterpriseProcess.getId());
        return gradedEnterpriseProcess.getId();
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseProcess::getId, ids)
                .set(GradedEnterpriseProcess::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseProcess::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseProcess::getUpdateTime, LocalDateTime.now())
                .update();
    }

}